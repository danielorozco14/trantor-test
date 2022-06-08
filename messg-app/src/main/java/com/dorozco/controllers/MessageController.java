package com.dorozco.controllers;

import com.dorozco.models.Message;
import com.dorozco.service.repository.MessageRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chat")
public class MessageController {
    private final MessageRepository messageRepository;

    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    /**
     * Adds new message to H2 DB
     */
    @PostMapping("/send")
    public ResponseEntity<String> saveMessage(@RequestParam()String to, @Valid @RequestBody Message message, Authentication authentication){
        String authName = authentication.getName();
        String sender = message.getSender();
        if(!authName.equals(sender) || to.equals(authName)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There was an error with your request. " +
                    "You cannot send messages with a user name different from yours or yourself");
        }

        try{
            message.setReceiver(to);//the user who will receive the message
            message.setStatus("not-read");
            //message.setSender(<the username authenticated who wrote the message>);
            messageRepository.save(message);
            return ResponseEntity.status(HttpStatus.OK).body("The message was sent to: " + to);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There was an error with your request");
        }
    }
    /**
     * Returns all unread messages from the authenticated user     *
     *  findByStatus == "not-read" returns all unread  messages.
     * */
    @GetMapping("/read")
    public List<Message> incomingMessages(Authentication authentication) {
        /*List<Message> result;
        result = messageRepository.findByStatus("not-read");
        return result;*/

        List<Message> incomMessages = messageRepository.findByStatus("not-read").stream().filter(message ->
                        !message.getSender().equals(authentication.getName())).collect(Collectors.toList());
        return incomMessages;

    }

    @Transactional
    @PutMapping("/mark-as-read")
    public ResponseEntity<String> markAsRead(@RequestParam()Integer message, Authentication authentication){
        if(message != null){
            Optional<Message> messageToMark = messageRepository.findById(message);
            if(messageToMark.isPresent()){
                if(messageToMark.get().getSender().equals(authentication.getName())){
                    messageRepository.updateMessageStatus(message);
                    return ResponseEntity.status(HttpStatus.OK).body("The message status was updated to: Message Readed");
                }else{
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is an error with your request. This message is not yours.");
                }
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The message does not exist in the database");
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is an error with your request.");
    }

    @Transactional
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteMessage(@RequestParam()Integer message, Authentication authentication) {
        if (message != null) {
            Optional<Message> messageToMark = messageRepository.findById(message);
            if (messageToMark.isPresent()) {
                if(messageToMark.get().getSender().equals(authentication.getName())){
                    messageRepository.deleteById(message);
                    return ResponseEntity.status(HttpStatus.OK).body("The message was deleted");
                }else{
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is an error with your request. This message is not yours.");
                }

            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The message does not exist in the database");
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is an error with your request.");
    }

}
