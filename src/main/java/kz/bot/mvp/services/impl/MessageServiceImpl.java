//package kz.bot.mvp.services.impl;
//
//import kz.bot.mvp.models.MessageEntity;
//import kz.bot.mvp.repositories.MessageRepository;
//import kz.bot.mvp.services.MessageService;
//import lombok.NonNull;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//@Service
//public class MessageServiceImpl implements MessageService {
//    private final MessageRepository messageRepository;
//
//    @Autowired
//    public MessageServiceImpl(MessageRepository messageRepository) {
//        this.messageRepository = messageRepository;
//    }
//
//    @Override
//    public @NonNull Page<MessageEntity> getAll(@NonNull String username, @NonNull Pageable pageable) {
//        return messageRepository.findAllByChat_UsernameIgnoreCase(username, pageable);
//    }
//}
