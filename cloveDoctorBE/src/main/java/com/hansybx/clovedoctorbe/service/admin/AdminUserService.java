package com.hansybx.clovedoctorbe.service.admin;

import com.github.pagehelper.PageHelper;
import com.hansybx.clovedoctorbe.DTO.UserDTO;
import com.hansybx.clovedoctorbe.common.CommonResponse;
import com.hansybx.clovedoctorbe.common.CommonResult;
import com.hansybx.clovedoctorbe.mapper.UserMapper;
import com.hansybx.clovedoctorbe.model.User;
import com.hansybx.clovedoctorbe.model.UserExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminUserService {
    @Resource
    UserMapper userMapper;

    public CommonResult getUserList(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        UserExample example = new UserExample();
        example.createCriteria();
        List<User> userList = userMapper.selectByExample(example);
        Long num = userMapper.countByExample(example);
        Map<String, Object> map = new HashMap<>();
        map.put("userList", userList);
        map.put("total", num);
        return CommonResponse.Success(map);
    }

    public CommonResult searchUser(String keyword, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        UserExample example = new UserExample();
        example.createCriteria().andUsernameLike("%" + keyword + "%");
        List<User> userList = userMapper.selectByExample(example);
        Long num = userMapper.countByExample(example);
        Map<String, Object> map = new HashMap<>();
        map.put("userList", userList);
        map.put("total", num);
        return CommonResponse.Success(map);
    }

    public CommonResult changeType(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setPassword(userDTO.getPassword());
        user.setType(userDTO.getType());
        user.setUsername(userDTO.getUsername());
        userMapper.updateByPrimaryKey(user);
        return CommonResponse.Success();
    }

    public CommonResult userDel(UserDTO userDTO) {

        int res = userMapper.deleteByPrimaryKey(userDTO.getId());
        if (res > 0) {
            return CommonResponse.Success();
        }
        return CommonResponse.Fail("删除失败");
    }
}
