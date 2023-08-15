package co.wanted.board.api.post.presentation.dto;

import co.wanted.board.api.member.domain.Member;
import co.wanted.board.api.post.domain.Post;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class PostUpdate {

    @Getter
    @RequiredArgsConstructor
    public static class Request {

        private final String title;
        private final Long authorId;
        private final String contents;
    }

    @Getter
    @RequiredArgsConstructor
    public static class Response {

        private final Long id;

        public static Response of(Post updatedPost) {
            return new Response(updatedPost.getId());
        }
    }
}
