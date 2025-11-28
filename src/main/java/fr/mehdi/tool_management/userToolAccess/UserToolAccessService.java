package fr.mehdi.tool_management.userToolAccess;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserToolAccessService {

    @Autowired
    private UserToolRepository      userToolRepository;

    /** FIND ALL **/

    public List<UserToolAccess> findAll() {
        return this.userToolRepository.findAll();
    }
    
}
