ALTER TABLE mediflow_schema.billing
Drop CONSTRAINT IF EXISTS billing_status_check;

ALTER TABLE mediflow_schema.billing
    ADD CONSTRAINT  billing_status_check
    CHECK (status IN ('PENDING','APPROVED','TRANSFERRED','INVOICED','CANCELLED','OVERDUE','N/A'));


ALTER TABLE mediflow_schema.billing
ADD COLUMN  IF NOT EXISTS payment_date timestamp default now() ;

ALTER TABLE mediflow_schema.billing
DROP CONSTRAINT IF EXISTS billing_type_check;

ALTER TABLE mediflow_schema.billing
ADD COLUMN IF NOT EXISTS type varchar(50) NOT NULL DEFAULT 'N/A',
ADD CONSTRAINT  billing_type_check
CHECK (type IN ('DEPOSIT','TREATMENT','N/A',''))
