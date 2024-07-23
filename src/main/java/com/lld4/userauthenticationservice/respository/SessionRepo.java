package com.lld4.userauthenticationservice.respository;

import com.lld4.userauthenticationservice.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepo extends JpaRepository<Session, Long> {
}
