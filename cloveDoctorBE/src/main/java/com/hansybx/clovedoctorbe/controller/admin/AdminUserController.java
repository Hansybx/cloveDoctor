package com.hansybx.clovedoctorbe.controller.admin;

import com.hansybx.clovedoctorbe.DTO.UserDTO;
import com.hansybx.clovedoctorbe.common.CommonResult;
import com.hansybx.clovedoctorbe.service.admin.AdminUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/admin/user")
public class AdminUserController {
    @Resource
    AdminUserService adminUserService;

    @GetMapping("")
    public CommonResult getTradeList(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return adminUserService.getUserList(pageNum, pageSize);
    }

    @GetMapping("/search")
    public CommonResult searchTradeList(@RequestParam String keywords, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return adminUserService.searchUser(keywords, pageNum, pageSize);
    }

    @PostMapping("/change/type")
    public CommonResult changeType(@RequestBody UserDTO userDTO) {
        return adminUserService.changeType(userDTO);
    }

    @PostMapping("/del")
    public CommonResult userDel(@RequestBody UserDTO userDTO) {
        return adminUserService.userDel(userDTO);
    }
}
