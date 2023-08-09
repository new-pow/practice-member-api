package co.wanted.board.api.auth.applicaion;

import co.wanted.board.api.member.domain.Password;

public interface PasswordEncoder {

    Password encrypt(final String text);
    Password encrypt(final String text, final byte[] salt);
}
