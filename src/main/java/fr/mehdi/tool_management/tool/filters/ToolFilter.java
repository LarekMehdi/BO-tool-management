package fr.mehdi.tool_management.tool.filters;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import fr.mehdi.tool_management.constantes.Department;
import fr.mehdi.tool_management.constantes.ToolStatus;
import fr.mehdi.tool_management.filters.FiltersApplied;
import fr.mehdi.tool_management.filters.GenericFilter;
import fr.mehdi.tool_management.tool.Tool;
import fr.mehdi.tool_management.utils.UtilEntity;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ToolFilter extends GenericFilter{

    private BigDecimal          minCost;
    private BigDecimal          maxCost;
    private ToolStatus          status;
    private Department          department;
    private String              category;     // category name pour matcher la demande (passer par id serait mieux)
    private LocalDateTime       createdAt;

    /** MIN COST **/

    public boolean hasMinCost() {
        return !UtilEntity.isEmpty(this.minCost);
    }

    /** MAX COST **/

    public boolean hasMaxCost() {
        return !UtilEntity.isEmpty(this.maxCost);
    }

    /** STATUS **/

    public boolean hasStatus() {
        return !UtilEntity.isEmpty(this.status);
    }

    /** DEPARTMENT **/

    public boolean hasDepartment() {
        return !UtilEntity.isEmpty(this.department);
    }

    /** CATEGORY **/

    public boolean hasCategory() {
        return !UtilEntity.isEmpty(this.category);
    }

    /** CREATED AT **/

    public boolean hasCreatedAt() {
        return !UtilEntity.isEmpty(this.createdAt);
    }

    /** WHERE CONDITIONS **/

    public Specification<Tool> toSpecification() {
        //root => colonne
        //query => requete complete
        //cb => criteriaBuilder
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // MIN COST
            if (this.hasMinCost()) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("monthlyCost"), this.getMinCost()));
            }

            // MAX COST
            if (this.hasMaxCost()) {
                predicates.add(cb.lessThanOrEqualTo(root.get("monthlyCost"), this.getMaxCost()));
            }

            // STATUS
            if (this.hasStatus()) {
                predicates.add(cb.equal(
                    cb.function("text", String.class, root.get("status")),
                    this.getStatus().name()
                ));
            }

            // DEPARTMENT
            if (this.hasDepartment()) {
                predicates.add(cb.equal(
                    cb.function("text", String.class, root.get("ownerDepartment")),
                    this.getDepartment().name()
                ));
            }

            // CREATED AT
            if (this.hasCreatedAt()) {
                LocalDateTime dateMin = this.getCreatedAt().toLocalDate().atStartOfDay();
                LocalDateTime dateMax = this.getCreatedAt().toLocalDate().atTime(23, 59, 59);
                predicates.add(cb.between(root.get("createdAt"), dateMin, dateMax));
            }

            // CATEGORY (jointure)
            if (this.hasCategory()) {
                var categoryJoin = root.join("category");
                predicates.add(cb.like(cb.lower(categoryJoin.get("name")), "%" + this.getCategory().toLowerCase() + "%"));
            }

            return predicates.isEmpty() ? cb.conjunction() : cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    /** FILTERS APPLIED **/

    @Override
    protected void populateFiltersApplied(FiltersApplied fa) {
        if (this.hasMinCost()) fa.add("minCost", this.getMinCost());
        if (this.hasMaxCost()) fa.add("maxCost", this.getMaxCost());
        if (this.hasStatus()) fa.add("status", this.getStatus());
        if (this.hasDepartment()) fa.add("department", this.getDepartment());
        if (this.hasCategory()) fa.add("category", this.getCategory());
        if (this.hasCreatedAt()) fa.add("createdAt", this.getCreatedAt());
    }
}
