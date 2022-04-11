package com.hansybx.clovedoctorbe.controller.user;

import com.alipay.api.AlipayApiException;
import com.hansybx.clovedoctorbe.DTO.DrugTradeDTO;
import com.hansybx.clovedoctorbe.DTO.MsgDTO;
import com.hansybx.clovedoctorbe.common.CommonResult;
import com.hansybx.clovedoctorbe.service.user.MessageService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/user/msg")
public class MsgController {
    @Resource
    MessageService messageService;

    @PostMapping("/create")
    public CommonResult trade(@RequestBody MsgDTO msgDTO) {
        return messageService.MsgCreate(msgDTO);
    }

    @GetMapping("")
    public CommonResult getMsgList(@RequestParam String msgType, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return messageService.getMsgList(msgType, pageNum, pageSize);
    }

    @PostMapping("/change")
    public CommonResult changeStatus(@RequestBody MsgDTO msgDTO) {
        return messageService.changeMsgStatus(msgDTO);
    }

    @PostMapping("/del")
    public CommonResult msgDel(@RequestBody MsgDTO msgDTO) {
        return messageService.msgDel(msgDTO.getId());
    }
}
