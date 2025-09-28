-- ====================================================
-- Flyway Migration: V1__init.sql
-- MediFlow Hospital Management System
-- ====================================================

-- SCHEMA
CREATE SCHEMA IF NOT EXISTS mediflow_schema;
SET search_path TO mediflow_schema;

-- ======================
-- TYPES
-- ======================
CREATE TYPE gender_enum AS ENUM ('MALE','FEMALE','OTHER');
CREATE TYPE blood_group AS ENUM ('00RH+','00RH-','A0RH+','A0RH-','B0RH+','B0RH-','ABRH+','ABRH-');
CREATE TYPE title_enum AS ENUM ('INTERN','ASSISTANT','SPECIALIST','PROFESSOR');
CREATE TYPE appointment_status_enum AS ENUM ('PENDING','APPROVED','SCHEDULED','REJECTED','DONE');
CREATE TYPE billing_status_enum AS ENUM ('PENDING','APPROVED','TRANSFERRED','INVOICED');

-- ======================
-- TABLE: patients
-- ======================
CREATE TABLE patients (
                          id BIGSERIAL PRIMARY KEY,
                          first_name VARCHAR(50) NOT NULL,
                          last_name VARCHAR(50) NOT NULL,
                          date_of_birth DATE,
                          gender gender_enum NOT NULL,
                          phone VARCHAR(20) NOT NULL,
                          blood_group blood_group NOT NULL,
                          email VARCHAR(100),
                          version BIGINT DEFAULT 0,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ======================
-- TABLE: doctors
-- ======================
CREATE TABLE doctors (
                         id BIGSERIAL PRIMARY KEY,
                         doctor_code BIGINT,
                         title title_enum,
                         first_name VARCHAR(50) NOT NULL,
                         last_name VARCHAR(50) NOT NULL,
                         specialty VARCHAR(100),
                         phone VARCHAR(20),
                         email VARCHAR(100),
                         version BIGINT DEFAULT 0,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ======================
-- TABLE: appointments
-- ======================
CREATE TABLE appointments (
                              id BIGSERIAL PRIMARY KEY,
                              patient_id INT NOT NULL REFERENCES patients(id) ON DELETE CASCADE,
                              doctor_id INT NOT NULL REFERENCES doctors(id) ON DELETE CASCADE,
                              appointment_date TIMESTAMP NOT NULL,
                              reason TEXT,
                              status appointment_status_enum DEFAULT 'PENDING',
                              version BIGINT DEFAULT 0,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ======================
-- TABLE: medical_records
-- ======================
CREATE TABLE medical_records (
                                 id BIGSERIAL PRIMARY KEY,
                                 patient_id INT NOT NULL REFERENCES patients(id) ON DELETE CASCADE,
                                 doctor_id INT REFERENCES doctors(id),
                                 diagnosis TEXT,
                                 treatment TEXT,
                                 prescription TEXT,
                                 version BIGINT DEFAULT 0,
                                 record_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ======================
-- TABLE: departments
-- ======================
CREATE TABLE departments (
                             id BIGSERIAL PRIMARY KEY,
                             name VARCHAR(100) NOT NULL UNIQUE,
                             description TEXT,
                             version BIGINT DEFAULT 0,
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ======================
-- TABLE: doctor_department (Many-to-Many)
-- ======================
CREATE TABLE doctor_department (
                                   doctor_id INT NOT NULL REFERENCES doctors(id) ON DELETE CASCADE,
                                   department_id INT NOT NULL REFERENCES departments(id) ON DELETE CASCADE,
                                   PRIMARY KEY (doctor_id, department_id),
                                   version BIGINT DEFAULT 0,
                                   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ======================
-- TABLE: billing
-- ======================
CREATE TABLE billing (
                         id BIGSERIAL PRIMARY KEY,
                         patient_id INT NOT NULL REFERENCES patients(id) ON DELETE CASCADE,
                         amount DECIMAL(10,2) NOT NULL,
                         status billing_status_enum DEFAULT 'PENDING',
                         billing_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         version BIGINT DEFAULT 0,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
