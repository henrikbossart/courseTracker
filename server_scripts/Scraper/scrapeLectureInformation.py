# encoding: utf-8

import re
import urllib2
import os
import sys

from scraper.manipulateCSV import ManipulateCSVFile

# This is an example on how we can scrape information about lectures from NTNU's site.

class LectureInformationScraper:


    def __init__(self):
        self.courseInformationLink = None
        self.html = None
        self.courseCode = None
        self.startTime = None
        self.weekDay = None
        self.weekNum = None
        self.room = None
    

    # Fetches the link to the course from NTNU's site
    def getCourseInformationLink(self, courseCode, year, version):
        self.courseCode = courseCode
        url = "https://www.ntnu.edu/web/studies/courses?p_p_id=coursedetailsportlet_WAR_courselistportlet&p_p_lifecycle=2&p_p_resource_id=timetable&_coursedetailsportlet_WAR_courselistportlet_year=%d&_coursedetailsportlet_WAR_courselistportlet_courseCode=%s&year=%d&version=%d" %(year, courseCode, year, version)
        self.courseInformationLink = url
        #print self.courseInformationLink # For testing


    # Fetches and saves html-content to a string
    def getCourseInformation(self):

        response = urllib2.urlopen(self.courseInformationLink)
        courseInformationContent = response.read()
        self.html = courseInformationContent


    # Saves the html-content to a temporarily file if wanted
    def addLectureInformationToTxt():

        filePath = "/home/aleksanderhh/Downloads/temp.txt" # ADD
        file = open(filePath,'w') 
        file.write(html) 
        file.close() 	

    
    # Count numbers of lectures 
    def countNumLectures(self):

        return self.html.count('"FOR"')


    # Get all lecture indexes
    def getLectureIndex(self):

        lectureStr = '"FOR"'
        return [m.start() for m in re.finditer(lectureStr, self.html)]


    # Saves information from site as a string
    def fetchInfoFromHtml(self, lectureIndex):
        
        courseCodeStr = '"courseCode":'
        startTimeStr = '"from":'
    	weekDayStr = '"dayNum":'
    	weekNumStr = '"weeks":['
    	roomStr = '"romNavn":'
    	endStr = ',"'
        endDayNum = ','
        endWeekNum = '],"rooms":'

        # Finds all the indexes to make us fetch specific information from html
                
    	courseCodeStartIndex = (self.html.find(courseCodeStr, lectureIndex) + len(courseCodeStr))
    	courseCodeEndIndex = self.html.find(endStr, courseCodeStartIndex)
    	startTimeStartIndex = (self.html.find(startTimeStr, lectureIndex) + len(startTimeStr))
        startTimeEndIndex = self.html.find(endStr, startTimeStartIndex)
    	weekDayStartIndex = (self.html.find(weekDayStr, lectureIndex) + len(weekDayStr))
        weekDayEndIndex = self.html.find(endDayNum, weekDayStartIndex)
        weekNumStartIndex = (self.html.find(weekNumStr, lectureIndex) + len(weekNumStr))
        weekNumEndIndex = self.html.find(endWeekNum, weekNumStartIndex)
    	roomStartIndex = (self.html.find(roomStr, lectureIndex) + len(roomStr))
        roomEndIndex = self.html.find(endStr, roomStartIndex)
        #lectureIndex = (self.html.find(lectureStr, roomEndIndex) + len(lectureStr))

        # Stores specific information to the global variables for the class
        self.courseCode = self.html[courseCodeStartIndex:courseCodeEndIndex]
        self.startTime = self.html[startTimeStartIndex:startTimeEndIndex]
        self.weekDay = '"' + self.html[weekDayStartIndex:weekDayEndIndex] + '"'
        self.weekNum = self.html[weekNumStartIndex:weekNumEndIndex]
        self.room = self.html[roomStartIndex:roomEndIndex]
        
        # If room is not given        
        if(roomStartIndex < weekNumEndIndex):
            self.room = '""'        
        
    
        # For testing purposes

        print self.courseCode
        print self.startTime
        print self.weekDay
        print self.weekNum
        print self.room


    # Append information about lectures to CSV file
    def addLecturesToCSV(self):

        string = (self.courseCode + ',' + self.startTime + ',' + self.weekDay + ',' + self.weekNum + ',' + self.room + ',\n')

        csvFile = ManipulateCSVFile()
        csvFile.writeToCSV(csvFile.lecture, string)


    # Returns list of course codes in csv file
    def getCourseCodesInCSV(self):

        csvFile = ManipulateCSVFile()
        return csvFile.fetchFromCSV(csvFile.getCourseCodeFilePath(), 'courseCode')

    # Runs the command fixLextureWeeks in manipulateCSV
    def runFixLectureWeeks(self):

        csvFile = ManipulateCSVFile()
        csvFile.fixLectureWeeks()
