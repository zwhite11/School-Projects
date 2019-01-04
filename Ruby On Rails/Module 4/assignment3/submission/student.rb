# Zachary White
# Agile Development with Ruby on Rails
# Assignment 3

module Assignment3
	class Student
		attr_accessor :student_id, :first_name, :last_name, :city, :state, :email, :gender, :pounds, :gpa, :taking_courses
		def initialize
			yield self if block_given?
		end
		def to_s
			"Name: #{@first_name} #{last_name} (#{gender} from #{city}, #{state}, weight: #{pounds}, GPA: #{@gpa})  \n Courses: " + @taking_courses.join(', ')
		 end
	end
end
