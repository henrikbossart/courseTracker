# encoding: utf-8
from datetime import date
from scraper.scrapeLectureInformation import LectureInformationScraper
from scraper.manipulateCSV import ManipulateCSVFile


class Main():
    
    def run(self):
        courses = LectureInformationScraper()
      
        courseCodes = courses.getCourseCodesInCSV() # List with all course codes from csv file
        year = date.today().year
        version = 1

        # Iterate to add all courses in course-file to a csv file with lectures
        for courseNum in range(0, len(courseCodes)):
            course = LectureInformationScraper()
            course.getCourseInformationLink(courseCodes[courseNum], year, version)
            course.getCourseInformation()

            numLectures = course.countNumLectures()
            lectureIndexes = course.getLectureIndex()
       
            # Iterate through all lectures for every course and add them to a csv file with lectures
            for lectureNum in range(0,numLectures):
                course.fetchInfoFromHtml(lectureIndexes[lectureNum])
                course.addLecturesToCSV()

        # Make a lecture for every week instead of a lecture for ex. week 2-15
        course.runFixLectureWeeks()
        
        # Converts from weekday and week to date
        csv = ManipulateCSVFile()
        csv.lectureToDateFormat()

        print "Information regarding the course is saved to the csv-file"


Main().run()
