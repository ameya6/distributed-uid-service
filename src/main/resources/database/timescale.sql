CREATE TABLE duid_process (
  id uuid NOT NULL DEFAULT gen_random_uuid() ,
  time TIMESTAMPTZ NOT NULL,
  start_time bigint NOT NULL,
  end_time bigint NOT NULL,
  process_time bigint NOT NULL,
  duid bigint NOT NULL
);

CREATE UNIQUE INDEX ix_duid_process_time ON duid_process (id, time DESC);

SELECT create_hypertable('duid_process', 'time', if_not_exists => TRUE, chunk_time_interval => INTERVAL '1 day');