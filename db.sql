create database BankUTIM;
use BankUTIM;

-- identificador, nombre, apellidos,  domicilio, localidad, municipio, estado, CURP, RFC, teléfono 1, teléfono 2
-- drop table Cliente;
Create table Cliente(
	Id int auto_increment primary key,
	Nombre varchar(80) not null,
	Apellidos varchar(80) not null,
	Domicilio varchar(255) not null,
	Localidad varchar(156),
	Municipio varchar(156),
	Estado varchar(100),
	CURP varchar(20),
	RFC varchar(12),
	Telefono1 varchar(15),
	Telefono2 varchar(15)
);

insert into Cliente 
	(Nombre, Apellidos, Domicilio, Localidad, Municipio, Estado, CURP, RFC, Telefono1) 
values
	('Alejandro', 'Juárez Balbuena', 'Callejón del Olvido #13', 'Matzaco', 'Atlixco', 'Puebla', 'JUBA001200','JUBA001200', '1234567890'),
    ('Alma Rosa', 'Corona González', 'Primavera #35', 'Tlatlaxco', 'Tlatlaxco', 'Tlaxcala', 'COGA750523','COGA750523', '1234567890');

select * from Cliente;