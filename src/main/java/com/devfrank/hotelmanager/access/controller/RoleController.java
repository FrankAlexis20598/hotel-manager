package com.devfrank.hotelmanager.access.controller;

import com.devfrank.hotelmanager.access.dto.request.SaveRoleRequest;
import com.devfrank.hotelmanager.access.dto.response.RoleResponse;
import com.devfrank.hotelmanager.access.service.RoleService;
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
@RequestMapping(path = "/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleResponse>> findAll() {
        List<RoleResponse> roles = roleService.findAll().stream()
                .map(RoleResponse::fromDTO)
                .toList();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> findById(@PathVariable UUID id) {
        RoleResponse role = RoleResponse.fromDTO(roleService.findById(id));
        return ResponseEntity.ok(role);
    }

    @PostMapping
    public ResponseEntity<RoleResponse> create(@RequestBody @Valid SaveRoleRequest request) {
        RoleResponse role = RoleResponse.fromDTO(roleService.create(request.toCommand()));
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(role.id())
                .toUri();
        return ResponseEntity.created(uri).body(role);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponse> update(@PathVariable UUID id, @RequestBody @Valid SaveRoleRequest request) {
        RoleResponse role = RoleResponse.fromDTO(roleService.update(id, request.toCommand()));
        return ResponseEntity.ok(role);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}