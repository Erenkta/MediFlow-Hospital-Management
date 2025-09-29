DROP SEQUENCE IF EXISTS id_generator_seq;
CREATE SEQUENCE mediflow_schema.id_generator_seq
    START WITH 100000
    INCREMENT BY 10;

ALTER SEQUENCE mediflow_schema.id_generator_seq OWNER TO mediflow;