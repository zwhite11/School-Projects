# Zachary White
# Agile Development with Ruby on Rails
# Assignment 4


require 'httparty'


class RecipePuppyClient
	include HTTParty
	base_uri 'http://www.recipepuppy.com/api/'
	default_params onlyImages: 1
	format :json

	def self.for(ingredient)
		get("", query: {q: ingredient})['results']
	end

	puts self.for("chocolate")

end
