package fr.mehdi.tool_management.accessRequest;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import fr.mehdi.tool_management.constantes.RequestStatus;
import fr.mehdi.tool_management.tool.Tool;
import fr.mehdi.tool_management.user.User;
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
@Table(name = "access_requests")
public class AccessRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer             id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String              businessJustification;

    @Column
    @Enumerated(EnumType.STRING)
    private RequestStatus       status = RequestStatus.pending;

    @Column
    @CreationTimestamp
    private LocalDateTime       requestedAt;

    @Column
    private LocalDateTime       processedAt;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String              processingNotes;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User                user;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "processed_by")
    private User                processedBy;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "tool_id")
    private Tool                tool;

    /** IDS BRUTS (pour éviter les fetchs automatiques) **/

    @Column(name = "user_id", insertable = false, updatable = false)
    private Integer             userId;

    @Column(name = "processed_by", insertable = false, updatable = false)
    private Integer             processedById;

    @Column(name = "tool_id", insertable = false, updatable = false)
    private Integer             toolId;


}
