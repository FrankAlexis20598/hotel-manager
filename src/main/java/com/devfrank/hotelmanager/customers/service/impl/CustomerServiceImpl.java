package com.devfrank.hotelmanager.customers.service.impl;

import com.devfrank.hotelmanager.customers.dto.CustomerDTO;
import com.devfrank.hotelmanager.customers.dto.command.SaveCustomerCommand;
import com.devfrank.hotelmanager.customers.entity.Customer;
import com.devfrank.hotelmanager.customers.repository.CustomerRepository;
import com.devfrank.hotelmanager.customers.service.CustomerService;
import com.devfrank.hotelmanager.customers.util.exception.CustomerNotFoundException;
import com.devfrank.hotelmanager.customers.util.mapper.CustomerMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerDTO> findAll() {
        return customerMapper.toDTOs(customerRepository.findAllByIsActiveTrue());
    }

    @Override
    public CustomerDTO findById(UUID id) {
        return customerRepository.findByIdAndIsActiveTrue(id)
                .map(customerMapper::toDTO)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @Override
    public CustomerDTO create(SaveCustomerCommand command) {
        Customer customer = customerMapper.toEntity(command);
        return customerMapper.toDTO(customerRepository.save(customer));
    }

    @Override
    public CustomerDTO update(UUID id, SaveCustomerCommand command) {
        Customer customer = customerRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        customerMapper.toEntity(customer, command);
        return customerMapper.toDTO(customerRepository.save(customer));
    }

    @Transactional
    @Override
    public void delete(UUID id) {
        Customer foundCustomer = customerRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        customerRepository.deactivateById(foundCustomer.getId());
    }
}