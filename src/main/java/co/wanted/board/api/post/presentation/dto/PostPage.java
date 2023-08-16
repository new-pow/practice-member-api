package co.wanted.board.api.post.presentation.dto;

import co.wanted.board.api.member.domain.Member;
import co.wanted.board.api.post.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class PostPage {

    @Getter
    @RequiredArgsConstructor
    public static class Request {
        private final Long last;
    }

    @Getter
    public static class Response {
        private Long lastId;
        private Page<PostSummary> postPage;

        @Builder
        public Response(Long lastId, Page<PostSummary> postPage) {
            this.lastId = lastId;
            this.postPage = postPage;
        }

        public static Response of(Page<Post> postPage, List<PostSummary> allMembersByPosts) {
            Page<PostPage.PostSummary> postSummaries = new PageImpl<>(allMembersByPosts, postPage.getPageable(), postPage.getTotalElements());
            PostSummary last = allMembersByPosts.get(allMembersByPosts.size() - 1);
            return Response.builder()
                    .lastId(last.getId())
                    .postPage(postSummaries)
                    .build();
        }
    }

    @Getter
    public static class PostSummary {
        private Long id;
        private String title;
        private AuthorSummary author;
        private Instant createdAt;
        private Instant updatedAt;

        @Builder
        public PostSummary(Long id, String title, Long authorId, String authorname, Instant createdAt, Instant updatedAt) {
            this.id = id;
            this.title = title;
            this.author = new AuthorSummary(authorId, authorname);
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }

        public static PostSummary fromDomain(Post post, Member member) {
            return PostSummary.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .authorId(post.getAuthorId())
                    .authorname(member.getUsername())
                    .createdAt(post.getCreatedAt())
                    .updatedAt(post.getUpdatedAt())
                    .build();
        }
    }
}
