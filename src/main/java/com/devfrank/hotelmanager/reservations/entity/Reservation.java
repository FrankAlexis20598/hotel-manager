package com.devfrank.hotelmanager.reservations.entity;

import com.devfrank.hotelmanager.customers.entity.Customer;
import com.devfrank.hotelmanager.payments.entity.Payment;
import com.devfrank.hotelmanager.payments.util.enums.PaymentStatus;
import com.devfrank.hotelmanager.reservations.util.enums.ReservationStatus;
import com.devfrank.hotelmanager.rooms.entity.Room;
import com.devfrank.hotelmanager.shared.constans.JpaConstants;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = JpaConstants.RESERVATIONS_TABLE,
        uniqueConstraints = {
                @UniqueConstraint(name = JpaConstants.UK_RESERVATIONS_NUMBER, columnNames = JpaConstants.RESERVATION_NUMBER_COLUMN)
        },
        indexes = {
                @Index(name = JpaConstants.IDX_RESERVATION_NUMBER, columnList = JpaConstants.RESERVATION_NUMBER_COLUMN)
        }
)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = JpaConstants.RESERVATION_NUMBER_COLUMN, nullable = false, length = 20)
    private String reservationNumber;

    @Column(nullable = false)
    private LocalDateTime checkInDate;

    @Column(nullable = false)
    private LocalDateTime checkOutDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal basePrice;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @Column(precision = 10, scale = 2)
    private BigDecimal refundableAmount;

    @Column(length = 500)
    private String cancellationReason;

    private LocalDateTime cancelledAt;

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

    @ManyToOne
    @JoinColumn(
            name = JpaConstants.CUSTOMER_ID_COLUMN,
            nullable = false,
            foreignKey = @ForeignKey(name = JpaConstants.FK_RESERVATIONS_CUSTOMERS)
    )
    private Customer customer;

    @ManyToOne
    @JoinColumn(
            name = JpaConstants.ROOM_ID_COLUMN,
            nullable = false,
            foreignKey = @ForeignKey(name = JpaConstants.FK_RESERVATIONS_ROOMS)
    )
    private Room room;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments = new ArrayList<>();

    public BigDecimal getTotalPaid() {
        return this.payments.stream()
                .filter(p -> p.getStatus() == PaymentStatus.PAGADO)
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getRemainingBalance() {
        return this.totalAmount.subtract(this.getTotalPaid());
    }

    public boolean isFullyPaid() {
        return this.getRemainingBalance().compareTo(BigDecimal.ZERO) <= 0;
    }

    public void calculateTotalAmount() {
        if (this.checkInDate != null && this.checkOutDate != null && this.basePrice != null) {
            long days = ChronoUnit.DAYS.between(this.checkInDate.toLocalDate(), this.checkOutDate.toLocalDate());
            long payableDays = Math.max(1, days);
            this.totalAmount = this.basePrice.multiply(BigDecimal.valueOf(payableDays));
        }
    }
}