package com.devfrank.hotelmanager.rooms.entity;

import com.devfrank.hotelmanager.rooms.util.enums.RoomStatus;
import com.devfrank.hotelmanager.rooms.util.enums.RoomType;
import com.devfrank.hotelmanager.shared.constans.JpaConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = JpaConstants.ROOMS_TABLE, uniqueConstraints = {
        @UniqueConstraint(name = JpaConstants.UK_ROOMS_NUMBER, columnNames = "number")
})
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Room {

    @Id
    private UUID id;

    @Column(nullable = false, length = 3)
    private String number;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private RoomType type;

    @Column(nullable = false)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RoomStatus status;

    @Column(nullable = false)
    private Boolean isActive;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(nullable = false, length = 150)
    private String createdBy;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @LastModifiedBy
    @Column(nullable = false, length = 150)
    private String updatedBy;
}