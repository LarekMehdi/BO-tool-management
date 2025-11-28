package fr.mehdi.tool_management.tool.custom;

import org.springframework.stereotype.Repository;

import fr.mehdi.tool_management.constantes.ToolStatus;
import fr.mehdi.tool_management.tool.dtos.CreateToolDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class CustomToolRepositoryImpl implements CustomToolRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Integer insertToolWithEnumCast(CreateToolDto dto) {
        String sql = """
            INSERT INTO tools 
            (name, description, vendor, website_url, monthly_cost, active_users_count, 
             owner_department, status, created_at, category_id)
            VALUES 
            (:name, :description, :vendor, :websiteUrl, :monthlyCost, :activeUsersCount,
             CAST(:ownerDepartment AS department_type), 
             CAST(:status AS tool_status_type), 
             NOW(), :categoryId)
            RETURNING id
            """;

        return (Integer) entityManager.createNativeQuery(sql)
            .setParameter("name", dto.getName())
            .setParameter("description", dto.getDescription())
            .setParameter("vendor", dto.getVendor())
            .setParameter("websiteUrl", dto.getWebsiteUrl())
            .setParameter("monthlyCost", dto.getMonthlyCost())
            .setParameter("activeUsersCount", 0)
            .setParameter("ownerDepartment", dto.getOwnerDepartment().name())
            .setParameter("status", ToolStatus.active.name())
            .setParameter("categoryId", dto.getCategoryId())
            .getSingleResult();
    }
}
