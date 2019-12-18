package com.tst.reportDtos;

import com.tst.reports.AccountLedgerDto;
import com.tst.test2.QueryReportData;
import lombok.Data;

@Data
public class AccountLedgerReportDto {
    private String accountType;
    private String accountNo;
    private String Concessionaire;
    private String address;
    private String fromDate;
    private String toDate;
    private String runningBalance;
    private QueryReportData<AccountLedgerDto> data;
}
