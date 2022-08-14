import unittest

from leetcode.coin_change_322.coin_change import Solution


class TestCoinChange(unittest.TestCase):

    def test_coin_change(self):
        self.assertEqual(3, Solution().coinChange([1, 2, 5], 11))
        self.assertEqual(-1, Solution().coinChange([2], 3))
        self.assertEqual(0, Solution().coinChange([1], 0))
