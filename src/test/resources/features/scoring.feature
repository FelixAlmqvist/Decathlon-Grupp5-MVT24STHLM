Feature: Check for correct calculation for scores

  Scenario:
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

    Examples:
      | Browser | Name | Sport | Result | TotalScore |
      |         | anna | 100m  | 300    | 66         |




