package fr.mehdi.tool_management.analytics;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.mehdi.tool_management.analytics.dtos.AnalyticDto;
import fr.mehdi.tool_management.analytics.dtos.AnalyticItemDto;
import fr.mehdi.tool_management.analytics.dtos.AnalyticSummaryDto;
import fr.mehdi.tool_management.analytics.filters.AnalyticFilter;
import fr.mehdi.tool_management.constantes.Department;
import fr.mehdi.tool_management.tool.Tool;
import fr.mehdi.tool_management.tool.ToolService;
import fr.mehdi.tool_management.userToolAccess.UserToolAccess;
import fr.mehdi.tool_management.userToolAccess.UserToolAccessService;
import fr.mehdi.tool_management.utils.UtilList;
import fr.mehdi.tool_management.utils.UtilMetrics;

@Service
public class AnalyticService {

    @Autowired
    private ToolService                 toolService;

    @Autowired
    private UserToolAccessService       userToolAccessService;


    
    /** FIND ALL **/

    public AnalyticDto findDepartmentCost(AnalyticFilter filter) {
        
        // récupération des données
        List<Tool> tools = this.toolService.findAll();
        List<UserToolAccess> access = this.userToolAccessService.findAll();

        // organisation des données
        Map<Department, List<Tool>> toolsByDepartment = tools.stream().filter(Tool::hasOwnerDepartment).collect(Collectors.groupingBy(Tool::getOwnerDepartment));
        Map<Integer, List<UserToolAccess>> accessByToolId = access.stream().filter(UserToolAccess::hasToolId).collect(Collectors.groupingBy(UserToolAccess::getToolId));

        // calcul des metrics
        List<AnalyticItemDto> items = UtilMetrics.buildAnalyticItems(toolsByDepartment, accessByToolId);

        // tris des résultats
        if (filter.hasSortBy())  UtilList.sortByField(items, filter.getSortBy(), filter.isOrderAsc());

        // calcul du summary
        BigDecimal totalCompanyCosts = UtilList.collect(items, AnalyticItemDto::getTotalCost).stream().filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
        int departmentCount = items.size();
        Department mostExpensiveDepartment = items.stream()
            .max(Comparator.comparing(AnalyticItemDto::getTotalCost).thenComparing(item -> item.getDepartment().name()))
            .map(AnalyticItemDto::getDepartment)
            .orElse(null);

        AnalyticSummaryDto summary = new AnalyticSummaryDto(totalCompanyCosts, departmentCount, mostExpensiveDepartment);

        return new AnalyticDto(items, summary);
    }
}
