DROP SCHEMA IF EXISTS shop CASCADE;

CREATE SCHEMA shop;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TABLE IF EXISTS shop.shops CASCADE;

CREATE TABLE shop.shops
(
    id uuid NOT NULL,
    name character varying COLLATE pg_catalog."default" NOT NULL,
    active boolean NOT NULL,
    CONSTRAINT shops_pkey PRIMARY KEY (id)
);

DROP TYPE IF EXISTS approval_status;

CREATE TYPE approval_status AS ENUM ('APPROVED', 'REJECTED');

DROP TABLE IF EXISTS shop.order_approval CASCADE;

CREATE TABLE shop.order_approval
(
    id uuid NOT NULL,
    shop_id uuid NOT NULL,
    order_id uuid NOT NULL,
    status approval_status NOT NULL,
    CONSTRAINT order_approval_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS shop.products CASCADE;

CREATE TABLE shop.products
(
    id uuid NOT NULL,
    name character varying COLLATE pg_catalog."default" NOT NULL,
    price numeric(10,2) NOT NULL,
    available boolean NOT NULL,
    CONSTRAINT products_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS shop.shop_products CASCADE;

CREATE TABLE shop.shop_products
(
    id uuid NOT NULL,
    shop_id uuid NOT NULL,
    product_id uuid NOT NULL,
    CONSTRAINT shop_products_pkey PRIMARY KEY (id)
);

ALTER TABLE shop.shop_products
    ADD CONSTRAINT "FK_SHOP_ID" FOREIGN KEY (shop_id)
        REFERENCES shop.shops (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE RESTRICT
        NOT VALID;

ALTER TABLE shop.shop_products
    ADD CONSTRAINT "FK_PRODUCT_ID" FOREIGN KEY (product_id)
        REFERENCES shop.products (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE RESTRICT
        NOT VALID;

DROP TYPE IF EXISTS outbox_status;
CREATE TYPE outbox_status AS ENUM ('STARTED', 'COMPLETED', 'FAILED');

DROP TABLE IF EXISTS shop.order_outbox CASCADE;

CREATE TABLE shop.order_outbox
(
    id uuid NOT NULL,
    saga_id uuid NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    processed_at TIMESTAMP WITH TIME ZONE,
    type character varying COLLATE pg_catalog."default" NOT NULL,
    payload jsonb NOT NULL,
    outbox_status outbox_status NOT NULL,
    approval_status approval_status NOT NULL,
    version integer NOT NULL,
    CONSTRAINT order_outbox_pkey PRIMARY KEY (id)
);

CREATE INDEX "shop_order_outbox_saga_status"
    ON "shop".order_outbox
        (type, approval_status);

CREATE UNIQUE INDEX "shop_order_outbox_saga_id"
    ON "shop".order_outbox
        (type, saga_id, approval_status, outbox_status);

DROP MATERIALIZED VIEW IF EXISTS shop.order_shop_materialized_view;

CREATE MATERIALIZED VIEW shop.order_shop_materialized_view
    TABLESPACE pg_default
AS
SELECT r.id AS shop_id,
       r.name AS shop_name,
       r.active AS shop_active,
       p.id AS product_id,
       p.name AS product_name,
       p.price AS product_price,
       p.available AS product_available
FROM shop.shops r,
     shop.products p,
     shop.shop_products rp
WHERE r.id = rp.shop_id AND p.id = rp.product_id
WITH DATA;

refresh materialized VIEW shop.order_shop_materialized_view;

DROP function IF EXISTS shop.refresh_order_shop_materialized_view;

CREATE OR replace function shop.refresh_order_shop_materialized_view()
    returns trigger
AS '
    BEGIN
        refresh materialized VIEW shop.order_shop_materialized_view;
        return null;
    END;
'  LANGUAGE plpgsql;

DROP trigger IF EXISTS refresh_order_shop_materialized_view ON shop.shop_products;

CREATE trigger refresh_order_shop_materialized_view
    after INSERT OR UPDATE OR DELETE OR truncate
    ON shop.shop_products FOR each statement
EXECUTE PROCEDURE shop.refresh_order_shop_materialized_view();
