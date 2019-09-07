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
	password    VARCHAR(200)     NOT NULL,
	main_office BIGINT          NOT NULL,
	constraint fk_employee_main_office foreign key (main_office) references office (office_id)
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
	constraint fk_pe_permission_id foreign key (permission_id) references permission (permission_id),
	constraint fk_pe_employee_id foreign key (employee_id) references employee (employee_id)
);

IF object_id('trip') is null
CREATE TABLE trip
(
	trip_id                BIGINT IDENTITY (1,1) PRIMARY KEY,
	created_by             BIGINT NOT NULL,
	name                   VARCHAR(50) NOT NULL,
	date_from              DATETIME NOT NULL,
	date_to                DATETIME NOT NULL,
	destination            BIGINT NOT NULL,
	constraint fk_trip_created_by foreign key (created_by) references employee (employee_id),
	constraint fk_trip_destination foreign key (destination) references office (office_id)
);

IF object_id('apartment') is null
CREATE TABLE apartment
(
	apartment_id           BIGINT IDENTITY (1,1) PRIMARY KEY,
	address                VARCHAR(200) NOT NULL,
	capacity               INT NOT NULL,
	office                 BIGINT NOT NULL,
	constraint fk_apartment_office foreign key (office) references office (office_id)
);

IF object_id('employee_trip') is null
CREATE TABLE employee_trip
(
	employee_trip_id           BIGINT IDENTITY (1,1) PRIMARY KEY,
	employee_id                BIGINT NOT NULL,
	trip_id                    BIGINT NOT NULL,
	isApproved                 BIT NOT NULL DEFAULT 0,
	constraint fk_et_employee_id foreign key (employee_id) references employee (employee_id),
	constraint fk_et_trip_id foreign key (trip_id) references trip (trip_id)
);

IF object_id('apartment_accommodation') is null
CREATE TABLE apartment_accommodation
(
	apartment_accommodation    BIGINT IDENTITY (1,1) PRIMARY KEY,
	employee_trip_id           BIGINT NOT NULL,
	apartment_id               BIGINT NOT NULL,
	constraint fk_aa_employee_trip_id foreign key (employee_trip_id) references employee_trip (employee_trip_id),
	constraint fk_aa_apartment_id foreign key (apartment_id) references apartment (apartment_id)
);

IF object_id('hotel_accommodation') is null
CREATE TABLE hotel_accommodation
(
	hotel_accommodation        BIGINT IDENTITY (1,1) PRIMARY KEY,
	employee_trip_id           BIGINT NOT NULL,
	address                    VARCHAR(200) NOT NULL,
	price                      FLOAT NOT NULL,
	constraint fk_ha_employee_trip_id foreign key (employee_trip_id) references employee_trip (employee_trip_id)
);

IF object_id('ticket') is null
CREATE TABLE ticket
(
	ticket_id                  BIGINT IDENTITY (1,1) PRIMARY KEY,
	employee_trip_id           BIGINT NOT NULL,
	description                VARCHAR(500),
	price                      FLOAT NOT NULL,
	constraint fk_ticket_employee_trip_id foreign key (employee_trip_id) references employee_trip (employee_trip_id)
);

IF object_id('transport') is null
CREATE TABLE transport
(
	transport_id               BIGINT IDENTITY (1,1) PRIMARY KEY,
	employee_trip_id           BIGINT NOT NULL,
	description                VARCHAR(500),
	price                      FLOAT NOT NULL,
	constraint fk_transport_employee_trip_id foreign key (employee_trip_id) references employee_trip (employee_trip_id)
);

IF object_id('checklist') is null
CREATE TABLE checklist
(
	checklist_id               BIGINT IDENTITY (1,1) PRIMARY KEY,
	employee_trip_id           BIGINT NOT NULL,
	needTicket                 BIT NOT NULL DEFAULT 0,
	needAccommodation          BIT NOT NULL DEFAULT 0,
	needTransport              BIT NOT NULL DEFAULT 0,
	constraint fk_checklist_employee_trip_id foreign key (employee_trip_id) references employee_trip (employee_trip_id)
);