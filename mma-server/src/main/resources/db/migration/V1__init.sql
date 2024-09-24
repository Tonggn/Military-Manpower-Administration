create table company
(
    id                 bigint                                                                                                                                                                                                                                                                                                                                                                                                                                                               not null primary key,
    name               varchar(255)                                                                                                                                                                                                                                                                                                                                                                                                                                                         not null,
    address            varchar(255)                                                                                                                                                                                                                                                                                                                                                                                                                                                         not null,
    selected_year      int                                                                                                                                                                                                                                                                                                                                                                                                                                                                  null,
    business_type_code varchar(255)                                                                                                                                                                                                                                                                                                                                                                                                                                                         null,
    contact            varchar(255)                                                                                                                                                                                                                                                                                                                                                                                                                                                         null,
    website_url        varchar(255)                                                                                                                                                                                                                                                                                                                                                                                                                                                         null,
    business_type      enum ('AGRICULTURE_AND_FISHERY', 'ANIMATION', 'CEMENT_CERAMIC', 'CHEMICAL', 'CONSTRUCTION', 'DEFENSE_INDUSTRY', 'ELECTRIC', 'ELECTRONIC', 'ENERGY', 'FISHERY', 'FOOD_AND_BEVERAGE', 'GAME_SOFTWARE', 'HOUSEHOLD', 'INDUSTRY', 'INFORMATION', 'MACHINERY', 'MANUFACTURING', 'MEDICAL_PHARMACEUTICAL', 'MINING', 'RESEARCH_INSTITUTE', 'SHIPPING', 'SKILLED_TECHNICIAN', 'STEEL', 'TELECOMMUNICATION_EQUIPMENT', 'TEXTILE', 'VETERINARY_PHARMACEUTICAL', 'VIDEO_GAME') not null,
    scale_type         enum ('FARMERS_AND_FISHERMEN', 'MAJOR_COMPANY', 'MIDDLE_SIZED_COMPANY', 'NONE', 'SMALL_AND_MEDIUM_SIZED_COMPANY', 'VENTURE_COMPANY')                                                                                                                                                                                                                                                                                                                                 not null,
    created_at         datetime(6)                                                                                                                                                                                                                                                                                                                                                                                                                                                          null,
    updated_at         datetime(6)                                                                                                                                                                                                                                                                                                                                                                                                                                                          null
);

create table job_posting
(
    id                           bigint auto_increment primary key,
    company_name                 varchar(255)                                                                                                                                                                                                                                                                                                                                                                                                                                    not null,
    posting_number               varchar(255)                                                                                                                                                                                                                                                                                                                                                                                                                                    not null unique,
    title                        varchar(255)                                                                                                                                                                                                                                                                                                                                                                                                                                    not null,
    task                         varchar(255)                                                                                                                                                                                                                                                                                                                                                                                                                                    null,
    welfare                      varchar(255)                                                                                                                                                                                                                                                                                                                                                                                                                                    not null,
    work_address                 varchar(255)                                                                                                                                                                                                                                                                                                                                                                                                                                    not null,
    area_code                    varchar(255)                                                                                                                                                                                                                                                                                                                                                                                                                                    not null,
    min_education                varchar(255)                                                                                                                                                                                                                                                                                                                                                                                                                                    not null,
    business_type                enum ('AGRICULTURE_AND_FISHERY', 'ANIMATION', 'CEMENT_CERAMIC', 'CHEMICAL', 'CONSTRUCTION', 'DEFENSE_INDUSTRY', 'ELECTRIC', 'ELECTRONIC', 'ENERGY', 'FISHERY', 'FOOD_AND_BEVERAGE', 'GAME_SOFTWARE', 'HOUSEHOLD', 'INFORMATION', 'MACHINERY', 'MEDICAL_PHARMACEUTICAL', 'MINING', 'NONE', 'RESEARCH_INSTITUTE', 'SHIPPING', 'SKILLED_TECHNICIAN', 'STEEL', 'TELECOMMUNICATION_EQUIPMENT', 'TEXTILE', 'VETERINARY_PHARMACEUTICAL', 'VIDEO_GAME') not null,
    experience_years             varchar(255)                                                                                                                                                                                                                                                                                                                                                                                                                                    not null,
    experience_division          varchar(255)                                                                                                                                                                                                                                                                                                                                                                                                                                    not null,
    salary_type                  enum ('CODE_07', 'CODE_08', 'CODE_09', 'CODE_10', 'CODE_11', 'CODE_12', 'CODE_13', 'CODE_14', 'CODE_15', 'CODE_16', 'CODE_17', 'CODE_18', 'CODE_19', 'CODE_20', 'CODE_21', 'CODE_25', 'CODE_30', 'NONE')                                                                                                                                                                                                                                        not null,
    service_status_type          enum ('ACTIVE_DUTY_STATUS', 'ETC', 'NONE', 'REPLACEMENT_STATUS')                                                                                                                                                                                                                                                                                                                                                                                not null,
    agent_type                   enum ('DETAIL_RESEARCH_PERSONNEL', 'NONE', 'RESERVE_FORCES_FOR_SEA_DUTY', 'SKILLED_INDUSTRIAL_PERSONNEL')                                                                                                                                                                                                                                                                                                                                       not null,
    available                    bit                                                                                                                                                                                                                                                                                                                                                                                                                                             not null,
    business_registration_number varchar(255)                                                                                                                                                                                                                                                                                                                                                                                                                                    not null,
    closing_date                 date                                                                                                                                                                                                                                                                                                                                                                                                                                            not null,
    modified_date                date                                                                                                                                                                                                                                                                                                                                                                                                                                            null,
    published_date               date                                                                                                                                                                                                                                                                                                                                                                                                                                            null,
    created_at                   datetime(6)                                                                                                                                                                                                                                                                                                                                                                                                                                     null,
    updated_at                   datetime(6)                                                                                                                                                                                                                                                                                                                                                                                                                                     null
);
