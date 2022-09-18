import unittest

from leetcode.coin_change_ii_518.coin_change_ii import Solution


class TestCoinChange(unittest.TestCase):

    def test_coin_change(self):
        self.assertEqual(1, Solution().change(10, [10]))
        self.assertEqual(0, Solution().change(3, [2]))
        self.assertEqual(4, Solution().change(5, [1, 2, 5]))
        self.assertEqual(2, Solution().change(3, [1, 2]))
