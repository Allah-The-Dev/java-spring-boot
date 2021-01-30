create database technicalblog;

create table IF NOT EXISTS posts (
    id SERIAL PRIMARY KEY,
    title varchar(255) NOT NULL,
    body varchar(255) NOT NULL,
    date date
);