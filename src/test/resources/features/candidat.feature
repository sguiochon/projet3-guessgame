Feature: CRUD operations on Customer

  Background:
    Given the repository exists

  Scenario: Creating a new Customer
    When a Customer is stored in DB
    Then it is successfully stored


  Scenario Outline: Creating a Customer with one specified login
      When a customer having login=<login> is created
      Then a record is found in DB having login=<login>
      Examples:
        |login             |
        | inferno@HELL.com |
        | SAM@inferno.hell |
        | test@sfr.fr      |
