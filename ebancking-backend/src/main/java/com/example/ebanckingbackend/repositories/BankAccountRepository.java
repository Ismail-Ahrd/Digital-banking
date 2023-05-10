package com.example.ebanckingbackend.repositories;

import com.example.ebanckingbackend.dtos.BankAccountDTO;
import com.example.ebanckingbackend.entities.BankAccount;
import com.example.ebanckingbackend.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
    Page<BankAccount> findByCustomerId(Long id, Pageable pageable);
}
