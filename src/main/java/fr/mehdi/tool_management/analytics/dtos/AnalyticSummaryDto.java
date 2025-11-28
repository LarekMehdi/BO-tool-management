package fr.mehdi.tool_management.analytics.dtos;

import java.math.BigDecimal;

import fr.mehdi.tool_management.constantes.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticSummaryDto {
    
    private BigDecimal          totalCompanyCost;
    private Integer             departmentsCount;
    private Department          mostExpensiveDepartment;
}
