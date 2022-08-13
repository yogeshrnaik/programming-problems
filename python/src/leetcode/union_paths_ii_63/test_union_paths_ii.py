import unittest

from leetcode.union_paths_ii_63.union_paths_ii import Solution


class TestUniquePathsWithObstacles(unittest.TestCase):

    def test_union_paths(self):
        self.assertEqual(2, Solution().uniquePathsWithObstacles([[0, 0, 0], [0, 1, 0], [0, 0, 0]]))
        self.assertEqual(1, Solution().uniquePathsWithObstacles([[0, 1], [0, 0]]))
