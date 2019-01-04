# Zachary White
# Agile Development with Ruby on Rails
# Assignment 4


require_relative "student"
require_relative "course"
require "yaml"

class DbApi

	@@students = YAML.load_file("university_db.yml")

	def self.method_missing (name, *args)
		compare = args[0]

		method_name = name.to_s

		attribute_name = method_name[22..-1]
		
		attribute = args[1]

		@@students.select{|student| student.send(attribute_name).method(compare).(attribute) }
	end
end


