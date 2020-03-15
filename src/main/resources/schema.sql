create table if not exists Ingredients (
  id varchar(4) not null,
  name varchar(25) not null,
  type varchar(10) not null
);

create table if not exists Tacos (
  id identity,
  name varchar(50) not null,
  createdAt timestamp not null
);

create table if not exists TacosIngredients (
  taco_id bigint not null,
  ingredient_id varchar(4) not null
);

alter table TacosIngredients
    add foreign key (taco_id) references Tacos(id);
alter table TacosIngredients
    add foreign key (ingredient_id) references Ingredients(id);

create table if not exists Orders (
	id identity,
	name varchar(50) not null,
	street varchar(50) not null,
	city varchar(50) not null,
	state varchar(2) not null,
	zip varchar(10) not null,
	creditCard varchar(16) not null,
	expiration varchar(5) not null,
	cvv varchar(3) not null,
    placedAt timestamp not null
);

create table if not exists OrdersTacos (
	order_id bigint not null,
	taco_id bigint not null
);

alter table OrdersTacos
    add foreign key (order_id) references Orders(id);
alter table OrdersTacos
    add foreign key (taco_id) references Tacos(id);