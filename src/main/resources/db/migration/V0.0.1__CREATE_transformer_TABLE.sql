create table transformer
(
    id                  uuid primary key default gen_random_uuid(),
    group_id            integer not null,
    transformer_id      integer not null,
    transformer_code    varchar(20) not null,
    transformer_name    varchar(64) not null,
    is_active           boolean not null,
    created_at          timestamp default CURRENT_TIMESTAMP,
    updated_at          timestamp
);

comment on table transformer is 'Transformers dictionary';

comment on column transformer.id is 'Unique identifier';
comment on column transformer.group_id is 'Group id';
comment on column transformer.transformer_id is 'Transformer id';
comment on column transformer.transformer_code is 'Transformer code';
comment on column transformer.transformer_name is 'Transformer name';
comment on column transformer.is_active is 'Active sign';
comment on column transformer.created_at is 'Creation timestamp';
comment on column transformer.updated_at is 'Update timestamp';
