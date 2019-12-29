create database db_security character set utf8mb4 collate utf8mb4_0900_ai_ci;
use db_security;
create table if not exists `sys_user`(
    `id` bigint unsigned primary key auto_increment comment '主键',
    `username` varchar(255) unique comment '用户名',
    `nickname` varchar(50) comment '昵称',
    `password` varchar(255) comment '密码',
    `birthday` datetime comment '出生年月日',
    `sex` tinyint unsigned comment '性别：0 表示女，1 表示男',
    `mobile` varchar(20) comment '手机号码',
    `email` varchar(50) comment '电子邮箱',
    `is_delete` tinyint unsigned default 1 comment '该条记录是否删除，1 表示是，0 表示否',
    `creator` varchar(255) not null comment '创建人',
    `create_time` datetime not null default now() comment '创建时间',
    `updater` varchar(255) comment '更新人',
    `update_time` datetime comment '更新时间'
)engine=innodb comment '用户信息';

create table if not exists `sys_user_role`(
    `id` bigint unsigned primary key auto_increment comment '主键',
    `user_id` int not null comment '用户表主键',
    `role_id` int not null comment '角色表主键',
    `is_delete` tinyint unsigned default 1 comment '该条记录是否删除，1 表示是，0 表示否',
    `creator` varchar(255) not null comment '创建人',
    `create_time` datetime not null default now() comment '创建时间',
    `updater` varchar(255) comment '更新人',
    `update_time` datetime comment '更新时间'
)engine =innodb comment '用户-角色表';

create table if not exists `sys_role`(
    `id` bigint unsigned primary key auto_increment comment '主键',
    `role_name` varchar(50) comment '角色名称',
    `enable` tinyint unsigned default 1 comment '是否启用：0 表示禁用，1 表示启用',
    `is_delete` tinyint unsigned default 1 comment '该条记录是否删除，1 表示是，0 表示否',
    `creator` varchar(255) not null comment '创建人',
    `create_time` datetime not null default now() comment '创建时间',
    `updater` varchar(255) comment '更新人',
    `update_time` datetime comment '更新时间'
)engine =innodb comment '角色信息';

create table if not exists `sys_role_permission`(
    `id` bigint unsigned primary key auto_increment comment '主键',
    `role_id` int not null comment '角色id',
    `permission_id` int not null comment '权限资源id',
    `is_delete` tinyint unsigned default 1 comment '该条记录是否删除，1 表示是，0 表示否',
    `creator` varchar(255) not null comment '创建人',
    `create_time` datetime not null default now() comment '创建时间',
    `updater` varchar(255) comment '更新人',
    `update_time` datetime comment '更新时间'
)engine =innodb comment '角色-权限资源表';

create table if not exists `sys_permission`(
    `id` bigint unsigned primary key auto_increment comment '主键',
    `permission_name` varchar(255) not null comment '权限资源名称',
    `uri` varchar(255) not null comment 'uri',
    `req_method` varchar(10) not null comment '请求方式',
    `is_delete` tinyint unsigned default 1 comment '该条记录是否删除，1 表示是，0 表示否',
    `creator` varchar(255) not null comment '创建人',
    `create_time` datetime not null default now() comment '创建时间',
    `updater` varchar(255) comment '更新人',
    `update_time` datetime comment '更新时间'
)engine =innodb comment '权限资源';

-- 插入几个用户 密码是 123456
insert into sys_user(username, password, is_delete, creator, create_time)
values
       ('admin', '$2a$10$pTG7LsGDVZ2slRnu8ZNqQOEsOWZpg5777oT8w0fWgRwwSdK6NOCcm', 0, 'SYSTEM', now()),
       ('李白', '$2a$10$pTG7LsGDVZ2slRnu8ZNqQOEsOWZpg5777oT8w0fWgRwwSdK6NOCcm', 0, 'SYSTEM', now()),
       ('杜甫', '$2a$10$pTG7LsGDVZ2slRnu8ZNqQOEsOWZpg5777oT8w0fWgRwwSdK6NOCcm', 0, 'SYSTEM', now()),
       ('白居易', '$2a$10$pTG7LsGDVZ2slRnu8ZNqQOEsOWZpg5777oT8w0fWgRwwSdK6NOCcm', 0, 'SYSTEM', now())
