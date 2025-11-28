package fr.mehdi.tool_management.tool;
import java.math.BigDecimal;
import java.time.LocalDateTime;


import org.hibernate.annotations.CreationTimestamp;
import fr.mehdi.tool_management.category.Category;
import fr.mehdi.tool_management.constantes.Department;
import fr.mehdi.tool_management.constantes.ToolStatus;
import fr.mehdi.tool_management.converters.DepartmentConverter;
import fr.mehdi.tool_management.converters.ToolStatusConverter;
import fr.mehdi.tool_management.tool.dtos.CreateToolDto;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tools")
public class Tool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer             id;

    @Column(nullable = false)
    private String              name;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String              description;

    @Column
    private String              vendor;

    @Column
    private String              websiteUrl;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal          monthlyCost; 

    @Column(nullable = false)
    private Integer             activeUsersCount;

    @Column(nullable = false, columnDefinition = "department_type")
    @Convert(converter = DepartmentConverter.class)
    private Department          ownerDepartment;

    @Column(columnDefinition = "tool_status_type")
    @Convert(converter = ToolStatusConverter.class)
    private ToolStatus          status = ToolStatus.active;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime       createdAt;

    @Column
    private LocalDateTime       updatedAt;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category            category;

    /** IDS BRUTS (pour éviter les fetchs automatiques) **/

    @Column(name = "category_id", insertable = false, updatable = false)
    private Integer             categoryId;



    public Tool(CreateToolDto dto) {
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.vendor = dto.getVendor();
        this.websiteUrl = dto.getWebsiteUrl();
        this.categoryId = dto.getCategoryId();
        this.monthlyCost = dto.getMonthlyCost();
        this.ownerDepartment = dto.getOwnerDepartment();
    }
    

}
