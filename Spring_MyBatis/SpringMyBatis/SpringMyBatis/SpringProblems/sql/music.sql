DROP TABLE music;

CREATE TABLE music (
  musicid NUMBER NOT NULL 
, title VARCHAR2(40) 
, artist VARCHAR2(40) 
, genre VARCHAR2(40) 
, CONSTRAINT music_pk PRIMARY KEY (musicid) ENABLE 
);


INSERT INTO music VALUES(1, 'White Album', 'The Beatles', 'Rock');
INSERT INTO music VALUES(2, 'Dark Side of the Moon', 'Pink Floyd', 'Rock');
INSERT INTO music VALUES(3, 'Worlds Collide', 'Apocalyptica', 'World');		
INSERT INTO music VALUES(4, 'Out of This World', 'Tito Puente', 'Jazz');		
    
COMMIT;