;

insert into sys_role(role_name,is_delete, creator, create_time)
values('ROLE_超级管理员', 0, 'admin', now()),
       ('ROLE_管理员', 0, 'admin', now()),
       ('ROLE_会员', 0, 'admin', now()),
       ('ROLE_普通', 0, 'admin', now());

insert into sys_user_role(user_id, role_id, is_delete, creator, create_time)
values
(1, 1, 0, 'SYSTEM', now()),
(1, 2, 0, 'SYSTEM', now()),
(1, 3, 0, 'SYSTEM', now()),
(1, 4, 0, 'SYSTEM', now()),
(2, 2, 0, 'SYSTEM', now()),
(2, 3, 0, 'SYSTEM', now()),
(2, 4, 0, 'SYSTEM', now()),
(3, 3, 0, 'SYSTEM', now()),
(3, 4, 0, 'SYSTEM', now()),
(4, 4, 0, 'SYSTEM', now())
;

insert into sys_permission(permission_name, uri, req_method, is_delete, creator, create_time)
values
       ('USER新增', '/user', 'POST', 0, 'admin', now()),
       ('USER删除', '/user/*', 'DELETE', 0, 'admin', now()),
       ('USER更新', '/user', 'PUT', 0, 'admin', now()),
       ('USER-GET', '/user/*', 'GET', 0, 'admin', now()),
       ('USER-LIST', '/user/list', 'GET', 0, 'admin', now()),
       ('ROLE新增', '/role', 'POST', 0, 'admin', now()),
       ('ROLE删除', '/role', 'DELETE', 0, 'admin', now()),
       ('ROLE更新', '/role', 'PUT', 0, 'admin', now()),
       ('ROLE-GET', '/role/*', 'GET', 0, 'admin', now()),
       ('ROLE-LIST', '/role/list', 'GET', 0, 'admin', now()),
       ('PERMISSION新增', '/permission', 'POST', 0, 'admin', now()),
       ('PERMISSION删除', '/permission', 'DELETE', 0, 'admin', now()),
       ('PERMISSION更新', '/permission', 'PUT', 0, 'admin', now()),
       ('PERMISSION-GET', '/permission/*', 'GET', 0, 'admin', now()),
       ('PERMISSION-LIST', '/permission/list', 'GET', 0, 'admin', now())
;

insert into sys_role_permission(role_id, permission_id, is_delete, creator, create_time)
values
       (1, 1, 0, 'amdin', now()),
       (1, 2, 0, 'amdin', now()),
       (1, 3, 0, 'amdin', now()),
       (1, 4, 0, 'amdin', now()),
       (1, 5, 0, 'amdin', now()),
       (1, 6, 0, 'amdin', now()),
       (1, 7, 0, 'amdin', now()),
       (1, 8, 0, 'amdin', now()),
       (1, 9, 0, 'amdin', now()),
       (1, 10, 0, 'amdin', now()),
       (1, 11, 0, 'amdin', now()),
       (1, 12, 0, 'amdin', now()),
       (1, 13, 0, 'amdin', now()),
       (1, 14, 0, 'amdin', now()),
       (1, 15, 0, 'amdin', now()),
       (2, 1, 0, 'amdin', now()),
       (2, 2, 0, 'amdin', now()),
       (2, 3, 0, 'amdin', now()),
       (2, 4, 0, 'amdin', now()),
       (2, 5, 0, 'amdin', now()),
       (3, 4, 0, 'amdin', now()),
       (3, 5, 0, 'amdin', now()),
       (4, 4, 0, 'amdin', now())
;