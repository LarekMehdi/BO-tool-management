package fr.mehdi.tool_management.usageLog.filters;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import fr.mehdi.tool_management.filters.FiltersApplied;
import fr.mehdi.tool_management.filters.GenericFilter;
import fr.mehdi.tool_management.usageLog.UsageLog;
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
public class UsageLogFilter extends GenericFilter {

    private Integer         toolId;
    private LocalDateTime   minSessionDate;

    /** TOOL ID **/

    public boolean hasToolId() {
        return !UtilEntity.isEmpty(this.toolId);
    }

    /** MIN SESSION DATE **/

    public boolean hasMinSessionDate() {
        return !UtilEntity.isEmpty(this.minSessionDate);
    }

    /** WHERE CONDITIONS **/

    public Specification<UsageLog> toSpecification() {
        //root => colonne
        //query => requete complete
        //cb => criteriaBuilder
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // TOOL ID
            if (this.hasToolId()) {
                predicates.add(
                    cb.equal(root.get("toolId"), this.getToolId())
                );
            }

            // MIN SESSION DATE
            if (this.hasMinSessionDate()) {
                LocalDateTime min = this.minSessionDate.toLocalDate().atStartOfDay();
                predicates.add(cb.greaterThanOrEqualTo(root.get("sessionDate"), min));
            }

            return predicates.isEmpty() ? cb.conjunction() : cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    /** FILTERS APPLIED **/

    @Override
    protected void populateFiltersApplied(FiltersApplied fa) {
        if (this.hasToolId()) fa.add("toolId", this.getToolId());
        if (this.hasMinSessionDate()) fa.add("minSessionDate", this.getMinSessionDate());
    }

    
}
