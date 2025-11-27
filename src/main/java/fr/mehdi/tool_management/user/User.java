package fr.mehdi.tool_management.user;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import fr.mehdi.tool_management.constantes.Department;
import fr.mehdi.tool_management.constantes.UserRole;
import fr.mehdi.tool_management.constantes.UserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer             id;

    @Column(nullable = false)
    private String              name;

    @Column(nullable = false, unique = true)
    private String              email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Department          department;

    @Column
    @Enumerated(EnumType.STRING)
    private UserRole            role = UserRole.employee;

    @Column
    @Enumerated(EnumType.STRING)
    private UserStatus          status = UserStatus.active;

    @Column
    private LocalDate           hireDate;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime       createdAt;

    @Column
    private LocalDateTime       updatedAt;
    



 

}
