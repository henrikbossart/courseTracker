BULK INSERT Lecture
FROM '"FILEPATH"\Lectures.csv' --insert FILEPATH to file
WITH
(
FIELDTERMINATOR = ',',
ROWTERMINATOR = '\n'
)
GO
--Check the content of the table.
SELECT *
FROM Lecture
GO
--Drop the table to clean up database.
DROP TABLE Lecture
GO
