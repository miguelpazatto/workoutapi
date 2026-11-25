CREATE TABLE exercises (
    id UUID PRIMARY KEY,
    owner_id UUID NOT NULL,
    name varchar(255) NOT NULL,
    video_url varchar(255),
    active BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE UNIQUE INDEX uq_exercises_owner_name
    ON exercises (owner_id, lower(name));