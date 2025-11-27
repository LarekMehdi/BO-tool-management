package fr.mehdi.tool_management.usageLog.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsageMetricsDto {

    private UsageStatsDto   last30Days;
    
}
