INSERT INTO roles (name, description) VALUES
                                          ('ADMIN', 'Administrador'),
                                          ('USER', 'Usuário'),
                                          ('SELLER', 'Vendedor')
    ON CONFLICT (name) DO NOTHING;
