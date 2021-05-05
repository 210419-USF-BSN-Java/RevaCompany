create table departments(
	dept_id SERIAL primary key,
	dept_name VARCHAR(50) not null,
	monthly_budget NUMERIC(7,2)
);

create table if not exists employees(
	empl_id SERIAL primary key,
	empl_name VARCHAR(50) unique,
	monthly_salary NUMERIC(6,2),
	empl_position VARCHAR(50),
	manager_id INTEGER references employees(empl_id),
	dept_id INTEGER references departments(dept_id)
);

insert into departments (dept_name, monthly_budget) values ('HR', 3000);
insert into departments (dept_name, monthly_budget) values ('Sales', 7165.5);
insert into departments (dept_name, monthly_budget) values ('Delivery', 6130.4);
insert into departments (dept_name, monthly_budget) values ('Finance', 5988.7);

insert into employees (empl_name, monthly_salary, empl_position, manager_id, dept_id) values('Kevin T.', 3000, 'Trainer', 1, 1);
insert into employees (empl_name, monthly_salary, empl_position, manager_id, dept_id) values ('Sonair L.', 1217.84, 'Cogidoo', 1, 1);
insert into employees (empl_name, monthly_salary, empl_position, manager_id, dept_id) values ('Wrapsafe', 1609.14, 'Vinte', 2, 2);
insert into employees (empl_name, monthly_salary, empl_position, manager_id, dept_id) values ('Quo Lux', 867.8, 'Photobug', 1, 2);
insert into employees (empl_name, monthly_salary, empl_position, manager_id, dept_id) values ('Cardify', 523.49, 'Wikivu', 2, 3);
insert into employees (empl_name, monthly_salary, empl_position, manager_id, dept_id) values ('Voltsillam', 652.61, 'Browsebug', 3, 4);
