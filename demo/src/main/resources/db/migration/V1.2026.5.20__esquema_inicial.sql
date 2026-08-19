

CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(200) NOT NULL,
    apellido VARCHAR(200) NOT NULL,
    email VARCHAR(200) NOT NULL,
    fecha_nacimiento DATE NOT NULL
);