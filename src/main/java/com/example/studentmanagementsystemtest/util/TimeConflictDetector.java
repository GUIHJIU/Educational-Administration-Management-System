package com.example.studentmanagementsystemtest.util;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public class TimeConflictDetector {

    /**
     * 使用线段树检测时间冲突（时间复杂度 O(log n)）
     */
    public static boolean checkConflict(List< TimeRange > existingRanges, TimeRange newRange) {
        TreeSet< TimeRange > timeline = new TreeSet<>(Comparator.comparing(TimeRange::getStart));
        for (TimeRange range : existingRanges) {
            // 查找最近的结束时间不大于新开始时间的区间
            TimeRange floor = timeline.floor(new TimeRange(newRange.getStart(), newRange.getStart()));
            if (floor != null && floor.getEnd().isAfter(newRange.getStart())) {
                return true;
            }
            // 查找最近的开始时间不小于新开始时间的区间
            TimeRange ceiling = timeline.ceiling(newRange);
            if (ceiling != null && newRange.getEnd().isAfter(ceiling.getStart())) {
                return true;
            }
            timeline.add(range);
        }
        return false;
    }
}

