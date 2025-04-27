package com.example.studentmanagementsystemtest;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.studentmanagementsystemtest.mapper")
@SpringBootApplication
public class StudentManagementSystemTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentManagementSystemTestApplication.class, args);
	}

}
