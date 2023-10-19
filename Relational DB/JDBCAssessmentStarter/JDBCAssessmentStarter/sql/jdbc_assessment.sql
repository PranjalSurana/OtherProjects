
--DROP TABLE aa_client_phone;
--DROP TABLE aa_client;

CREATE TABLE aa_client (
    client_id   NUMBER(6, 0),
    client_name VARCHAR2(200) NOT NULL,
    client_risk CHAR(1)       NOT NULL,
    CONSTRAINT aa_client_pk PRIMARY KEY(client_id)
);

CREATE TABLE aa_client_phone (
    client_id    NUMBER(6, 0),
    phone_number VARCHAR2(15) NOT NULL,
    CONSTRAINT aa_client_phone_pk PRIMARY KEY(client_id)
);
    
ALTER TABLE aa_client_phone
    ADD CONSTRAINT aa_client_client_phone_fk
    FOREIGN KEY (client_id)
    REFERENCES aa_client(client_id);

INSERT INTO aa_client (client_id, client_name, client_risk) VALUES (1, 'Ford Prefect', 'H');
INSERT INTO aa_client (client_id, client_name, client_risk) VALUES (2, 'Arthur Dent', 'L');
INSERT INTO aa_client (client_id, client_name, client_risk) VALUES (3, 'Tricia McMillan', 'M');
INSERT INTO aa_client (client_id, client_name, client_risk) VALUES (4, 'Zaphod Beeblebrox', 'P');
INSERT INTO aa_client (client_id, client_name, client_risk) VALUES (5, 'Hotblack Desiato', 'H');
INSERT INTO aa_client (client_id, client_name, client_risk) VALUES (6, 'Slartibartfast', 'L');

INSERT INTO aa_client_phone (client_id, phone_number) VALUES (1, '+441174960234');
INSERT INTO aa_client_phone (client_id,  phone_number) VALUES (2, '+441632960987');
INSERT INTO aa_client_phone (client_id,  phone_number) VALUES (3, '+14245550110');
INSERT INTO aa_client_phone (client_id,  phone_number) VALUES (4, '+914440311010');
INSERT INTO aa_client_phone (client_id,  phone_number) VALUES (6, '+353209174242');

COMMIT;

SELECT client_id, client_name, client_risk FROM aa_client;

SELECT client_id, phone_number FROM aa_client_phone;

SELECT c.client_id AS client_id,
       c.client_name AS client_name,
       c.client_risk AS client_risk,
       p.phone_number AS phone_number
FROM aa_client c
LEFT JOIN aa_client_phone p
ON c.client_id = p.client_id;

SELECT c.client_id AS client_id,
       c.client_name AS client_name,
       c.client_risk AS client_risk,
       p.phone_number AS phone_number
FROM aa_client c
LEFT JOIN aa_client_phone p
ON c.client_id = p.client_id
WHERE c.client_id = 1;

INSERT INTO aa_client (client_name, client_risk, client_id) VALUES ('abc', 'M', 10);

INSERT INTO aa_client (client_id, client_name, client_risk) VALUES (10, 'New User', 'N');

INSERT INTO aa_client_phone (client_id, phone_number) VALUES (10, '+914440311010');

DELETE FROM aa_client_phone WHERE client_id = 10;

DELETE FROM aa_client WHERE client_id = 10;


SELECT c.client_id AS client_id,
                   c.client_name AS client_name,
                   c.client_risk AS client_risk,
                   p.phone_number AS phone_number
            FROM aa_client c
            LEFT JOIN aa_client_phone p
            ON c.client_id = p.client_id
            ORDER BY  c.client_id, c.client_name;
            
SELECT client_id, client_name, client_risk FROM aa_client WHERE client_id = 17;