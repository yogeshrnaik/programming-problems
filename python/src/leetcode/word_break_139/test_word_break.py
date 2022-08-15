import unittest

from leetcode.word_break_139.word_break import Solution


class TestWordBreak(unittest.TestCase):

    def test_word_break(self):
        # self.assertEqual(True, Solution().wordBreak("leetcode", ["leet", "code"]))
        # self.assertEqual(True, Solution().wordBreak("applepenapple", ["apple", "pen"]))
        # self.assertEqual(False, Solution().wordBreak("catsandog", ["cats","dog","sand","and","cat"]))
        self.assertFalse(Solution().wordBreak("a", ["b"]))
