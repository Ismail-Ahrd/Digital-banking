package com.example.ebanckingbackend.web;

import com.example.ebanckingbackend.dtos.*;
import com.example.ebanckingbackend.exceptions.BalanceNotSufficientException;
import com.example.ebanckingbackend.exceptions.BankAccoutNotFounException;
import com.example.ebanckingbackend.services.BankAccountService;
import com.example.ebanckingbackend.services.BankAccountServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class BankAccountRestController {
    private BankAccountService bankAccountService;

    @GetMapping("/accounts/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccoutNotFounException {
        return bankAccountService.getBankAccount(accountId);
    }

    @GetMapping("/accounts")
    public List<BankAccountDTO> listBankAccount() {
        return bankAccountService.bankAccountList();
    }

    @GetMapping("/accounts/{accountId}/operations")
    public List<AccountOperationDTO> getHistory(@PathVariable String accountId) {
        return bankAccountService.accountHistory(accountId);
    }

    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistory(
            @PathVariable String accountId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size)
            throws BankAccoutNotFounException
    {
        return bankAccountService.getAccountHistory(accountId,page,size);
    }

    @GetMapping("/accounts/customer/{customerId}")
    public List<BankAccountDTO> getAccountHistory(
            @PathVariable Long customerId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size)
            throws BankAccoutNotFounException
    {
        return bankAccountService.getAccountsByCustomerId(customerId, page, size);
    }



    @PostMapping("/operations/debit")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO)
            throws BankAccoutNotFounException, BalanceNotSufficientException
    {
        this.bankAccountService.debit(
                debitDTO.getAccountId(), debitDTO.getAmount(), debitDTO.getDescription());
        return debitDTO;
    }

    @PostMapping("/operations/credit")
    public CreditDTO credit(@RequestBody CreditDTO creditDTO)
            throws BankAccoutNotFounException
    {
        this.bankAccountService.credit(
                creditDTO.getAccountId(), creditDTO.getAmount(), creditDTO.getDescription());
        return creditDTO;
    }

    @PostMapping("/operations/transfer")
    public void transfer(@RequestBody TransferDTO transferDTO)
            throws BankAccoutNotFounException, BalanceNotSufficientException
    {
        this.bankAccountService.transfer(
                transferDTO.getAccountIdSource(),
                transferDTO.getAccountIdDestination(),
                transferDTO.getAmount());
    }

}
