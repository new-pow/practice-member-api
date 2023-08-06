package co.wanted.board.api.member.presentation.dto;

import co.wanted.board.api.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class Signup {

    @Getter
    @RequiredArgsConstructor
    public static class Request {

        @Email
        @NotBlank()
        private final String email;
        @Size(min = 8)
        private final String password;
    }

    @Getter
    @RequiredArgsConstructor
    public static class Summary {

        private final Long id;
        private final String email;

        public static Summary of(Member savedMember) {
            return new Summary(savedMember.getId(), savedMember.getEmail());
        }
    }

}
