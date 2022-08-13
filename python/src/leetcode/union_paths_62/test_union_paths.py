import unittest
from union_paths.union_paths import Solution


class TestUnionPaths(unittest.TestCase):
    def test_union_paths(self):
        self.assertEqual(3, Solution().uniquePaths(3, 2))
        self.assertEqual(28, Solution().uniquePaths(3, 7))
