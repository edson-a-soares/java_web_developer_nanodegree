-----------------------------------------------------------------
-- Users
-----------------------------------------------------------------
INSERT INTO
    USERS (userid, username, salt, password, firstname, lastname)
VALUES
    (null, 'default', '/aNrytAdzCk4T2opZ+hinQ==', 'LA38MvrKOOHLZLGazwcQEw==', 'Default', 'Default');

-----------------------------------------------------------------
-- Notes
-----------------------------------------------------------------
INSERT INTO
    NOTES (id, title, description, userid)
VALUES
    (null, 'First note title.', 'First note description.', 1),
    (null, 'Second note title.', 'Second note description.', 1),
    (null, 'Third note title.', 'Third note description.', 1),
    (null, 'Fourth note title.', 'Fourth note description.', 1);

-----------------------------------------------------------------
-- Credentials
-----------------------------------------------------------------
INSERT INTO
    CREDENTIALS (id, key, url, password, username, userid)
VALUES
    (null, 'VxmmGbeTZ+lxNUYvhrvJoQ==', 'https://facebook.com', 'vC7hyExnwF7kpJOqz5ctcA==', 'default', 1),
    (null, 'pdmRQe8+1kZuDBLqq+8h2A==', 'https://parler.com', 'R9oueQKILcIyO5EbPWTu3Q==', 'default', 1),
    (null, '4cbw5byNRgjxWN4AZiKk4A==', 'https://instagram.com', '88Je1tLEK6oZO6W4IcfMwA==', 'default', 1);
