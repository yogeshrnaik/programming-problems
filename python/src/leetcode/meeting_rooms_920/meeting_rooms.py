from typing import (
    List,
)


class Interval(object):
    def __init__(self, start, end):
        self.start = start
        self.end = end

    def __str__(self):
        return f"{self.start},{self.end}"

# Problem: https://www.lintcode.com/problem/920/
class Solution:
    """
    @param intervals: an array of meeting time intervals
    @return: if a person could attend all meetings
    """

    def can_attend_meetings(self, intervals: List[Interval]) -> bool:
        intervals.sort(key=lambda interval: interval.start)

        for i in range(1, len(intervals)):
            interval1 = intervals[i-1]
            interval2 = intervals[i]
            if interval2.start < interval1.end:
                return False

        return True
