package com.devfrank.hotelmanager.access.dto.request;

import com.devfrank.hotelmanager.access.dto.command.SaveRoleCommand;
import com.devfrank.hotelmanager.shared.constans.ValidationConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record SaveRoleRequest(
        @NotBlank(message = ValidationConstants.ROLE_NAME_NOT_BLANK)
        @Size(max = 50, message = ValidationConstants.ROLE_NAME_SIZE)
        String name,

        @NotBlank(message = ValidationConstants.ROLE_DESCRIPTION_NOT_BLANK)
        @Size(max = 255, message = ValidationConstants.ROLE_DESCRIPTION_SIZE)
        String description,

        @NotEmpty(message = ValidationConstants.ROLE_PERMISSIONS_NOT_EMPTY)
        List<
                @NotBlank(message = ValidationConstants.ROLE_PERMISSIONS_ID_NOT_BLANK)
                @Size(min = 36, max = 36, message = ValidationConstants.ROLE_PERMISSIONS_ID_SIZE)
                @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", message = ValidationConstants.ROLE_PERMISSIONS_ID_UUID_FORMAT)
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
