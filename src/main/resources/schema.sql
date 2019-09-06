IF object_id('office') is null
CREATE TABLE office
(
	office_id BIGINT IDENTITY (1,1) PRIMARY KEY,
	name      VARCHAR(50)  NOT NULL,
	address   VARCHAR(200) NOT NULL,
);

IF object_id('employee') is null
CREATE TABLE employee
(
	employee_id BIGINT IDENTITY (1,1) PRIMARY KEY,
	full_name   VARCHAR(50)     NOT NULL,
	email       VARCHAR(100)    NOT NULL,
	password    VARCHAR(50)     NOT NULL,
	main_office BIGINT          NOT NULL,
	constraint fk_main_office foreign key (main_office) references office (office_id)
);

IF object_id('permission') is null
CREATE TABLE permission
(
	permission_id BIGINT IDENTITY (1,1) PRIMARY KEY,
	name          VARCHAR(50) NOT NULL,
);

IF object_id('permission_employee') is null
CREATE TABLE permission_employee
(
	permission_employee_id BIGINT IDENTITY (1,1) PRIMARY KEY,
	permission_id          BIGINT not null,
	employee_id            BIGINT not null,
	constraint fk_permission_id foreign key (permission_id) references permission (permission_id),
	constraint fk_employee_id foreign key (employee_id) references employee (employee_id)
);