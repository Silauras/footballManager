drop table if exists player CASCADE;
drop table if exists team CASCADE;

create table player
(
    id         integer primary key auto_increment,
    age        integer not null,
    experience integer not null,
    name       varchar(255),
    team_id    integer,
    primary key (id)
);

create table team
(
    id         integer primary key auto_increment,
    commission decimal(19, 2),
    money      decimal(19, 2),
    name       varchar(255) unique,
    primary key (id)
);

alter table player
    add constraint FK_player_team foreign key (team_id) references team;

