CREATE TABLE IF NOT EXISTS stats (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  app VARCHAR(255) NOT NULL,
  uri VARCHAR(512) NOT NULL,
  ip VARCHAR(64) NOT NULL,
  times TIMESTAMP NOT NULL,
  CONSTRAINT pk_user PRIMARY KEY (id)
);