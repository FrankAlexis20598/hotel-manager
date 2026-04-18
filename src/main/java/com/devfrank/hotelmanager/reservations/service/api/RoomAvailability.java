package com.devfrank.hotelmanager.reservations.service.api;

import java.math.BigDecimal;
import java.util.UUID;

public interface RoomAvailability {
    BigDecimal verifyAndGetPrice(UUID roomId);
}