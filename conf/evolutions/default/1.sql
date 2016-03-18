# post schema
 
# --- !Ups
 
CREATE TABLE post (
    id int NOT NULL AUTO_INCREMENT,
    title varchar(255) NOT NULL,
    content text NOT NULL,
    status tinyint NOT NULL,
    create_date datetime NOT NULL,
    update_date datetime NOT NULL,
    PRIMARY KEY (id)
);
 
# --- !Downs
 
DROP TABLE post;
