CREATE TABLE User(
id          INT(10)     NOT NULL    PRIMARY KEY,
username    VARCHAR(25) NOT NULL,
password    VARCHAR(20) NOT NUll);

CREATE TABLE Semester(
id          INT(3)      NOT NULL    PRIMARY KEY,
year        TIMESTAMP   NOT NULL,
type        CHAR(6)     NOT NULL);

CREATE TABLE CourseCoordinator(
mail        VARCHAR(30) NOT NULL    PRIMARY KEY,
name        VARCHAR(20),
courseCode  CHAR(7)     NOT NULL    FOREIGN KEY);

CREATE TABLE Course(
CourseCode  CHAR(7)     NOT NULL    PRIMARY KEY,
CourseName  VARCHAR(40),
location    VARCHAR(20),
department  VARCHAR(20));

CREATE TABLE Lecture(
CourseCode  CHAR(7)     NOT NULL    PRIMARY KEY,
weekDay     INT(1)      NOT NULL,
WeekNum     INT(2)      NOT NULL,
StartTime   CHAR(5)     NOT NULL,
Room        CHAR(3)     NOT NULL);

CREATE TABLE Lecturer(
mail        VARCHAR(30) NOT NULL    PRIMARY KEY,
name        VARCHAR(20),
courseCode  CHAR(7)     NOT NULL    FOREIGN KEY);