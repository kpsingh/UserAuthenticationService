package com.lld4.userauthenticationservice.respository;

import com.lld4.userauthenticationservice.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepo extends JpaRepository<Session, Long> {
    Optional<Session> findByToken(String token);
}
