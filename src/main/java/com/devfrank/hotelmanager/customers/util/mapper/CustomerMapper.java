package com.devfrank.hotelmanager.customers.util.mapper;

import com.devfrank.hotelmanager.customers.dto.CustomerDTO;
import com.devfrank.hotelmanager.customers.dto.command.SaveCustomerCommand;
import com.devfrank.hotelmanager.customers.entity.Customer;
import com.devfrank.hotelmanager.shared.base.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomerMapper implements BaseMapper<Customer, CustomerDTO, SaveCustomerCommand, SaveCustomerCommand> {

    @Override
    public CustomerDTO toDTO(Customer entity) {
        return new CustomerDTO(
                entity.getId(),
                entity.getNames(),
                entity.getSurnames(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getDocumentType(),
                entity.getDocumentNumber(),
                entity.getIsActive()
        );
    }

    @Override
    public Customer toEntity(SaveCustomerCommand command) {
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setNames(command.names());
        customer.setSurnames(command.surnames());
        customer.setEmail(command.email());
        customer.setPhone(command.phone());
        customer.setDocumentType(command.documentType());
        customer.setDocumentNumber(command.documentNumber());
        customer.setIsActive(Boolean.TRUE);
        return customer;
    }

    @Override
    public void toEntity(Customer entity, SaveCustomerCommand command) {
        entity.setNames(command.names());
        entity.setSurnames(command.surnames());
        entity.setEmail(command.email());
        entity.setPhone(command.phone());
        entity.setDocumentType(command.documentType());
        entity.setDocumentNumber(command.documentNumber());
    }
}