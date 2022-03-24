package com.hansybx.clovedoctorbe;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hansybx.clovedoctorbe.mapper")
public class CloveDoctorBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloveDoctorBeApplication.class, args);
    }

}
