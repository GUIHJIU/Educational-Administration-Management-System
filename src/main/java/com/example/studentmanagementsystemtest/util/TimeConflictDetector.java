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
        // 先添加所有已有时间段
        timeline.addAll(existingRanges);

        // 检查左侧相邻区间（floor）
        TimeRange floor = timeline.floor(newRange);
        if (floor != null && floor.getEnd().isAfter(newRange.getStart())) {
            return true; // 左侧区间结束时间 > 新开始时间 → 冲突
        }

        // 检查右侧相邻区间（ceiling）
        TimeRange ceiling = timeline.ceiling(newRange);
        if (ceiling != null && newRange.getEnd().isAfter(ceiling.getStart())) {
            return true; // 新结束时间 > 右侧区间开始时间 → 冲突
        }

        return false;
    }
}

