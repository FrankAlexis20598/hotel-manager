package com.devfrank.hotelmanager.access.controller;

import com.devfrank.hotelmanager.access.dto.response.PermissionResponse;
import com.devfrank.hotelmanager.access.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @GetMapping("/grouped")
    public ResponseEntity<Map<String, List<PermissionResponse>>> getPermissionsByModule() {
        Map<String, List<PermissionResponse>> response = permissionService.getGroupedByModule().entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue()
                                .stream()
                                .map(PermissionResponse::fromDTO)
                                .toList()
                ));
        return ResponseEntity.ok(response);
    }
}