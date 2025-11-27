package fr.mehdi.tool_management.category;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>, JpaSpecificationExecutor<Category>{

    /** FIND ALL **/

    @Query("SELECT c FROM Category c WHERE c.id in :ids")
    public List<Category> findAllByIds(@Param("ids") List<Integer> ids);

    
}
