create table if not exists chat
(
    id         bigint,
    created_at timestamp with time zone not null default now(),

    primary key (id)
);

create table if not exists link
(
    id         bigint generated always as identity,
    url        varchar                  not null,
    created_at timestamp with time zone not null default now(),

    primary key (id),
    unique (url)
);

create table if not exists chat_link
(
    link_id bigint not null,
    chat_id bigint not null,
    primary key (link_id, chat_id)
);

alter table chat_link
    add constraint fk_link_id foreign key (link_id) references link (id) on delete cascade;
alter table chat_link
    add constraint fk_chat_id foreign key (chat_id) references chat (id) on delete cascade;
