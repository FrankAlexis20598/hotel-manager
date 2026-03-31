package com.devfrank.hotelmanager.access.entity;

import com.devfrank.hotelmanager.shared.constans.JpaConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = JpaConstants.PERMISSIONS_TABLE, uniqueConstraints = {
        @UniqueConstraint(name = JpaConstants.UK_PERMISSIONS_MODULE_ACTION, columnNames = {"module", "action"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Permission {

    public Permission(UUID id) {
        this.id = id;
    }

    @Id
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(nullable = false, length = 50)
    private String module;

    @Column(nullable = false, length = 50)
    private String action;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}