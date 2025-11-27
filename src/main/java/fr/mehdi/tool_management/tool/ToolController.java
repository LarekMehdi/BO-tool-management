package fr.mehdi.tool_management.tool;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.mehdi.tool_management.filters.PageDto;
import fr.mehdi.tool_management.tool.dtos.ToolDto;
import fr.mehdi.tool_management.tool.filters.ToolFilter;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tools")
public class ToolController {

    @Autowired
    private ToolService     toolService;

    /** FIND ALL **/

    @GetMapping()
    public PageDto<ToolDto> findAll(@ModelAttribute @Valid ToolFilter filter) {
        return this.toolService.findAll(filter);
    }
    
}
