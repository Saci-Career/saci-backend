CREATE TABLE levels (
    id INT AUTO_INCREMENT PRIMARY KEY,
    role_id INT,
    name VARCHAR (255),
    min_coefficient INT,
    max_coefficient INT,
    link VARCHAR (255),
    FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE
);