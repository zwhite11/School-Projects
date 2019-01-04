# read line, if email found, add to count
line_arr = []
words = IO.read("assignment_two_text").split(/\s+/)

domain_pattern = /\w+@\w+.com/
just_domains = words.select { |e| e =~ domain_pattern }
unique_domains = just_domains.uniq 

unique_domains.each{ |domain| p just_domains.count(domain) }