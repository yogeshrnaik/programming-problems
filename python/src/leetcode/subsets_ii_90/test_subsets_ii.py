import unittest

from leetcode.subsets_ii_90.subsets_ii import Solution


class TestSubsetsII(unittest.TestCase):

    def test_subsets_ii(self):
        self.assertCountEqual([[], [1], [1, 2], [1, 2, 2], [2], [2, 2]], Solution().subsetsWithDup([1, 2, 2]))
        self.assertCountEqual([[], [0]], Solution().subsetsWithDup([0]))
