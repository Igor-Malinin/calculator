Feature: calculations

  Background: adding data into the database
    Given calculations table
      | firstNum | firstNumSystem | secondNum | secondNumSystem | operation | result | dateTime             |
      | 10       | 10             | 20        | 10              | +         | 30     | 11.11.2023, 14:13:22 |
      | 101      | 2              | 11        | 2               | -         | 10     | 12.11.2023, 16:13:22 |
      | 15       | 8              | 56        | 8               | *         | 1126   | 12.11.2023, 17:13:22 |
      | 3242FD   | 16             | 435C      | 16              | /         | BF     | 12.11.2023, 18:13:22 |

  Scenario: get all calculations according to given data
    When I perform get request "http://localhost:8191/api/calculations"
    Then I should receive response status code 200
    And Body should contain calculations that are given into the table

  Scenario Outline: get the result of calculation calculation
    When I perform get request "http://localhost:8191/api/result/" with parameters <firstNum> and <secondNum> and <operation> and <system>
    Then I see the result as <result>
    Examples:
      | firstNum | secondNum | operation | system | result |
      | 10       | 20        | +         | 10     | 30     |

  Scenario: post new calculation in database
    When I perform post request "http://localhost:8191/api/calculation" and I pass below body
      | firstNum | firstNumSystem | secondNum | secondNumSystem | operation | result | dateTime             |
      | 234F     | 16             | 45AD      | 16              | *         | 99c2763| 12.11.2023, 18:42:22 |
    Then I should receive status code 200
    And I should receive body that given into the table

  @LastTest
  Scenario: post new calculation in database with specific date
    When I perform post request "http://localhost:8191/api/calculation" and I pass this date 13.11.2023, 13:14:17 and below body
      | firstNum | firstNumSystem | secondNum | secondNumSystem | operation | result | dateTime             |
      | 48       | 10             | 6         | 10              | /         | 8      | null                 |
    Then I receive status code 200
    And I receive body that given into the table and with given date





#    When I perform <operation> on two integers <firstNum> in <firstNumSystem> and <secondNum> in <secondNumSystem> and recording <dateTime>



#  Scenario: get calculations
#    When I make a GET request "/api/calculations"
#    Then I should receive response status code 200
#    And should receive a non-empty body
#    Then I see the result as "{"id": "4f410818-8c71-466d-b8b3-145ae4d567e0","firstNum": "24","firstNumSystem": "10","secondNum": "45","secondNumSystem": "10","operation": "+","result": "69","dateTime": "18.10.2023, 15:56:31"}"


#  Scenario Outline: add calculation
#    When I perform <operation> on two integers <firstNum> in <firstNumSystem> and <secondNum> in <secondNumSystem> and recording <dateTime>
#    Then I see the result as <result>
#
#    Examples:
#      | firstNum | firstNumSystem | secondNum | secondNumSystem| operation | result | dateTime               |
#      | "10"     | 10             | "20"      | 10             | "+"       | "30"   | "11.11.2023, 14:13:22" |

