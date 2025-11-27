package fr.mehdi.tool_management.filters;

import java.util.List;

import fr.mehdi.tool_management.utils.UtilEntity;

public class PageDto<T> {
    
    private List<T>     datas;
    private Long        totalElement;
    
    public PageDto(List<T> datas, Long totalElement) {
        this.datas = datas;
        this.totalElement = totalElement;
    }

    /** DATAS **/

    public List<T> getDatas() {
        return this.datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    public boolean hasDatas() {
        return !UtilEntity.isEmpty(this.datas);
    }

    /** TOTAL ELEMENT **/

    public Long getTotalElement() {
        return this.totalElement;
    }

    public void setTotalElement(Long totalElement) {
        this.totalElement = totalElement;
    }

    public boolean hasTotalElement() {
        return !UtilEntity.isEmpty(this.totalElement);
    }
}
