package com.blanc.market.domain.user.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

    private String nickname;

    private String email;

    private String password;

    private String address;
}
