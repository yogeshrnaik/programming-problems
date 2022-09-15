import unittest

from leetcode.subsets_78.subsets import Solution


class TestSubsets(unittest.TestCase):

    def test_subsets(self):
        self.assertCountEqual([[], [1], [2], [1, 2], [3], [1, 3], [2, 3], [1, 2, 3]], Solution().subsets([1, 2, 3]))
        self.assertCountEqual([[], [0]], Solution().subsets([0]))
