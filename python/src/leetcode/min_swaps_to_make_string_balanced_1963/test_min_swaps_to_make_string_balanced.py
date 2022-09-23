import unittest

from leetcode.min_swaps_to_make_string_balanced_1963.min_swaps_to_make_string_balanced import Solution


class TestMinSwapsToMakeStringBalanced(unittest.TestCase):

    def test_min_swaps_to_make_string_balanced(self):
        self.assertEqual(1, Solution().minSwaps("][]["))
        self.assertEqual(2, Solution().minSwaps("]]][[["))
        self.assertEqual(0, Solution().minSwaps("[]"))
