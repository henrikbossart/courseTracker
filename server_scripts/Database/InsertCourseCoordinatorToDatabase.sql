BULK INSERT CourseCoordinator
FROM '"FILEPATH"\CourseCoordinator.csv' --insert FILEPATH to file
WITH
(
FIELDTERMINATOR = ',',
ROWTERMINATOR = '\n'
)
GO
--Check the content of the table.
SELECT *
FROM CourseCoordinator
GO
--Drop the table to clean up database.
DROP TABLE CourseCoordinator
GO
