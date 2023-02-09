package kz.bot.mvp.repositories;

import kz.bot.mvp.models.MessageEntity;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, UUID> {
    @Query("select m from MessageEntity m where upper(m.chat.username) = upper(?1)")
    Page<MessageEntity> findByChat_UsernameIgnoreCase(@org.springframework.lang.NonNull String username,
                                                      Pageable pageable);

    @NonNull List<MessageEntity> findAllByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}