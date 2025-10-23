CREATE TABLE polarrealms_realms
(
    id          UUID PRIMARY KEY NOT NULL,
    owner_uid   UUID             NOT NULL,
    name        VARCHAR(64),
    properties  JSON,
    last_loaded TIMESTAMP,
    created_at  TIMESTAMP        NOT NULL DEFAULT NOW(),
    CONSTRAINT uq_owner_realm_name UNIQUE (owner_uid, name)
);
CREATE INDEX idx_realms_owner_uid ON polarrealms_realms (owner_uid);

CREATE TABLE polarrealms_realm_members
(
    realm_id    UUID REFERENCES polarrealms_realms (id) ON DELETE CASCADE,
    member      UUID,
    trust_level SMALLINT NOT NULL CHECK ( trust_level >= 0 ),
    PRIMARY KEY (realm_id, member)
);
CREATE INDEX idx_realm_members_member ON polarrealms_realm_members (member);
