DROP TABLE IF EXISTS tasks;

CREATE TABLE tasks (
    id IDENTITY PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    is_completed BOOLEAN DEFAULT FALSE NOT NULL,
    due_date DATETIME NOT NULL
);