create database travel_agency;
use travel_agency;



insert into users(email, password) values("amal.kabulov@gmail.com", "123");
insert into users(email, password) values("b", "b");
insert into users(email, password) values("c", "c");
insert into users(email, password) values("d", "d");
insert into users(email, password) values("e", "e");

insert into countries(`name`, phone_code) values("Belarus", "+375");


insert into cities(`name`, country_id) values("Minsk", 1), ("Vitebsk", 1);

insert into airports(`name`, city_id) values("Minsk National Airport", 1);

insert into roles(role_name) values("admin");
insert into users_roles(id_user, id_role) values(1, 1);

select * from users_roles;


drop table users;
drop table users_info;
drop table roles;
drop table users_roles;
drop table tours;
drop table countries;
drop table cities;
drop table airports;
drop table tour_types;
drop table tours_tour_types;
drop table tours_cities;
drop table orders;





create table users(id bigint auto_increment,
				   email varchar(50) not null unique,
                   `password` varchar(50) not null,
                   is_active tinyint not null default 1,
                   primary key(id));

create table users_info(id_user bigint,
						first_name varchar(50) not null,
                        last_name varchar(50) not null,
                        date_of_birth date not null,
                        country_id int not null,
                        city_id int not null,
                        phone_number varchar(20) unique not null,
                        discount tinyint,
                        primary key(id_user),
                        foreign key(id_user) references users(id),
                        foreign key(country_id) references countries(id),
                        foreign key(city_id) references cities(id));

create table roles(id bigint auto_increment,
				   role_name varchar(50) not null unique,
                   primary key(id));

create table users_roles(id_user bigint not null,
						 id_role bigint not null,
						 foreign key(id_user) references users(id),
                         foreign key(id_role) references roles(id));


create table tours(id bigint auto_increment,
				  `name` varchar(50) not null,
				   price int not null,
				   arrival_date datetime not null,
				   departure_date datetime not null,
                   arrival_from bigint not null,
                   departure_to bigint not null,
				   is_active tinyint not null default 1,
				   urgent tinyint not null default 0,
                   primary key(id),
                   foreign key(arrival_from) references airports(id),
                   foreign key(departure_to) references airports(id));


create table countries(id int auto_increment,
					  `name` varchar(50) not null unique,
					   phone_code varchar(5) not null unique,
                       primary key(id));


create table cities(id int auto_increment,
				   `name` varchar(50) not null,
                    country_id int not null,
                    primary key(id),
                    foreign key(country_id) references countries(id));

create table airports(id bigint auto_increment,
					 `name` varchar(100) not null,
					  city_id int not null,
                      primary key(id),
                      foreign key(city_id) references cities(id));


create table tour_types(id bigint auto_increment,
					  `type` varchar(50) not null unique,
                       primary key(id));

create table tours_tour_types(id_tour bigint not null,
							  id_tour_type bigint not null,
                              foreign key(id_tour) references tours(id),
                              foreign key(id_tour_type) references tour_types(id));

create table tours_cities(id_tour bigint not null,
						  id_city int not null,
                          foreign key(id_tour) references tours(id),
                          foreign key(id_city) references cities(id));

create table orders(id bigint auto_increment,
					id_user bigint not null,
					id_tour bigint not null,
					discount tinyint not null,
                    price_with_discount int not null,
                    primary key(id),
                    foreign key(id_user) references users(id),
                    foreign key(id_tour) references tours(id));



