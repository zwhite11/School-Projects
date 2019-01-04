# Zachary White
# Agile Development with Ruby on Rails
# Assignment 4

require 'csv'
require_relative'course'
require_relative 'student'
require 'yaml'

# create course names
course_names = []
course_names.push("Introduction to Python")
course_names.push("Introduction to Programming Using Java")
course_names.push("Data Structures")
course_names.push("Discrete Mathematics")
course_names.push("Computer Organization")
course_names.push("Foundations of Software Engineering")
course_names.push("Foundations of Computer Architecture")
course_names.push("Foundations of Algorithms")
course_names.push("Agile Development with Ruby on Rails")
course_names.push("Principles of Enterprise Web Development")
course_names.push("Web Application Development with Java")
course_names.push("Enterprise Computing with Java")
course_names.push("Mobile Application Development for the Android Platform")

#courses
courses = []
0.upto(12) do |n|
	course = Course.new(course_names[n], (n+1))
	courses.push(course)
end

courses.each{ |x| puts x}
students_array = []

read_students = CSV.read("students.csv", headers:false)
all_students = read_students.to_a
all_students.shift
id_count = 1;
all_students.each do |row|
	student = Student.new do |s|
		s.student_id = id_count
		id_count += 1
		s.first_name = row[0]
		s.last_name = row[1]
		s.city = row[3]
		s.state = row[4]
		s.email = row[6]
		s.gender = row[7]
		s.pounds = row[8].to_f
		s.gpa = rand(2.0..4.0)
		number_of_courses = rand(0..4)
		s.taking_courses = courses.sample(number_of_courses)
	end
	students_array.push(student)
end

yaml_data = students_array.to_yaml
File.open("university_db.yml", "w") do |file|
   file.write(yaml_data)
end
