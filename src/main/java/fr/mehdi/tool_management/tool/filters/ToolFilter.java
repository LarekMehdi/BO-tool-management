package fr.mehdi.tool_management.tool.filters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import fr.mehdi.tool_management.filters.GenericFilter;
import fr.mehdi.tool_management.tool.Tool;
import jakarta.persistence.criteria.Predicate;

public class ToolFilter extends GenericFilter{


    /** METHODS **/

    public Specification<Tool> toSpecification() {
        //root => colonne
        //query => requete complete
        //cb => criteriaBuilder
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            //exemple
            // if (this.hasTitle()) {
            //     predicates.add(cb.like(cb.lower(root.get("title")), "%" + this.getTitle().toLowerCase() + "%"));
            // }

            return predicates.isEmpty() ? cb.conjunction() : cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
