package fr.mehdi.tool_management.category;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository      categoryRepository;

    /** FIND ALL **/

    public List<Category> findAllByIds(List<Integer> ids) {
        return this.categoryRepository.findAllByIds(ids);
    }

    /** FIND **/

    public Category findById(Integer id) {
        Optional<Category> optCategory = this.categoryRepository.findById(id);
        if (!optCategory.isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No category found with id " + id); 
        return optCategory.get();
    }

    /** COUNT **/

    public boolean existsById(Integer id) {
        return this.categoryRepository.existsById(id);
    }
    
}
