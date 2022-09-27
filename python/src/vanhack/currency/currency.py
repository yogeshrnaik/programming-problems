"""
Overview
Create a class Currency with two methods, numWays(amount) and minChange(amount). A Currency instance will be initialized with an array of denominations of coins. The class will use its two methods to determine how many ways you can make change with the denominations and what the minimum count of each coin to reach the amount of change is.

This set will be used in the following examples:

us_cents = Currency([100, 50, 25, 10, 5, 1])
Currency.num_ways(amount)
takes one argument, an amount of money and will return the total possible number of ways that amount can be made with the given denominations of coins (above)

Parameters
amount: Integer - the amount of money we're looking to match

Return Value
Integer - the amount of ways you can make change with the denominations

Examples
amount	Return Value
6	2
10	4
There are 4 different combinations of coins to match the denomination of 10

Currency.min_change(amount)
takes one argument, an amount of money, and returns the fewest possbile number of coins required to make that amount.

Parameters
amount: Integer - the amount of money we're looking to match

Return Value
Integer - the minimum amount of denominations needed to match the amount

Examples
amount	Return Value
7	3
16	3
In order to make a denomination of 7, there is a required amount of 3 coins

"""


class Currency:
    def __init__(self, denominations):
        pass

    # number of ways to make change for amount
    def num_ways(self, amount):
        pass

    # minumum number of coins required to make change for amount
    def min_change(self, amount):
        pass
