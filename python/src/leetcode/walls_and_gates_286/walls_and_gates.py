# Problem: https://leetcode.com/problems/walls-and-gates/
# Facebook
# Solution:
# Neetcode: https://www.youtube.com/watch?v=e69C6xhiSQE
# Kevin Naughton Jr: https://www.youtube.com/watch?v=Pj9378ZsCh4
from collections import deque
from typing import List


class Solution:
    GATE = 0
    WALL = -1

    def wallsAndGets(self, rooms: List[List[int]]) -> List[List[int]]:
        return self.bfsSolution(rooms)

    def bfsSolution(self, rooms: List[List[int]]) -> List[List[int]]:
        rows = len(rooms)
        cols = len(rooms[0])
        visited = set()
        queue = deque()

        def addRoom(r, c):
            if r < 0 or c < 0 or r >= rows or c >= cols or rooms[r][c] == self.WALL or (r, c) in visited:
                return
            visited.add((r, c))
            queue.append((r, c))

        for i in range(0, rows):
            for j in range(0, cols):
                if rooms[i][j] == self.GATE:
                    visited.add((i, j))
                    queue.append((i, j))

        distance = 0
        while queue:
            for i in range(len(queue)):
                r, c = queue.popleft()
                rooms[r][c] = distance
                addRoom(r - 1, c)
                addRoom(r + 1, c)
                addRoom(r, c - 1)
                addRoom(r, c + 1)

            distance += 1

        return rooms

    def dfsSolution(self, rooms: List[List[int]]) -> List[List[int]]:
        rows = len(rooms)
        cols = len(rooms[0])

        def dfs(r, c, depth):
            if r < 0 or c < 0 or r >= rows or c >= cols or rooms[r][c] == self.WALL or rooms[r][c] < depth:
                return

            rooms[r][c] = depth
            dfs(r + 1, c, depth + 1)
            dfs(r - 1, c, depth + 1)
            dfs(r, c - 1, depth + 1)
            dfs(r, c + 1, depth + 1)

        for i in range(0, rows):
            for j in range(0, cols):
                if rooms[i][j] == self.GATE:
                    dfs(i, j, 0)

        return rooms
