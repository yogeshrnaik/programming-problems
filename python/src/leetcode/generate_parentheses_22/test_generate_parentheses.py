import unittest

from leetcode.generate_parentheses_22.generate_parentheses import Solution


class TestGenerateParenthesis(unittest.TestCase):

    def test_generate_parentheses(self):
        self.assertListEqual(["((()))", "(()())", "(())()", "()(())", "()()()"], Solution().generateParenthesis(3))
        self.assertListEqual(["()"], Solution().generateParenthesis(1))
