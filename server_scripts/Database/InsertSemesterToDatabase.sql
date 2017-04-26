BULK INSERT Semester
FROM '"FILEPATH"\Semester.csv' --insert FILEPATH to file
WITH
(
FIELDTERMINATOR = ',',
ROWTERMINATOR = '\n'
)
GO
--Check the content of the table.
SELECT *
FROM Semester
GO
--Drop the table to clean up database.
DROP TABLE Semester
GO
