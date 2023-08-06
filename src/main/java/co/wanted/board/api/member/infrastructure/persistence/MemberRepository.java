package co.wanted.board.api.member.infrastructure.persistence;

import co.wanted.board.api.member.domain.Member;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MemberRepository extends PagingAndSortingRepository<Member, Long> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
