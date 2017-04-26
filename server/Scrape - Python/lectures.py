# This is an example on how we can scrape information about lectures from NTNU's site.

# courseCode in uppercases, etc TDT4140, TDT4145, TTM4100
def getCourseInformation(courseCode, year, version):
    url = "https://www.ntnu.no/web/studier/emner?p_p_id=coursedetailsportlet_WAR_courselistportlet&p_p_lifecycle=2&p_p_resource_id=timetable&_coursedetailsportlet_WAR_courselistportlet_year=%d&_coursedetailsportlet_WAR_courselistportlet_courseCode=%s&year=%d&version=%d" %(year, courseCode, year, version)
    return url

print (getCourseInformation("TDT4140", 2017, 1)) # PU som eksempel


'''

Gir oss:

{"course":{"summarized":[{"acronym":"ØV","description":"Øving","arsterminId":"2017_VÅR","courseCode":"TDT4140","courseName":"Programvareutvikling","studyProgramKeys":["BIT","MLREAL","MTDT","MTING","MTIØT","MTTK"],"from":"08:15","to":"10:00","dayNum":1,"weeks":["2-14","17"],"rooms":[{"etasje":0,"kapasitetUnd":0,"romNavn":"R1","syllabusKey":"360CU1-101","syllabusromkode":"360CU1-101"}]},{"acronym":"FOR","description":"Forelesning","arsterminId":"2017_VÅR","courseCode":"TDT4140","courseName":"Programvareutvikling","studyProgramKeys":["BIT","MLREAL","MTDT","MTING","MTIØT","MTTK"],"from":"16:15","to":"18:00","dayNum":2,"weeks":["2-14","17"],"rooms":[{"etasje":0,"kapasitetUnd":0,"romNavn":"R1","syllabusKey":"360CU1-101","syllabusromkode":"360CU1-101"}]},{"acronym":"FOR","description":"Forelesning","arsterminId":"2017_VÅR","courseCode":"TDT4140","courseName":"Programvareutvikling","studyProgramKeys":["BIT","MLREAL","MTDT","MTING","MTIØT","MTTK"],"from":"14:15","to":"16:00","dayNum":5,"weeks":["2-14","16"],"rooms":[{"etasje":0,"kapasitetUnd":0,"romNavn":"R1","syllabusKey":"360CU1-101","syllabusromkode":"360CU1-101"}]}]}}


'''
