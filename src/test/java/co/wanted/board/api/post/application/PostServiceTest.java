package co.wanted.board.api.post.application;

import co.wanted.board.api.member.domain.Member;
import co.wanted.board.api.post.domain.Post;
import co.wanted.board.api.post.infrastructure.PostRepository;
import co.wanted.board.fixture.MemberFixtureFactory;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private MemberFixtureFactory memberFixtureFactory;
    private Member testMember;

    @BeforeEach
    void memberInit() {
        testMember = memberFixtureFactory.getDefaultMember();
    }

    @Test
    @DisplayName("post를 작성할 수 있다.")
    void write_success() throws Exception{
        Post write = postService.write(testMember.getId(), "test", "contents");
        Post savedPost = postRepository.findById(write.getId()).orElseThrow();

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(write.getId()).isEqualTo(savedPost.getId());
            softAssertions.assertThat(write.getCreatedAt()).isEqualTo(savedPost.getCreatedAt());
        });
    }

    @Test
    @DisplayName("post를 조회할 수 있다.")
    void getPost_success() throws Exception{
        Post write = postService.write(testMember.getId(), "test", "contents");
        Post servedPost = postService.getPost(write.getId());
        Post savedPost = postRepository.findById(write.getId()).orElseThrow();

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(servedPost.getId()).isEqualTo(savedPost.getId());
            softAssertions.assertThat(servedPost.getPostContent().getContent()).isEqualTo(savedPost.getPostContent().getContent());
            softAssertions.assertThat(servedPost.getCreatedAt()).isEqualTo(savedPost.getCreatedAt());
        });
    }


    @Test
    @DisplayName("post를 수정할 수 있다.")
    void updatePost_success() throws Exception{
        Instant before = Instant.now();
        Post write = postService.write(testMember.getId(), "test", "contents");
        Post updatedPost = postService.updatePost(write, "anotherTitle", "anotherContents");
        Post savedPost = postRepository.findById(write.getId()).orElseThrow();

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(updatedPost.getId()).isEqualTo(write.getId());
            softAssertions.assertThat(updatedPost.getTitle()).isEqualTo("anotherTitle");
            softAssertions.assertThat(updatedPost.getPostContent().getContent()).isEqualTo("anotherContents");
            softAssertions.assertThat(updatedPost.getUpdatedAt()).isAfter(before);
        });
    }

    @Test
    @DisplayName("post를 삭제할 수 있다.")
    void deletePost_success() throws Exception{
        Post write = postService.write(testMember.getId(), "test", "contents");
        postService.deletePost(write);
        Optional<Post> post = postRepository.findById(write.getId());

        assertThat(post).isEmpty();
    }
}
