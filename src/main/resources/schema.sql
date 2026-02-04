--Primero se deben borrar todas las tablas (de detalle a maestro) y lugo anyadirlas (de maestro a detalle)
--(en este caso en cada aplicacion se usa solo una tabla, por lo que no hace falta)

--Para giis.demo.tkrun:
drop table Carreras;
create table Carreras (
	id int primary key not null, 
	inicio date not null, 
	fin date not null, 
	fecha date not null, 
	descr varchar(32), 
	check(inicio<=fin), 
	check(fin<fecha)
);

drop table Registrations;
drop table Athletes;
drop table Competitions;

--Pal homework
create table Competitions (
    id int primary key not null,
    name text not null,
    date not null,
    description varchar(32)
);

create table Athletes (
    id int primary key not null,
    dni varchar(9),
    name text not null,
    email text
);

create table Registrations (
    competition_id int,
    athlete_id int,
    registration_date date,
    PRIMARY KEY(competition_id, athlete_id),
    FOREIGN KEY(competition_id) REFERENCES Competitions(id),
    FOREIGN KEY(athlete_id) REFERENCES Athletes(id)
);


INSERT INTO Competitions (name, date, description) VALUES ('Carrera San Silvestre', '2023-12-31', 'Carrera fin de año');
INSERT INTO Competitions (name, date, description) VALUES ('Maratón Gijón', '2024-05-15', '42km por la costa');