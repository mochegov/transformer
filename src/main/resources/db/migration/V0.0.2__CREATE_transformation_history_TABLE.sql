create table transformation_history
(
    id                  uuid primary key default gen_random_uuid(),
    request_id          varchar(50) not null,
    processed_at        timestamp default CURRENT_TIMESTAMP
);

comment on table transformation_history is 'Transformation history';

comment on column transformation_history.id is 'Unique identifier';
comment on column transformation_history.request_id is 'Request id';
comment on column transformation_history.processed_at is 'Processing timestamp';

create index transformation_history_request_id_index on transformation_history (request_id);