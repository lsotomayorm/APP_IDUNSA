
INSERT IGNORE INTO usuario (cui, nombre, apellido, email, password, escuela, rol)
VALUES (123, 'Admin', 'Admin', 'admin@unsa.edu.com', 'admin123', '', 'ADMIN');



INSERT IGNORE INTO escuela (nombre) VALUES ('Ingenieria de Sistemas');
INSERT IGNORE INTO escuela (nombre) VALUES ('Ingenieria Industrial');
INSERT IGNORE INTO escuela (nombre) VALUES ('Ingenieria Civil');
INSERT IGNORE INTO escuela (nombre) VALUES ('Ingenieria Electronica');
INSERT IGNORE INTO escuela (nombre) VALUES ('Ingenieria Mecánica');
INSERT IGNORE INTO escuela (nombre) VALUES ('Ingenieria Ambiental');
INSERT IGNORE INTO escuela (nombre) VALUES ('Arquitectura');
INSERT IGNORE INTO escuela (nombre) VALUES ('Administracion de Empresas');
INSERT IGNORE INTO escuela (nombre) VALUES ('Contabilidad');
INSERT IGNORE INTO escuela (nombre) VALUES ('Economia');
INSERT IGNORE INTO escuela (nombre) VALUES ('Derecho');
INSERT IGNORE INTO escuela (nombre) VALUES ('Psicologia');
INSERT IGNORE INTO escuela (nombre) VALUES ('Educacion');
INSERT IGNORE INTO escuela (nombre) VALUES ('Medicina');
INSERT IGNORE INTO escuela (nombre) VALUES ('Ciencias de la Comunicacion');
INSERT IGNORE INTO escuela (nombre) VALUES ('Turismo y Hoteleria');
INSERT IGNORE INTO escuela (nombre) VALUES ('Ciencias de la Computacion');



INSERT IGNORE INTO deporte (nombre, jugadores, suplentes, tipo) VALUES ('Futbolito', 7, 2, "Grupal");
INSERT IGNORE INTO deporte (nombre, jugadores, suplentes, tipo) VALUES ('Voley', 6, 2, "Grupal");
INSERT IGNORE INTO deporte (nombre, jugadores, suplentes, tipo) VALUES ('Basquet', 5, 2, "Grupal");
INSERT IGNORE INTO deporte (nombre, jugadores, suplentes, tipo) VALUES ('Tenis', 2, 0, "Grupal");
INSERT IGNORE INTO deporte (nombre, jugadores, suplentes, tipo) VALUES ('Ajedrez', 1, 0, "Individual");
INSERT IGNORE INTO deporte (nombre, jugadores, suplentes, tipo) VALUES ('Speedcubing', 1, 0, "Individual");
