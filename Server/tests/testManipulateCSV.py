# encoding: utf-8
import unittest2
import os

from scraper.scrapeLectureInformation import LectureInformationScraper
from scraper.manipulateCSV import ManipulateCSVFile


class TestManipulateCSVFile:


    # Add filepaths!
    def __init__(self):

        self.testTemp = '/home/aleksanderhh/Downloads/Database/tests/temp.csv'
        # Init manipulateCSV
        self.self.testMCSV = ManipulateCSVFile()


    # Test: Commandline for choosing file to manipulate
    def testChooseFile(self):
        assertEqual(self.testMCSV.chooseFile(0), self.testMCSV.lecture)
        assertEqual(self.testMCSV.chooseFile(1), self.testMCSV.testTdtCourses)
        assertEqual(self.testMCSV.chooseFile(2), self.testMCSV.courseCoordinator)
        assertEqual(self.testMCSV.chooseFile(3), self.testMCSV.lecturer)
        assertEqual(self.testMCSV.chooseFile(4), self.testMCSV.semester)
        assertEqual(self.testMCSV.chooseFile(5), self.testMCSV.lectureFixed)
        assertNotEqual(self.testMCSV.chooseFile(6), -1)


    # Test: Returns TDT courses csv filepath
    def testGetCourseCodeFilePath(self):

        assertEqual(self.testMCSV.testTdtCourses, )


    # Test: Prints out content in csv file
    def testReadCSV(self):

        self.testMCSV.readCSV(self.testMCSV.tdtCourses)


    # Test: Fetch and saves a specific column in csv file to a list
    def testFetchFromCSV(self):

        assertNotEqual(self.testMCSV.fetchFromCSV(self.testMCSV.tdtCourses, 0), "")
    

    # Test: Writes to csv file
    def testWriteToCSV(self):

        sizeBefore = self.testMCSV.tdtCourses.st_size
        self.testMCSV.writeToCSV(self.testMCSV.tdtCourses, "TEST")
        assertNotEqual(sizeBefore, self.testMCSV.tdtCourses.st_size)
        

    # Test: Count lines in csv file
    def testCountLinesInCSV(self):

        assertEqual(self.testMCSV.countLinesInCSV(self.testTemp), 1)


    # Test: Deletes information stored in the csv-file
    def testCleanCSVFile(self):

        self.testMCSV.cleanCSVFile(self.testTemp)
        assertEqual(self.testTemp.st_size, 0)


    # Test: Makes the csv file correct for implementation to sql database
    def testFixtestLectureWeeks(self):

        self.testMCSV.fixLectureWeeks()
        assertNotEqual(self.testMCSV.lectureFixed.st_size, 0)


    # Returns date from weekday, weeknumber and year
    def testGetDate(self):
    
        assertEqual(self.testMCSV.getDate(3,11), '2017-03-16')
    

    # Convert weekday and week to date
    def testLectureToDateFormat(self):
        
        assertNotEqual(self.testMCSV.lectureFixedDate.st_size, 0)
        