//package kz.bot.mvp.repositories;
//
//import kz.bot.mvp.models.ChatEntity;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.lang.NonNull;
//import org.springframework.stereotype.Repository;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.UUID;
//
//
//@Repository
//public interface ChatRepository extends JpaRepository<ChatEntity, UUID> {
//    @Query("select c from ChatEntity c inner join c.messages messages where messages.createdAt between ?1 and ?2 group by c")
//    List<ChatEntity> findAllByMessages_CreatedAtBetween(@NonNull LocalDateTime createdAtStart,
//                                                        @NonNull LocalDateTime createdAtEnd);
//
//    ChatEntity findByUsername(@NonNull String username);
//}