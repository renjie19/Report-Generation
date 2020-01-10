package com.tst.reportDtos;

import lombok.Data;

@Data
public class AccountsReceivableReportDto {
    private String accountNumber;
    private String concessionaire;
    private String particular;
    private String date;
    private String dateBilled;
    private String dueDate;
    private double amount;
    private int amountPaid;
    private double adjustment;
    private double balance;
}
