import unittest

from leetcode.letter_combinations_of_phone_num.letter_combinations_of_phone_num import Solution


class TestLetterCombinationsOfPhoneNumber(unittest.TestCase):

    def test_letter_combinations_of_phone_num(self):
        self.assertListEqual(["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"],
                             Solution().letterCombinations(digits="23"))
        self.assertListEqual([], Solution().letterCombinations(digits=""))
        self.assertListEqual(["a", "b", "c"], Solution().letterCombinations(digits="2"))
