create table if not exists github_repository
(
    id          bigint,
    link_id     bigint references link (id) on delete cascade,
    full_name   varchar not null,
    forks_count integer not null,
    updated_at  timestamp with time zone,

    primary key (id)
);

create table if not exists stackoverflow_question
(
    id                 bigint,
    link_id            bigint references link (id) on delete cascade,
    title              varchar not null,
    is_answered        bool    not null,
    score              integer not null,
    answer_count       bigint  not null,
    last_activity_date timestamp with time zone,

    primary key (id)
);

