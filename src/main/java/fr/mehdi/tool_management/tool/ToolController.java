package fr.mehdi.tool_management.tool;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.mehdi.tool_management.filters.PageDto;
import fr.mehdi.tool_management.tool.dtos.CreateToolDto;
import fr.mehdi.tool_management.tool.dtos.ToolDetailsDto;
import fr.mehdi.tool_management.tool.dtos.ToolDto;
import fr.mehdi.tool_management.tool.dtos.UpdateToolDto;
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

    /** FIND **/

    @GetMapping("/{id}")
    public ToolDetailsDto findDetailsById(@PathVariable("id") Integer id) {
        return this.toolService.findDetailsById(id);
    }

    /** UPDATE **/

    @PutMapping("/{id}")
    public ToolDto update(@RequestBody @Valid UpdateToolDto dto, @PathVariable("id") Integer id) {
        return this.toolService.update(dto, id);
    }

    /** CREATE **/

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ToolDto create(@RequestBody @Valid CreateToolDto dto) {
        return this.toolService.create(dto);
    }
    
}
