require_relative "db_api"

males = DbApi.select_students_where_gender "==", "male"
first_name_an = DbApi.select_students_where_first_name "=~", /an/i
less_than_190 = DbApi.select_students_where_pounds "<", 190
from_TX = DbApi.select_students_where_state "==", "TX"

puts "Number of males: #{males.size}"
puts "Number of people whose first name contains 'an': #{first_name_an.size}"
puts "Number of people who weigh less than 190 pounds: #{less_than_190.size}"
puts "Number of people from Texas: #{from_TX.size}"

puts

puts (males & first_name_an & less_than_190 & from_TX)

# SAMPLE OUTPUT
#Number of males: 7533
#Number of people whose first name contains 'an': 2113
#Number of people who weigh less than 190 pounds: 9008
#Number of people from Texas: 1163
#
#Name: Nathan Norris (male from Waco, TX, weight: 161.0, GPA: 2.727110644952492)
#Name: Ryan Rochon (male from Dallas, TX, weight: 183.9, GPA: 3.0782607457883913)
#Courses: 605.401 - Intro to Java
#Name: Francis Bowers (male from Houston, TX, weight: 156.6, GPA: 2.035645803608335)
#Courses: 605.405 - Machine Learning, 605.403 - Intro to Rails, 605.404 - Advanced Rails, 605.401 - Intro to Java, 605.402 - Intro to Ruby
#Name: Anthony Wood (male from Houston, TX, weight: 164.8, GPA: 2.2084388486108306)
#Courses: 605.404 - Advanced Rails, 605.402 - Intro to Ruby, 605.405 - Machine Learning, 605.403 - Intro to Rails, 605.401 - Intro to Java
#Name: Sean Roberts (male from Waco, TX, weight: 161.3, GPA: 2.115900955006929)
#Courses: 605.402 - Intro to Ruby, 605.401 - Intro to Java, 605.405 - Machine Learning
#Name: Frank Heller (male from Dallas, TX, weight: 129.6, GPA: 3.492663717458235)
