package com.devfrank.hotelmanager.customers.entity;

import com.devfrank.hotelmanager.customers.util.converter.DocumentTypeConverter;
import com.devfrank.hotelmanager.customers.util.enums.DocumentType;
import com.devfrank.hotelmanager.shared.constans.JpaConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = JpaConstants.CUSTOMERS_TABLE, uniqueConstraints = {
        @UniqueConstraint(name = JpaConstants.UK_CUSTOMERS_EMAIL, columnNames = "email"),
        @UniqueConstraint(name = JpaConstants.UK_CUSTOMERS_DOCUMENT_NUMBER, columnNames = "documentNumber")
})
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Customer {

    public Customer(UUID id) {
        this.id = id;
    }

    @Id
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(nullable = false, length = 100)
    private String names;

    @Column(nullable = false, length = 100)
    private String surnames;

    @Column(nullable = false, length = 150, unique = true)
    private String email;

    @Column(length = 20)
    private String phone;

    @Convert(converter = DocumentTypeConverter.class)
    @Column(nullable = false, length = 2)
    private DocumentType documentType;

    @Column(nullable = false, length = 12, unique = true)
    private String documentNumber;

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