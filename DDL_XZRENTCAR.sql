CREATE TABLE xzrentcar.info_renta (
    id_renta int not null auto_increment,
    dni VARCHAR(127),
    nombres VARCHAR(127),
    modelo VARCHAR(127),
    marca VARCHAR(127),
    fe_nacimiento DATE,
    tiene_tc VARCHAR(3),
        usr_creacion varchar(63),
    estado varchar(15),
    fecha_modificacion date,
    usr_modificacion varchar(63),
    PRIMARY KEY (id_renta)
);