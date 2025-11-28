package fr.mehdi.tool_management.tool;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolRepository extends JpaRepository<Tool, Integer>, JpaSpecificationExecutor<Tool> {

    /** COUNT **/

    @Query("SELECT COUNT(t) FROM Tool t WHERE LOWER(t.name) = LOWER(:name)")
    public int countByName(@Param("name") String name);

    
} 
