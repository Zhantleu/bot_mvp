package kz.bot.mvp.repositories;

import kz.bot.mvp.models.MessageEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, UUID> {

    @NonNull List<MessageEntity> findAllByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}