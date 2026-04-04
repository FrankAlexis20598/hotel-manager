-- Permissions for Habitaciones
INSERT INTO permissions (id, module, action, description, created_at)
VALUES ('18625691-88f5-47e9-867c-179379854495', 'Habitaciones', 'ver_activos', 'Permiso para ver habitaciones activas', NOW()),
       ('f5c19359-49ea-4731-947d-ac3e2abe882e', 'Habitaciones', 'ver_inactivos', 'Permiso para ver habitaciones inactivas', NOW()),
       ('3297a7a5-673e-4649-8d8a-6750346a0845', 'Habitaciones', 'crear', 'Permiso para crear habitaciones', NOW()),
       ('d5006b50-d46f-4098-9584-7a3c7f3e2e83', 'Habitaciones', 'editar', 'Permiso para editar habitaciones', NOW()),
       ('922904e5-950c-4034-8c83-06a1476e3f42', 'Habitaciones', 'cambiar_estado', 'Permiso para cambiar el estado de habitaciones', NOW()),
       ('2df454b8-3bed-432a-bfb4-0e160405be58', 'Habitaciones', 'desactivar', 'Permiso para desactivar habitaciones', NOW()),
       ('5ec5ac5f-6bfb-4cce-bdf5-8c0d827802fe', 'Habitaciones', 'reactivar', 'Permiso para reactivar habitaciones', NOW())
ON CONFLICT (id) DO NOTHING;

-- Permissions for Clientes
INSERT INTO permissions (id, module, action, description, created_at)
VALUES ('2215e985-364e-4f7c-9b16-928646736486', 'Clientes', 'ver_activos', 'Permiso para ver clientes activos', NOW()),
       ('b09432a5-dd46-4c6d-8b7e-3cdf596c794c', 'Clientes', 'ver_inactivos', 'Permiso para ver clientes inactivos', NOW()),
       ('0b58e72c-297c-4740-8f9d-8d5945284347', 'Clientes', 'crear', 'Permiso para crear clientes', NOW()),
       ('8e4d291e-f3f1-4384-8141-9457313a261a', 'Clientes', 'editar', 'Permiso para editar clientes', NOW()),
       ('f6c9d09c-76e3-4700-9e6e-21297e64a193', 'Clientes', 'desactivar', 'Permiso para desactivar clientes', NOW()),
       ('4f439f08-1a4d-40c5-83ad-edc8a26d9edb', 'Clientes', 'reactivar', 'Permiso para reactivar clientes', NOW())
ON CONFLICT (id) DO NOTHING;

-- Permissions for Roles y Permisos
INSERT INTO permissions (id, module, action, description, created_at)
VALUES ('8f0f0896-1875-488b-b8c7-43c2c4d62164', 'Roles', 'ver', 'Permiso para ver roles activos', NOW()),
       ('2b8f8a84-7546-4488-8422-793540c49615', 'Roles', 'crear', 'Permiso para crear roles', NOW()),
       ('09c6d56d-e462-4217-9154-2975979c3d9a', 'Roles', 'editar', 'Permiso para editar roles', NOW()),
       ('5b29c91f-8898-4443-8588-440266016147', 'Roles', 'desactivar', 'Permiso para desactivar roles', NOW()),
       ('0e579cb1-3b67-4649-b416-c893094f9f67', 'Roles', 'reactivar', 'Permiso para reactivar roles', NOW()),
       ('54e4868b-2a66-4a33-9723-54def559b099', 'Permisos', 'ver', 'Permiso para ver permisos', NOW())
ON CONFLICT (id) DO NOTHING;

-- Permissions for Reservas
INSERT INTO permissions (id, module, action, description, created_at)
VALUES ('d4e2a14b-70c1-426c-829d-649089069632', 'Reservas', 'ver', 'Permiso para ver reservas', NOW()),
       ('37a7f45c-20c3-424a-93f5-937243c24194', 'Reservas', 'crear', 'Permiso para crear reservas', NOW()),
       ('10e9f456-11f4-4148-8998-386477196345', 'Reservas', 'editar', 'Permiso para editar reservas', NOW()),
       ('9154e190-6744-4691-8402-454567845345', 'Reservas', 'confirmar', 'Permiso para confirmar reservas', NOW()),
       ('7fb7a068-0a62-4941-b441-9de45d619043', 'Reservas', 'cancelar', 'Permiso para cancelar reservas', NOW()),
       ('5e171fcc-fc5f-4832-aa22-4ffdbf6597fb', 'Reservas', 'finalizar', 'Permiso para finalizar reservas', NOW())
ON CONFLICT (id) DO NOTHING;

-- Permissions for Usuarios
INSERT INTO permissions (id, module, action, description, created_at)
VALUES ('6f9c2d11-57c4-4245-8c7c-482439343754', 'Usuarios', 'ver', 'Permiso para ver usuarios', NOW()),
       ('3429e710-85f5-47e1-9543-529068475249', 'Usuarios', 'crear', 'Permiso para crear usuarios', NOW()),
       ('189c47a0-9e45-4248-8432-843290632483', 'Usuarios', 'editar', 'Permiso para editar usuarios', NOW()),
       ('1a7aad36-096f-4e32-97ce-ae68756bc867', 'Usuarios', 'desactivar', 'Permiso para desactivar usuarios', NOW()),
       ('4932c10a-374c-4785-8c43-248324832483', 'Usuarios', 'reactivar', 'Permiso para reactivar usuarios', NOW())
