BULK INSERT Course
FROM '"FILEPATH"\Courses.csv' --insert FILEPATH to file
WITH
(
FIELDTERMINATOR = ',',
ROWTERMINATOR = '\n'
)
GO
--Check the content of the table.
SELECT *
FROM Course
GO
--Drop the table to clean up database.
DROP TABLE Course
GO
