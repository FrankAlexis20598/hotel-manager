package com.devfrank.hotelmanager.users.controller;

import com.devfrank.hotelmanager.users.dto.request.CreateAppUserRequest;
import com.devfrank.hotelmanager.users.dto.request.UpdateAppUserRequest;
import com.devfrank.hotelmanager.users.dto.response.AppUserResponse;
import com.devfrank.hotelmanager.users.service.AppUserService;
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
@RequestMapping(path = "/api/v1/users")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping
    public ResponseEntity<List<AppUserResponse>> findAll() {
        List<AppUserResponse> users = appUserService.findAll().stream()
                .map(AppUserResponse::fromDTO)
                .toList();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppUserResponse> findById(@PathVariable UUID id) {
        AppUserResponse user = AppUserResponse.fromDTO(appUserService.findById(id));
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<AppUserResponse> create(@RequestBody CreateAppUserRequest request) {
        AppUserResponse user = AppUserResponse.fromDTO(appUserService.create(request.toCommand()));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.id())
                .toUri();
        return ResponseEntity.created(location).body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppUserResponse> update(@PathVariable UUID id, @RequestBody UpdateAppUserRequest request) {
        AppUserResponse user = AppUserResponse.fromDTO(appUserService.update(id, request.toCommand()));
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        appUserService.delete(id);
        return ResponseEntity.noContent().build();
    }
}