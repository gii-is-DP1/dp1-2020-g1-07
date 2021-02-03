INSERT INTO GAMETYPES VALUES(1,'Roulette');
INSERT INTO GAMETYPES VALUES(2, 'Cards');
INSERT INTO GAMETYPES VALUES(3,'Dices');
INSERT INTO SKILLS VALUES(1,'AMATEUR');
INSERT INTO SKILLS VALUES(2,'PROFFESIONAL');
INSERT INTO games VALUES (1,'Poker',8,2);
INSERT INTO games VALUES (2,'BlackJack',10,2);
INSERT INTO games VALUES (3,'Texas Hold em',8,2);
INSERT INTO games VALUES (4,'Crazy Dices',4,3);
INSERT INTO games VALUES (5,'Fortune Roulette',6,1);

INSERT INTO statuses VALUES(1,'OK');
INSERT INTO statuses VALUES(2,'COLLECT');
INSERT INTO statuses VALUES(3,'REPAIR');

INSERT INTO slotgames VALUES (1,'Phoenix Fury',100);
INSERT INTO slotgames VALUES (2,'The Atlantis Treasure',0);
INSERT INTO slotgames VALUES (3,'Amazon Adventure',58);

INSERT INTO slotmachines VALUES (1,1,1);
INSERT INTO slotmachines VALUES (2,2,2);
INSERT INTO slotmachines VALUES (3,3,3);

INSERT INTO slotgains VALUES (1,'1',100,'2010-09-07',1);
INSERT INTO slotgains VALUES (2,'2',300,'2010-09-08',1);
INSERT INTO slotgains VALUES (3,'3',200,'2010-09-09',1);
INSERT INTO slotgains VALUES (4,'4',400,'2010-09-09',2);
INSERT INTO slotgains VALUES (5,'5',600,'2010-09-10',2);
INSERT INTO slotgains VALUES (6,'6',500,'2010-09-11',2);
INSERT INTO slotgains VALUES (7,'7',700,'2010-09-11',3);
INSERT INTO slotgains VALUES (8,'8',900,'2010-09-12',3);
INSERT INTO slotgains VALUES (9,'9',800,'2010-09-13',3);


INSERT INTO SHOWTYPES VALUES(1, 'Music');
INSERT INTO SHOWTYPES VALUES(2, 'Theater');
INSERT INTO SHOWTYPES VALUES(3, 'Magic');


INSERT INTO CASINOTABLES VALUES(1, 'Mesa 1','2020-01-04','11:30:00','09:30:00',1,2,1);
INSERT INTO CASINOTABLES VALUES(2, 'Mesa 2','2020-01-04','13:30:00','12:30:00',1,2,1);
INSERT INTO CASINOTABLES VALUES(3, 'Mesa 3','2020-01-04','23:30:00','18:30:00',1,2,1);
INSERT INTO CASINOTABLES VALUES(4, 'Mesa 4','2020-01-04','04:40:00','23:30:00',1,2,1);

INSERT INTO employees(id,dni,name,phone_number) VALUES (1,'12345678A','Manuel Rodriguez','987654321');
INSERT INTO employees(id,dni,name,phone_number) VALUES (2,'98765432Z','Rosa Garcia','123456789');
INSERT INTO employees(id,dni,name,phone_number) VALUES (3,'30987456M','Jorge Martin','567891234');
INSERT INTO administrators(id) VALUES (1);
INSERT INTO administrators(id) VALUES (2);
INSERT INTO administrators(id) VALUES (3);

INSERT INTO employees(id,dni,name,phone_number) VALUES (4,'12345677A','Natalia Rodriguez','987654320');
INSERT INTO employees(id,dni,name,phone_number) VALUES (5,'98765431Z','Quique Garcia','123456788');
INSERT INTO employees(id,dni,name,phone_number) VALUES (6,'30987455M','Luis Martin','567891233');
INSERT INTO artists(id) VALUES (4);
INSERT INTO artists(id) VALUES (5);
INSERT INTO artists(id) VALUES (6);
INSERT INTO STAGE (id, capacity ) VALUES(1,40);
INSERT INTO STAGE(id, capacity) VALUES(2,40);
INSERT INTO EVENTS (id,name,date,showtype_id,artist_id,stage_id)VALUES(1,'Magic and Pasion','2019-12-21',3,4,1);
INSERT INTO EVENTS (id,name,date,showtype_id,artist_id,stage_id)VALUES(2,'Hamlet','2020-02-17',2,6,2);

