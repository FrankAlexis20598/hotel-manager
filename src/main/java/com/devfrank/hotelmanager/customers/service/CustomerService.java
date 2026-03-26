package com.devfrank.hotelmanager.customers.service;

import com.devfrank.hotelmanager.customers.dto.CustomerDTO;
import com.devfrank.hotelmanager.customers.dto.command.SaveCustomerCommand;
import com.devfrank.hotelmanager.shared.base.BaseService;

import java.util.UUID;

public interface CustomerService extends BaseService<SaveCustomerCommand, SaveCustomerCommand, CustomerDTO, UUID> {
}