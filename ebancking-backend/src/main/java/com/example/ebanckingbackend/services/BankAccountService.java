package com.example.ebanckingbackend.services;

import com.example.ebanckingbackend.dtos.*;
import com.example.ebanckingbackend.entities.BankAccount;
import com.example.ebanckingbackend.entities.CurrentAccount;
import com.example.ebanckingbackend.entities.Customer;
import com.example.ebanckingbackend.entities.SavingAccount;
import com.example.ebanckingbackend.exceptions.BalanceNotSufficientException;
import com.example.ebanckingbackend.exceptions.BankAccoutNotFounException;
import com.example.ebanckingbackend.exceptions.CustomerNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BankAccountService {
    CustomerDTO saveCustomer(CustomerDTO customer);
    CurrentAccountDTO saveCurrentAccount(double initialBalance, double overDraft, Long customerId)
            throws CustomerNotFoundException;
    SavingAccountDTO saveSavingAccount(double initialBalance, double interestRate, Long customerId)
            throws CustomerNotFoundException;
    List<CustomerDTO> listCustomers();
    BankAccountDTO getBankAccount(String accountId) throws BankAccoutNotFounException;
    void debit(String accountId, double amount, String description) throws BankAccoutNotFounException, BalanceNotSufficientException;
    void credit(String accountId, double amount, String description) throws BankAccoutNotFounException;
    void transfer(String accountIdSource, String accountIdDestination, double amount)
            throws BankAccoutNotFounException, BalanceNotSufficientException;
    List<BankAccountDTO> bankAccountList();
    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long customerId);

    List<AccountOperationDTO> accountHistory(String accountId);

    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccoutNotFounException;

    List<CustomerDTO> searchCustomers(String keyword);
    List<BankAccountDTO> getAccountsByCustomerId(Long customerId, int page, int size);
}
