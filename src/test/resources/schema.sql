create table if not exists WORK_ROLE
(
    ROLE_ID       INTEGER GENERATED ALWAYS AS IDENTITY,
    TITLE         VARCHAR(50) not null,
    DESCRIPTION   VARCHAR(50) not null,
    SALARY        DOUBLE      not null,
    CREATION_DATE DATE        not null,
    CONSTRAINT UQ_TITLE UNIQUE (TITLE),
    primary key (ROLE_ID)
);