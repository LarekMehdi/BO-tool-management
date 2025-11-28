package fr.mehdi.tool_management.analytics;

import org.springframework.stereotype.Service;

import fr.mehdi.tool_management.analytics.dtos.AnalyticDto;
import fr.mehdi.tool_management.analytics.filters.AnalyticFilter;

@Service
public class AnalyticService {
    
    /** FIND ALL **/

    public AnalyticDto findDepartmentCost(AnalyticFilter filter) {

        return new AnalyticDto();
    }
}
