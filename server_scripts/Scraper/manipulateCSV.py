# encoding: utf-8
import csv    
from datetime import date
import datetime
from collections import defaultdict

class ManipulateCSVFile:


    # Add filepaths!
    def __init__(self):

        self.lecture = '/home/aleksanderhh/Downloads/Database/Lectures.csv' 
        self.lectureFixed = '/home/aleksanderhh/Downloads/Database/LecturesFixed.csv'
        self.lectureFixedDate = '/home/aleksanderhh/Downloads/Database/LecturesFixedDate.csv'
        self.tdtCourses = '/home/aleksanderhh/Downloads/Database/TDT_Courses.csv'
        self.courseCoordinator = '/home/aleksanderhh/Downloads/Database/CourseCoordinator.csv'
        self.lecturer = '/home/aleksanderhh/Downloads/Database/Lecturer.csv'
        self.semester = '/home/aleksanderhh/Downloads/Database/Semester.csv'
        self.temp = '/home/aleksanderhh/Downloads/Database/temp.csv'


    # Commandline for choosing file to manipulate
    def chooseFile(self, choice):

        string = ("What file do you wan't to manipulate? \n0: Lecture \n1: TDT Courses\n2: Course coordinator \n3: Lecturer \n4: Semester \nChoice: ")
    
        if(choice == 0):
            choice = input(string)

        if(choice == 0):
            return self.lecture
        elif(choice == 1):
            return self.tdtCourses
        elif(choice == 2):
            return self.courseCoordinator
        elif(choice == 3):
            return self.lecturer
        elif(choice == 4):
            return self.semester
        elif(choice == 5):
            return self.lectureFixed
        else:
            print "You didn't choose any of the options"


    # Returns TDT courses csv filepath
    def getCourseCodeFilePath(self):

        return self.tdtCourses


    # Prints out content in csv file
    def readCSV(self, file):

        csvFile = open(file,'r') 

        for row in csvFile:
            print ', '.join(row)

        csvFile.close()


    # Fetch and saves a specific column in csv file to a list
    def fetchFromCSV(self, file, columnName):

        columns = defaultdict(list)
        with open(file) as f:
            reader = csv.DictReader(f)
            for row in reader: 
                for (k,v) in row.items():
                    columns[k].append(v)
                        
        return columns[columnName]


    # Writes to csv file
    def writeToCSV(self, file, string):

    	csvFile = open(file,'a') 
        csvFile.write(string)
        csvFile.close()


    # Count lines in csv file
    def countLinesInCSV(self, file):

        numCourses = sum(1 for line in open(file))
        return numCourses     


    # Deletes information stored in the csv-file
    def cleanCSVFile(self, file):

    	csvFile = open(file,'w') 
        csvFile.close()

    # Makes the csv file correct for implementation to sql database
    def fixLectureWeeks(self):

        fileRead = self.lecture # HUSK Å ENDRE TIL LECTURE
        fileWrite = self.lectureFixed        
    
        with open(fileRead) as f:
            for line in f:
                row = line.split(',')
                courseCode = row[0]
                startTime = row[1]
                weekDay = row[2]
                room = row[len(row)-2]

        
                weekNum1 = row[3].replace('"', "")
                if(weekNum1.find('-') != -1):
                    weekNum1S = weekNum1.split('-')
                    for week1 in range(int(weekNum1S[0]), int(weekNum1S[1])+1):
                        self.writeToCSV(fileWrite, (courseCode + ',' + startTime + ',' + weekDay + ',"' + str(week1) + '",' + room + ',\n'))
                
                else:
                    self.writeToCSV(fileWrite, (courseCode + ',' + startTime + ',' + weekDay + ',"' + weekNum1 + '",' + room + ',\n'))             

                if(row[4] != room):
                    weekNum2 = row[4].replace('"', "")
                    if(weekNum2.find('-') != -1):
                        weekNum2S = weekNum2.split('-')
                        for week2 in range(int(weekNum2S[0]), int(weekNum2S[1])+1):
                            self.writeToCSV(fileWrite, (courseCode + ',' + startTime + ',' + weekDay + ',"' + str(week2) + '",' + room + ',\n'))

                    else:
                        self.writeToCSV(fileWrite, (courseCode + "," + startTime + "," + weekDay + ',"' + weekNum2 + '",' + room + ',\n'))
 
                if(row[4] != room and row[5] != room):
                    weekNum3 = row[5].replace('"', "")
                    if(weekNum3.find('-') != -1):
                        weekNum3S = weekNum3.split('-')
                        for week3 in range(int(weekNum3S[0]), int(weekNum3S[1])+1):
                            self.writeToCSV(fileWrite, (courseCode + ',' + startTime + ',' + weekDay + ',"' + str(week3) + '",' + room + ',\n'))
 
                    else:
                        self.writeToCSV(self.lectureFixed, (courseCode + ',' + startTime + ',' + weekDay + ',"' + weekNum3 + '",' + room + ',\n')) 

                if(row[4] != room and row[5] != room and row[6] != room):
                    weekNum4 = row[6].replace('"', "")
                    if(weekNum4.find('-') != -1):
                        weekNum4S = weekNum4.split('-')
                        for week4 in range(int(weekNum4S[0]), int(weekNum4S[1])+1):
                            self.writeToCSV(fileWrite, (courseCode + ',' + startTime + ',' + weekDay + ',"' + str(week4) + '",' + room + ',\n'))
 
                    else:
                        self.writeToCSV(self.lectureFixed, (courseCode + ',' + startTime + ',' + weekDay + ',"' + weekNum4 + '",' + room + ',\n')) 

                if(row[4] != room and row[5] != room and row[6] != room and row[7] != room):
                    weekNum5 = row[7].replace('"', "")
                    if(weekNum5.find('-') != -1):
                        weekNum5S = weekNum5.split('-')
                        for week5 in range(int(weekNum5S[0]), int(weekNum5S[1])+1):
                            self.writeToCSV(fileWrite, (courseCode + ',' + startTime + ',' + weekDay + ',"' + str(week5) + '",' + room + ',\n'))
 
                    else:
                        self.writeToCSV(self.lectureFixed, (courseCode + ',' + startTime + ',' + weekDay + ',"' + weekNum5 + '",' + room + ',\n'))



    # Returns date from weekday, weeknumber and year
    def getDate(self, weekDay, weekNum):
    
        year = date.today().year    
        return (datetime.date(year,1,1) + datetime.timedelta(weekNum * 7 - weekDay))


    # Convert weekday and week to date
    def lectureToDateFormat(self):
        
        with open(self.lectureFixed) as f:
            for line in f:
                row = line.split(',')
                courseCode = row[0]
                startTime = row[1]
                weekDay = int(row[2].replace('"', ""))
                weekNum = int(row[3].replace('"', ""))
                room = row[4]

                self.writeToCSV(self.lectureFixedDate, (courseCode + ',' + startTime + ',"' + str(self.getDate(weekDay, weekNum)) + '",' + room + '\n'))

