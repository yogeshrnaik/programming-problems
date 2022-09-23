import unittest

from leetcode.min_remove_to_make_valid_parentheses_1249.min_remove_to_make_valid_parentheses import Solution


class TestMinRemoveToMakeValidParentheses(unittest.TestCase):

    def test_min_remove_to_make_valid_parentheses(self):
        self.assertEqual("lee(t(c)o)de", Solution().minRemoveToMakeValid("lee(t(c)o)de)"))
        self.assertEqual("ab(c)d", Solution().minRemoveToMakeValid("a)b(c)d"))
        self.assertEqual("", Solution().minRemoveToMakeValid("))(("))
