package fr.mehdi.tool_management.tool.custom;

import fr.mehdi.tool_management.tool.dtos.CreateToolDto;
import fr.mehdi.tool_management.tool.dtos.UpdateToolDto;

public interface CustomToolRepository {
    
    /** CREATE **/

    Integer insertToolWithEnumCast(CreateToolDto dto);

    /** UPDATE **/

    void updateToolWithEnumCast(Integer id, UpdateToolDto dto);
}