package fr.mehdi.tool_management.tool;


import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import fr.mehdi.tool_management.filters.PageDto;
import fr.mehdi.tool_management.tool.dtos.ToolDto;
import fr.mehdi.tool_management.tool.filters.ToolFilter;
import fr.mehdi.tool_management.utils.UtilMapper;

@Service
public class ToolService {

    @Autowired
    private ToolRepository      toolRepository;

    /** FIND ALL **/

    public PageDto<ToolDto> findAll(ToolFilter filter) {
        filter.initGenericFilterIfNeeded();
        Pageable pageable = filter.toPageable();
        Specification<Tool> specification = filter.toSpecification();
        Page<Tool> tools = this.toolRepository.findAll(specification, pageable);

        List<ToolDto> toolDtos = UtilMapper.mapToolListToDtos(tools.getContent());
        PageDto<ToolDto> result = new PageDto<>(toolDtos, tools.getTotalElements());
        return result;

    }
    
}
