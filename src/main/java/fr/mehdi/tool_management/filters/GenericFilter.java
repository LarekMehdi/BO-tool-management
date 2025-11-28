package fr.mehdi.tool_management.filters;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import fr.mehdi.tool_management.constantes.SortOrder;
import fr.mehdi.tool_management.utils.UtilEntity;
import jakarta.validation.constraints.Min;

public abstract class GenericFilter {

    @Min(value = 1, message = "Limit must be greater than 0")
    private Integer         limit;

    @Min(value = 0, message = "Offset must be positif")
    private Integer         offset;

    private String          sortBy;

    private SortOrder       order;    
    
    public GenericFilter() {}

    public GenericFilter(Integer limit, Integer offset, String sortBy, SortOrder order) {
        this.limit = limit;
        this.offset = offset;
        this.sortBy = sortBy;
        this.order = order;
    }

    public GenericFilter(Integer limit, Integer offset) {
        this.limit = limit;
        this.offset = offset;
    }

    /** LIMIT **/

    public Integer getLimit() {
        return this.limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public boolean hasLimit() {
        return !UtilEntity.isEmpty(this.limit);
    }

    /** OFFSET **/

    public Integer getOffset() {
        return this.offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public boolean hasOffset() {
        return !UtilEntity.isEmpty(this.offset);
    }

    /** SORT BY **/

    public String getSortBy() {
        return this.sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public boolean hasSortBy() {
        return !UtilEntity.isEmpty(this.sortBy);
    }

    /** ORDER **/

    public SortOrder getOrder() {
        return this.order;
    }

    public void setOrder(SortOrder order) {
        this.order = order;
    }

    public boolean hasOrder() {
        return !UtilEntity.isEmpty(this.order);
    }

    public boolean isOrderAsc() {
        return this.hasOrder() && this.getOrder() == SortOrder.ASC;
    }

    public boolean isOrderDesc() {
        return this.hasOrder() && this.getOrder() == SortOrder.DESC;
    }

    /** METHODS **/

    public void initGenericFilterIfNeeded() {
        if (!this.hasLimit()) this.limit = 10;
        if (!this.hasOffset()) this.offset = 0;
    }

    public Pageable toPageable() {
        Sort sort = Sort.unsorted();
        if (this.hasSortBy()) {
            sort = Sort.by(
                this.isOrderDesc() ? Sort.Direction.DESC : Sort.Direction.ASC,
                this.getSortBy()
            );
        }
        return PageRequest.of(this.getPage(), this.limit, sort);
    }

    public Integer getPage() {
        return this.offset / this.limit;
    }

    /** FILTERS APPLIED **/
    
    protected abstract void populateFiltersApplied(FiltersApplied filtersApplied);

    public FiltersApplied buildFiltersApplied() {
        FiltersApplied fa = new FiltersApplied();
        populateFiltersApplied(fa);
        return fa;
    }

}
