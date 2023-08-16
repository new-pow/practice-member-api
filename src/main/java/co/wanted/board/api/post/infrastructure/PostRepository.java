package co.wanted.board.api.post.infrastructure;

import co.wanted.board.api.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Long> {
    Page<Post> findAllByIdLessThanOrderByIdDesc(Long id, Pageable pageable);
    Post findTopByOrderByIdDesc();
}
