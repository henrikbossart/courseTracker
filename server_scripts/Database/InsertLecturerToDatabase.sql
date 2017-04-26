BULK INSERT Lecturer
FROM '"FILEPATH"\Lecturer.csv' --insert FILEPATH to file
WITH
(
FIELDTERMINATOR = ',',
ROWTERMINATOR = '\n'
)
GO
--Check the content of the table.
SELECT *
FROM Lecturer
GO
--Drop the table to clean up database.
DROP TABLE Lecturer
GO
