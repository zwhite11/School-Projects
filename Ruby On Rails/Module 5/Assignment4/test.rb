
require_relative 'RecipePuppyClient'

puts RecipePuppyClient.for('chocolate') ["results"]
puts RecipePuppyClient.for('apple pie') ["results"]

# \"title\":\"Recipe Puppy\",\"version\":0.1,\"href\":\"http:\\/\\/www.recipepuppy.com\\/\",\"results\"