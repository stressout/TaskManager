create schema TaskManagerDB;

CREATE TABLE Projects (
	projectId INT AUTO_INCREMENT NOT NULL,
	projectName VARCHAR NOT NULL,
  	PRIMARY KEY (projectId)
);

CREATE TABLE Users (
	userId INT AUTO_INCREMENT NOT NULL,
	firstName VARCHAR(45) NOT NULL,
	lastName VARCHAR(45) NOT NULL,
	projectId INT,
  	PRIMARY KEY (userId),
	FOREIGN KEY (projectId) REFERENCES Projects (projectId) ON DELETE SET NULL
);

CREATE TABLE Tasks (
	taskId INT AUTO_INCREMENT NOT NULL,
	taskName VARCHAR NOT NULL,
	description VARCHAR,
	userId INT,
  	PRIMARY KEY (taskId),
	FOREIGN KEY (userId) REFERENCES Users (userId) ON DELETE SET NULL
);

CREATE TABLE Subtasks (
    subtaskId INT AUTO_INCREMENT NOT NULL,
	taskId INT,
	subtaskName VARCHAR NOT NULL,
	PRIMARY KEY (subtaskId),
	FOREIGN KEY (taskId) REFERENCES Tasks (taskId) ON DELETE CASCADE
);

INSERT INTO Projects (projectName) VALUES ('Java Collections'), ('Java Exception'), ('Spring'), ('MySQL');

INSERT INTO Users (userId, firstName, lastName, projectId) VALUES ('1', 'Joshua', 'Bloch', '1');
INSERT INTO Users (userId, firstName, lastName) VALUES ('2', 'Neal', 'Gafter');
INSERT INTO Users (userId, firstName, lastName, projectId) VALUES ('3', 'Frank', 'Yellin', '2');
INSERT INTO Users (userId, firstName, lastName, projectId) VALUES ('4', 'Juergen', 'Hoeller', '3');
INSERT INTO Users (userId, firstName, lastName, projectId) VALUES ('5', 'Michael', 'Widenius', '4');

INSERT INTO Tasks (taskName, description, userId) VALUES ('Design the Set intefrace', 'Test Description1', 1);
INSERT INTO Tasks (taskName, description, userId) VALUES ('Create component BeanFactoryPostProcessor for Autowired annotation', '', 4);
INSERT INTO Tasks (taskName, description, userId) VALUES ('Add to new query in SQL language', 'Add query to insert new row in database', 5);
INSERT INTO Tasks (taskName, description, userId) VALUES ('Design the Map interface', 'Test Description1', 1);
INSERT INTO Tasks (taskName, description, userId) VALUES ('Design the ArrayList class', '', 1);

INSERT INTO Subtasks (taskId, subtaskName) VALUES (1, 'Create method clear()');
INSERT INTO Subtasks (taskId, subtaskName) VALUES (2, 'Create Class LinkedHashMap');
INSERT INTO Subtasks (taskId, subtaskName) VALUES (3, 'Create method addAll()');
INSERT INTO Subtasks (taskId, subtaskName) VALUES (3, 'Create method size()');
INSERT INTO Subtasks (taskId, subtaskName) VALUES (5, 'Add support for subquery');