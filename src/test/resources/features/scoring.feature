Feature: Check for correct calculation for scores

  Scenario: Succesful entry of 1 contestant Deca
    Given That i am on the correct page on "chrome"
    When  I fill in "Anna"
    And   I pick competition "Deca"
    And   I pick sport "LongJump"
    And   I put in my "300"
    Then  I submit and verify "66"

  Scenario Outline: checking the score calculations Positive tests Deca
    Given That i am on the correct page on "<Browser>"
    When  I fill in "<Name>"
    And   I pick competition "<Competition>"
    And   I pick sport "<Sport>"
    And   I put in my "<Result>"
    Then  I submit and verify "<AddedScore>"

    Examples:  #min edge version var inte korrekt för att kunna öppna med selenium
      | Browser | Competition | Name   | Sport       | Result | AddedScore |
      | Chrome  | Deca        | Andrea | 100m        | 12     | 651        |
      | Chrome  | Deca        | Andrea | 110mhurdles | 12     | 1249       |
      | Chrome  | Deca        | Andrea | 400m        | 25     | 2317       |
      | Chrome  | Deca        | Andrea | 1500m       | 5      | 3372       |
      | Chrome  | Deca        | Andrea | Discus      | 72     | 1338       |
      | Chrome  | Deca        | Andrea | Highjump    | 44     | 0          |
      | Chrome  | Deca        | Andrea | Javelin     | 99     | 1339       |
      | Chrome  | Deca        | Andrea | Longjump    | 456    | 301        |
      | Chrome  | Deca        | Andrea | Polevault   | 800    | 1938       |
      | Chrome  | Deca        | Andrea | Shotput     | 17     | 913        |
      | Firefox | Deca        | Adam   | 100m        | 16     | 89         |
      | Firefox | Deca        | Adam   | 110mhurdles | 25     | 63         |
      | Firefox | Deca        | Adam   | 400m        | 96     | 0          |
      | Firefox | Deca        | Adam   | 1500m       | 5      | 3372       |
      | Firefox | Deca        | Adam   | Discus      | 73     | 1360       |
      | Firefox | Deca        | Adam   | Highjump    | 60     | 0          |
      | Firefox | Deca        | Adam   | Javelin     | 105    | 1434       |
      | Firefox | Deca        | Adam   | Longjump    | 275    | 39         |
      | Firefox | Deca        | Adam   | Polevault   | 812    | 1983        |
      | Firefox | Deca        | Adam   | Shotput     | 22     | 1225       |

  Scenario Outline: checking the score calculations Positive tests Hepta
    Given That i am on the correct page on "<Browser>"
    When  I fill in "<Name>"
    And   I pick competition "<Competition>"
    And   I pick sport "<Sport>"
    And   I put in my "<Result>"
    Then  I submit and verify "<AddedScore>"

    Examples:  #min edge version var inte korrekt för att kunna öppna med selenium
      | Browser | Competition | Name | Sport       | Result | AddedScore |
      | Chrome  | Hepta       | Bert | 100mHurdles | 16     | 714        |
      | Chrome  | Hepta       | Bert | 200m        | 22     | 1181       |
      | Chrome  | Hepta       | Bert | 800m        | 75     | 1924       |
      | Chrome  | Hepta       | Bert | Highjump    | 82     | 25         |
      | Chrome  | Hepta       | Bert | Javelin     | 55     | 957        |
      | Chrome  | Hepta       | Bert | Longjump    | 300    | 107        |
      | Chrome  | Hepta       | Bert | Shotput     | 15     | 861        |
      | Firefox | Hepta       | Bea  | 100mHurdles | 5      | 2616       |
      | Firefox | Hepta       | Bea  | 200m        | 27     | 712        |
      | Firefox | Hepta       | Bea  | 800m        | 85     | 1727       |
      | Firefox | Hepta       | Bea  | Highjump    | 83     | 30         |
      | Firefox | Hepta       | Bea  | Javelin     | 2      | 0          |
      | Firefox | Hepta       | Bea  | Longjump    | 100    | 0          |
      | Firefox | Hepta       | Bea  | Shotput     | 23     | 1404       |



