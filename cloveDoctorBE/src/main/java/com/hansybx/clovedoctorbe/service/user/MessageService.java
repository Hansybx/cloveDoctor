package com.hansybx.clovedoctorbe.service.user;

import com.hansybx.clovedoctorbe.DTO.MsgDTO;
import com.hansybx.clovedoctorbe.common.CommonResult;

public interface MessageService {
    CommonResult MsgCreate(MsgDTO msgDTO);
    CommonResult getMsgList(String msgType,Integer pageNum, Integer pageSize);
    CommonResult changeMsgStatus(MsgDTO msgDTO);
    CommonResult msgDel(Integer id);
}
