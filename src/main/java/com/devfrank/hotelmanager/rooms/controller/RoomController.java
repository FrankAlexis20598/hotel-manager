package com.devfrank.hotelmanager.rooms.controller;

import com.devfrank.hotelmanager.rooms.dto.request.CreateRoomRequest;
import com.devfrank.hotelmanager.rooms.dto.request.UpdateRoomRequest;
import com.devfrank.hotelmanager.rooms.dto.response.RoomResponse;
import com.devfrank.hotelmanager.rooms.service.RoomService;
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
@RequestMapping(path = "/api/v1/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<List<RoomResponse>> findAll() {
        List<RoomResponse> rooms = roomService.findAll().stream()
                .map(RoomResponse::fromDTO)
                .toList();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> findById(@PathVariable UUID id) {
        RoomResponse room = RoomResponse.fromDTO(roomService.findById(id));
        return ResponseEntity.ok(room);
    }

    @PostMapping
    public ResponseEntity<RoomResponse> create(@RequestBody @Valid CreateRoomRequest request) {
        RoomResponse room = RoomResponse.fromDTO(roomService.create(request.toCommand()));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(room.id())
                .toUri();
        return ResponseEntity.created(location).body(room);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomResponse> update(@PathVariable UUID id, @RequestBody @Valid UpdateRoomRequest request) {
        RoomResponse room = RoomResponse.fromDTO(roomService.update(id, request.toCommand()));
        return ResponseEntity.ok(room);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        roomService.delete(id);
        return ResponseEntity.noContent().build();
    }
}