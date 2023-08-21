-- Crear la tabla de usuarios (modificada para incluir el nombre)
CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    alias VARCHAR(100) NOT NULL UNIQUE,
    pass INT NOT NULL,
    saldo DOUBLE NOT NULL
);

-- Crear la tabla de historico
CREATE TABLE IF NOT EXISTS historico (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT,
    tipo_operacion VARCHAR(50) NOT NULL,
    cantidad DOUBLE,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- Insertar datos de ejemplo
-- Insertar datos de ejemplo en usuarios
INSERT INTO usuarios (nombre, alias, pass, saldo) VALUES
('Juan Perez', 'jperez', 1234, 1000.0),
('Ana Ramirez', 'aramirez', 5678, 2500.0),
('Carlos Gomez', 'cgomez', 9012, 500.0),
('Marta Torres', 'mtorrez', 3456, 750.0),
('Luisa Fernandez', 'lfernandez', 7890, 3000.0);

-- Insertar datos de ejemplo en historico (asumiendo que los IDs de los usuarios coinciden con los valores insertados anteriormente)
-- Juan Perez hizo un depósito de 200.0
INSERT INTO historico (usuario_id, tipo_operacion, cantidad) VALUES
(1, 'deposito', 1000.0),
(2, 'deposito', 2500.0),
(3, 'deposito', 500.0),
(4, 'deposito', 750.0),
(5, 'deposito', 3000.0);
