require 'test_helper'

class RecipesControllerControllerTest < ActionDispatch::IntegrationTest
  test "should get index" do
    get recipes_controller_index_url
    assert_response :success
  end

end
