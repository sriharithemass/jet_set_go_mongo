package com.training.security.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {
    private String id;
    private String username;
    private String email;
    private String role;
}
