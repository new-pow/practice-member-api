package co.wanted.board.api.member.infrastructure.persistence;

import co.wanted.board.api.member.domain.Member;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Repository
public interface MemberRepository extends PagingAndSortingRepository<Member, Long> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<Member> findByEmail(String email);
}
