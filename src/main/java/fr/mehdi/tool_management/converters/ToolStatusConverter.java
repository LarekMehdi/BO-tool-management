package fr.mehdi.tool_management.converters;

import fr.mehdi.tool_management.constantes.ToolStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ToolStatusConverter implements AttributeConverter<ToolStatus, String> {
    
    @Override
    public String convertToDatabaseColumn(ToolStatus attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.name();
    }

    @Override
    public ToolStatus convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return ToolStatus.valueOf(dbData);
    }
}