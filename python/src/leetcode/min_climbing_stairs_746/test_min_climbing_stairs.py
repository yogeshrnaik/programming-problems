import unittest

from leetcode.min_climbing_stairs_746.min_climbing_stairs import Solution


class TestMinClimbingStairs(unittest.TestCase):

    def test_min_climbing_stair(self):
        self.assertEqual(15, Solution().minCostClimbingStairs([10, 15, 20]))
        self.assertEqual(6, Solution().minCostClimbingStairs([1, 100, 1, 1, 1, 100, 1, 1, 100, 1]))
