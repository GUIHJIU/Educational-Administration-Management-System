package com.example.studentmanagementsystemtest.util;

import lombok.Data;

import java.time.LocalDateTime;

// 时间段定义（实现Comparable）
@Data
public
class TimeRange implements Comparable< TimeRange > {
    private LocalDateTime start;
    private LocalDateTime end;

    public TimeRange(LocalDateTime s, LocalDateTime e) {
        this.start = s;
        this.end = e;
    }

    @Override
    public int compareTo(TimeRange other) {
        return this.start.compareTo(other.start);
    }
}
