package com.devfrank.hotelmanager.customers.service;

import com.devfrank.hotelmanager.customers.dto.CustomerDTO;
import com.devfrank.hotelmanager.customers.dto.filter.CustomerCriteria;
import com.devfrank.hotelmanager.customers.dto.request.SaveCustomerRequest;
import com.devfrank.hotelmanager.shared.base.service.CreateService;
import com.devfrank.hotelmanager.shared.base.service.LifecycleService;
import com.devfrank.hotelmanager.shared.base.service.ReadService;
import com.devfrank.hotelmanager.shared.base.service.UpdateService;

import java.util.UUID;

public interface CustomerService extends
        ReadService<CustomerDTO, UUID, CustomerCriteria>,
        CreateService<SaveCustomerRequest, CustomerDTO>,
        UpdateService<UUID, SaveCustomerRequest, CustomerDTO>,
        LifecycleService<UUID> {
}