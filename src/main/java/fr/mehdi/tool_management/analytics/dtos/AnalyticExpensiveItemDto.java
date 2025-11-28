package fr.mehdi.tool_management.analytics.dtos;

import java.math.BigDecimal;

import fr.mehdi.tool_management.constantes.Department;
import fr.mehdi.tool_management.constantes.EfficiencyRating;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticExpensiveItemDto {
    
    private Integer             id;
    private String              name;
    private BigDecimal          monthlyCost;
    private Integer             activeUsersCount;
    private Department          department;
    private String              vendor;
    private EfficiencyRating    efficiencyRating;
}
