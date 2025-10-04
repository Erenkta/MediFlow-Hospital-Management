ALTER TABLE mediflow_schema.doctors DROP COLUMN IF EXISTS specialty CASCADE;
DROP TYPE IF EXISTS specialty_enum;

DROP TABLE mediflow_schema.specialties;

CREATE TABLE IF NOT EXISTS mediflow_schema.specialties(
        code VARCHAR(3) PRIMARY KEY,
        name VARCHAR(30) NOT NULL UNIQUE,
        department_id BIGINT REFERENCES departments(id)
);
INSERT INTO mediflow_schema.specialties (code, name) VALUES
                                         ('001', 'Cardiology'),
                                         ('002', 'Dermatology'),
                                         ('004', 'Neurology'),
                                         ('005', 'Immunology'),
                                         ('006', 'Internal Medicine'),
                                         ('007', 'Emergency Medicine'),
                                         ('008', 'Nephrology'),
                                         ('003', 'Hematology'),
                                         ('000', 'N/A');

ALTER TABLE mediflow_schema.doctors ADD COLUMN specialty VARCHAR(30),
ADD CONSTRAINT  fk_specialty FOREIGN KEY (specialty) REFERENCES specialties(code)