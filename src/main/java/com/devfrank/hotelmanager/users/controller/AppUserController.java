package com.devfrank.hotelmanager.users.controller;

import com.devfrank.hotelmanager.shared.response.PagedResponse;
import com.devfrank.hotelmanager.users.dto.filter.AppUserCriteria;
import com.devfrank.hotelmanager.users.dto.request.SaveAppUserRequest;
import com.devfrank.hotelmanager.users.dto.response.AppUserResponse;
import com.devfrank.hotelmanager.users.service.AppUserService;
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
@RequestMapping(path = "/api/v1/users")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping
    public ResponseEntity<PagedResponse<AppUserResponse>> findAll(@Valid AppUserCriteria criteria,
                                                                  @PageableDefault Pageable pageable) {
        Page<AppUserResponse> users = appUserService.findAllBy(criteria, pageable)
                .map(AppUserResponse::fromDTO);
        return ResponseEntity.ok(PagedResponse.of(users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppUserResponse> findById(@PathVariable UUID id) {
        AppUserResponse user = AppUserResponse.fromDTO(appUserService.findById(id));
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<AppUserResponse> create(@RequestBody @Valid SaveAppUserRequest request) {
        AppUserResponse user = AppUserResponse.fromDTO(appUserService.create(request));
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.id())
                .toUri();
        return ResponseEntity.created(uri).body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppUserResponse> update(@PathVariable UUID id,
                                                  @RequestBody @Valid SaveAppUserRequest request) {
        AppUserResponse user = AppUserResponse.fromDTO(appUserService.update(id, request));
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{id}/reactivate")
    public ResponseEntity<Void> reactivate(@PathVariable UUID id) {
        appUserService.reactivate(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable UUID id) {
        appUserService.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}