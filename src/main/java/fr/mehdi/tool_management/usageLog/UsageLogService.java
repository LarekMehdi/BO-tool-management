package fr.mehdi.tool_management.usageLog;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import fr.mehdi.tool_management.usageLog.filters.UsageLogFilter;

@Service
public class UsageLogService {

    @Autowired
    private UsageLogRepository      usageLogRepository;

    /** FIND ALL **/

    public List<UsageLog> findAll(UsageLogFilter filter) {
        // récupération des logs avec filtre (sans pagination)
        filter.initGenericFilterIfNeeded();
        Specification<UsageLog> specification = filter.toSpecification();
        List<UsageLog> tools = this.usageLogRepository.findAll(specification);

        return tools;
    }

    public List<UsageLog> findAllByToolId(Integer toolId) {
        return this.usageLogRepository.findAllByToolId(toolId);
    }

    public List<UsageLog> findAllByToolIds(List<Integer> toolIds) {
        return this.usageLogRepository.findAllByToolIds(toolIds);
    }
    
}
