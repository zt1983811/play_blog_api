# Category schema
 
# --- !Ups
 
CREATE TABLE category (
    id int NOT NULL AUTO_INCREMENT,
    title varchar(255) NOT NULL,
    description text NOT NULL,
    status tinyint NOT NULL,
    create_date datetime NOT NULL,
    update_date datetime NOT NULL,
    PRIMARY KEY (id)
);
 
# --- !Downs
 
DROP TABLE category;
