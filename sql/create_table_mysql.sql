/*用户表*/
drop table if exists sys_user_t;
create table sys_user_t
(
    sys_user_id      bigint      not null auto_increment comment '用户表主键' primary key,
    account          varchar(20) not null comment '账号',
    passwd           varchar(40) not null comment '密码',
    email            varchar(20) not null comment '邮箱',
    mobile_phone     varchar(11) null comment '手机号码',
    uuid             varchar(40) not null comment '盐值',
    created_by       bigint      not null comment '创建人',
    creation_date    timestamp   not null comment '创建时间',
    last_updated_by  bigint      not null comment '最后更新人',
    last_update_date timestamp   not null comment '最后更新时间',
    constraint account_unique_index unique (account)
) engine = innodb
  auto_increment = 24578001 comment '用户表';

/*角色表*/
drop table if exists sys_role_t;
create table sys_role_t
(
    sys_role_id          bigint            not null auto_increment comment '角色表主键' primary key,
    role_en              varchar(15)       not null comment '角色名英文名',
    role_zh              varchar(15)       not null comment '角色名中文名',
    description          varchar(30)       null comment '描述',
    effective_start_time timestamp         not null comment '有效开始时间，格式为YYYY-MM-DD',
    effective_end_time   timestamp         not null comment '有效结束时间，格式为YYYY-MM-DD',
    is_effective         tinyint default 1 not null comment '是否有效，默认有效，0无效，1有效',
    created_by           bigint            not null comment '创建人',
    creation_date        timestamp         not null comment '创建时间',
    last_updated_by      bigint            not null comment '最后更新人',
    last_update_date     timestamp         not null comment '最后更新时间',
    constraint role_unique_index unique (role_en, role_zh)
) engine = innodb comment '角色表';

/*用户角色关系表*/
drop table if exists user_role_relation_t;
create table user_role_relation_t
(
    sys_user_role_relation_id bigint    not null auto_increment comment '用户角色关系表主键' primary key,
    sys_user_id               bigint    not null comment '用户ID',
    sys_role_id               bigint    not null comment '角色ID',
    created_by                bigint    not null comment '创建人',
    creation_date             timestamp not null comment '创建时间',
    last_updated_by           bigint    not null comment '最后更新人',
    last_update_date          timestamp not null comment '最后更新时间',
    constraint role_unique_index unique (sys_user_id, sys_role_id)
) engine = innodb comment '用户角色关系表';

/*权限表*/
drop table if exists sys_permission_t;
create table sys_permission_t
(
    sys_permission_id        bigint       not null auto_increment comment '权限表主键' primary key,
    first_level_id_code      varchar(200) not null comment '一级权限识别码',
    first_level_id_code_desc varchar(200) not null comment '一级权限识别码描述',
    permission_op_code       varchar(100) not null comment '权限操作码',
    permission_op_code_desc  varchar(200) not null comment '权限码描述',
    url                      varchar(200) not null comment '对应的url',
    created_by               bigint       not null comment '创建人',
    creation_date            timestamp    not null comment '创建时间',
    last_updated_by          bigint       not null comment '最后更新人',
    last_update_date         timestamp    not null comment '最后更新时间'
) engine = innodb comment '权限表';

/*角色权限关系表*/
drop table if exists role_permission_relation_t;
create table role_permission_relation_t
(
    role_permission_relation_id bigint    not null auto_increment comment '角色权限关系表主键' primary key,
    sys_user_role_relation_id   bigint    not null comment '用户角色关系表主键',
    sys_permission_id           bigint    not null comment '权限表主键',
    created_by                  bigint    not null comment '创建人',
    creation_date               timestamp not null comment '创建时间',
    last_updated_by             bigint    not null comment '最后更新人',
    last_update_date            timestamp not null comment '最后更新时间',
    constraint role_permission_relation_unique_index unique (sys_user_role_relation_id, sys_permission_id)
) engine = innodb comment '角色权限关系表';
