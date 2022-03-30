package com.hansybx.clovedoctorbe.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class CommonController {

    private LineCaptcha lineCaptcha;

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

//    @GetMapping("/captcha")
//    public void getCaptcha(HttpServletResponse response) {
//        RandomGenerator randomGenerator = new RandomGenerator("0123456789", 4);
//        lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
//        response.setContentType("image/jpeg");
//        response.setHeader("Pragma", "No-cache");
//        lineCaptcha.setGenerator(randomGenerator);
////        stringRedisTemplate.opsForValue().set("asd", lineCaptcha.getCode(),1L, TimeUnit.MINUTES);
//
//        try {
//            lineCaptcha.write(response.getOutputStream());
//            // 关闭流
//            response.getOutputStream().close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @PostMapping("/test")
    public String Test(HttpServletRequest request){
        System.out.println(lineCaptcha.getCode());
        System.out.println(request.getParameter("captcha"));
        return request.getParameter("captcha");
    }

}
