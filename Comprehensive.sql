drop database comprehensive;
create database comprehensive;
use comprehensive;

-- 用户表
create table client(
    pk_id bigint unsigned primary key auto_increment,
    
	account varchar(50) default '',
	
	email varchar(50) default '',
    tel varchar(50) default '',
    password varchar(50) not null,
	hashcode varchar(20) not null,
    
    jurisdiction int,
    profile_photo varchar(200) default '',
    qr_code varchar(200) default '',
    
    register_time TIMESTAMP default CURRENT_TIMESTAMP ,
    
    create_time TIMESTAMP default CURRENT_TIMESTAMP,
    update_time TIMESTAMP default CURRENT_TIMESTAMP
)ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;

-- 用户初始化
insert into client(account,password,email,jurisdiction) values("admin","admin","admin",9);
insert into client(account,password,email,jurisdiction) values("windy","123","windy",0);
insert into client(account,password,email,jurisdiction) values("cx","456","cx",0);

alter table client AUTO_INCREMENT=1000000000;
insert into client(email,password,jurisdiction) values("379949419@qq.com","123456",0);
insert into client(tel,password,jurisdiction) values("15171544044","123456",0);
insert into client(email,password,jurisdiction) values("592467023@qq.com","caoxuan",0);

-- 消息类型表
create table msg_type(
    pk_id bigint unsigned primary key auto_increment,
    type_id int not null,
    type_name varchar(50),
    create_time TIMESTAMP default CURRENT_TIMESTAMP,
    update_time TIMESTAMP default CURRENT_TIMESTAMP
)ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;

-- 消息内容初始化
insert into msg_type(type_id,type_name) values(0,"the msg is send by system");
insert into msg_type(type_id,type_name) values(1,"the msg is send because of login");
insert into msg_type(type_id,type_name) values(2,"the msg is send because of register");
insert into msg_type(type_id,type_name) values(3,"the msg is send because of relay");
insert into msg_type(type_id,type_name) values(4,"the msg is send because of error");
insert into msg_type(type_id,type_name) values(5,"the msg is send because of histort record");
insert into msg_type(type_id,type_name) values(6,"none");
insert into msg_type(type_id,type_name) values(7,"none");


-- 系统群
create table msg_1(
    pk_id bigint unsigned primary key auto_increment,
	account varchar(50),
    receiver varchar(50),
    msg varchar(1000) default '',
    type_id int not null,
    send_time TIMESTAMP default CURRENT_TIMESTAMP,
    create_time TIMESTAMP default CURRENT_TIMESTAMP ,
    update_time TIMESTAMP default CURRENT_TIMESTAMP
)ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;

-- 消息内容表
create table msg_3(
    pk_id bigint unsigned primary key auto_increment,
	account varchar(50),
    receiver varchar(50),
    msg varchar(1000) default '',
    type_id int not null,
    send_time TIMESTAMP default CURRENT_TIMESTAMP,
    create_time TIMESTAMP default CURRENT_TIMESTAMP ,
    update_time TIMESTAMP default CURRENT_TIMESTAMP
)ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;

create table msg_2(
    pk_id bigint unsigned primary key auto_increment,
	account varchar(50),
    receiver varchar(50),
    msg varchar(1000) default '',
    type_id int not null,
    send_time TIMESTAMP default CURRENT_TIMESTAMP,
    create_time TIMESTAMP default CURRENT_TIMESTAMP ,
    update_time TIMESTAMP default CURRENT_TIMESTAMP
)ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;

create table msg_1000000000(
    pk_id bigint unsigned primary key auto_increment,
	account varchar(50),
    receiver varchar(50),
    msg varchar(1000) default '',
    type_id int not null,
    send_time TIMESTAMP default CURRENT_TIMESTAMP,
    create_time TIMESTAMP default CURRENT_TIMESTAMP ,
    update_time TIMESTAMP default CURRENT_TIMESTAMP
)ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;

create table msg_1000000001(
    pk_id bigint unsigned primary key auto_increment,
	account varchar(50),
    receiver varchar(50),
    msg varchar(1000) default '',
    type_id int not null,
    send_time TIMESTAMP default CURRENT_TIMESTAMP,
    create_time TIMESTAMP default CURRENT_TIMESTAMP ,
    update_time TIMESTAMP default CURRENT_TIMESTAMP
)ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;

create table msg_1000000002(
    pk_id bigint unsigned primary key auto_increment,
	account varchar(50),
    receiver varchar(50),
    msg varchar(1000) default '',
    type_id int not null,
    send_time TIMESTAMP default CURRENT_TIMESTAMP,
    create_time TIMESTAMP default CURRENT_TIMESTAMP ,
    update_time TIMESTAMP default CURRENT_TIMESTAMP
)ENGINE=INNODB DEFAULT CHARSET=UTF8MB4;