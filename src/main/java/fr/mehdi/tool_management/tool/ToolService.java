package fr.mehdi.tool_management.tool;


import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;

import fr.mehdi.tool_management.category.Category;
import fr.mehdi.tool_management.category.CategoryService;
import fr.mehdi.tool_management.filters.FiltersApplied;
import fr.mehdi.tool_management.filters.PageDto;
import fr.mehdi.tool_management.tool.dtos.CreateToolDto;
import fr.mehdi.tool_management.tool.dtos.ToolDetailsDto;
import fr.mehdi.tool_management.tool.dtos.ToolDto;
import fr.mehdi.tool_management.tool.dtos.UpdateToolDto;
import fr.mehdi.tool_management.tool.filters.ToolFilter;
import fr.mehdi.tool_management.usageLog.UsageLog;
import fr.mehdi.tool_management.usageLog.UsageLogService;
import fr.mehdi.tool_management.usageLog.dtos.UsageMetricsDto;
import fr.mehdi.tool_management.usageLog.dtos.UsageStatsDto;
import fr.mehdi.tool_management.usageLog.filters.UsageLogFilter;
import fr.mehdi.tool_management.utils.UtilDate;
import fr.mehdi.tool_management.utils.UtilList;
import fr.mehdi.tool_management.utils.UtilMapper;
import fr.mehdi.tool_management.utils.UtilMetrics;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class ToolService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ToolRepository      toolRepository;

    @Autowired
    private CategoryService     categoryService;

    @Autowired
    private UsageLogService     usageLogService;

    /** FIND ALL **/

    public PageDto<ToolDto> findAll(ToolFilter filter) {
        // récupération des tools avec le filtre
        filter.initGenericFilterIfNeeded();
        Pageable pageable = filter.toPageable();
        Specification<Tool> specification = filter.toSpecification();
        Page<Tool> tools = this.toolRepository.findAll(specification, pageable);

        // Nombre total après filtrage (sans pagination)
        long filtered = this.toolRepository.count(specification);

        // récupération des category liées sans lazy loading (pour optimisation)
        List<Tool> toolList = tools.getContent();
        List<Integer> categoryIds = UtilList.collect(toolList, Tool::getCategoryId);
        List<Category> categories = this.categoryService.findAllByIds(categoryIds);
        Map<Integer, Category> categoryById = categories.stream().filter(Category::hasId).collect(Collectors.toMap(Category::getId, Function.identity()));

        FiltersApplied filtersApplied = filter.buildFiltersApplied();

        // construction du résultat
        List<ToolDto> toolDtos = UtilMapper.mapToolListToDtos(tools.getContent(), categoryById);
        PageDto<ToolDto> result = new PageDto<>(toolDtos, tools.getTotalElements(), filtersApplied, filtered);
        return result;

    }

    /** FIND **/

    public Tool findById(Integer id) {
        Optional<Tool> optTool = this.toolRepository.findById(id);
        if (!optTool.isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No tool found with id " + id); 
        return optTool.get();
    }

    public ToolDetailsDto findDetailsById(Integer id) {

        // récupération du tool
        Optional<Tool> optTool = this.toolRepository.findById(id);
        // TODO: NotFoundException
        if (!optTool.isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No tool found with id " + id);

        Tool tool = optTool.get();
        Category category = tool.getCategory();

        // récupération des logs   
        LocalDateTime thirtyDaysAgo = UtilDate.removeDaysFromNow(30);
        UsageLogFilter filter = new UsageLogFilter(id, thirtyDaysAgo);
        List<UsageLog> usageLogs = this.usageLogService.findAll(filter);

        // calcul des stats
        int avgMinutes = UtilMetrics.computeAvgSessionMinutes(usageLogs);
        int totalSession = usageLogs.size();
        
        // construction des metrics
        UsageStatsDto statsDto = new UsageStatsDto(totalSession, avgMinutes);
        UsageMetricsDto usageMetrics = new UsageMetricsDto(statsDto);

        // construction du résultat final
        ToolDetailsDto dto = new ToolDetailsDto(tool, category, usageMetrics);

        return dto;
        
    }

    /** COUNT **/

    public int countByName(String name) {
        return this.toolRepository.countByName(name);
    }

    /** UPDATE **/

    @Transactional
    public ToolDto update(UpdateToolDto dto, Integer id) {
        // Vérifier que le tool existe
        Tool existingTool = this.findById(id);
        
        // name unique
        if (dto.hasName() && !dto.getName().equals(existingTool.getName())) {
            int nameExist = this.toolRepository.countByName(dto.getName());
            if (nameExist > 0) throw new ResponseStatusException(HttpStatus.CONFLICT, "Tool name already exist [" + dto.getName() + "]");
        }
        
        // categorie existe
        Category category = existingTool.getCategory();
        if (dto.hasCategoryId() && !dto.getCategoryId().equals(existingTool.getCategoryId())) {
            category = this.categoryService.findById(dto.getCategoryId());
        }
        
        // Update via repository custom à cause de l'enum
        this.toolRepository.updateToolWithEnumCast(id, dto);

        // maj du tool
        entityManager.flush();
        entityManager.refresh(existingTool);

        return new ToolDto(existingTool, category);
    }

    /** CREATE **/

    @Transactional
    public ToolDto create(CreateToolDto dto) {
        // name unique
        // TODO: catcher erreur sql plutot que de vérifier avant
        int nameExist = this.toolRepository.countByName(dto.getName());
        if (nameExist > 0) throw new ResponseStatusException(HttpStatus.CONFLICT, "Tool name already exist [" + dto.getName() + "]");

        Category category = this.categoryService.findById(dto.getCategoryId());

        // Insertion via repository custom à cause de l'enum
        Integer toolId = this.toolRepository.insertToolWithEnumCast(dto);

        // Récupérer l'objet créé
        Tool tool = this.toolRepository.findById(toolId).orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Tool created but not found"));

        return new ToolDto(tool, category);
    }
    
}
