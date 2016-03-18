# comment schema
 
# --- !Ups
 
CREATE TABLE comment (
    id int NOT NULL AUTO_INCREMENT,
    content text NOT NULL,
    status tinyint NOT NULL,
    create_date datetime NOT NULL,
    update_date datetime NOT NULL,
    PRIMARY KEY (id)
);
 
# --- !Downs
 
DROP TABLE comment;
