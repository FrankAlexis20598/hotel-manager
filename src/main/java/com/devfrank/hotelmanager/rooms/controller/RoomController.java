package com.devfrank.hotelmanager.rooms.controller;

import com.devfrank.hotelmanager.rooms.dto.filter.RoomCriteria;
import com.devfrank.hotelmanager.rooms.dto.request.SaveRoomRequest;
import com.devfrank.hotelmanager.rooms.dto.request.UpdateRoomStatusRequest;
import com.devfrank.hotelmanager.rooms.dto.response.RoomResponse;
import com.devfrank.hotelmanager.rooms.service.RoomService;
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
@RequestMapping(path = "/api/v1/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<PagedResponse<RoomResponse>> findAll(@Valid RoomCriteria criteria,
                                                               @PageableDefault Pageable pageable) {
        Page<RoomResponse> rooms = roomService.findAllBy(criteria, pageable)
                .map(RoomResponse::fromDTO);
        return ResponseEntity.ok(PagedResponse.of(rooms));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> findById(@PathVariable UUID id) {
        RoomResponse room = RoomResponse.fromDTO(roomService.findById(id));
        return ResponseEntity.ok(room);
    }

    @PostMapping
    public ResponseEntity<RoomResponse> create(@RequestBody @Valid SaveRoomRequest request) {
        RoomResponse room = RoomResponse.fromDTO(roomService.create(request));
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(room.id())
                .toUri();
        return ResponseEntity.created(location).body(room);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomResponse> update(@PathVariable UUID id,
                                               @RequestBody @Valid SaveRoomRequest request) {
        RoomResponse room = RoomResponse.fromDTO(roomService.update(id, request));
        return ResponseEntity.ok(room);
    }

    @PatchMapping("/{id}/reactivate")
    public ResponseEntity<Void> reactivate(@PathVariable UUID id) {
        roomService.reactivate(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable UUID id) {
        roomService.deactivate(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable UUID id,
                                             @RequestBody @Valid UpdateRoomStatusRequest request) {
        roomService.updateStatus(id, request);
        return ResponseEntity.noContent().build();
    }
}