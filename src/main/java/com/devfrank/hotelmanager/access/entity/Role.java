package com.devfrank.hotelmanager.access.entity;

import com.devfrank.hotelmanager.shared.constans.JpaConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = JpaConstants.ROLES_TABLE, uniqueConstraints = {
        @UniqueConstraint(name = JpaConstants.UK_ROLES_NAME, columnNames = "name")
})
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Role {

    public Role(UUID id) {
        this.id = id;
    }

    @Id
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(nullable = false, length = 50)
    @NotBlank
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Boolean isActive;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(nullable = false)
    private String createdBy;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @LastModifiedBy
    @Column(nullable = false)
    private String updatedBy;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = JpaConstants.ROLE_PERMISSIONS_TABLE,
            joinColumns = @JoinColumn(name = JpaConstants.ROLE_ID_COLUMN),
            inverseJoinColumns = @JoinColumn(name = JpaConstants.PERMISSION_ID_COLUMN),
            uniqueConstraints = @UniqueConstraint(columnNames = {JpaConstants.ROLE_ID_COLUMN, JpaConstants.PERMISSION_ID_COLUMN})
    )
    private List<Permission> permissions;
}