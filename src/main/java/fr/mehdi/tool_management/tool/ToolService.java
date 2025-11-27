package fr.mehdi.tool_management.tool;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import fr.mehdi.tool_management.category.Category;
import fr.mehdi.tool_management.category.CategoryService;
import fr.mehdi.tool_management.filters.FiltersApplied;
import fr.mehdi.tool_management.filters.PageDto;
import fr.mehdi.tool_management.tool.dtos.ToolDto;
import fr.mehdi.tool_management.tool.filters.ToolFilter;
import fr.mehdi.tool_management.utils.UtilList;
import fr.mehdi.tool_management.utils.UtilMapper;

@Service
public class ToolService {

    @Autowired
    private ToolRepository      toolRepository;

    @Autowired
    private CategoryService     categoryService;

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
    
}
