import unittest

from leetcode.letter_case_permutation_784.letter_case_permutation import Solution


class TestLetterCasePermutation(unittest.TestCase):

    def test_letter_case_permutation(self):
        self.assertListEqual(["3z4", "3Z4"], Solution().letterCasePermutation("3z4"))
        self.assertListEqual(["a1b2", "a1B2", "A1b2", "A1B2"], Solution().letterCasePermutation("a1b2"))
        self.assertListEqual(["a", "A"], Solution().letterCasePermutation("a"))
        self.assertListEqual(["a1", "A1"], Solution().letterCasePermutation("a1"))
        self.assertListEqual(["a1b", "a1B", "A1b", "A1B"], Solution().letterCasePermutation("a1b"))
        self.assertListEqual(["aa", "aA", "Aa", "AA"], Solution().letterCasePermutation("aa"))
        self.assertListEqual(["A", "a"], Solution().letterCasePermutation("A"))
        self.assertCountEqual(["a", "A"], Solution().letterCasePermutation("a"))
