-- Permissions for Habitaciones
INSERT INTO permissions (id, module, action, description, created_at) VALUES 
('18625691-88f5-47e9-867c-179379854495', 'Habitaciones', 'Ver', 'Permiso para ver habitaciones', NOW()),
('3297a7a5-673e-4649-8d8a-6750346a0845', 'Habitaciones', 'Crear', 'Permiso para crear habitaciones', NOW()),
('d5006b50-d46f-4098-9584-7a3c7f3e2e83', 'Habitaciones', 'Editar', 'Permiso para editar habitaciones', NOW()),
('922904e5-950c-4034-8c83-06a1476e3f42', 'Habitaciones', 'Eliminar', 'Permiso para eliminar habitaciones', NOW())
ON CONFLICT (id) DO NOTHING;

-- Permissions for Clientes
INSERT INTO permissions (id, module, action, description, created_at) VALUES 
('2215e985-364e-4f7c-9b16-928646736486', 'Clientes', 'Ver', 'Permiso para ver clientes', NOW()),
('0b58e72c-297c-4740-8f9d-8d5945284347', 'Clientes', 'Crear', 'Permiso para crear clientes', NOW()),
('8e4d291e-f3f1-4384-8141-9457313a261a', 'Clientes', 'Editar', 'Permiso para editar clientes', NOW()),
('f6c9d09c-76e3-4700-9e6e-21297e64a193', 'Clientes', 'Eliminar', 'Permiso para eliminar clientes', NOW())
ON CONFLICT (id) DO NOTHING;

-- Permissions for Roles y Permisos
INSERT INTO permissions (id, module, action, description, created_at) VALUES 
('8f0f0896-1875-488b-b8c7-43c2c4d62164', 'Roles y Permisos', 'Ver', 'Permiso para ver roles y permisos', NOW()),
('2b8f8a84-7546-4488-8422-793540c49615', 'Roles y Permisos', 'Crear', 'Permiso para crear roles y permisos', NOW()),
('09c6d56d-e462-4217-9154-2975979c3d9a', 'Roles y Permisos', 'Editar', 'Permiso para editar roles y permisos', NOW()),
('5b29c91f-8898-4443-8588-440266016147', 'Roles y Permisos', 'Eliminar', 'Permiso para eliminar roles y permisos', NOW())
ON CONFLICT (id) DO NOTHING;

-- Permissions for Reservas
INSERT INTO permissions (id, module, action, description, created_at) VALUES 
('d4e2a14b-70c1-426c-829d-649089069632', 'Reservas', 'Ver', 'Permiso para ver reservas', NOW()),
('37a7f45c-20c3-424a-93f5-937243c24194', 'Reservas', 'Crear', 'Permiso para crear reservas', NOW()),
('10e9f456-11f4-4148-8998-386477196345', 'Reservas', 'Editar', 'Permiso para editar reservas', NOW()),
('9154e190-6744-4691-8402-454567845345', 'Reservas', 'Eliminar', 'Permiso para eliminar reservas', NOW())
ON CONFLICT (id) DO NOTHING;

-- Permissions for Usuarios
INSERT INTO permissions (id, module, action, description, created_at) VALUES 
('6f9c2d11-57c4-4245-8c7c-482439343754', 'Usuarios', 'Ver', 'Permiso para ver usuarios', NOW()),
('3429e710-85f5-47e1-9543-529068475249', 'Usuarios', 'Crear', 'Permiso para crear usuarios', NOW()),
('189c47a0-9e45-4248-8432-843290632483', 'Usuarios', 'Editar', 'Permiso para editar usuarios', NOW()),
('4932c10a-374c-4785-8c43-248324832483', 'Usuarios', 'Eliminar', 'Permiso para eliminar usuarios', NOW())
ON CONFLICT (id) DO NOTHING;

-- Permissions for Pagos
INSERT INTO permissions (id, module, action, description, created_at) VALUES 
('c293c10a-74c3-4328-8432-843290632483', 'Pagos', 'Ver', 'Permiso para ver pagos', NOW()),
('8432c10a-374c-4785-8c43-248324832483', 'Pagos', 'Crear', 'Permiso para crear pagos', NOW()),
('9432c10a-374c-4785-8c43-248324832483', 'Pagos', 'Editar', 'Permiso para editar pagos', NOW()),
('a432c10a-374c-4785-8c43-248324832483', 'Pagos', 'Eliminar', 'Permiso para eliminar pagos', NOW())
ON CONFLICT (id) DO NOTHING;

-- Roles
INSERT INTO roles (id, name, description, is_active, created_at, created_by, updated_at, updated_by) VALUES 
('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', 'ADMINISTRADOR', 'Acceso total a todos los módulos del sistema', true, NOW(), 'SYSTEM', NOW(), 'SYSTEM')
ON CONFLICT (id) DO NOTHING;

-- Role Permissions for ADMINISTRADOR
INSERT INTO role_permissions (role_id, permission_id) VALUES 
('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', '18625691-88f5-47e9-867c-179379854495'),
('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', '3297a7a5-673e-4649-8d8a-6750346a0845'),
('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', 'd5006b50-d46f-4098-9584-7a3c7f3e2e83'),
('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', '922904e5-950c-4034-8c83-06a1476e3f42'),
('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', '2215e985-364e-4f7c-9b16-928646736486'),
('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', '0b58e72c-297c-4740-8f9d-8d5945284347'),
('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', '8e4d291e-f3f1-4384-8141-9457313a261a'),
('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', 'f6c9d09c-76e3-4700-9e6e-21297e64a193'),
('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', '8f0f0896-1875-488b-b8c7-43c2c4d62164'),
('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', '2b8f8a84-7546-4488-8422-793540c49615'),
('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', '09c6d56d-e462-4217-9154-2975979c3d9a'),
('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', '5b29c91f-8898-4443-8588-440266016147'),
('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', 'd4e2a14b-70c1-426c-829d-649089069632'),
('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', '37a7f45c-20c3-424a-93f5-937243c24194'),
('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', '10e9f456-11f4-4148-8998-386477196345'),
('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', '9154e190-6744-4691-8402-454567845345'),
('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', '6f9c2d11-57c4-4245-8c7c-482439343754'),
('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', '3429e710-85f5-47e1-9543-529068475249'),
('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', '189c47a0-9e45-4248-8432-843290632483'),
('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', '4932c10a-374c-4785-8c43-248324832483'),
('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', 'c293c10a-74c3-4328-8432-843290632483'),
('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', '8432c10a-374c-4785-8c43-248324832483'),
('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', '9432c10a-374c-4785-8c43-248324832483'),
('a7b6c5d4-e3f2-a1b0-c9d8-e7f6a5b4c3d2', 'a432c10a-374c-4785-8c43-248324832483')
ON CONFLICT (role_id, permission_id) DO NOTHING;

