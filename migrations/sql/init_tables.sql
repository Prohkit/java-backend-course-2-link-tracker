create table if not exists chat
(
    id         bigint generated always as identity,
    tg_chat_id int                      not null,
    created_at timestamp with time zone not null,

    primary key (id),
    unique (tg_chat_id)
);

create table if not exists link
(
    id         bigint generated always as identity,
    url        varchar                  not null,
    created_at timestamp with time zone not null,

    primary key (id),
    unique (url)
);

create table if not exists chat_link
(
    link_id bigint not null,
    chat_id bigint not null
);

alter table chat_link
    add constraint fk_link_id foreign key (link_id) references link (id);
alter table chat_link
    add constraint fk_chat_id foreign key (chat_id) references chat (id);
