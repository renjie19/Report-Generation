package com.tst.reports;

import lombok.Data;

@Data
public class AccountLedgerDto {
    private String date;
    private String particular;
    private String reading;
    private String Usage;
    private String dueDate;
    private String reference;
    private String debit;
    private String credit;
    private String advance;
    private int balance;
    private String user;
}
