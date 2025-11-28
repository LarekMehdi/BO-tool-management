package fr.mehdi.tool_management.tool.custom;

import fr.mehdi.tool_management.tool.dtos.CreateToolDto;

public interface CustomToolRepository {
    
    Integer insertToolWithEnumCast(CreateToolDto dto);
}