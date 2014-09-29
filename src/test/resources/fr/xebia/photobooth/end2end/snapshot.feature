Feature: snapshot

  Scenario: should offer a choice of command
    Given I go to homepage
    When I choose a portrait color command
    Then video url should be displayed

  Scenario: should takes a snapshot
    Given video url is displayed
    Given I fill video url
    Given I click on start Video
    And video is displayed
    When I click on "snapshot" button
    Then My picture should be displayed

#  Scenario: should takes real photo
#    Given my picture is displayed
#    When I click on "ok" button
#    Then I can send the link to my mother

#  Scenario: A user can take a full color portrait of himself using the photomaton

#    Given a user wanted a full color portrait of himself
#    When the user take the picture
#    Then the photomaton should print the full color portrait of the user