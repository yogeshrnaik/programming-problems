import unittest

from leetcode.climbing_stairs_70.climbing_stairs import Solution


class TestClimbingStairs(unittest.TestCase):

    def test_climbing_stair(self):
        self.assertEqual(2, Solution().climbStairs(2))
        self.assertEqual(3, Solution().climbStairs(3))
