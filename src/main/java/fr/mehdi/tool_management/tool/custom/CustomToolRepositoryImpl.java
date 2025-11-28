package fr.mehdi.tool_management.tool.custom;

import org.springframework.stereotype.Repository;

import fr.mehdi.tool_management.constantes.ToolStatus;
import fr.mehdi.tool_management.tool.dtos.CreateToolDto;
import fr.mehdi.tool_management.tool.dtos.UpdateToolDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class CustomToolRepositoryImpl implements CustomToolRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /** CREATE **/

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

    /** UPDATE **/

    @Override
    public void updateToolWithEnumCast(Integer id, UpdateToolDto dto) {

        StringBuilder sql = new StringBuilder("UPDATE tools SET updated_at = NOW()");
        
        if (dto.hasName()) {
            sql.append(", name = :name");
        }
        if (dto.hasDescription()) {
            sql.append(", description = :description");
        }
        if (dto.hasVendor()) {
            sql.append(", vendor = :vendor");
        }
        if (dto.hasWebsiteUrl()) {
            sql.append(", website_url = :websiteUrl");
        }
        if (dto.hasMonthlyCost()) {
            sql.append(", monthly_cost = :monthlyCost");
        }
        if (dto.hasActiveUsersCount()) {
            sql.append(", active_users_count = :activeUsersCount");
        }
        if (dto.hasOwnerDepartment()) {
            sql.append(", owner_department = CAST(:ownerDepartment AS department_type)");
        }
        if (dto.hasStatus()) {
            sql.append(", status = CAST(:status AS tool_status_type)");
        }
        if (dto.hasCategoryId()) {
            sql.append(", category_id = :categoryId");
        }
        
        sql.append(" WHERE id = :id");
        
        var query = entityManager.createNativeQuery(sql.toString());
        query.setParameter("id", id);
        
        if (dto.hasName()) {
            query.setParameter("name", dto.getName());
        }
        if (dto.hasDescription()) {
            query.setParameter("description", dto.getDescription());
        }
        if (dto.hasVendor()) {
            query.setParameter("vendor", dto.getVendor());
        }
        if (dto.hasWebsiteUrl()) {
            query.setParameter("websiteUrl", dto.getWebsiteUrl());
        }
        if (dto.hasMonthlyCost()) {
            query.setParameter("monthlyCost", dto.getMonthlyCost());
        }
        if (dto.hasActiveUsersCount()) {
            query.setParameter("activeUsersCount", dto.getActiveUsersCount());
        }
        if (dto.hasOwnerDepartment()) {
            query.setParameter("ownerDepartment", dto.getOwnerDepartment().name());
        }
        if (dto.hasStatus()) {
            query.setParameter("status", dto.getStatus().name());
        }
        if (dto.hasCategoryId()) {
            query.setParameter("categoryId", dto.getCategoryId());
        }
        
        query.executeUpdate();
    }
}
