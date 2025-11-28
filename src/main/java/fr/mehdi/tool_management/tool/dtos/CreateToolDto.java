package fr.mehdi.tool_management.tool.dtos;

import java.math.BigDecimal;

import fr.mehdi.tool_management.constantes.Department;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateToolDto {
    
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String          name;

    private String          description;

    @NotBlank(message = "vendor is required")
    @Size(max = 100, message = "Vendor must be shorter than 100 characters")
    private String          vendor;

    @Pattern(
        regexp = "^(https?://).+$",
        message = "Website URL must be a valid HTTP or HTTPS URL"
    )
    private String          websiteUrl;
    
    @NotNull(message = "Category id is required")
    @Positive(message = "Category id must be a positive number")
    private Integer         categoryId;

    @NotNull(message = "Monthly cost is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Monthly cost cannot be negative")
    @Digits(integer = 10, fraction = 2, message = "Monthly cost must have max 2 decimal places")
    private BigDecimal      monthlyCost;

    @NotNull(message = "Owner department is required")
    private Department          ownerDepartment;            
}
