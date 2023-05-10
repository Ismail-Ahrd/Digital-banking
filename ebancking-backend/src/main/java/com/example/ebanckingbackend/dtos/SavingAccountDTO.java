package com.example.ebanckingbackend.dtos;

import com.example.ebanckingbackend.enums.AccountStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;


@Data
public class SavingAccountDTO extends BankAccountDTO{
    /*private String id;
    private double balance;
    private Date createdAt;
    private AccountStatus accountStatus;
    private CustomerDTO customerDTO;*/
    private double interestRate;
}
