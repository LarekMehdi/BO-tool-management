package fr.mehdi.tool_management.usageLog.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsageStatsDto {

    private Integer         totalSessions;
    private Integer         avgSessionMinutes;
    
}
