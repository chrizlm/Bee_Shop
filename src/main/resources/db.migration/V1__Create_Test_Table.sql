CREATE SEQUENCE product_id_seq;

CREATE TABLE product (
    id BIGINT PRIMARY KEY DEFAULT nextval('product_id_seq'),
    name VARCHAR(255) NOT NULL,
    description TEXT
);

ALTER SEQUENCE product_id_seq OWNED BY product.id;