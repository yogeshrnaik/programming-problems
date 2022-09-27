import random
import unittest

from vanhack.currency.currency import Currency


class Test(unittest.TestCase):
    def test_american_coins(self):
        us_cent = Currency([100, 50, 25, 10, 5, 1])
        self.assertEqual(293, us_cent.num_ways(100))
        self.assertEqual(9, us_cent.min_change(194))
