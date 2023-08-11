package co.wanted.board.api.auth.presentation.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class Refresh {

    @Getter
    @RequiredArgsConstructor
    public static class Request {
        private final String memberEmail;
        private final String refreshToken;
    }
}
