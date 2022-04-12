package com.hansybx.clovedoctorbe.service.user;

import com.hansybx.clovedoctorbe.DTO.UserDTO;
import com.hansybx.clovedoctorbe.common.CommonResponse;
import com.hansybx.clovedoctorbe.common.CommonResult;
import com.hansybx.clovedoctorbe.mapper.UserMapper;
import com.hansybx.clovedoctorbe.model.User;
import com.hansybx.clovedoctorbe.model.UserExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {
    @Resource
    UserMapper userMapper;

    public CommonResult register(UserDTO userDTO) {
        userDTO.setType("USER");
        int res = userMapper.insert(userDTO);
        if (res > 0) {
            UserExample userExample = new UserExample();
            userExample.createCriteria()
                    .andUsernameEqualTo(userDTO.getUsername())
                    .andPasswordEqualTo(userDTO.getPassword());
            List<User> userList= userMapper.selectByExample(userExample);
            if (userList.size() == 1) {
                return CommonResponse.Success("注册成功", userList.get(0));
            }
        }

        return CommonResponse.Fail("注册失败");
    }

    public CommonResult login(UserDTO userDTO) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andUsernameEqualTo(userDTO.getUsername())
                .andPasswordEqualTo(userDTO.getPassword());
        List<User> userList= userMapper.selectByExample(userExample);
        if (userList.size() == 1) {
            return CommonResponse.Success("登录成功", userList.get(0));
        }
        return CommonResponse.Fail("登录失败");
    }
}
