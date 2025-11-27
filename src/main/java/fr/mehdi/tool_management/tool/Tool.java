package fr.mehdi.tool_management.tool;
import java.math.BigDecimal;
import java.time.LocalDateTime;


import org.hibernate.annotations.CreationTimestamp;

import fr.mehdi.tool_management.category.Category;
import fr.mehdi.tool_management.constantes.Department;
import fr.mehdi.tool_management.constantes.ToolStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Department          ownerDepartment;

    @Column
    @Enumerated(EnumType.STRING)
    private ToolStatus          status;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime       createdAt;

    @Column
    private LocalDateTime       updatedAt;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category            category;
    

    
  

   
 
}
