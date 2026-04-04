package com.devfrank.hotelmanager.security.entity;

import com.devfrank.hotelmanager.shared.constans.JpaConstants;
import com.devfrank.hotelmanager.users.entity.AppUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = JpaConstants.REFRESH_TOKENS_TABLE, uniqueConstraints = {
        @UniqueConstraint(name = JpaConstants.UK_REFRESH_TOKENS_TOKEN, columnNames = "token")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private Instant expiryDate;

    @OneToOne
    @JoinColumn(
            name = JpaConstants.USER_ID_COLUMN,
            nullable = false,
            foreignKey = @ForeignKey(name = JpaConstants.FK_REFRESH_TOKENS_USERS)
    )
    private AppUser user;
}