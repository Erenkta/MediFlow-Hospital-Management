-- ====================================================
-- Flyway Migration: V2__update_fk_bigint.sql
-- Update INT FKs to BIGINT for MediFlow
-- ====================================================

SET search_path TO mediflow_schema;

-- ----------------------------
-- Drop existing FK constraints
-- ----------------------------

ALTER TABLE appointments DROP CONSTRAINT IF EXISTS appointments_patient_id_fkey;
ALTER TABLE appointments DROP CONSTRAINT IF EXISTS appointments_doctor_id_fkey;

ALTER TABLE medical_records DROP CONSTRAINT IF EXISTS medical_records_patient_id_fkey;
ALTER TABLE medical_records DROP CONSTRAINT IF EXISTS medical_records_doctor_id_fkey;

ALTER TABLE doctor_department DROP CONSTRAINT IF EXISTS doctor_department_doctor_id_fkey;
ALTER TABLE doctor_department DROP CONSTRAINT IF EXISTS doctor_department_department_id_fkey;

ALTER TABLE billing DROP CONSTRAINT IF EXISTS billing_patient_id_fkey;

-- ----------------------------
-- Alter columns INT -> BIGINT
-- ----------------------------

ALTER TABLE appointments ALTER COLUMN patient_id TYPE BIGINT;
ALTER TABLE appointments ALTER COLUMN doctor_id TYPE BIGINT;

ALTER TABLE medical_records ALTER COLUMN patient_id TYPE BIGINT;
ALTER TABLE medical_records ALTER COLUMN doctor_id TYPE BIGINT;

ALTER TABLE doctor_department ALTER COLUMN doctor_id TYPE BIGINT;
ALTER TABLE doctor_department ALTER COLUMN department_id TYPE BIGINT;

ALTER TABLE billing ALTER COLUMN patient_id TYPE BIGINT;

-- ----------------------------
-- Re-create FK constraints
-- ----------------------------

ALTER TABLE appointments
    ADD CONSTRAINT appointments_patient_id_fkey FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE;

ALTER TABLE appointments
    ADD CONSTRAINT appointments_doctor_id_fkey FOREIGN KEY (doctor_id) REFERENCES doctors(id) ON DELETE CASCADE;

ALTER TABLE medical_records
    ADD CONSTRAINT medical_records_patient_id_fkey FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE;

ALTER TABLE medical_records
    ADD CONSTRAINT medical_records_doctor_id_fkey FOREIGN KEY (doctor_id) REFERENCES doctors(id);

ALTER TABLE doctor_department
    ADD CONSTRAINT doctor_department_doctor_id_fkey FOREIGN KEY (doctor_id) REFERENCES doctors(id) ON DELETE CASCADE;

ALTER TABLE doctor_department
    ADD CONSTRAINT doctor_department_department_id_fkey FOREIGN KEY (department_id) REFERENCES departments(id) ON DELETE CASCADE;

ALTER TABLE billing
    ADD CONSTRAINT billing_patient_id_fkey FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE;
