package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.*;
import com.example.exception.*;
import com.example.repository.*;

@Service
public class AccountService {

    private AccountRepository accountRepository;
    private MessageRepository messageRepository;

    /*
     * Constructor method.
     * 
     * @param accountRepository an account repository object.
     * @param messageRepository a message repository object.
     */
    @Autowired
    public AccountService(AccountRepository accountRepository, MessageRepository messageRepository) {
        this.accountRepository = accountRepository;
        this.messageRepository = messageRepository;
    }

    /*
     * Method to retieve all messages for a specified account.
     * 
     * @param accountId the account id to search for.
     * @return list of messages.
     */
    public List<Message> getMessagesByAccountId(Integer accountId) {
        return messageRepository.findAllMessagesByPostedBy(accountId);
    }

    /*
     * Method to register a new user and save to database. Method checks if the username field is blank, if the password is less
     * than 4 characters long, and if the username already exists in the database.
     * 
     * @param account an account object.
     * @return the saved account.
     * @throws BlankMessageException, PasswordCriteriaException, DuplicateUsernameException
     */
    public Account registerUser(Account account) throws BlankMessageException, PasswordCriteriaException, DuplicateUsernameException {
        Optional<Account> validAccount = accountRepository.findAccountByUsername(account.getUsername());
        if (account.getUsername().isBlank()) {
            throw new BlankMessageException("The username field is blank. Please enter a username.");
        } else if (account.getPassword().length() < 4) {
            throw new PasswordCriteriaException("The password entered must be more than 4 characters in length.");
        } else if (validAccount.isPresent() && !validAccount.get().equals(null)) {
            throw new DuplicateUsernameException("Username already exists.");
        } else {
            Account newAccount = accountRepository.save(account);
            return newAccount;
        }
    }

    /*
     * Method to check if account exists in the database by just using the account id.
     * 
     * @param accountId the account id to search for.
     * @return true if the account exists or false otherwise.
     */
    public boolean validAccount(Integer accountId) {
        Optional<Account> isValid = accountRepository.findAccountByAccountId(accountId);
        if (isValid.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * Method for logging in a user using an account object. If the username and password do not match in the database,
     * an exception is thrown.
     * 
     * @param account an account object.
     * @return the valid account or null if not found.
     * @throws UnauthorizedUserException
     */
    public Account accountLogin(Account account) throws UnauthorizedUserException {
        Optional<Account> validAccount = accountRepository.findAccountByUsernameAndPassword(account.getUsername(), account.getPassword());
        if (validAccount.isPresent()) {
            return validAccount.get();
        } else {
            throw new UnauthorizedUserException("The username and/or password is incorrect.");
        }
    }
}
