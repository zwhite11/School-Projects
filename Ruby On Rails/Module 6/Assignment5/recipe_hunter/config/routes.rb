Rails.application.routes.draw do
  get 'recipes_controller/index'


  root 'recipes_controller#index'
  # For details on the DSL available within this file, see http://guides.rubyonrails.org/routing.html
end
