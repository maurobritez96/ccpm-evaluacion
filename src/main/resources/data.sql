-- Insertar usuario inicial
INSERT INTO users (name, email, password, created, modified, last_login, token, is_active)
VALUES (
    'Juan Perez',
    'juan.perez@correo.cl',
    'Password123!',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWFuLnBlcmV6QGNvcnJlby5jbCJ9.signature_ejemplo',
    true
);

-- Insertar teléfonos asociados al usuario creado (id = 1)
INSERT INTO phones (number, city_code, contry_code, user_id)
VALUES ('1234567', '1', '57', 1);

INSERT INTO phones (number, city_code, contry_code, user_id)
VALUES ('8765432', '2', '57', 1);