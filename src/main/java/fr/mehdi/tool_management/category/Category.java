package fr.mehdi.tool_management.category;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import fr.mehdi.tool_management.utils.UtilEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer             id;

    @Column(nullable = false)
    private String              name;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String              description;

    @Column
    private String              colorHex;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime       createdAt;

    /** ID **/

    public boolean hasId() {
        return !UtilEntity.isEmpty(this.id);
    }
    
}
