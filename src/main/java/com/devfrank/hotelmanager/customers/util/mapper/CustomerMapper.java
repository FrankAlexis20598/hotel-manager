package com.devfrank.hotelmanager.customers.util.mapper;

import com.devfrank.hotelmanager.customers.dto.CustomerDTO;
import com.devfrank.hotelmanager.customers.dto.request.SaveCustomerRequest;
import com.devfrank.hotelmanager.customers.entity.Customer;
import com.devfrank.hotelmanager.customers.util.enums.DocumentType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDTO toDTO(Customer customer);

    @Mapping(target = "id", expression = "java(defaultValueForId())")
    @Mapping(target = "isActive", expression = "java(defaultValueForIsActive())")
    @Mapping(target = "documentType", qualifiedByName = "stringToDocumentType")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    Customer toEntity(SaveCustomerRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "documentType", qualifiedByName = "stringToDocumentType")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateEntity(SaveCustomerRequest request, @MappingTarget Customer customer);

    @Named("stringToDocumentType")
    default DocumentType stringToDocumentType(String value) {
        return DocumentType.fromValue(value);
    }

    @Named("defaultValueForId")
    default UUID defaultValueForId() {
        return UUID.randomUUID();
    }

    @Named("defaultValueForIsActive")
    default boolean defaultValueForIsActive() {
        return Boolean.TRUE;
    }
}