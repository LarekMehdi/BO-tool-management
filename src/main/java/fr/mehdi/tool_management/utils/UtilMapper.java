package fr.mehdi.tool_management.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.mehdi.tool_management.category.Category;
import fr.mehdi.tool_management.tool.Tool;
import fr.mehdi.tool_management.tool.dtos.ToolDto;

public abstract class UtilMapper {

    /** TOOL **/

    public static List<ToolDto> mapToolListToDtos(List<Tool> tools, Map<Integer, Category> categoryById) {
        List<ToolDto> dtos = new ArrayList<>();
        for (Tool tool : tools) {
            if (categoryById.containsKey(tool.getCategory().getId())) {
                Category category = categoryById.get(tool.getCategory().getId());
                ToolDto dto = new ToolDto(tool, category);
            dtos.add(dto);
            }
        }

        return dtos;
    }
    
}
