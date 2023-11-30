/** 初始化超级用户，created_by和last_updated_by两个字段的值不能修改，其他的可以修改 */
insert into mg_user_t (account, passwd, email, mobile_phone, uuid, created_by, creation_time, last_updated_by,
                       last_update_time)
values ('admin', '123456', '12345678', '12345678', 'testes', 9499999997, now(),
        9499999997, now());