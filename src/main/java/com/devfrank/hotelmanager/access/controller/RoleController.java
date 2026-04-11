package com.devfrank.hotelmanager.access.controller;

import com.devfrank.hotelmanager.access.dto.filter.RoleCriteria;
import com.devfrank.hotelmanager.access.dto.request.SaveRoleRequest;
import com.devfrank.hotelmanager.access.dto.response.RoleResponse;
import com.devfrank.hotelmanager.access.service.RoleService;
import com.devfrank.hotelmanager.shared.response.PagedResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<PagedResponse<RoleResponse>> findAll(@Valid RoleCriteria criteria,
                                                               @PageableDefault Pageable pageable) {
        Page<RoleResponse> roles = roleService.findAllBy(criteria, pageable)
                .map(RoleResponse::fromDTO);
        return ResponseEntity.ok(PagedResponse.of(roles));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> findById(@PathVariable UUID id) {
        RoleResponse role = RoleResponse.fromDTO(roleService.findById(id));
        return ResponseEntity.ok(role);
    }

    @PostMapping
    public ResponseEntity<RoleResponse> create(@RequestBody @Valid SaveRoleRequest request) {
        RoleResponse role = RoleResponse.fromDTO(roleService.create(request));
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(role.id())
                .toUri();
        return ResponseEntity.created(uri).body(role);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponse> update(@PathVariable UUID id,
                                               @RequestBody @Valid SaveRoleRequest request) {
        RoleResponse role = RoleResponse.fromDTO(roleService.update(id, request));
        return ResponseEntity.ok(role);
    }

    @PatchMapping("/{id}/reactivate")
    public ResponseEntity<Void> reactivate(@PathVariable UUID id) {
        roleService.reactivate(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable UUID id) {
        roleService.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}