package co.wanted.board.api.auth.presentation.dto;

import co.wanted.board.api.member.presentation.validation.Email;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class Refresh {

    @Getter
    @RequiredArgsConstructor
    public static class Request {
        @Email
        private final String memberEmail;
        private final String refreshToken;
    }
}
