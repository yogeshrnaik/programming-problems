from typing import (
    List,
)


class Interval(object):
    def __init__(self, start, end):
        self.start = start
        self.end = end

    def __str__(self):
        return f"{self.start},{self.end}"


# Problem: https://www.lintcode.com/problem/919/


class Solution:
    """
    @param intervals: an array of meeting time intervals
    @return: the minimum number of conference rooms required
    """

    def min_meeting_rooms(self, intervals: List[Interval]) -> int:
        startTimes = sorted([i.start for i in intervals])
        endTimes = sorted([i.end for i in intervals])

        result, count = 0, 0
        s, e = 0, 0
        while s < len(startTimes):
            if startTimes[s] < endTimes[e]:
                s += 1
                count += 1
            else:
                e += 1
                count -= 1
            result = max(result, count)

        return result

        return result
