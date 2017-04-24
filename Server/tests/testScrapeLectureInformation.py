# encoding: utf-8

import unittest2
import os
from datetime import date

from scraper.scrapeLectureInformation import LectureInformationScraper
from scraper.manipulateCSV import ManipulateCSVFile

# This is an example on how we can scrape information about lectures from NTNU's site.

class TestLectureInformationScraper(unittest2.TestCase):
    

    # Init lectureinformationscraper for course TDT4140
    def __init__(self, test):

        self.testLFS = LectureInformationScraper()
        self.testLink = "https://www.ntnu.edu/web/studies/courses?p_p_id=coursedetailsportlet_WAR_courselistportlet&p_p_lifecycle=2&p_p_resource_id=timetable&_coursedetailsportlet_WAR_courselistportlet_year=%d&_coursedetailsportlet_WAR_courselistportlet_courseCode=%s&year=%d&version=%d" %(date.today().year, 'TDT4140', date.today().year, 1)
        self.testHtml = '{"course":{"summarized":[{"acronym":"ØV","description":"Øving","arsterminId":"2017_VÅR","courseCode":"TDT4140","courseName":"Programvareutvikling","studyProgramKeys":["BIT","MLREAL","MTDT","MTING","MTIØT","MTTK"],"from":"08:15","to":"10:00","dayNum":1,"weeks":["2-14","17"],"rooms":[{"etasje":0,"kapasitetUnd":0,"romNavn":"R1","syllabusKey":"360CU1-101","syllabusromkode":"360CU1-101"}]},{"acronym":"FOR","description":"Forelesning","arsterminId":"2017_VÅR","courseCode":"TDT4140","courseName":"Programvareutvikling","studyProgramKeys":["BIT","MLREAL","MTDT","MTING","MTIØT","MTTK"],"from":"16:15","to":"18:00","dayNum":2,"weeks":["2-14","17"],"rooms":[{"etasje":0,"kapasitetUnd":0,"romNavn":"R1","syllabusKey":"360CU1-101","syllabusromkode":"360CU1-101"}]},{"acronym":"FOR","description":"Forelesning","arsterminId":"2017_VÅR","courseCode":"TDT4140","courseName":"Programvareutvikling","studyProgramKeys":["BIT","MLREAL","MTDT","MTING","MTIØT","MTTK"],"from":"14:15","to":"16:00","dayNum":5,"weeks":["2-14","16"],"rooms":[{"etasje":0,"kapasitetUnd":0,"romNavn":"R1","syllabusKey":"360CU1-101","syllabusromkode":"360CU1-101"}]}]}}'

    
    # Test: Fetches the link to the course from NTNU's site
    def testGetCourseInformationLink(self):

        self.assertEqual(self.testLFS.getCourseInformation('TDT4140', date.today().year, 1), self.testLink)


    # Test: Fetches and saves html-content to a string
    def testGetCourseInformation(self):

        self.testLFS.courseInformationLink = self.testLink
        self.assertEqual(self.testLFS.getCourseInformation(), self.testHtml)


    # Test: Saves the html-content to a temporarily file if wanted
    def testAddLectureInformationToTxt(self):

        self.testLFS.AddLectureInformationToTxt()
        self.assertNotEqual(os.stat("/home/aleksanderhh/Downloads/temp.txt").st_size, 0)

    
    # Test: Count numbers of lectures 
    def testCountNumLectures(self):

        self.assertEqual(self.testLFS.testCountNumLectures(), 1)


    # Test: Get all lecture indexes
    def testGetLectureIndex(self):

        self.assertNotEqual(self.testLFS.getLectureIndex(), 0)


    # Test: Saves information from site as a string
    def testFetchInfoFromHtml(self):
        
        self.testLFS.fetchInfoFromHtml(0)

        # Checks global variables for class generetaed in function above
        self.assertNotEqual(self.testLFS.courseInformationLink, None)
        self.assertNotEqual(self.testLFS.html, None)
        self.assertNotEqual(self.testLFS.courseCode, None)
        self.assertNotEqual(self.testLFS.startTime, None)
        self.assertNotEqual(self.testLFS.weekDay, None)
        self.assertNotEqual(self.testLFS.weekNum, None)
        self.assertNotEqual(self.testLFS.room, None)


    # Test: Append information about lectures to CSV file
    def testAddLecturesToCSV(self):

        self.testLFS.addLectureToCSV()
        testString = self.testLFS.courseCode + ',' + self.testLFS.startTime + ',' + self.testLFS.weekDay + ',' + self.testLFS.weekNum + ',' + self.testLFS.room
        self.assertEqual(ManipulateCSVFile().readCSV(), testString)


    # Test: Returns list of course codes in csv file
    def testGetCourseCodesInCSV(self):

        self.assertEqual(ManipulateCSVFile().fetchFromCSV(csvFile.getCourseCodeFilePath(), 'courseCode'), 'TDT4140')

    # Test: Runs the command fixLextureWeeks in manipulateCSV
    def testRunFixLectureWeeks(self):

        self.assertNotEqual(os.stat('/home/aleksanderhh/Downloads/Database/tests/testLecturesFixed.csv').st_size, 0)
