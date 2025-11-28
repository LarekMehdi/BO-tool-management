package fr.mehdi.tool_management.utils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import fr.mehdi.tool_management.analytics.dtos.AnalyticItemDto;
import fr.mehdi.tool_management.constantes.Department;
import fr.mehdi.tool_management.tool.Tool;
import fr.mehdi.tool_management.usageLog.UsageLog;
import fr.mehdi.tool_management.userToolAccess.UserToolAccess;

public abstract class UtilMetrics {

    /** SESSION **/

    public static int computeAvgSessionMinutes(List<UsageLog> logs) {
        return (int) logs.stream().map(UsageLog::getUsageMinutes).filter(Objects::nonNull).mapToInt(Integer::intValue).average().orElse(0);
    }

    public static void keepSessionsSince(List<UsageLog> logs, LocalDateTime limit) {
        LocalDate date = limit.toLocalDate();
        logs.removeIf(log -> !log.getSessionDate().isAfter(date));
    }

    /** ANALYTIC ITEM **/

    public static List<AnalyticItemDto> buildAnalyticItems(Map<Department, List<Tool>> toolsByDepartment, Map<Integer, List<UserToolAccess>> accessesByToolId) {
        List<AnalyticItemDto> dtos = new ArrayList<>();

        BigDecimal companyTotalCost = __computeCompanyTotalCost(toolsByDepartment, accessesByToolId);

        toolsByDepartment.forEach((department, tools) -> {
            // calculs des metrics
            BigDecimal departmentTotalCost = __computeDepartmentTotalCost(tools, accessesByToolId);
            long totalUsers = tools.stream().mapToLong(tool -> __countActiveUsers(tool, accessesByToolId)).sum();
            int toolCount = tools.size();
            BigDecimal avgCostPerTool = UtilNumber.divideBigDecimal(departmentTotalCost, toolCount);
            BigDecimal costPercentage = UtilNumber.divideBigDecimal(UtilNumber.multiplyBigDecimal(departmentTotalCost, BigDecimal.valueOf(100)),companyTotalCost);

            // construction dto
            AnalyticItemDto dto = new AnalyticItemDto();
            dto.setDepartment(department);
            dto.setTotalUsers((int) totalUsers);
            dto.setTotalCost(departmentTotalCost);
            dto.setAverageCostPerTool(avgCostPerTool);
            dto.setCostPercentage(costPercentage);
            dto.setToolsCount(toolCount);

            dtos.add(dto);
            
        });

        return dtos;
    }

    /** ACTIVE USERS **/

    private static long __countActiveUsers(Tool tool, Map<Integer, List<UserToolAccess>> accessesByToolId) {
        List<UserToolAccess> accessList = accessesByToolId.get(tool.getId());
        if (UtilEntity.isEmpty(accessList)) return 0;

        return accessList.stream().filter(a -> a.isStatusActive() && Objects.equals(tool.getId(), a.getToolId())).count();
    }

    /** TOTAL COST **/

    private static BigDecimal __computeToolTotalCost(Tool tool, Map<Integer, List<UserToolAccess>> accessesByToolId) {
        long totalUsers = __countActiveUsers(tool, accessesByToolId);
        return UtilNumber.multiplyBigDecimal(tool.getMonthlyCost(), (int) totalUsers);
    }

    /** COMPANY TOTAL COST **/

    private static BigDecimal __computeCompanyTotalCost(Map<Department, List<Tool>> toolsByDepartment, Map<Integer, List<UserToolAccess>> accessesByToolId) {
        return toolsByDepartment.values().stream()
            .flatMap(List::stream) // liste de liste => liste
            .map(tool -> __computeToolTotalCost(tool, accessesByToolId))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /** DEPARTMENT TOTAL COST **/

    private static BigDecimal __computeDepartmentTotalCost(List<Tool> tools, Map<Integer, List<UserToolAccess>> accessesByToolId) {
        return tools.stream().map(tool -> __computeToolTotalCost(tool, accessesByToolId))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
}
