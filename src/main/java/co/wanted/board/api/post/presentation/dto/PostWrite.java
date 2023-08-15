package co.wanted.board.api.post.presentation.dto;

import co.wanted.board.api.post.domain.Post;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class PostWrite {

    @Getter
    @RequiredArgsConstructor
    public static class Request {

        private String title;
        private String contents;
    }

    @Getter
    public static class Response {

        private Long id;

        public Response(Long id) {
            this.id = id;
        }

        public static Response of(Post savedPost) {
            return new Response(savedPost.getId());
        }
    }
}
