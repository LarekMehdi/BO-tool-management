package fr.mehdi.tool_management.tool.dtos;

import java.math.BigDecimal;

import fr.mehdi.tool_management.constantes.Department;
import fr.mehdi.tool_management.constantes.ToolStatus;
import fr.mehdi.tool_management.utils.UtilEntity;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateToolDto {

    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String          name;

    private String          description;

    @Size(max = 100, message = "Vendor must be shorter than 100 characters")
    private String          vendor;

    @Pattern(
        regexp = "^(https?://).+$",
        message = "Website URL must be a valid HTTP or HTTPS URL"
    )
    private String          websiteUrl;

    @DecimalMin(value = "0.0", inclusive = true, message = "Monthly cost cannot be negative")
    @Digits(integer = 10, fraction = 2, message = "Monthly cost must have max 2 decimal places")
    private BigDecimal      monthlyCost;

    @Min(value = 0, message = "Active users count cannot be negative")
    private Integer         activeUsersCount;

    private Department      ownerDepartment;

    private ToolStatus      status;

    private Integer         categoryId;

    
    public boolean hasName() { return !UtilEntity.isEmpty(this.name); }
    public boolean hasDescription() { return !UtilEntity.isEmpty(this.description); }
    public boolean hasVendor() { return !UtilEntity.isEmpty(this.vendor); }
    public boolean hasWebsiteUrl() { return!UtilEntity.isEmpty(this.websiteUrl); }
    public boolean hasMonthlyCost() { return !UtilEntity.isEmpty(this.monthlyCost); }
    public boolean hasActiveUsersCount() { return !UtilEntity.isEmpty(this.activeUsersCount); }
    public boolean hasOwnerDepartment() { return !UtilEntity.isEmpty(this.ownerDepartment); }
    public boolean hasStatus() { return !UtilEntity.isEmpty(this.status); }
    public boolean hasCategoryId() { return !UtilEntity.isEmpty(this.categoryId); }
}