package com.tst;

import lombok.Data;

@Data
public class YearToDateReportDto {
    private String accountNumber;
    private String concessionaire;
    private String consumption;
    private String billAmount;
    private String unpaidBalance;
}
