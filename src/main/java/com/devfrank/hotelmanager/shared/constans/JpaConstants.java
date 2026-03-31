package com.devfrank.hotelmanager.shared.constans;

public class JpaConstants {
    // TABLES
    public static final String ROOMS_TABLE = "rooms";
    public static final String CUSTOMERS_TABLE = "customers";
    public static final String PERMISSIONS_TABLE = "permissions";
    public static final String ROLES_TABLE = "roles";
    public static final String ROLE_PERMISSIONS_TABLE = "role_permissions";

    // COLUMNS
    public static final String ROLE_ID_COLUMN = "role_id";
    public static final String PERMISSION_ID_COLUMN = "permission_id";

    // CONSTRAINTS
    public static final String UK_ROOMS_NUMBER = "uk_rooms_number";
    public static final String UK_PERMISSIONS_MODULE_ACTION = "uk_permissions_module_action";
    public static final String UK_ROLES_NAME = "uk_roles_name";
}