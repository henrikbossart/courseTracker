# Test case
import unittest2
from mainLectureScraper import Main
from tests.testScrapeLectureInformation import TestLectureInformationScraper
from tests.testManipulateCSV import TestManipulateCSVFile


class TestMain(unittest2.TestCase):

    def testRun(self):
        # Main class
        mainMethod = Main()

        # Checks variables for the lines 9-13

        self.assertEqual(mainMethod.courses, TestLectureInformationScraper())
        self.assertEqual(mainMethod.courseCodes, TestLectureInformationScraper.testGetCourseCodesInCSV)
        self.assertEqual(mainMethod.year, date.today().year)
        self.assertEqual(mainMethod.version, 1)
        
        # Checks variables for the lines 16-22
        self.assertEqual(mainMethod.courseCodes, len(courses.testGetCourseCodesInCSV()))
        self.assertEqual(mainMethod.course, TestLectureInformationScraper())
        self.assertEqual(mainMethod.course.getCourseInformationLink(courseCodes[0], mainMethod.year, mainMethod.version), TestLectureInformationScraper().testGetCourseInformationLink(LectureInformationScraper().getCourseCodesInCSV()[0], '2017', '1'))
        self.assertEqual(mainMethod.couse.getCourseInformation(), TestLectureInformationScraper().testGetCourseInformation())
        self.assertEqual(mainMethod.numLectures, TestLectureInformationScraper().testCountNumLectures())
        self.assertEqual(mainMethod.lectureIndexes, TestLectureInformationScraper().testGetLectureIndex())

        # Test: Add lecture to csv file
        TestLectureInformationScraper().testFetchInfoFromHtml(lectureIndexes[0])
        TestLectureInformationScraper().testAddLecturesToCSV()


        # Test: Make a lecture for every week instead of a lecture for ex. week 2-15
        TestLectureInformationScraper().testRunFixLectureWeeks()


        # Test: # Converts from weekday and week to date
        TestManipulateCSVFile().testLectureToDateFormat()

        # -------------------------------------------------------------
        # Testing functions in testScrapeLectureInformation:
        testSLI = TestLectureInformationScraper()

        # Test: Fetches the link to the course from NTNU's site
        testSLI.testGetCourseInformationLink()


        # Test: Fetches and saves html-content to a string
        testSLI.testGetCourseInformation()


        # Test: Saves the html-content to a temporarily file if wanted
        testSLI.testAddLectureInformationToTxt()

        
        # Test: Count numbers of lectures 
        testSLI.testCountNumLectures()


        # Test: Get all lecture indexes
        testSLI.testGetLectureIndex()


        # Test: Saves information from site as a string
        testSLI.testFetchInfoFromHtml()

            
        # Test: Append information about lectures to CSV file
        testSLI.testAddLecturesToCSV()


        # Test: Returns list of course codes in csv file
        testSLI.testGetCourseCodesInCSV()


        # Test: Runs the command fixLextureWeeks in manipulateCSV
        testSLI.testRunFixLectureWeeks()


        # -------------------------------------------------------------
        # Testing functions in testManipulateCSV:
        testMCSV = TestManipulateCSVFile()

        # Test: Commandline for choosing file to manipulate
        testMCSV.testChooseFile()
            

        # Test: Returns TDT courses csv filepath
        testMCSV.testGetCourseCodeFilePath()


        # Test: Prints out content in csv file
        testMCSV.testReadCSV()


        # Test: Fetch and saves a specific column in csv file to a list
        testMCSV.testFetchFromCSV()


        # Test: Writes to csv file
        testMCSV.testWriteToCSV()


        # Test: Count lines in csv file
        testMCSV.testCountLinesInCSV()

        
        # Test: Deletes information stored in the csv-file
        testMCSV.testCleanCSVFile()

        # Test: Makes the csv file correct for implementation to sql database
        testMCSV.testFixtestLectureWeeks()


        # Returns date from weekday, weeknumber and year
        testMCSV.testGetDate()


        # Convert weekday and week to date
        testMCSV.testLectureToDateFormat()


        print "Testing complete"

#TestMain().testRun()
if __name__ == '__main__':
    unittest2.main()
