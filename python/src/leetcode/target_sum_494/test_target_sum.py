import unittest

from leetcode.target_sum_494.target_sum import Solution


class TestTargetSum(unittest.TestCase):

    def test_target_sum(self):
        self.assertEqual(5, Solution().findTargetSumWays([1, 1, 1, 1, 1], 3))
        self.assertEqual(1, Solution().findTargetSumWays([1], 1))
