DROP TABLE prestamos IF EXISTS;

CREATE TABLE prestamos (
   prestamo_id BIGINT IDENTITY NOT NULL PRIMARY KEY,
   nombre VARCHAR(255) NOT NULL,
   apellido VARCHAR (255) NOT NULL,
   dni BIGINT NOT NULL,
   fechaComienzo DATE NOT NULL,
   fechaVencimiento DATE NOT NULL,
   monto DOUBLE NOT NULL
   )