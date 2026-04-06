package com.devfrank.hotelmanager.access.service.api;

import java.util.List;
import java.util.UUID;

public interface RoleUsageValidator {
    List<String> getUsageIdentities(UUID roleId);

    String getResourceName();
}