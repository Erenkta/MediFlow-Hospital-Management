ALTER TABLE mediflow_schema.doctors DROP COLUMN specialty CASCADE;
DROP TYPE IF EXISTS specialty_enum;

ALTER TABLE mediflow_schema.doctors
    ADD COLUMN specialty VARCHAR(50) NOT NULL DEFAULT 'N/A',
    ADD CONSTRAINT specialty_check
        CHECK (specialty IN ('CARDIOLOGY','DERMATOLOGY','HEMATOLOGY','NEUROLOGY','IMMUNOLOGY','INTERNAL_MEDICINE','EMERGENCY_MEDICINE','NEPHROLOGY','N/A'));

ALTER TABLE mediflow_schema.doctors DROP COLUMN title CASCADE;
DROP TYPE IF EXISTS title_enum;

ALTER TABLE mediflow_schema.doctors
    ADD COLUMN title VARCHAR(15) NOT NULL DEFAULT 'N/A',
    ADD CONSTRAINT title_check
        CHECK (title IN ('INTERN','ASSISTANT','SPECIALIST','PROFESSOR','N/A'));



ALTER TABLE mediflow_schema.patients DROP COLUMN gender CASCADE;
DROP TYPE IF EXISTS gender_enum;

ALTER TABLE mediflow_schema.patients
    ADD COLUMN gender VARCHAR(10) NOT NULL DEFAULT 'N/A',
    ADD CONSTRAINT gender_check
        CHECK (gender IN ('MALE','FEMALE','OTHER','N/A'));

ALTER TABLE mediflow_schema.patients DROP COLUMN blood_group CASCADE;
DROP TYPE IF EXISTS blood_group;

ALTER TABLE mediflow_schema.patients
    ADD COLUMN blood_group VARCHAR(5) NOT NULL DEFAULT 'N/A',
    ADD CONSTRAINT blood_group_check
        CHECK (blood_group IN ('00RH+','00RH-','A0RH+','A0RH-','B0RH+','B0RH-','ABRH+','ABRH-','N/A'));

ALTER TABLE mediflow_schema.appointments DROP COLUMN status CASCADE;
DROP TYPE IF EXISTS appointment_status_enum;

ALTER TABLE mediflow_schema.appointments
    ADD COLUMN status VARCHAR(15) NOT NULL DEFAULT 'N/A',
    ADD CONSTRAINT appointment_status_check
        CHECK (status IN ('PENDING','APPROVED','SCHEDULED','REJECTED','DONE','N/A'));

ALTER TABLE mediflow_schema.billing DROP COLUMN status CASCADE;
DROP TYPE IF EXISTS billing_status_enum;

ALTER TABLE mediflow_schema.billing
    ADD COLUMN status VARCHAR(15) NOT NULL DEFAULT 'N/A',
    ADD CONSTRAINT billing_status_check
        CHECK (status IN ('PENDING','APPROVED','TRANSFERRED','INVOICED','N/A'));

