-- DROP TABLE
DROP TABLE IF EXISTS ms_role;

-- Create app_task table
CREATE TABLE ms_role (
    id VARCHAR(5) PRIMARY KEY NOT NULL,
    name VARCHAR(50) NOT NULL,
    created_date TIMESTAMP NOT NULL,
    creator_id INT4 NOT NULL,
    updated_date TIMESTAMP NULL,
    updater_id INT4 NULL,
    deleted_date TIMESTAMP NULL,
    deleter_id INT4 NULL,
    rec_status VARCHAR(1) NULL DEFAULT 'N'::VARCHAR
)
WITH (
    OIDS = FALSE
);

-- QUERY ALL data
SELECT * FROM ms_role;