ON CONFLICT (id) DO NOTHING;

-- Permissions for Pagos
INSERT INTO permissions (id, module, action, description, created_at)
VALUES ('c293c10a-74c3-4328-8432-843290632483', 'Pagos', 'ver', 'Permiso para ver pagos', NOW()),
       ('8432c10a-374c-4785-8c43-248324832483', 'Pagos', 'crear', 'Permiso para crear pagos', NOW()),
       ('a432c10a-374c-4785-8c43-248324832483', 'Pagos', 'anular', 'Permiso para anular pagos', NOW())
ON CONFLICT (id) DO NOTHING;

-- Roles
INSERT INTO roles (id, name, description, is_active, created_at, created_by, updated_at, updated_by)
VALUES ('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', 'ADMINISTRADOR', 'Acceso total a todos los módulos del sistema', true,
        NOW(), 'SYSTEM', NOW(), 'SYSTEM')
ON CONFLICT (id) DO NOTHING;

-- Role Permissions for ADMINISTRADOR
INSERT INTO role_permissions (role_id, permission_id)
VALUES ('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', '18625691-88f5-47e9-867c-179379854495'), -- Habitaciones: ver_activos
       ('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', 'f5c19359-49ea-4731-947d-ac3e2abe882e'), -- Habitaciones: ver_inactivos
       ('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', '3297a7a5-673e-4649-8d8a-6750346a0845'), -- Habitaciones: crear
       ('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', 'd5006b50-d46f-4098-9584-7a3c7f3e2e83'), -- Habitaciones: editar
       ('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', '922904e5-950c-4034-8c83-06a1476e3f42'), -- Habitaciones: cambiar_estado
       ('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', '2df454b8-3bed-432a-bfb4-0e160405be58'), -- Habitaciones: desactivar
       ('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', '5ec5ac5f-6bfb-4cce-bdf5-8c0d827802fe'), -- Habitaciones: reactivar
       ('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', '2215e985-364e-4f7c-9b16-928646736486'), -- Clientes: ver_activos
       ('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', 'b09432a5-dd46-4c6d-8b7e-3cdf596c794c'), -- Clientes: ver_inactivos
       ('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', '0b58e72c-297c-4740-8f9d-8d5945284347'), -- Clientes: crear
       ('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', '8e4d291e-f3f1-4384-8141-9457313a261a'), -- Clientes: editar
       ('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', 'f6c9d09c-76e3-4700-9e6e-21297e64a193'), -- Clientes: desactivar
       ('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', '4f439f08-1a4d-40c5-83ad-edc8a26d9edb'), -- Clientes: reactivar
       ('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', '8f0f0896-1875-488b-b8c7-43c2c4d62164'), -- Roles: ver
       ('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', '2b8f8a84-7546-4488-8422-793540c49615'), -- Roles: crear
       ('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', '09c6d56d-e462-4217-9154-2975979c3d9a'), -- Roles: editar
       ('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', '5b29c91f-8898-4443-8588-440266016147'), -- Roles: desactivar
       ('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', '0e579cb1-3b67-4649-b416-c893094f9f67'), -- Roles: reactivar
       ('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', '54e4868b-2a66-4a33-9723-54def559b099'), -- Permisos: ver
       ('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', 'd4e2a14b-70c1-426c-829d-649089069632'), -- Reservas: ver
       ('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', '37a7f45c-20c3-424a-93f5-937243c24194'), -- Reservas: crear
       ('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', '10e9f456-11f4-4148-8998-386477196345'), -- Reservas: editar
       ('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', '9154e190-6744-4691-8402-454567845345'), -- Reservas: confirmar
       ('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', '7fb7a068-0a62-4941-b441-9de45d619043'), -- Reservas: cancelar
       ('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', '5e171fcc-fc5f-4832-aa22-4ffdbf6597fb'), -- Reservas: finalizar
       ('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', '6f9c2d11-57c4-4245-8c7c-482439343754'), -- Usuarios: ver
       ('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', '3429e710-85f5-47e1-9543-529068475249'), -- Usuarios: crear
       ('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', '189c47a0-9e45-4248-8432-843290632483'), -- Usuarios: editar
       ('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', '1a7aad36-096f-4e32-97ce-ae68756bc867'), -- Usuarios: desactivar
       ('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', '4932c10a-374c-4785-8c43-248324832483'), -- Usuarios: reactivar
       ('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', 'c293c10a-74c3-4328-8432-843290632483'), -- Pagos: ver
       ('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', '8432c10a-374c-4785-8c43-248324832483'), -- Pagos: crear
       ('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', 'a432c10a-374c-4785-8c43-248324832483')  -- Pagos: anular
ON CONFLICT (role_id, permission_id) DO NOTHING;

-- Users
-- Password for 'admin@hotelmanager.com' is 'admin123' (BCrypt encrypted)
INSERT INTO users (id, email, password, is_active, role_id, created_at, created_by, updated_at, updated_by)
VALUES ('e3b0c442-98fc-1c14-9afb-f4c8996fb924', 'admin@hotelmanager.com', '$2a$12$z5HKe/ZRTCvcOPdBkCgAw.EySqGOIWyQjkv9umU1ogKHqKqNpZ6c.', true,
        'a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', NOW(), 'SYSTEM', NOW(), 'SYSTEM')
ON CONFLICT (id) DO NOTHING;
