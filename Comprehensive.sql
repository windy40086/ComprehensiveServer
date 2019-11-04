drop database comprehensive;
create database comprehensive;
use comprehensive;

-- 用户表
create table client(
    pk_id bigint unsigned primary key auto_increment,
    account varchar(50) unique not null,
    password varchar(50) not null,
    jurisdiction int,
    create_time TIMESTAMP default CURRENT_TIMESTAMP,
    update_time TIMESTAMP default CURRENT_TIMESTAMP
);

-- 用户初始化
insert into client(account,password,jurisdiction) values("admin","admin",9);
insert into client(account,password,jurisdiction) values("windy","123",0);
insert into client(account,password,jurisdiction) values("cx","456",0);

-- 消息类型表
create table msg_type(
    pk_id bigint unsigned primary key auto_increment,
    type_id int not null,
    type_name varchar(50),
    create_time TIMESTAMP default CURRENT_TIMESTAMP,
    update_time TIMESTAMP default CURRENT_TIMESTAMP
);

-- 消息内容初始化
insert into msg_type(type_id,type_name) values(1,"the msg is send by user");
insert into msg_type(type_id,type_name) values(2,"the msg is send by system");
insert into msg_type(type_id,type_name) values(3,"the msg is send because of error");

-- 消息内容表
create table msg(
    pk_id bigint unsigned primary key auto_increment,
    account varchar(50),
    receiver varchar(50),
    msg varchar(1000) default '',
    type_id int not null,
    send_time TIMESTAMP default CURRENT_TIMESTAMP,
    create_time TIMESTAMP default CURRENT_TIMESTAMP ,
    update_time TIMESTAMP default CURRENT_TIMESTAMP

);

-- 新建消息列表
insert into msg(account,receiver,msg,type_id) values("cx","windy","hello java",1);
insert into msg(account,receiver,msg,type_id) values("windy","cx","hello python",1);
insert into msg(account,receiver,msg,type_id) values("admin","windy","hello android",2);
insert into msg(account,receiver,msg,type_id) values("apk","admin","find an error",3);

-- 用户信息
create table user_profile(
    pk_id bigint unsigned primary key auto_increment,
    account varchar(50) not null,

    avatar varchar(200) default '',
    qr_code varchar(200) default '',
    email varchar(50) default '',
    tel varchar(50) default '',
    register_time TIMESTAMP default CURRENT_TIMESTAMP ,

    create_time TIMESTAMP default CURRENT_TIMESTAMP ,
    update_time TIMESTAMP default CURRENT_TIMESTAMP
);

-- 初始化用户信息
insert into user_profile(account) values("admin");
insert into user_profile(account,email) values("windy","379949419@qq.com");
insert into user_profile(account,email) values("cx","12345678912");

