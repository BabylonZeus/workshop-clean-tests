Feature: US1 - Buy a picture

  As a photomaton customer, I want to buy a full color portrait, In order to offer it to my mother

  Acceptance criteria :

    * Price : 1 euros

  Scenario: take a free picture

    Given I'm in front of the photomaton camera
    When I take the picture
    And decide to print it
    Then the photomaton print the processed picture

  Scenario: buy a picture with the exact change

    Given I'm in front of the photomaton camera
    And I have given the exact change
    When I take the picture
    And decide to print it
    Then the photomaton print the processed picture

  Scenario: buy a picture with not enough change

    Given I'm in front of the photomaton camera
    And I hav'nt give enough change
    When I take the picture
    And decide to print it
    Then the photomaton print the processed picture