package fr.mehdi.tool_management.utils;

import java.util.ArrayList;
import java.util.List;

import fr.mehdi.tool_management.tool.Tool;
import fr.mehdi.tool_management.tool.dtos.ToolDto;

public abstract class UtilMapper {

    /** TOOL **/

    public static List<ToolDto> mapToolListToDtos(List<Tool> tools) {
        List<ToolDto> dtos = new ArrayList<>();
        for (Tool tool : tools) {
            ToolDto dto = new ToolDto(tool);
            dtos.add(dto);
        }

        return dtos;
    }
    
}
