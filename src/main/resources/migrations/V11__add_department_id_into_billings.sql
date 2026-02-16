ALTER TABLE mediflow_schema.billing
ADD COLUMN IF NOT EXISTS department_id bigint;

ALTER TABLE mediflow_schema.billing
    ADD COLUMN IF NOT EXISTS appointment_id bigint;

ALTER TABLE mediflow_schema.billing
    ADD CONSTRAINT billing_department_id_fkey FOREIGN KEY (department_id) REFERENCES departments(id) ON DELETE CASCADE;
ALTER TABLE mediflow_schema.billing
    ADD CONSTRAINT billing_appointment_id_fkey FOREIGN KEY (appointment_id) REFERENCES appointments(id) ON DELETE CASCADE;
