Feature: Check for correct calculation for scores

  Scenario: Succesful entry of 1 contestant
    Given That i am on the correct page on "chrome"
    When  I fill in "Anna"
    And   I pick "LongJump"
    And   I put in my "300"
    Then  I submit and verify "66"

  Scenario Outline: checking the score calculations Positive tests
    Given That i am on the correct page on "<Browser>"
    When  I fill in "<Name>"
    #And  I pick which "<Category>"  hepa vs deca kom inte på bättre namn
    And   I pick "<Sport>"
    And   I put in my "<Result>"
    Then  I submit and verify "<TotalScore>"

    Examples:  #min edge version var inte korrekt för att kunna öppna med selenium
      | Browser | Name   | Sport    | Result | TotalScore |
      | Chrome  | Andrea | 100m     | 12     | 651        |
      | Chrome  | Andrea | 400m     | 25     | 2968       |
      | Chrome  | Andrea | longjump | 456    | 3269       |
      | Chrome  | Andrea | shotput  | 17     | 4182       |
      | Firefox | Adam   | 100m     | 16     | 89         |
      | Firefox | Adam   | 400m     | 96     | 89         |
      | Firefox | Adam   | longjump | 275    | 128        |
      | Firefox | Adam   | shotput  | 22     | 1353       |




