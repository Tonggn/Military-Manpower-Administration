drop table if exists enterprise;
drop table if exists notice;

create table enterprise
(
    id            bigint auto_increment primary key,
    name          varchar(255)  not null,
    business_type varchar(20)   not null,
    website_url   varchar(1024) not null,
    address       varchar(1024) not null
);

create table notice
(
    id                      bigint auto_increment primary key,
    title                   varchar(255)  not null,
    task                    varchar(255)  not null,
    business_type           varchar(20)   not null,
    welfare                 varchar(255)  not null,
    salary_code             varchar(20)   not null,
    service_address         varchar(1024) not null,
    highest_education_level varchar(255)  not null,
    experience_division     varchar(255)  not null,
    service_status_code     varchar(20)   not null,
    agent_code              varchar(20)   not null,
    enterprise_id           bigint        not null,
    notice_number           bigint        not null,
    created_date            date          not null,
    updated_date            date          not null,
    deadline_date           date          not null,
    created_at              datetime(6)   null,
    updated_at              datetime(6)   null
);
