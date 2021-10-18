Feature: Beer Challenge Test
  Tests prepared as interview task

  Scenario: Beer ABV value validation
    Given The request specification to PUNK API
    When I get all beers produced after '12-2015'
    Then ABV values for all beers should not be a null
    And ABV values should not be an empty string
    And ABV values should be a double
    And ABV values should be higher than 4.0

  Scenario: Beer name validation
    Given The request specification to PUNK API
    When I get all beers produced after '12-2015'
    Then Beer names should not be a null
    And Beer names should not be an empty string

  Scenario: Beer image url validation
    Given The request specification to PUNK API
    When I get all beers produced after '12-2015'
    Then Image links should point to image file on PUNK API server

  Scenario: Ingredients validation
    Given The request specification to PUNK API
    When I get all beers produced after '12-2015'
    Then There should be at least one malt in the ingredients list
    And There should be at least one hops in the ingredients list
    And Yeast should not be null or empty