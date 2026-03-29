package com.devfrank.hotelmanager.customers.controller;

import com.devfrank.hotelmanager.customers.dto.request.SaveCustomerRequest;
import com.devfrank.hotelmanager.customers.dto.response.CustomerResponse;
import com.devfrank.hotelmanager.customers.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> findAll() {
        List<CustomerResponse> customers = customerService.findAll().stream()
                .map(CustomerResponse::fromDTO)
                .toList();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> findById(@PathVariable UUID id) {
        CustomerResponse customer = CustomerResponse.fromDTO(customerService.findById(id));
        return ResponseEntity.ok(customer);
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> create(@RequestBody @Valid SaveCustomerRequest request) {
        CustomerResponse customer = CustomerResponse.fromDTO(customerService.create(request.toCommand()));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(customer.id())
                .toUri();
        return ResponseEntity.created(location).body(customer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> update(@PathVariable UUID id, @RequestBody @Valid SaveCustomerRequest request) {
        CustomerResponse customer = CustomerResponse.fromDTO(customerService.update(id, request.toCommand()));
        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}