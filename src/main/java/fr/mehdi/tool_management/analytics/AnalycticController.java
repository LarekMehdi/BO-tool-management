package fr.mehdi.tool_management.analytics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.mehdi.tool_management.analytics.dtos.AnalyticDto;
import fr.mehdi.tool_management.analytics.dtos.AnalyticExpensiveDto;
import fr.mehdi.tool_management.analytics.filters.AnalyticFilter;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/analytics")
public class AnalycticController {

    @Autowired
    private AnalyticService         analyticService;

    /** FIND ALL **/

    @GetMapping("/department-costs")
    public AnalyticDto findDepartmentCost(@ModelAttribute @Valid AnalyticFilter filter) {
        return this.analyticService.findDepartmentCost(filter);
    }

    @GetMapping("expensive-tools")
    public AnalyticExpensiveDto findExpensiveTools(@ModelAttribute @Valid AnalyticFilter filter) {
        return this.analyticService.findExpensiveTools(filter);
    }

    
}
