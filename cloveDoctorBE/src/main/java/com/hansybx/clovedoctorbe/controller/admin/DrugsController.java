package com.hansybx.clovedoctorbe.controller.admin;

import com.hansybx.clovedoctorbe.DTO.DrugsDTO;
import com.hansybx.clovedoctorbe.common.CommonResult;
import com.hansybx.clovedoctorbe.model.Drugs;
import com.hansybx.clovedoctorbe.service.admin.DrugsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/admin")
public class DrugsController {

    // 药品管理

    @Resource
    DrugsService drugsService;

    @PostMapping("/drugs")
    public CommonResult addDrugs(@RequestBody DrugsDTO drugsDTO) {
        return drugsService.addDrug(drugsDTO);
    }

    @GetMapping("/drugs")
    public CommonResult getDrugs(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return drugsService.getDrugs(pageNum, pageSize);
    }

    @PostMapping("/drugs/status")
    public CommonResult changeStatus(@RequestBody Drugs drugsDTO) {
        return drugsService.changeStatus(drugsDTO);
    }

    @PostMapping("/drugs/update")
    public CommonResult drugsUpdate(@RequestBody Drugs drugsDTO) {
        return drugsService.drugUpdate(drugsDTO);
    }

    @PostMapping("/drugs/del/single")
    public CommonResult drugDelSingle(@RequestBody DrugsDTO drugsDTO) {
        return drugsService.drugDelSingle(drugsDTO);
    }

    @PostMapping("/drugs/del/multi")
    public CommonResult drugDelSingle(@RequestBody DrugsDTO[] drugsDTOs) {
        return drugsService.drugDelSingle(drugsDTOs);
    }

    @GetMapping("/drugs/search")
    public CommonResult drugSearch(@RequestParam String keyword, @RequestParam Integer status, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return drugsService.drugSearch(keyword, status, pageNum, pageSize);
    }
}
