package fr.mehdi.tool_management.converters;

import fr.mehdi.tool_management.constantes.Department;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class DepartmentConverter implements AttributeConverter<Department, String> {
    
    @Override
    public String convertToDatabaseColumn(Department attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.name();
    }

    @Override
    public Department convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return Department.valueOf(dbData);
    }
}