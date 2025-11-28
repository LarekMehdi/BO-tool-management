package fr.mehdi.tool_management.userToolAccess;

import java.time.LocalDateTime;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;

import fr.mehdi.tool_management.constantes.AccessStatus;
import fr.mehdi.tool_management.tool.Tool;
import fr.mehdi.tool_management.user.User;
import fr.mehdi.tool_management.utils.UtilEntity;
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
@Table(name = "user_tool_access")
public class UserToolAccess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer             id;

    @Column
    @CreationTimestamp
    private LocalDateTime       grantedAt;

    @Column
    private LocalDateTime       revokedAt;

    @Column
    @Enumerated(EnumType.STRING)
    private AccessStatus        status = AccessStatus.active;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "granted_by")
    private User                grantedBy;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "revoked_by")
    private User                revokedBy;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User                user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "tool_id")
    private Tool                tool;

    /** IDS BRUTS (pour éviter les fetchs automatiques) **/

    @Column(name = "granted_by", insertable = false, updatable = false)
    private Integer             grantedById;

    @Column(name = "revoked_by", insertable = false, updatable = false)
    private Integer             revokedById;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Integer             userId;

    @Column(name = "tool_id", insertable = false, updatable = false)
    private Integer             toolId;

    /** TOOL ID **/

    public boolean hasToolId() {
        return !UtilEntity.isEmpty(this.toolId);
    }

    /** STATUS **/

    public boolean hasStatus() {
        return !UtilEntity.isEmpty(this.status);
    }

    public boolean isStatusActive() {
        return this.hasStatus() && Objects.equals(AccessStatus.active, this.status);
    }



}
