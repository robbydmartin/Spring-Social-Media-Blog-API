package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.*;
import com.example.service.*;
import com.example.exception.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
@ControllerAdvice
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    /*
     * Constructor method.
     * 
     * @param accountService an account service object.
     * @param messageService a message service object.
     */
    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    /*
     * Method to handle get all message request.
     * 
     * @return a reponse entity with list of messages as the response body and http status 200.
     */
   @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.status(200).body(messageService.getAllMessages());
    }

    /*
     * Method to handle get message by id request.
     * 
     * @param messageId the id of the message taken from http path.
     * @return a response entity with the specified message in the response body and http status 200.
     */
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer messageId) {
        return ResponseEntity.status(200).body(messageService.getMessageById(messageId));
    }

    /*
     * Method to handle the creation of new message requests.
     * 
     * @param message the new message to save.
     * @return a repsonse entity with the new message in the response body with http status 200.
     */
    @PostMapping("/messages")
    public ResponseEntity<Message> addNewMessage(@RequestBody Message message) {
        return ResponseEntity.status(200).body(messageService.addMessage(message));
    }

    /*
     * Method to handle a message patch request.
     * 
     * @param messageId the id of the message to update.
     * @param message the updated message text.
     * @return a reponse entity with the update message in the response body and http status 200.
     */
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessage(@PathVariable Integer messageId, @RequestBody Message message) {
        return ResponseEntity.status(200).body(messageService.updateMessage(messageId, message));
    }

    /*
     * Method to handle message delete requests.
     * 
     * @param messageId the id of the message to delete.
     * @return a response entity with the number of rows deleted and the http status 200.
     */
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable Integer messageId) {
        return ResponseEntity.status(200).body(messageService.deleteMessageById(messageId));
    }

    /*
     * Method to handle registering new account request. 
     * 
     * @param account a account object
     * @return a reponse entity with the newly created account object as the response body with http status 200.
     */
    @PostMapping("/register")
    public ResponseEntity<Account> registerUser(@RequestBody Account account) {
        return ResponseEntity.status(200).body(accountService.registerUser(account));
    }

    /*
     * Method to handle login request.
     * 
     * @param account an account object
     * @return a response entity with the account as the response body with http status 200.
     */
    @PostMapping("/login")
    public ResponseEntity<Account> accountLogin(@RequestBody Account account) {
        return ResponseEntity.status(200).body(accountService.accountLogin(account));
    }

    /*
     * Method to handle requests to retrive messages using an account id.
     * 
     * @param accountId the account id to look up messages by.
     * @return a response entity with a list of all the messages of the specified account as the response body and https status 200.
     */
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByAccountId(@PathVariable("accountId") Integer accountId) {
        List<Message> messageList = accountService.getMessagesByAccountId(accountId);
        return ResponseEntity.status(200).body(messageList);
    }

    /*
     * Method to handle BlankMethod exceptions.
     * 
     * @return ResponseEntity with http status 400 and exception text in reponse body.
     */
    @ExceptionHandler(BlankMessageException.class)
    public ResponseEntity<BlankMessageException> handleBlankMessageException(BlankMessageException ex) {
        return ResponseEntity.status(400).body(ex);
    }

    /*
     * Method to handle MessageLength exceptions.
     * 
     * @return ResponseEntity with http status 400 and exception text in reponse body.
     */
    @ExceptionHandler(MessageLengthException.class)
    public ResponseEntity<MessageLengthException> handleMessageLengthException(MessageLengthException ex) {
        return ResponseEntity.status(400).body(ex);
    }

    /*
     * Method to handle InvalidMessageId exceptions.
     * 
     * @return ResponseEntity with http status 400 and exception text in reponse body.
     */
    @ExceptionHandler(InvalidMessageIdException.class)
    public ResponseEntity<InvalidMessageIdException> handleInvalidMessageIdException(InvalidMessageIdException ex) {
        return ResponseEntity.status(400).body(ex);
    }

    /*
     * Method to handle InvalidAccount exceptions.
     * 
     * @return ResponseEntity with http status 400 and exception text in reponse body.
     */
    @ExceptionHandler(InvalidAccountException.class)
    public ResponseEntity<InvalidAccountException> handleInvalidAccountException(InvalidAccountException ex) {
        return ResponseEntity.status(400).body(ex);
    }

    /*
     * Method to handle DuplicateUsername exceptions.
     * 
     * @return ResponseEntity with http status 409 and exception text in reponse body.
     */
    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<DuplicateUsernameException> handleDuplicateUsernameException(DuplicateUsernameException ex) {
        return ResponseEntity.status(409).body(ex);
    }

    /*
     * Method to handle UnauthorizedUser exceptions.
     * 
     * @return ResponseEntity with http status 401 and exception text in reponse body.
     */
    @ExceptionHandler(UnauthorizedUserException.class)
    public ResponseEntity<UnauthorizedUserException> handleUnauthorizedUserException(UnauthorizedUserException ex) {
        return ResponseEntity.status(401).body(ex);
    }
}
