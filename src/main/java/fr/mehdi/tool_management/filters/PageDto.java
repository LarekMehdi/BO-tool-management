package fr.mehdi.tool_management.filters;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import fr.mehdi.tool_management.utils.UtilEntity;

@JsonPropertyOrder({
    "data",
    "total",
    "filtered",
    "filtersApplied"
})
public class PageDto<T> {
    
    private List<T>             data;
    private Long                total;
    private Long                filtered;
    private FiltersApplied      filtersApplied;
    
    public PageDto(List<T> datas, Long total) {
        this.data = datas;
        this.total = total;
    }

    public PageDto(List<T> datas, Long total, FiltersApplied filtersApplied) {
        this.data = datas;
        this.total = total;
        this.filtersApplied = filtersApplied;
    }

    /** DATA **/

    public List<T> getData() {
        return this.data;
    }

    public void setData(List<T> datas) {
        this.data = datas;
    }

    public boolean hasData() {
        return !UtilEntity.isEmpty(this.data);
    }

    /** TOTAL **/

    public Long getTotal() {
        return this.total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public boolean hasTotal() {
        return !UtilEntity.isEmpty(this.total);
    }

    /** FILTERED **/

    public Long getFiltered() {
        return this.filtered;
    }

    public void setFiltered(Long filtered) {
        this.filtered = filtered;
    }

    public boolean hasFiltered() {
        return !UtilEntity.isEmpty(this.filtered);
    }

    /** FILTERS APPLIED **/

    public FiltersApplied getFiltersApplied() {
        return this.filtersApplied;
    }

    public void setFiltersApplied(FiltersApplied filtersApplied) {
        this.filtersApplied = filtersApplied;
    }

    public boolean hasFiltersApplied() {
        return !UtilEntity.isEmpty(this.filtersApplied);
    }
}
