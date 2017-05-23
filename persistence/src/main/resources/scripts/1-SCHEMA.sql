drop schema if exists `computer-database-db`;
  create schema if not exists `computer-database-db`;
  use `computer-database-db`;

  drop table if exists computer;
  drop table if exists company;

  create table company (
    id                        bigint not null auto_increment,
    name                      varchar(255),
    constraint pk_company primary key (id))
  ;

  create table computer (
    id                        bigint not null auto_increment,
    name                      varchar(255),
    introduced                timestamp NULL,
    discontinued              timestamp NULL,
    company_id                bigint default NULL,
    constraint pk_computer primary key (id))
  ;

  create table user (
    username                  varchar(255) NOT NULL,
    password                  varchar(255) NOT NULL)
  ;

  create table user_role (
    id                        bigint not null auto_increment,
    username 		      varchar(45) NOT NULL,
    role                      varchar(255),
    constraint pk_role primary key (id))
  ;


  alter table computer add constraint fk_computer_company_1 foreign key (company_id) references company (id) on delete restrict on update restrict;
  create index ix_computer_company_1 on computer (company_id);

  alter table user_role add constraint fk_user_role_users foreign key (username) references user (username) on delete CASCADE on update restrict;
  create index ix_role_username on user_role (username,role);
