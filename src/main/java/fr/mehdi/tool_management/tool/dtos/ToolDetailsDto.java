package fr.mehdi.tool_management.tool.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import fr.mehdi.tool_management.category.Category;
import fr.mehdi.tool_management.constantes.Department;
import fr.mehdi.tool_management.constantes.ToolStatus;
import fr.mehdi.tool_management.tool.Tool;
import fr.mehdi.tool_management.usageLog.dtos.UsageMetricsDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
    "id",
    "name",
    "description",
    "vendor",
    "websiteUrl",
    "category",
    "monthlyCost",
    "ownerDepartment",
    "status",
    "activeUsersCount",
    "totalMonthlyCost",
    "createdAt",
    "updatedAt",
    "usageMetrics"
})
public class ToolDetailsDto {
    
    private Integer         id;
    private String          name;
    private String          description;
    private String          vendor;
    private String          websiteUrl;
    private String          category;
    private BigDecimal      monthlyCost;        
    private Department      ownerDepartment;
    private ToolStatus      status;
    private Integer         activeUsersCount;
    private BigDecimal      totalMonthlyCost;       // monthlyCost * active users? monthlyCost depuis createdAt?
    private LocalDateTime   createdAt;
    private LocalDateTime   updatedAt;
    private UsageMetricsDto usageMetrics;

    public ToolDetailsDto(Tool tool, Category category, UsageMetricsDto usageMetrics) {
        this.id = tool.getId();
        this.name = tool.getName();
        this.description = tool.getDescription();
        this.vendor = tool.getVendor();
        this.websiteUrl = tool.getWebsiteUrl();
        this.category = category.getName();
        this.monthlyCost = tool.getMonthlyCost();
        this.ownerDepartment = tool.getOwnerDepartment();
        this.status = tool.getStatus();
        this.activeUsersCount = tool.getActiveUsersCount();
        this.totalMonthlyCost = this.computeMonthlyCost();
        this.createdAt = tool.getCreatedAt();
        this.updatedAt = tool.getUpdatedAt();
        this.usageMetrics = usageMetrics;
    }

    /** MONTHLY COST **/

    public BigDecimal computeMonthlyCost() {
        if (this.activeUsersCount == null || this.monthlyCost == null) {
            return BigDecimal.ZERO;
        }
        return this.monthlyCost.multiply(BigDecimal.valueOf(this.activeUsersCount));
    }

}
