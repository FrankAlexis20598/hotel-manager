package com.devfrank.hotelmanager.customers.service.impl;

import com.devfrank.hotelmanager.customers.entity.Customer;
import com.devfrank.hotelmanager.customers.repository.CustomerRepository;
import com.devfrank.hotelmanager.reservations.service.api.CustomerValidation;
import com.devfrank.hotelmanager.shared.exception.BusinessException;
import com.devfrank.hotelmanager.shared.exception.ResourceIDsNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CustomerValidationImpl implements CustomerValidation {

    private final CustomerRepository customerRepository;

    @Override
    public void ensureCustomerIsActive(UUID customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceIDsNotFoundException(String.format("El siguiente ID de cliente no existe: %s", customerId)));

        if (!customer.getIsActive()) {
            throw new BusinessException(String.format("El cliente con ID %s está inactivo", customerId));
        }
    }
}