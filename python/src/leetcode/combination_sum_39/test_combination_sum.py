import unittest

from leetcode.combination_sum_39.combination_sum import Solution


class TestCombinationSum(unittest.TestCase):

    def test_combination_sum(self):
        self.assertListEqual([[2, 2, 3], [7]], Solution().combinationSum([2, 3, 6, 7], 7))
        self.assertListEqual([[2, 2, 2, 2], [2, 3, 3], [3, 5]], Solution().combinationSum([2, 3, 5], 8))
