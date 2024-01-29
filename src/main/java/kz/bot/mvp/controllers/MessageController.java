//package kz.bot.mvp.controllers;
//
//import kz.bot.mvp.models.MessageDto;
//import kz.bot.mvp.models.MessageEntity;
//import kz.bot.mvp.services.MessageService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/messages")
//public class MessageController {
//
//    private final static String TOKEN = "20032008";
//    private final MessageService messageService;
//
//    @Autowired
//    public MessageController(MessageService messageService) {
//        this.messageService = messageService;
//    }
//
//    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Page<MessageDto>> getAll(@PathVariable String username,
//                                                   String token,
//                                                   Pageable pageable) {
//        if (!TOKEN.equalsIgnoreCase(token)) {
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
//        final Page<MessageEntity> page = messageService.getAll(username, pageable);
//        final List<MessageDto> messageDtos = page
//            .stream()
//            .map(messageEntity -> new MessageDto(messageEntity.getText(), messageEntity.getCreatedAt()))
//            .collect(Collectors.toList());
//        return ResponseEntity.ok(new PageImpl<>(messageDtos, page.getPageable(), page.getTotalElements()));
//    }
//}
