Feature: Check for correct calculation for scores

  Scenario Outline: checking the score calculations Positive tests
    Given That i am on the correct page on "<Browser>"
    When  I fill in "<Name>"
    #And  I pick which "<Category>"  hepa vs deca kom inte på bättre namn
    And   I pick "<Sport>"
    And   I put in my "<Score>"
    Then  I submit and verify

    Examples:


