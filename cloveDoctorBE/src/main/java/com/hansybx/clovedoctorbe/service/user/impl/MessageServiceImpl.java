package com.hansybx.clovedoctorbe.service.user.impl;

import com.github.pagehelper.PageHelper;
import com.hansybx.clovedoctorbe.DTO.MsgDTO;
import com.hansybx.clovedoctorbe.common.CommonResponse;
import com.hansybx.clovedoctorbe.common.CommonResult;
import com.hansybx.clovedoctorbe.mapper.MessageMapper;
import com.hansybx.clovedoctorbe.model.Message;
import com.hansybx.clovedoctorbe.model.MessageExample;
import com.hansybx.clovedoctorbe.service.user.MessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    MessageMapper msgMapper;

    @Override
    public CommonResult MsgCreate(MsgDTO msgDTO) {
        msgMapper.insert(msgDTO);
        return CommonResponse.Success();
    }

    @Override
    public CommonResult getMsgList(String msgType, Integer pageNum, Integer pageSize) {
        String orderBy = "id desc";
        PageHelper.startPage(pageNum, pageSize, orderBy);
        MessageExample example = new MessageExample();

        if (msgType.length() > 0) example.createCriteria().andMsgStatusEqualTo(msgType);

        List<Message> list = msgMapper.selectByExample(example);
        Long num = msgMapper.countByExample(example);
        HashMap<String, Object> map = new HashMap<>();
        map.put("totalNum", num);
        map.put("msgList", list);
        return CommonResponse.Success(map);
    }

    @Override
    public CommonResult changeMsgStatus(MsgDTO msgDTO) {
        if(msgDTO.getMsgStatus().equals("0")) msgDTO.setMsgStatus("WILL_DO");
        msgMapper.updateByPrimaryKey(msgDTO);
        return CommonResponse.Success();
    }

    @Override
    public CommonResult msgDel(Integer id) {
        msgMapper.deleteByPrimaryKey(id);
        return CommonResponse.Success();
    }
}
