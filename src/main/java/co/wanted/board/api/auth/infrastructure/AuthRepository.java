package co.wanted.board.api.auth.infrastructure;

import co.wanted.board.api.auth.infrastructure.entity.MemberAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<MemberAuth, Long> {
    Optional<MemberAuth> findByIdAndRefreshToken(Long id, String refreshToken);
}
