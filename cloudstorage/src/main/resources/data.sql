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
