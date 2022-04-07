package com.hansybx.clovedoctorbe.controller.user;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import com.hansybx.clovedoctorbe.DTO.UserDTO;
import com.hansybx.clovedoctorbe.common.CommonResponse;
import com.hansybx.clovedoctorbe.common.CommonResult;
import com.hansybx.clovedoctorbe.service.user.UserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/user")
public class UserController {
    @Resource
    UserService userService;

    @Resource
    private RedisTemplate<String, String> stringRedisTemplate;

    private LineCaptcha lineCaptcha;

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/captcha")
    public void getCaptcha(HttpServletResponse response) {
        RandomGenerator randomGenerator = new RandomGenerator("0123456789", 4);
        lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "No-cache");
        lineCaptcha.setGenerator(randomGenerator);

        try {
            lineCaptcha.write(response.getOutputStream());
            // 关闭流
            response.getOutputStream().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/login")
    public CommonResult login(@RequestBody UserDTO userDTO) {
        if (userDTO.getCaptcha().equals(lineCaptcha.getCode())) {
            return userService.login(userDTO);
        }
//        {code: 200, message: "登录成功", data: {id: 3, username: "123", password: "123456", type: "USER"}}
        return CommonResponse.Fail("验证码不正确！");
    }

    @PostMapping("/register")
    public CommonResult register(@RequestBody UserDTO userDTO) {
        if (userDTO.getCaptcha().equals(lineCaptcha.getCode())) {
            return userService.register(userDTO);
        }
        return CommonResponse.Fail("验证码不正确！");
    }

}
