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


            for (Tool tool : tools) {
                if (accessesByToolId.containsKey(tool.getId())) {
                    List<UserToolAccess> accessList = accessesByToolId.get(tool.getId());

                    AnalyticItemDto dto = new AnalyticItemDto(tool);
                    long totalUser = accessList.stream().filter((a) -> a.isStatusActive() && Objects.equals(tool.getId(), a.getToolId())).count();
                    BigDecimal totalCost = tool.getMonthlyCost().multiply(BigDecimal.valueOf(totalUser));
                    int toolCount = tools.size();

                    // TODO: utilNumber
                    // avg = total_cost / tools_count (arrondi a deux deciml)
                    BigDecimal avgCostPerTool = totalCost.divide(BigDecimal.valueOf(toolCount), 2, RoundingMode.HALF_UP);

                    // cost_percentage = (département.total_cost / company.total_cost) * 100 
           
                    BigDecimal costPercentage = companyTotalCost.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : departmentTotalCost.multiply(BigDecimal.valueOf(100)).divide(companyTotalCost, 2, RoundingMode.HALF_UP);

                    dto.setTotalUsers((int) totalUser);
                    dto.setTotalCost(totalCost);
                    dto.setAverageCostPerTool(avgCostPerTool);
                    dto.setCostPercentage(costPercentage);

                    dtos.add(dto);
                }
            }
        });

        return dtos;
    }
    
}
