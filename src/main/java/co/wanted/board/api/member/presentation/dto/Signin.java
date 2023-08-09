package co.wanted.board.api.member.presentation.dto;

import co.wanted.board.api.member.presentation.validation.Email;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Size;

public class Signin {
    @Getter
    @RequiredArgsConstructor
    public static class Request {

        @Email
        private final String email;
        @Size(min = 8, message = "최소 8글자 이상이어야 합니다.")
        private final String password;
    }

    @Getter
    @RequiredArgsConstructor
    public static class Response {
        private final String username;
    }
}
