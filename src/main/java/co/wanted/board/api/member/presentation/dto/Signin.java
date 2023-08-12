package co.wanted.board.api.member.presentation.dto;

import co.wanted.board.api.auth.domain.Token;
import co.wanted.board.api.member.domain.Member;
import co.wanted.board.api.member.presentation.validation.Email;
import lombok.*;

import javax.validation.constraints.Size;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    public static class Response {
        private final String username;
        private final Token token;

        @Builder
        protected Response(String username, Token token) {
            this.username = username;
            this.token = token;
        }

        public static Response of(Member signinedMember, Token token) {
            return new Response(signinedMember.getUsername(), token);
        }
    }
}
