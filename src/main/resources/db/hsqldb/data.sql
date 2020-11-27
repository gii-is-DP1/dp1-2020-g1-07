-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');
-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('owner1','0wn3r',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (2,'owner1','owner');
-- One vet user, named vet1 with passwor v3t
INSERT INTO users(username,password,enabled) VALUES ('vet1','v3t',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (3,'vet1','veterinarian');



INSERT INTO GAMETYPES VALUES(1,'Roullete');
INSERT INTO GAMETYPES VALUES(2, 'Cards');
INSERT INTO GAMETYPES VALUES(3,'Dices');
INSERT INTO SKILLS VALUES(1,'AMATEUR');
INSERT INTO SKILLS VALUES(2,'PROFFESIONAL');
INSERT INTO games VALUES (1,'Poker',8,2);



INSERT INTO CASINOTABLE VALUES(1,'Mesa 1',1,2,1);

INSERT INTO employees(id,dni,name,phone_number) VALUES (1,'12345678a','Manuel Rodríguez','987654321');
INSERT INTO employees(id,dni,name,phone_number) VALUES (2,'98765432z','Rosa García','123456789');
INSERT INTO employees(id,dni,name,phone_number) VALUES (3,'30987456m','Jorge Martín','567891234');

INSERT INTO administrators(id,dni,name,phone_number) VALUES (1,'12345677a','Natalia Rodríguez','987654320');
INSERT INTO administrators(id,dni,name,phone_number) VALUES (2,'98765431z','Quique García','123456788');
INSERT INTO administrators(id,dni,name,phone_number) VALUES (3,'30987455m','Luis Martín','567891233');

INSERT INTO chefs(id,dni,name,phone_number) VALUES (1,'12345676a','Ofelia Rodríguez','987654319');
INSERT INTO chefs(id,dni,name,phone_number) VALUES (2,'98765430z','Raúl García','123456787');
INSERT INTO chefs(id,dni,name,phone_number) VALUES (3,'30987454m','María Martín','567891232');

INSERT INTO cooks(id,dni,name,phone_number) VALUES (1,'12345675a','Paula Rodríguez','987654318');
INSERT INTO cooks(id,dni,name,phone_number) VALUES (2,'98765420z','Sergio García','123456786');
INSERT INTO cooks(id,dni,name,phone_number) VALUES (3,'30987453m','Carlos Martín','567891231');

INSERT INTO croupiers(id,dni,name,phone_number) VALUES (1,'12345674a','Rafael Rodríguez','987654317');
INSERT INTO croupiers(id,dni,name,phone_number) VALUES (2,'98765419z','Tamara García','123456785');
INSERT INTO croupiers(id,dni,name,phone_number) VALUES (3,'30987452m','Diana Martín','567891230');

INSERT INTO maintenanceworkers(id,dni,name,phone_number) VALUES (1,'12345673a','Silvia Rodríguez','987654316');
INSERT INTO maintenanceworkers(id,dni,name,phone_number) VALUES (2,'98765418z','Víctor García','123456784');
INSERT INTO maintenanceworkers(id,dni,name,phone_number) VALUES (3,'30987451m','Emilio Martín','567891229');

INSERT INTO waiters(id,dni,name,phone_number) VALUES (1,'12345672a','Laura Rodríguez','987654315');
INSERT INTO waiters(id,dni,name,phone_number) VALUES (2,'98765417z','Ignacio García','123456783');
INSERT INTO waiters(id,dni,name,phone_number) VALUES (3,'30987450m','Fernando Martín','567891228');

INSERT INTO artists(id,dni,name,phone_number) VALUES (1,'12345671a','Miguel Rodríguez','987654314');
INSERT INTO artists(id,dni,name,phone_number) VALUES (2,'98765416z','Daniel García','123456782');
INSERT INTO artists(id,dni,name,phone_number) VALUES (3,'30987449m','Estela Martín','567891227');

INSERT INTO clients(id,dni,name,phone_number) VALUES (1,'12345678A', 'Paco Perez','123456789');


INSERT INTO dish_courses VALUES (1, 'First');
INSERT INTO dish_courses VALUES (2, 'Second');
INSERT INTO dish_courses VALUES (3, 'Dessert');

INSERT INTO shifts VALUES (1, 'Day');
INSERT INTO shifts VALUES (2, 'Afternoon');
INSERT INTO shifts VALUES (3, 'Night');
INSERT INTO shifts VALUES (4, 'Free');

INSERT INTO dishes(id,name,dish_course_id,shift_id) VALUES (1,'Ensalada Cesar',1, 2);
INSERT INTO dishes(id,name,dish_course_id,shift_id) VALUES (2,'Serranito',2, 2);
INSERT INTO dishes(id,name,dish_course_id,shift_id) VALUES (3,'Flan Potax',3, 2);


INSERT INTO vets VALUES (1, 'James', 'Carter');
INSERT INTO vets VALUES (2, 'Helen', 'Leary');
INSERT INTO vets VALUES (3, 'Linda', 'Douglas');
INSERT INTO vets VALUES (4, 'Rafael', 'Ortega');
INSERT INTO vets VALUES (5, 'Henry', 'Stevens');
INSERT INTO vets VALUES (6, 'Sharon', 'Jenkins');

INSERT INTO specialties VALUES (1, 'radiology');
INSERT INTO specialties VALUES (2, 'surgery');
INSERT INTO specialties VALUES (3, 'dentistry');

INSERT INTO vet_specialties VALUES (2, 1);
INSERT INTO vet_specialties VALUES (3, 2);
INSERT INTO vet_specialties VALUES (3, 3);
INSERT INTO vet_specialties VALUES (4, 2);
INSERT INTO vet_specialties VALUES (5, 1);

INSERT INTO types VALUES (1, 'cat');
INSERT INTO types VALUES (2, 'dog');
INSERT INTO types VALUES (3, 'lizard');
INSERT INTO types VALUES (4, 'snake');
INSERT INTO types VALUES (5, 'bird');
INSERT INTO types VALUES (6, 'hamster');

INSERT INTO owners VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023', 'owner1');
INSERT INTO owners VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749', 'owner1');
INSERT INTO owners VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763', 'owner1');
INSERT INTO owners VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198', 'owner1');
INSERT INTO owners VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765', 'owner1');
INSERT INTO owners VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654', 'owner1');
INSERT INTO owners VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387', 'owner1');
INSERT INTO owners VALUES (8, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683', 'owner1');
INSERT INTO owners VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435', 'owner1');
INSERT INTO owners VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487', 'owner1');

INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (1, 'Leo', '2010-09-07', 1, 1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (2, 'Basil', '2012-08-06', 6, 2);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (3, 'Rosy', '2011-04-17', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (4, 'Jewel', '2010-03-07', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (5, 'Iggy', '2010-11-30', 3, 4);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (6, 'George', '2010-01-20', 4, 5);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (7, 'Samantha', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (8, 'Max', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (9, 'Lucky', '2011-08-06', 5, 7);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (10, 'Mulligan', '2007-02-24', 2, 8);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (11, 'Freddy', '2010-03-09', 5, 9);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (12, 'Lucky', '2010-06-24', 2, 10);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (13, 'Sly', '2012-06-08', 1, 10);

INSERT INTO visits(id,pet_id,visit_date,description) VALUES (1, 7, '2013-01-01', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (2, 8, '2013-01-02', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (3, 8, '2013-01-03', 'neutered');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (4, 7, '2013-01-04', 'spayed');