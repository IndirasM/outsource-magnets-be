IF object_id('role') is null
CREATE TABLE role
(
    role_id     BIGINT IDENTITY (1,1) PRIMARY KEY,
    name        VARCHAR(50)  NOT NULL,
);

IF object_id('subject') is null
CREATE TABLE subject
(
    subject_id          BIGINT IDENTITY (1,1) PRIMARY KEY,
    parent_subject_id   BIGINT,
    name                VARCHAR(50) NOT NULL,
    description         NTEXT,
    date                VARCHAR(50) NOT NULL,
    constraint fk_parent_subject_subject foreign key (parent_subject_id) references subject (subject_id)
);

IF object_id('limit') is null
CREATE TABLE limit
(
    limit_id                    BIGINT IDENTITY (1,1) PRIMARY KEY,
    is_global                   BIT NOT NULL DEFAULT 0,
    days_in_year                INT NOT NULL,
    days_in_month               INT NOT NULL,
    days_in_row                 INT NOT NULL,
);

IF object_id('team') is null
CREATE TABLE team
(
    team_id                 BIGINT IDENTITY (1,1) PRIMARY KEY,
    name                    VARCHAR(50) NOT NULL,
    manager_employee_id     BIGINT NOT NULL,
);

IF object_id('employee') is null
CREATE TABLE employee
(
    employee_id     BIGINT IDENTITY (1,1) PRIMARY KEY,
    name            VARCHAR(50),
    email           VARCHAR(50),
    password        VARCHAR(50),
    team_id         BIGINT,
    limit_id        BIGINT NOT NULL,
    role_id         BIGINT NOT NULL,
    constraint fk_employee_team foreign key (team_id) references team (team_id),
    constraint fk_employee_limit foreign key (limit_id) references limit (limit_id),
    constraint fk_employee_role foreign key (role_id) references role (role_id),
);

IF object_id('employee_subject') is null
CREATE TABLE employee_subject
(
    employee_subject_id     BIGINT IDENTITY (1,1) PRIMARY KEY,
    employee_id             BIGINT NOT NULL,
    subject_id              BIGINT NOT NULL,
    created                 VARCHAR(50) NOT NULL,
    constraint fk_employee_subject_employee foreign key (employee_id) references employee (employee_id),
    constraint fk_employee_subject_subject foreign key (subject_id) references subject (subject_id),
);

IF object_id('learning_day') is null
CREATE TABLE learning_day
(
    learning_day_id     BIGINT IDENTITY (1,1) PRIMARY KEY,
    subject_id          BIGINT NOT NULL,
    employee_id         BIGINT NOT NULL,
    date                VARCHAR(50) NOT NULL,
    created             VARCHAR(50) NOT NULL,
    notes               NTEXT,
    constraint fk_learning_day_subject foreign key (subject_id) references subject (subject_id),
    constraint fk_learning_day_employee foreign key (employee_id) references employee (employee_id),
);

IF (OBJECT_ID('fk_team_employee') IS NULL)
ALTER TABLE dbo.team
    ADD constraint fk_team_employee foreign key (manager_employee_id) references employee (employee_id);