package com.devfrank.hotelmanager.customers.service.impl;

import com.devfrank.hotelmanager.customers.dto.CustomerDTO;
import com.devfrank.hotelmanager.customers.dto.filter.CustomerCriteria;
import com.devfrank.hotelmanager.customers.dto.request.SaveCustomerRequest;
import com.devfrank.hotelmanager.customers.entity.Customer;
import com.devfrank.hotelmanager.customers.repository.CustomerRepository;
import com.devfrank.hotelmanager.customers.service.CustomerService;
import com.devfrank.hotelmanager.customers.util.mapper.CustomerMapper;
import com.devfrank.hotelmanager.customers.util.specs.CustomerSpecs;
import com.devfrank.hotelmanager.shared.constans.ResourceConstants;
import com.devfrank.hotelmanager.shared.exception.BusinessException;
import com.devfrank.hotelmanager.shared.exception.DeactivatedCustomerException;
import com.devfrank.hotelmanager.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerDTO create(SaveCustomerRequest request) {
        Optional<Customer> customerOpt = customerRepository.findByDocumentNumber(request.documentNumber());

        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            if (!customer.getIsActive()) {
                throw new DeactivatedCustomerException(
                        "El cliente existe pero está dado de baja.",
                        customer.getId().toString()
                );
            }
        }

        // TODO Si tiene permisos para ver clientes activos e inactivos, entonces sigue el flujo normal
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
        // TODO Validar rol y permisos para retornar total de clientes o solamente clientes activos.
        return customerRepository.findAll(spec, pageable)
                .map(customerMapper::toDTO);
    }

    @Override
    public CustomerDTO findById(UUID id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceConstants.CUSTOMER, id.toString()));

        //TODO Verificar el rol del usuario actual

        //TODO Si el cliente encontrado es inactivo y tiene permisos solo para consultar cliente activos
        if (!customer.getIsActive()) {
            //TODO Si no es admin y el cliente está inactivo, lanzamos 404
            throw new ResourceNotFoundException(ResourceConstants.CUSTOMER, id.toString());
        }

        return customerMapper.toDTO(customer);
    }

    @Override
    public CustomerDTO update(UUID id, SaveCustomerRequest request) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceConstants.CUSTOMER, id.toString()));

        if (!customer.getIsActive()) {
            throw new BusinessException("No es posible editar un cliente inactivo.");
        }

        customerMapper.updateEntity(request, customer);
        return customerMapper.toDTO(customerRepository.save(customer));
    }
}