package com.jobdam.jwt_login.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@Data
@AllArgsConstructor
@Setter
public class ReqSignupUser {
    @Email(message = "이메일 형식은 필수 입니다.")
    @NotBlank(message = "이메일은 필수 입니다.")
    private String email; //이메일
    @NotBlank(message = "비밀번호는 필수입니다.")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,14}$",
            message = "비밀번호는 8자 이상 15자 미만이며, 대소문자, 숫자, 특수문자를 포함해야 합니다."
    )
    private String password; // password
    @NotBlank(message = "비밀번호를 재익력칸은 필수입니다.")
    private String reCheckPassword; // password 재입력
    private String name; // 이름
}
