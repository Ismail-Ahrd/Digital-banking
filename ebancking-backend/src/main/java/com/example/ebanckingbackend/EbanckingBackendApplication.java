package com.example.ebanckingbackend;

import com.example.ebanckingbackend.dtos.BankAccountDTO;
import com.example.ebanckingbackend.dtos.CurrentAccountDTO;
import com.example.ebanckingbackend.dtos.CustomerDTO;
import com.example.ebanckingbackend.dtos.SavingAccountDTO;
import com.example.ebanckingbackend.entities.*;
import com.example.ebanckingbackend.enums.AccountStatus;
import com.example.ebanckingbackend.enums.OperationType;
import com.example.ebanckingbackend.exceptions.BalanceNotSufficientException;
import com.example.ebanckingbackend.exceptions.BankAccoutNotFounException;
import com.example.ebanckingbackend.exceptions.CustomerNotFoundException;
import com.example.ebanckingbackend.mappers.BankAccountMapperImpl;
import com.example.ebanckingbackend.repositories.AccountOperationRepository;
import com.example.ebanckingbackend.repositories.BankAccountRepository;
import com.example.ebanckingbackend.repositories.CustomerRepository;
import com.example.ebanckingbackend.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbanckingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbanckingBackendApplication.class, args);
    }

    //@Bean
    CommandLineRunner start(BankAccountService bankAccountService,
                            BankAccountMapperImpl bankAccountMapper)
    {
        return args -> {
            Stream.of("Hassan", "Ahmed", "Nabil").forEach(name -> {
                CustomerDTO customer = new CustomerDTO();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                bankAccountService.saveCustomer(customer);
            });
            bankAccountService.listCustomers().forEach(customer -> {
                try {
                    bankAccountService.saveCurrentAccount(Math.random()*90000,
                            9000,customer.getId());
                    bankAccountService.saveSavingAccount(Math.random()*1200000,
                            5.5,customer.getId());
                } catch (CustomerNotFoundException e) {
                    e.printStackTrace();
                }
            });
            List<BankAccountDTO> bankAccountDTOs = bankAccountService.bankAccountList();
            for (BankAccountDTO bankAccountDTO : bankAccountDTOs) {
                BankAccount bankAccount = bankAccountMapper.fromBankAccountDTO(bankAccountDTO);
                for (int i = 0; i <10; i++) {
                    bankAccountService.credit(bankAccount.getId(),
                            10000+Math.random()*120000,"Credit");
                    bankAccountService.debit(bankAccount.getId(),
                            1000+Math.random()*9000,"Debit");
                }
            }
        };
    }

    //@Bean
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository,
                                        BankAccountRepository bankAccountRepository,
                                        AccountOperationRepository accountOperationRepository
    ) {
        return args -> {
            Stream.of("Hassan", "Ahmed", "Nabil").forEach(name -> {
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                customerRepository.save(customer);
            });
            customerRepository.findAll().forEach(customer -> {
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setCreatedAt(new Date());
                currentAccount.setBalance(Math.random()*90000);
                currentAccount.setAccountStatus(AccountStatus.CREATED);
                currentAccount.setOverDraft(9000);
                currentAccount.setCustomer(customer);
                bankAccountRepository.save(currentAccount);

                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setCreatedAt(new Date());
                savingAccount.setBalance(Math.random()*90000);
                savingAccount.setAccountStatus(AccountStatus.CREATED);
                savingAccount.setInterestRate(5.5);
                savingAccount.setCustomer(customer);
                bankAccountRepository.save(savingAccount);
            });

            bankAccountRepository.findAll().forEach(acc -> {
                for (int i = 0; i < 10; i++) {
                    AccountOperation accountOperation = new AccountOperation();
                    accountOperation.setAmount(Math.random()*1000);
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setOperationType(Math.random()<0.5? OperationType.CREDIT:OperationType.DEBIT);
                    accountOperation.setBankAccount(acc);
                    accountOperationRepository.save(accountOperation);
                }
            });
        };
    }


}
