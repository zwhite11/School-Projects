class RecipesControllerController < ApplicationController
  def index

  	@ingredient = params[:ingredient] || 'chocolate'
  	@recipes = Recipe.for(@ingredient)

  end
end
