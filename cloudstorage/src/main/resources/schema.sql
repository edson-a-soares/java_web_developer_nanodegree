CREATE TABLE IF NOT EXISTS USERS (
  userid INT PRIMARY KEY auto_increment,
  username VARCHAR(20),
  salt VARCHAR,
  password VARCHAR,
  firstname VARCHAR(20),
  lastname VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS NOTES (
    id INT PRIMARY KEY auto_increment,
    title VARCHAR(20),
    description VARCHAR (1000),
    userid INT,
    foreign key (userid) references USERS(userid)
);

CREATE TABLE IF NOT EXISTS FILES (
    id INT PRIMARY KEY auto_increment,
    name VARCHAR,
    content_type VARCHAR,
    size VARCHAR,
    userid INT,
    data BLOB,
    foreign key (userid) references USERS(userid)
);

CREATE TABLE IF NOT EXISTS CREDENTIALS (
    id INT PRIMARY KEY auto_increment,
    url VARCHAR(100),
    username VARCHAR (30),
    key VARCHAR,
    password VARCHAR,
    userid INT,
    foreign key (userid) references USERS(userid)
);
