#Agile Development with Ruby on Rails: assignment 2
#Zachary White

words = IO.read("assignment_two_text.txt").split(/\s+/)

domain_pattern = /\w+@\w+.com/
words_with_domains = words.select { |word| word =~ domain_pattern }

just_domains = Array.new
words_with_domains.each{ |domain| just_domains.push(domain[/\w+.com/])}

unique_domains = just_domains.uniq
unique_domains.sort!

unique_domains.each do |domain| 
	domain_count = just_domains.count(domain).to_s
	result = domain << ": " << domain_count << " time(s)"
	puts result.rjust(40)
end