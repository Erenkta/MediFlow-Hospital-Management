ALTER TABLE mediflow_schema.appointments
Drop CONSTRAINT appointment_status_check;

ALTER TABLE mediflow_schema.appointments
ADD CONSTRAINT  appointment_status_check
CHECK (mediflow_schema.appointments.status IN ('PENDING','APPROVED','SCHEDULED','REJECTED','DONE','CANCELLED','NOT_ATTENDED','ON_GOING','N/A'));