INSERT INTO employees(id,dni,name,phone_number) VALUES (7,'12345676A','Ofelia Rodriguez','987654319');
INSERT INTO employees(id,dni,name,phone_number) VALUES (8,'98765430Z','Raul Garcia','123456787');
INSERT INTO employees(id,dni,name,phone_number) VALUES (9,'30987454M','Maria Martin','567891232');
INSERT INTO administrators(id) VALUES (7);
INSERT INTO administrators(id) VALUES (8);
INSERT INTO administrators(id) VALUES (9);
INSERT INTO chefs(id) VALUES (7);
INSERT INTO chefs(id) VALUES (8);
INSERT INTO chefs(id) VALUES (9);

INSERT INTO employees(id,dni,name,phone_number) VALUES (10,'12345675A','Paula Rodriguez','987654318');
INSERT INTO employees(id,dni,name,phone_number) VALUES (11,'98765420Z','Sergio Garcia','123456786');
INSERT INTO employees(id,dni,name,phone_number) VALUES (12,'30987453M','Carlos Martin','567891231');
INSERT INTO cooks(id) VALUES (10);
INSERT INTO cooks(id) VALUES (11);
INSERT INTO cooks(id) VALUES (12);

INSERT INTO employees(id,dni,name,phone_number) VALUES (13,'12345674A','Rafael Rodriguez','987654317');
INSERT INTO employees(id,dni,name,phone_number) VALUES (14,'98765419Z','Tamara Garcia','123456785');
INSERT INTO employees(id,dni,name,phone_number) VALUES (15,'30987452M','Diana Martin','567891230');
INSERT INTO croupiers(id,casinotable) VALUES (13,1);
INSERT INTO croupiers(id,casinotable) VALUES (14,2);
INSERT INTO croupiers(id,casinotable) VALUES (15,2);

INSERT INTO employees(id,dni,name,phone_number) VALUES (16,'12345673A','Silvia Rodriguez','987654316');
INSERT INTO employees(id,dni,name,phone_number) VALUES (17,'98765418Z','Victor Garcia','123456784');
INSERT INTO employees(id,dni,name,phone_number) VALUES (18,'30987451M','Emilio Martin','567891229');
INSERT INTO maintenance_workers(id) VALUES (16);
INSERT INTO maintenance_workers(id) VALUES (17);
INSERT INTO maintenance_workers(id) VALUES (18);

INSERT INTO employees(id,dni,name,phone_number) VALUES (19,'12345672A','Laura Rodriguez','987654315');
INSERT INTO employees(id,dni,name,phone_number) VALUES (20,'98765417Z','Ignacio Garcia','123456783');
INSERT INTO employees(id,dni,name,phone_number) VALUES (21,'30987450M','Fernando Martin','567891228');
INSERT INTO waiters(id) VALUES (19);
INSERT INTO waiters(id) VALUES (20);
INSERT INTO waiters(id) VALUES (21);

INSERT INTO clients(id,dni,name,phone_number) VALUES (1,'11111111A', 'Ofelia Bustos','444444444');
INSERT INTO clients(id,dni,name,phone_number) VALUES (2,'22222222B', 'Federico Gonzalez','555555555');
INSERT INTO clients(id,dni,name,phone_number) VALUES (3,'33333333C', 'Aurelio Pino','666666666');

INSERT INTO client_gains(id,amount,date,client_id,game_id) VALUES (1,350,'2020-09-07',1,1);
INSERT INTO client_gains(id,amount,date,client_id,game_id) VALUES (2,500,'2020-09-08',1,1);
INSERT INTO client_gains(id,amount,date,client_id,game_id) VALUES (3,100,'2020-09-08',1,2);
INSERT INTO client_gains(id,amount,date,client_id,game_id) VALUES (4,-200,'2020-09-21',1,1);

-- ADMIN: admin1|4dm1n
INSERT INTO users(username,password,dni,enabled) VALUES ('admin1','4dm1n','12345678A',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');
-- EMPLOYEE: artist1|artist1
INSERT INTO users(username,password,dni,enabled) VALUES ('artist1','artist1','98765431Z',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (2,'artist1','employee');
-- CLIENT: client1|client1
INSERT INTO users(username,password,dni,enabled) VALUES ('client1','client1','11111111A',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (3,'client1','client');
-- TEMPORAL
INSERT INTO users(username,password,dni,enabled) VALUES ('owner1','0wn3r','98765431Z',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (4,'owner1','owner');

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

INSERT INTO menu VALUES (1, 'Random','2010-09-07', 3, 1, 2, 2);
INSERT INTO schedules(employees_id, date, shift_id) VALUES (1, '2010-09-07', 3);
INSERT INTO schedules(employees_id, date, shift_id) VALUES (9, '2010-09-09', 2);
INSERT INTO schedules(employees_id, date, shift_id) VALUES (16, '2010-09-06', 1);

-- TEMPORAL
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
