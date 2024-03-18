alter table link
    add last_update_check_time timestamp with time zone default now();

alter table link
    add last_modified_time timestamp with time zone;
