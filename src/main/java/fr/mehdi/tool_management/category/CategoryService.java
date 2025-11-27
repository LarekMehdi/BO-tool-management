package fr.mehdi.tool_management.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository      categoryRepository;

    /** FIND ALL **/

    public List<Category> findAllByIds(List<Integer> ids) {
        return this.categoryRepository.findAllByIds(ids);
    }
    
}
