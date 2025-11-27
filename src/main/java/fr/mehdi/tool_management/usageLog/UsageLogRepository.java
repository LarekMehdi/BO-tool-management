package fr.mehdi.tool_management.usageLog;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsageLogRepository extends JpaRepository<UsageLog, Integer>, JpaSpecificationExecutor<UsageLog> {

    /** FIND ALL **/

    @Query("SELECT ul FROM UsageLog ul WHERE ul.toolId = :toolId")
    public List<UsageLog> findAllByToolId(@Param("toolId") Integer toolId);

    @Query("SELECT ul FROM UsageLog ul WHERE ul.toolId IN :toolIds")
    public List<UsageLog> findAllByToolIds(@Param("toolIds") List<Integer> toolIds);

    
}