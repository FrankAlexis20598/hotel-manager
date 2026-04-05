package com.devfrank.hotelmanager.shared.constans;

public class JpaConstants {

    private JpaConstants() {
        throw new IllegalStateException("Utility class");
    }

    // TABLES
    public static final String ROOMS_TABLE = "rooms";
    public static final String CUSTOMERS_TABLE = "customers";
    public static final String PERMISSIONS_TABLE = "permissions";
    public static final String ROLES_TABLE = "roles";
    public static final String ROLE_PERMISSIONS_TABLE = "role_permissions";
    public static final String USERS_TABLE = "users";
    public static final String REFRESH_TOKENS_TABLE = "refresh_tokens";

    // TABLE COLUMNS
    public static final String ROLE_ID_COLUMN = "role_id";
    public static final String USER_ID_COLUMN = "user_id";
    public static final String PERMISSION_ID_COLUMN = "permission_id";

    // CONSTRAINTS
    public static final String UK_ROOMS_NUMBER = "uk_rooms_number";
    public static final String UK_PERMISSIONS_MODULE_ACTION = "uk_permissions_module_action";
    public static final String UK_ROLES_NAME = "uk_roles_name";
    public static final String UK_USERS_EMAIL = "uk_users_email";
    public static final String UK_CUSTOMERS_EMAIL = "uk_customers_email";
    public static final String UK_CUSTOMERS_DOCUMENT_NUMBER = "uk_customers_document_number";
    public static final String UK_REFRESH_TOKENS_TOKEN = "uk_refresh_tokens_token";

    // FOREIGN KEY
    public static final String FK_USERS_ROLES = "fk_users_roles";
    public static final String FK_REFRESH_TOKENS_USERS = "fk_refresh_tokens_users";
}