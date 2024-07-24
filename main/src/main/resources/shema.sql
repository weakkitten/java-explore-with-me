CREATE TABLE IF NOT EXISTS users (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(32) NOT NULL,
  CONSTRAINT pk_users PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS categories (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  name VARCHAR(255) NOT NULL UNIQUE,
  CONSTRAINT pk_categories PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS compilation_event (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  compilationId BIGINT NOT NULL,
  eventId BIGINT NOT NULL,
  CONSTRAINT pk_compilation_event PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS compilation (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  pinned boolean NOT NULL,
  title VARCHAR(255) NOT NULL,
  CONSTRAINT pk_compilation PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS events (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  annotation VARCHAR,
  category_id BIGINT,
  confirmed_requests BIGINT,
  created_on TIMESTAMP,
  description VARCHAR(2000),
  eventDate TIMESTAMP,
  initiator_id BIGINT,
  lat BIGINT,
  lon BIGINT,
  paid boolean NOT NULL,
  participant_limit BIGINT,
  published_on TIMESTAMP,
  request_moderation boolean,
  state VARCHAR(32),
  title VARCHAR(255) NOT NULL,
  views BIGINT,
  CONSTRAINT pk_compilation PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS request (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  requester BIGINT NOT NULL,
  created TIMESTAMP NOT NULL,
  status VARCHAR(255),
  event BIGINT,
  CONSTRAINT pk_compilation PRIMARY KEY (id)
);
