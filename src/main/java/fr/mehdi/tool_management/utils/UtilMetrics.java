package fr.mehdi.tool_management.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    // TODO: refactoriser
    public static List<AnalyticItemDto> buildAnalyticItems(Map<Department, List<Tool>> toolsByDepartment, Map<Integer, List<UserToolAccess>> accessesByToolId) {
        List<AnalyticItemDto> dtos = new ArrayList<>();

        // companyTotalCost
        BigDecimal companyTotalCost = toolsByDepartment.values().stream().flatMap(list -> list.stream()).map(tool -> {
            List<UserToolAccess> accessList = accessesByToolId.get(tool.getId());
            long totalUser = accessList == null ? 0 :
                accessList.stream()
                    .filter(a -> a.isStatusActive() && tool.getId().equals(a.getToolId()))
                    .count();

            return tool.getMonthlyCost().multiply(BigDecimal.valueOf(totalUser));
        }).reduce(BigDecimal.ZERO, BigDecimal::add);


        toolsByDepartment.forEach((department, tools) -> {

            // departmentTotalCost
            BigDecimal departmentTotalCost = tools.stream().map(tool -> {
                List<UserToolAccess> accessList = accessesByToolId.get(tool.getId());
                long totalUser = accessList == null ? 0 :
                        accessList.stream()
                                .filter(a -> a.isStatusActive() && tool.getId().equals(a.getToolId()))
                                .count();

                return tool.getMonthlyCost().multiply(BigDecimal.valueOf(totalUser));
            }).reduce(BigDecimal.ZERO, BigDecimal::add);

            long totalUsers = tools.stream()
                .mapToLong(tool -> {
                    List<UserToolAccess> accessList = accessesByToolId.get(tool.getId());
                    return accessList == null ? 0 :
                        accessList.stream()
                            .filter(a -> a.isStatusActive() && tool.getId().equals(a.getToolId()))
                            .count();
                })
                .sum();

            // TODO: utilNumber
            int toolCount = tools.size();
            BigDecimal avgCostPerTool = toolCount == 0 ? BigDecimal.ZERO :
                    departmentTotalCost.divide(BigDecimal.valueOf(toolCount), 2, RoundingMode.HALF_UP);

            BigDecimal costPercentage = companyTotalCost.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO :
                    departmentTotalCost.multiply(BigDecimal.valueOf(100)).divide(companyTotalCost, 2, RoundingMode.HALF_UP);

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
    
}
