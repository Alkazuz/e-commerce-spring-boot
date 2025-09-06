INSERT INTO users (id, name, email, password, enabled)
VALUES (
           gen_random_uuid(),
           'Administrador',
           'admin@dev.com',
           '$2a$10$e.bF7z1Z2gQb7ttbqf1e9Oe9r1J8dF2a0wFZl9kO4s6X7Y2i7m8/m', -- senha: admin123
           TRUE
       )
    ON CONFLICT (email) DO NOTHING;

INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u, roles r
WHERE u.email = 'admin@dev.com'
  AND r.name = 'ADMIN'
    ON CONFLICT DO NOTHING;
