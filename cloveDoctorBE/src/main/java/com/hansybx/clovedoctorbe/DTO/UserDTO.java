package com.hansybx.clovedoctorbe.DTO;

import com.hansybx.clovedoctorbe.model.User;
import lombok.Data;

@Data
public class UserDTO extends User {
    private String captcha;
}
