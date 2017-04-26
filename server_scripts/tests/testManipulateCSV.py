# encoding: utf-8
import unittest2
import os

from scraper.scrapeLectureInformation import LectureInformationScraper
from scraper.manipulateCSV import ManipulateCSVFile


class TestManipulateCSVFile(unittest2.TestCase):


    # Test: Commandline for choosing file to manipulate
    def testChooseFile(self):
        assertEqual(self.ManipulateCSVFile().chooseFile(0), self.ManipulateCSVFile().lecture)
        assertEqual(self.ManipulateCSVFile().chooseFile(1), self.ManipulateCSVFile().testTdtCourses)
        assertEqual(self.ManipulateCSVFile().chooseFile(2), self.ManipulateCSVFile().courseCoordinator)
        assertEqual(self.ManipulateCSVFile().chooseFile(3), self.ManipulateCSVFile().lecturer)
        assertEqual(self.ManipulateCSVFile().chooseFile(4), self.ManipulateCSVFile().semester)
        assertEqual(self.ManipulateCSVFile().chooseFile(5), self.ManipulateCSVFile().lectureFixed)
        assertNotEqual(self.ManipulateCSVFile().chooseFile(6), -1)


    # Test: Returns TDT courses csv filepath
    def testGetCourseCodeFilePath(self):

        assertEqual(self.ManipulateCSVFile().testTdtCourses, )


    # Test: Prints out content in csv file
    def testReadCSV(self):

        self.ManipulateCSVFile().readCSV(self.ManipulateCSVFile().tdtCourses)


    # Test: Fetch and saves a specific column in csv file to a list
    def testFetchFromCSV(self):

        assertNotEqual(self.ManipulateCSVFile().fetchFromCSV(self.ManipulateCSVFile().tdtCourses, 0), "")
    

    # Test: Writes to csv file
    def testWriteToCSV(self):

        sizeBefore = self.ManipulateCSVFile().tdtCourses.st_size
        self.ManipulateCSVFile().writeToCSV(self.ManipulateCSVFile().tdtCourses, "TEST")
        assertNotEqual(sizeBefore, self.ManipulateCSVFile().tdtCourses.st_size)
        

    # Test: Count lines in csv file
    def testCountLinesInCSV(self):

        assertEqual(self.ManipulateCSVFile().countLinesInCSV('/home/aleksanderhh/Downloads/Database/tests/temp.csv'), 1)


    # Test: Deletes information stored in the csv-file
    def testCleanCSVFile(self):

        self.ManipulateCSVFile().cleanCSVFile('/home/aleksanderhh/Downloads/Database/tests/temp.csv')
        assertEqual('/home/aleksanderhh/Downloads/Database/tests/temp.csv'.st_size, 0)


    # Test: Makes the csv file correct for implementation to sql database
    def testFixtestLectureWeeks(self):

        self.ManipulateCSVFile().fixLectureWeeks()
        assertNotEqual(self.ManipulateCSVFile().lectureFixed.st_size, 0)


    # Returns date from weekday, weeknumber and year
    def testGetDate(self):
    
        assertEqual(self.ManipulateCSVFile().getDate(3,11), '2017-03-16')
    

    # Convert weekday and week to date
    def testLectureToDateFormat(self):
        
        assertNotEqual(self.ManipulateCSVFile().lectureFixedDate.st_size, 0)
       


if __name__ == '__main__':
    unittest2.main()
