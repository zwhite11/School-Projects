# Zachary White
# Agile Development with Ruby on Rails
# Assignment 3

require_relative 'student'
require_relative 'course'
require 'yaml'

class DbApi

	@@students = YAML.load_file("university_db.yml")

	def self.students
		@@students
	end

	def self.select_by_gender(gender)
		self.students.select{|student| student.gender === gender}
	end

	def self.select_by_first_name(first_name)
		self.students.select{|student| student.first_name =~ first_name}	
	end

	def self.select_by_last_name(last_name)
		self.students.select{|student| student.last_name =~ last_name}	
	end

	def self.select_by_weight_more_than(pounds)
		self.students.select{|student| student.pounds > pounds.to_f}
	end

end
