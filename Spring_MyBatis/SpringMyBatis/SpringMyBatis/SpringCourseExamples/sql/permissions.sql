
DROP TABLE permissions;

CREATE TABLE permissions (
    username  VARCHAR2(20),
    perm      VARCHAR2(20)
);

INSERT INTO permissions (username, perm)
SELECT 'codd', 'superuser' FROM DUAL
UNION ALL
SELECT 'codd', 'admin' FROM DUAL
UNION ALL
SELECT 'date', 'readonly' FROM DUAL;

COMMIT;