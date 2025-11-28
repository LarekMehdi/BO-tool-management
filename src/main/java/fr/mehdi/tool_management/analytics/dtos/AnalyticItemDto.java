package fr.mehdi.tool_management.analytics.dtos;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import fr.mehdi.tool_management.constantes.Department;
import fr.mehdi.tool_management.tool.Tool;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
    "department",
    "totalCost",
    "toolsCount",
    "totalUsers",
    "averageCostPerTool",
    "costPercentage"
})
public class AnalyticItemDto {
    
    private Department      department;
    private BigDecimal      totalCost;
    private Integer         toolsCount;
    private Integer         totalUsers;
    private BigDecimal      averageCostPerTool;
    private BigDecimal      costPercentage;

    public AnalyticItemDto(Tool tool) {
        this.department = tool.getOwnerDepartment();
    }
}
