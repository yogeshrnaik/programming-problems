# Problem: https://leetcode.com/problems/validate-binary-search-tree/

# Definition for a binary tree node.
class TreeNode:
    def __init__(self, val=0, left=None, right=None):
        self.val = val
        self.left = left
        self.right = right


from typing import Optional


class Solution:
    def isValidBST(self, root: Optional[TreeNode]) -> bool:

        def valid(node, maxVal, minVal):
            if not node:
                return True

            if (maxVal is not None and node.val >= maxVal) or (minVal is not None and node.val <= minVal):
                return False

            return valid(node.left, node.val, minVal) and valid(node.right, maxVal, node.val)

        return valid(root, None, None)
