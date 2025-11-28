package fr.mehdi.tool_management.userToolAccess;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@Repository
public interface UserToolRepository extends JpaRepository<UserToolAccess, Integer>, JpaSpecificationExecutor<UserToolAccess> {
    
}
