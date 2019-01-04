require_relative 'student'
require_relative 'course'
require 'yaml'

students = YAML.load_file("university_db.yml")

puts students.select{|s| s.gender === "male"}.size
pattern = /andr/i
puts students.select{|s| s.pounds.to_f > "200".to_f}.size

		