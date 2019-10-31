drop database comprehensive;
create database comprehensive;
use comprehensive;

-- 用户表
create table client(
    pk_id bigint unsigned primary key auto_increment,
    account varchar(50) unique not null,
    password varchar(50) not null,
    jurisdiction int,
    create_time DATE ,
    update_time DATE 
);

-- 消息类型表
create table msg_type(
    pk_id bigint unsigned primary key auto_increment,
    type_id int,
    type_name varchar(50),
    create_time DATE,
    update_time DATE
);

-- 消息内容初始化
insert into msg_type(type_id,type_name) values(1,"the msg is send by user")
insert into msg_type(type_id,type_name) values(2,"the msg is send by system")

-- 消息内容表
create table msg(
    pk_id bigint unsigned primary key auto_increment,
    account varchar(50),
    msg varchar(1000) default '',
    msg_type int,
    send_time DATE ,
    create_time DATE ,
    update_time DATE ,
    FOREIGN KEY(msg_type) REFERENCES msg_type(type_id)
);

-- 用户信息
create table user_profile(
    pk_id bigint unsigned primary key auto_increment,
    account varchar(50) not null,

    avatar varchar(50) not null default '',
    photo varchar(200),
    qr_code varchar(200),
    email varchar(50) default '',
    tel varchar(50) default '',
    register_time DATE ,

    create_time DATE ,
    update_time DATE 
);



