package com.devfrank.hotelmanager.shared.constans;

import java.time.LocalTime;

public class UtilConstants {

    private UtilConstants() {
        throw new IllegalStateException("Utility class");
    }

    // FILTER PROPERTY VALUES
    public static final String IS_ACTIVE_FILTER_ALL = "all";
    public static final String IS_ACTIVE_FILTER_TRUE = "true";

    public static final String RESERVATION_NUMBER_PREFIX = "RES";
    public static final String RESERVATION_NUMBER_FORMAT = "%s-%s-%05d";
    public static final String RESERVATION_LOCAL_DATETIME_PATTERN = "dd/MM/yyyy HH:mm";

    // ENTITY FIELDS
    public static final String CREATE_AT_ATTRIBUTE = "createdAt";

    public static final LocalTime CHECK_OUT_LIMIT = LocalTime.of(12,0);
    public static final int GRACE_MINUTES = 30;
    public static final int FREE_CANCELLATION_LIMIT = 48;
}