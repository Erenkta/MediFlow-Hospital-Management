ALTER TABLE mediflow_schema.billing
Drop CONSTRAINT IF EXISTS billing_status_check ;

ALTER TABLE mediflow_schema.billing
    ADD CONSTRAINT  billing_status_check
    CHECK (status IN ('PENDING','APPROVED','TRANSFERRED','INVOICED','CANCELLED','N/A'));
