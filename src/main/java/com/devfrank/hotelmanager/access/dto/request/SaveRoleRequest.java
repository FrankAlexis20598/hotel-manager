package com.devfrank.hotelmanager.access.dto.request;

import com.devfrank.hotelmanager.access.dto.command.SaveRoleCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record SaveRoleRequest(
        @NotBlank(message = "{role.name.not_blank}")
        @Size(max = 50, message = "{role.name.size}")
        String name,

        @NotBlank(message = "{role.description.not_blank}")
        @Size(max = 255, message = "{role.description.size}")
        String description,

        @NotEmpty(message = "{role.permissions.not_empty}")
        List<
                @NotBlank(message = "{role.permissions.id.not_blank}")
                @Size(min = 36, max = 36, message = "{role.permissions.id.size}")
                @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", message = "{role.permissions.id.uuid_format}")
                        String> permissions
) {
    public SaveRoleCommand toCommand() {
        return new SaveRoleCommand(
                name,
                description,
                permissions.stream()
                        .map(UUID::fromString)
                        .toList()
        );
    }
}