create table transformation_history_detail
(
    id                  uuid primary key default gen_random_uuid(),
    history_id          uuid not null references transformation_history (id),
    original_value      varchar(128) not null,
    processed_value     varchar(128) not null
);

comment on table transformation_history_detail is 'Transformation history detail data';

comment on column transformation_history_detail.id is 'Unique identifier';
comment on column transformation_history_detail.history_id is 'Foreign key to the transformation history table';
comment on column transformation_history_detail.original_value is 'Original value';
comment on column transformation_history_detail.processed_value is 'Processed value';

create index transformation_history_detail_history_id_index on transformation_history_detail (history_id);