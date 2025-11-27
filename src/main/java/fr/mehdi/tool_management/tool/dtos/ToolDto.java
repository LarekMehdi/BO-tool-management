package fr.mehdi.tool_management.tool.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import fr.mehdi.tool_management.category.Category;
import fr.mehdi.tool_management.constantes.Department;
import fr.mehdi.tool_management.constantes.ToolStatus;
import fr.mehdi.tool_management.tool.Tool;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ToolDto {

    private Integer         id;
    private String          name;
    private String          description;
    private String          vendor;
    private String          category;
    private BigDecimal      monthlyCost;
    private Department      ownerDepartment;
    private ToolStatus      status;
    private String          websiteUrl;
    private Integer         activeUsersCount;
    private LocalDateTime   createdAt;
    

    public ToolDto(Tool tool, Category category) {
        this.id = tool.getId();
        this.name = tool.getName();
        this.description = tool.getDescription();
        this.vendor = tool.getVendor();
        this.category = category.getName();
        this.monthlyCost = tool.getMonthlyCost();
        this.ownerDepartment = tool.getOwnerDepartment();
        this.status = tool.getStatus();
        this.websiteUrl = tool.getWebsiteUrl();
        this.activeUsersCount = tool.getActiveUsersCount();
        this.createdAt = tool.getCreatedAt();
    }
    
}
