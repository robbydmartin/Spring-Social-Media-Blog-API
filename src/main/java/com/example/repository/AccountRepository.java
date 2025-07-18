package com.example.repository;

import com.example.entity.*;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findAccountByAccountId(Integer accountId);
    Optional<Account> findAccountByUsername(String username);
    Optional<Account> findAccountByUsernameAndPassword(String username, String password);
}
