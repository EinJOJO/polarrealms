
CREATE TABLE polarrealms_templates
(
    name               VARCHAR(64)  NOT NULL,
    permission         VARCHAR(128) NOT NULL,
    default_properties JSON         NOT NULL,
    world              BYTEA,
    CONSTRAINT polarrealms_templates_pk PRIMARY KEY (name)
);