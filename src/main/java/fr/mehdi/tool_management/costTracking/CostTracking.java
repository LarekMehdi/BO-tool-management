package fr.mehdi.tool_management.costTracking;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import fr.mehdi.tool_management.tool.Tool;
import jakarta.persistence.Column;
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
@Table(name = "cost_tracking")
public class CostTracking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer             id;

    @Column(nullable = false)
    private LocalDate           monthYear;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal          totalMonthlyCost; 

    @Column(nullable = false)
    private Integer             activeUsersCount = 0;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime       createdAt;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "tool_id")
    private Tool                tool;
    

}
