/** 用户表 */
drop sequence if exists mg_user_t_user_t_seq cascade;
create sequence mg_user_t_user_t_seq
    increment by 2
    minvalue 9499999997
    maxvalue 9999999999
    start with 9499999997;

drop table if exists mg_user_t;
create table mg_user_t
(
    user_id          bigint default nextval('mg_user_t_user_t_seq'::regclass) not null,
    account          varchar(20)                                              not null,
    passwd           varchar(40)                                              not null,
    email            varchar(20)                                              not null,
    mobile_phone     varchar(11)                                              not null,
    uuid             varchar(255)                                             not null,
    created_by       bigint                                                   not null,
    creation_time    timestamp(0)                                             not null,
    last_updated_by  bigint                                                   not null,
    last_update_time timestamp(0)                                             not null,
    constraint 'mg_user_t_pkey' primary key ('user_id')
);
comment on table mg_user_t is '用户表';
comment on column mg_user_t.user_id is '用户ID';
comment on column mg_user_t.account is '用户账号';
comment on column mg_user_t.passwd is '密码';
comment on column mg_user_t.email is '邮箱';
comment on column mg_user_t.mobile_phone is '手机号码';
comment on column mg_user_t.uuid is '盐值';
comment on column mg_user_t.created_by is '创建人';
comment on column mg_user_t.creation_time is '创建时间';
comment on column mg_user_t.last_updated_by is '最后更新人';
comment on column mg_user_t.last_update_time is '最后更新时间';

create unique index unique_user_id_account on mg_user_t (user_id, account);
