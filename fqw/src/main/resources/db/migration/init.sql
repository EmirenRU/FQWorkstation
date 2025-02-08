ALTER TABLE "department"
ALTER COLUMN department_code TYPE bigint USING department_code::bigint;

