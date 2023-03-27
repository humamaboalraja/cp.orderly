DROP SCHEMA IF EXISTS customer CASCADE;

CREATE SCHEMA customer;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE customer.customers (
    id uuid NOT NULL,
    username character varying COLLATE pg_catalog."default" NOT NULL,
    email character varying COLLATE pg_catalog."default" NOT NULL,
    first_name character varying COLLATE pg_catalog."default" NOT NULL,
    last_name character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT customer_pkey PRIMARY KEY (id)
);

DROP MATERIALIZED VIEW IF EXISTS customer.order_customer_materialized_view;

CREATE MATERIALIZED VIEW customer.order_customer_materialized_view
TABLESPACE pg_default
AS
    SELECT id,
           username,
           email
           first_name,
           last_name,
           FROM customer.customers
WITH DATA;


REFRESH MATERIALIZED VIEW customer.order_customer_materialized_view;

DROP function IF EXISTS customer.refresh_order_customer_materialized_view;

CREATE OR replace function customer.refresh_order_customer_materialized_view()
    returns trigger
AS '
    BEGIN
        refresh materialized VIEW customer.order_customer_materialized_view;
        return null;
    END;
'  LANGUAGE plpgsql;

DROP trigger IF EXISTS refresh_order_customer_materialized_view ON customer.customers;

CREATE trigger refresh_order_customer_materialized_view
    after INSERT OR UPDATE OR DELETE OR truncate
    ON customer.customers FOR each statement
EXECUTE PROCEDURE customer.refresh_order_customer_materialized_view();