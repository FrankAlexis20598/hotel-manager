package com.devfrank.hotelmanager.customers.service.impl;

import com.devfrank.hotelmanager.customers.dto.CustomerDTO;
import com.devfrank.hotelmanager.customers.dto.filter.CustomerCriteria;
import com.devfrank.hotelmanager.customers.dto.request.SaveCustomerRequest;
import com.devfrank.hotelmanager.customers.entity.Customer;
import com.devfrank.hotelmanager.customers.repository.CustomerRepository;
import com.devfrank.hotelmanager.customers.service.CustomerService;
import com.devfrank.hotelmanager.customers.util.mapper.CustomerMapper;
import com.devfrank.hotelmanager.customers.util.specs.CustomerSpecs;
import com.devfrank.hotelmanager.security.util.SecurityUtils;
import com.devfrank.hotelmanager.shared.constans.PermissionsConstants;
import com.devfrank.hotelmanager.shared.constans.ErrorConstants;
import com.devfrank.hotelmanager.shared.constans.ResourceConstants;
import com.devfrank.hotelmanager.shared.constans.UtilConstants;
import com.devfrank.hotelmanager.shared.exception.BusinessException;
import com.devfrank.hotelmanager.shared.exception.DeactivatedCustomerException;
import com.devfrank.hotelmanager.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final SecurityUtils securityUtils;

    @Override
    public CustomerDTO create(SaveCustomerRequest request) {
        boolean canViewInactive = securityUtils.hasPermission(PermissionsConstants.CLIENTES_VER_INACTIVOS);

        if (!canViewInactive) {
            Optional<Customer> customerOpt = customerRepository.findByDocumentNumber(request.documentNumber());

            if (customerOpt.isPresent() && !customerOpt.get().getIsActive()) {
                throw new DeactivatedCustomerException(
                        ErrorConstants.CUSTOMER_ALREADY_EXISTS_INACTIVE,
                        customerOpt.get().getId().toString()
                );
            }
        }

        Customer customer = customerMapper.toEntity(request);
        return customerMapper.toDTO(customerRepository.save(customer));
    }

    @Override
    public void deactivate(UUID id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceConstants.CUSTOMER, id.toString()));

        //TODO Antes de dar de baja, verificar si tiene reservas y pagos pendientes.
        //TODO Si tiene pendientes, entonces enviar excepción indicando que no se puede dar de baja y la razón.
        //TODO Si no tiene pendientes, se procede a dar de baja al cliente.

        customer.setIsActive(Boolean.FALSE);
        customerRepository.save(customer);
    }

    @Override
    public void reactivate(UUID id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceConstants.CUSTOMER, id.toString()));
        customer.setIsActive(Boolean.TRUE);
        customerRepository.save(customer);
    }

    @Override
    public Page<CustomerDTO> findAllBy(CustomerCriteria filter, Pageable pageable) {
        Specification<Customer> spec = CustomerSpecs.filter(filter);
        boolean canViewInactive = securityUtils.hasPermission(PermissionsConstants.CLIENTES_VER_INACTIVOS);

        if (filter.getIsActive() == null) {
            filter.setIsActive(canViewInactive
                    ? UtilConstants.IS_ACTIVE_FILTER_ALL
                    : UtilConstants.IS_ACTIVE_FILTER_TRUE);
        }

        if ((filter.getIsActive().equals(UtilConstants.IS_ACTIVE_FILTER_ALL)) && !canViewInactive) {
            throw new AccessDeniedException(ErrorConstants.CUSTOMER_FORBIDDEN_INACTIVE_VIEW);
        }

        return customerRepository.findAll(spec, pageable)
                .map(customerMapper::toDTO);
    }

    @Override
    public CustomerDTO findById(UUID id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceConstants.CUSTOMER, id.toString()));

        boolean canViewInactive = securityUtils.hasPermission(PermissionsConstants.CLIENTES_VER_INACTIVOS);

        if (!customer.getIsActive() && !canViewInactive) {
            throw new ResourceNotFoundException(ResourceConstants.CUSTOMER, id.toString());
        }

        return customerMapper.toDTO(customer);
    }

    @Override
    public CustomerDTO update(UUID id, SaveCustomerRequest request) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceConstants.CUSTOMER, id.toString()));

        if (!customer.getIsActive()) {
            throw new BusinessException(ErrorConstants.CUSTOMER_UPDATE_INACTIVE_PROHIBITED);
        }

        customerMapper.updateEntity(request, customer);
        return customerMapper.toDTO(customerRepository.save(customer));
    }
}