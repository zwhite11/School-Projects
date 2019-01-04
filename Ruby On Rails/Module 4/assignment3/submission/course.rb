# Zachary White
# Agile Development with Ruby on Rails
# Assignment 3

module Assignment3
	class Course
		def initialize (course_name, course_id)
			@course_name = course_name
			@course_id = course_id
		end
		def to_s
			"#{@course_id}: #{@course_name}"
		end

		def course_name
			@course_name
		end
		def course_name= (new_course_name)
			@course_name = new_course_name
		end

		def course_id
			@course_id
		end
		def course_id= (new_course_id)
			@course_id = new_course_id
		end
	end
end 