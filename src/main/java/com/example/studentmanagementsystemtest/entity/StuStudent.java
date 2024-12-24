package com.example.studentmanagementsystemtest.entity;

import lombok.Data;

import java.util.Date;
@Data
public class StuStudent {
    private String 姓名;
    private String 学号;
    private String 性别;
    private String 名族;
    private String 学制;
    private String 学院;
    private String 专业;
    private String 班级;
    private String 证件类型;
    private String 证件号码;
    private String 出生日期;
    private String 入学日期;
    private String 政治面貌;
    private String 电话号码;
    private String 当前所在级;
    private String 预计毕业日;

    // Getters and Setters

    public String get姓名() {
        return 姓名;
    }

    public void set姓名(String 姓名) {
        this.姓名 = 姓名;
    }

    public String get学号() {
        return 学号;
    }

    public void set学号(String 学号) {
        this.学号 = 学号;
    }

    public String get性别() {
        return 性别;
    }

    public void set性别(String 性别) {
        this.性别 = 性别;
    }

    public String get名族() {
        return 名族;
    }

    public void set名族(String 名族) {
        this.名族 = 名族;
    }

    public String get学制() {
        return 学制;
    }

    public void set学制(String 学制) {
        this.学制 = 学制;
    }

    public String get学院() {
        return 学院;
    }

    public void set学院(String 学院) {
        this.学院 = 学院;
    }

    public String get专业() {
        return 专业;
    }

    public void set专业(String 专业) {
        this.专业 = 专业;
    }

    public String get班级() {
        return 班级;
    }

    public void set班级(String 班级) {
        this.班级 = 班级;
    }

    public String get证件类型() {
        return 证件类型;
    }

    public void set证件类型(String 证件类型) {
        this.证件类型 = 证件类型;
    }

    public String get证件号码() {
        return 证件号码;
    }

    public void set证件号码(String 证件号码) {
        this.证件号码 = 证件号码;
    }

    public String get出生日期() {
        return 出生日期;
    }

    public void set出生日期(String 出生日期) {
        this.出生日期 = 出生日期;
    }

    public String get入学日期() {
        return 入学日期;
    }

    public void set入学日期(String 入学日期) {
        this.入学日期 = 入学日期;
    }

    public String get政治面貌() {
        return 政治面貌;
    }

    public void set政治面貌(String 政治面貌) {
        this.政治面貌 = 政治面貌;
    }

    public String get电话号码() {
        return 电话号码;
    }

    public void set电话号码(String 电话号码) {
        this.电话号码 = 电话号码;
    }

    public String get当前所在级() {
        return 当前所在级;
    }

    public void set当前所在级(String 当前所在级) {
        this.当前所在级 = 当前所在级;
    }

    public String get预计毕业日() {
        return 预计毕业日;
    }

    public void set预计毕业日(String 预计毕业日) {
        this.预计毕业日 = 预计毕业日;
    }

    @Override
    public String toString() {
        return "Student{" +

                ", 姓名='" + 姓名 + '\'' +
                ", 学号='" + 学号 + '\'' +
                ", 性别='" + 性别 + '\'' +
                ", 名族='" + 名族 + '\'' +
                ", 学制='" + 学制 + '\'' +
                ", 学院='" + 学院 + '\'' +
                ", 专业='" + 专业 + '\'' +
                ", 班级='" + 班级 + '\'' +
                ", 证件类型='" + 证件类型 + '\'' +
                ", 证件号码='" + 证件号码 + '\'' +
                ", 出生日期='" + 出生日期 + '\'' +
                ", 入学日期='" + 入学日期 + '\'' +
                ", 政治面貌='" + 政治面貌 + '\'' +
                ", 电话号码='" + 电话号码 + '\'' +
                ", 当前所在级='" + 当前所在级 + '\'' +
                ", 预计毕业日='" + 预计毕业日 + '\'' +
                '}';
    }
}