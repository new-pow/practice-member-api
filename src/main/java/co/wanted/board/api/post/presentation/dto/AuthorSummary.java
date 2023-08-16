package co.wanted.board.api.post.presentation.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthorSummary {
    private final Long authorId;
    private final String authorname;
}
