-- ----------------------------
-- 1、用户ID序列
-- ----------------------------
drop sequence if exists mg_user_base_t_seq cascade;
create sequence mg_user_base_t_seq increment by 2 minvalue 9499999997 maxvalue 9999999999 start with 9499999997;

-- ----------------------------
-- 2、用户表
-- ----------------------------
drop table if exists mg_user_base_t;
create table if not exists mg_user_base_t(
  user_id               bigint default nextval('mg_user_base_t_seq'::regclass)    not null,
  account               varchar(20)                                               not null,
  nick_name             varchar(20)                                               default null,
  email                 varchar(20)                                               not null,
  mobile_phone          varchar(11)                                               not null,
  avatar                varchar(255)                                              default null,
  gender                int                                                       default null,
  account_status        smallint                                                  default 1,
  delete_flag           boolean                                                   not null default false,
  last_login_ip         inet                                                      default null,
  last_login_time       timestamp(0)                                              default null,
  created_by            bigint                                                    not null,
  creation_time         timestamp(0)                                              not null,
  last_updated_by       bigint                                                    not null,
  last_update_time      timestamp(0)                                              not null,
  constraint mg_user_base_t_pkey primary key(user_id)
);
comment on table mg_user_base_t                                            is '用户基础表';
comment on column mg_user_base_t.user_id                                   is '用户id';
comment on column mg_user_base_t.account                                   is '用户账号';
comment on column mg_user_base_t.nick_name                                 is '用户昵称';
comment on column mg_user_base_t.email                                     is '邮箱';
comment on column mg_user_base_t.mobile_phone                              is '手机号码';
comment on column mg_user_base_t.avatar                                    is '用户头像';
comment on column mg_user_base_t.gender                                    is '用户性别(1: 男, 2: 女, 3: 其他)';
comment on column mg_user_base_t.account_status                            is '用户账号状态(0: 停用, 1: 正常)';
comment on column mg_user_base_t.delete_flag                               is '删除标识(true: 删除, false: 未删除)';
comment on column mg_user_base_t.last_login_ip                             is '最后登录IP';
comment on column mg_user_base_t.last_login_time                           is '上次登录时间';
comment on column mg_user_base_t.created_by                                is '创建人';
comment on column mg_user_base_t.creation_time                             is '创建时间';
comment on column mg_user_base_t.last_updated_by                           is '最后更新人';
comment on column mg_user_base_t.last_update_time                          is '最后更新时间';

create unique index uqe_user_id_account on mg_user_base_t(user_id, account);

-- ----------------------------
-- 3、用户密码表
-- ----------------------------
drop table if exists mg_user_passwd_t;
create table if not exists mg_user_passwd_t(
  user_id               bigint            not null,
  passwd                varchar(40)       not null,
  slat                  varchar(255)      not null,
  created_by            bigint            not null,
  creation_time         timestamp(0)      not null,
  last_updated_by       bigint            not null,
  last_update_time      timestamp(0)      not null
);
comment on table mg_user_passwd_t                                           is '用户密码表';
comment on column mg_user_passwd_t.user_id                                  is '用户ID';
comment on column mg_user_passwd_t.passwd                                   is '密码';
comment on column mg_user_passwd_t.slat                                     is '盐值';
comment on column mg_user_passwd_t.created_by                               is '创建人';
comment on column mg_user_passwd_t.creation_time                            is '创建时间';
comment on column mg_user_passwd_t.last_updated_by                          is '最后更新人';
comment on column mg_user_passwd_t.last_update_time                         is '最后更新时间';

create unique index uqe_user_id on mg_user_passwd_t(user_id);

-- ----------------------------
-- 4、应用组表
-- ----------------------------
drop table if exists mg_app_group_t;
create table if not exists mg_app_group_t(
  app_group_id          serial8           not null,
  app_group_name        varchar(20)       not null,
  parent_id             bigint            not null,
  tenant_id             bigint            not null,
  delete_flag           boolean           not null default false,
  created_by            bigint            not null,
  creation_time         timestamp(0)      not null,
  last_updated_by       bigint            not null,
  last_update_time      timestamp(0)      not null
);
comment on table mg_app_group_t                                             is '应用组表';
comment on column mg_app_group_t.app_group_id                               is '应用组ID';
comment on column mg_app_group_t.app_group_name                             is '应用组名称';
comment on column mg_app_group_t.parent_id                                  is '父ID';
comment on column mg_app_group_t.tenant_id                                  is '租户ID';
comment on column mg_app_group_t.delete_flag                                is '删除标识(true: 删除, false: 未删除)';
comment on column mg_app_group_t.created_by                                 is '创建人';
comment on column mg_app_group_t.creation_time                              is '创建时间';
comment on column mg_app_group_t.last_updated_by                            is '最后更新人';
comment on column mg_app_group_t.last_update_time                           is '最后更新时间';

-- ----------------------------
-- 5、应用(租户)表
-- ----------------------------
drop table if exists mg_app_t;
create table if not exists mg_app_t(
  app_id                varchar(255)              not null,
  tenant_id             bigint                    not null,
  app_addr              varchar(255)              default null,
  is_app_effective      boolean                   not null default true,
  delete_flag           boolean                   not null default false,
  created_by            bigint                    not null,
  creation_time         timestamp(0)              not null,
  last_updated_by       bigint                    not null,
  last_update_time      timestamp(0)              not null
);
comment on table mg_app_t                                                    is '应用(租户)表';
comment on column mg_app_t.app_id                                            is '应用ID(均以app_作为前缀)';
comment on column mg_app_t.tenant_id                                         is '租户ID';
comment on column mg_app_t.app_addr                                          is '应用地址';
comment on column mg_app_t.is_app_effective                                  is '应用是否有效(默认为有效,true)';
comment on column mg_app_t.delete_flag                                       is '删除标识(true: 删除, false: 未删除)';
comment on column mg_app_t.created_by                                        is '创建人';
comment on column mg_app_t.creation_time                                     is '创建时间';
comment on column mg_app_t.last_updated_by                                   is '最后更新人';
comment on column mg_app_t.last_update_time                                  is '最后更新时间';

create index idx_app_id on mg_app_t(app_id);
create index idx_tenant_id on mg_app_t(tenant_id);

-- ----------------------------
-- 6、用户租户关联表
-- ----------------------------
drop table if exists mg_user_tenant_rel_t;
create table if not exists mg_user_tenant_rel_t(
  user_id               bigint            not null,
  tenant_id             bigint            not null,
  created_by            bigint            not null,
  creation_time         timestamp(0)      not null,
  last_updated_by       bigint            not null,
  last_update_time      timestamp(0)      not null
);
comment on table mg_user_passwd_t is '用户租户关联表';
comment on column mg_user_passwd_t.user_id is '用户id';
comment on column mg_user_passwd_t.tenant_id is '租户ID';
comment on column mg_user_passwd_t.created_by is '创建人';
comment on column mg_user_passwd_t.creation_time is '创建时间';
comment on column mg_user_passwd_t.last_updated_by is '最后更新人';
comment on column mg_user_passwd_t.last_update_time is '最后更新时间';

create index idx_user_id on mg_user_tenant_rel_t(user_id);
create index idx_tenant_id on mg_user_tenant_rel_t(tenant_id);
