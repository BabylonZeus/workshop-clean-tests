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
