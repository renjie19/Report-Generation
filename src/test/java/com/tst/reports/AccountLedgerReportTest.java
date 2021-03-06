package com.tst.reports;

import com.tst.reportDtos.AccountLedgerReportDto;
import com.tst.test2.PdfReport;
import com.tst.test2.QueryReportData;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AccountLedgerReportTest {

    public List<AccountLedgerReportDto> getData() {
        List<AccountLedgerReportDto> list = new ArrayList<>();
        for (int x = 0; x < 15; x++) {
            AccountLedgerReportDto ledgerDto = new AccountLedgerReportDto();
            ledgerDto.setAdvance("0");
            ledgerDto.setBalance((int) (x * Math.random()));
            ledgerDto.setCredit("0");
            ledgerDto.setDate("01/01/20");
            ledgerDto.setDebit("0");
            ledgerDto.setDueDate("01/01/20");
            ledgerDto.setParticular("Labor");
            ledgerDto.setReading("0");
            ledgerDto.setReference("00-00-00");
            ledgerDto.setUsage("0");
            ledgerDto.setUser("user");
            list.add(ledgerDto);
        }
        return list;
    }

    @Test
    public void generateWaterBillReport() {
        try {
            Map<String, String> headerParams = new LinkedHashMap<>();
            headerParams.put("Account Number: ", "1234-5555");
            headerParams.put("Concessionaire: ", "Test Concessionaire");
            headerParams.put("Address: ", "Iloilo City");
            headerParams.put("","");
            headerParams.put("From Date: ", "MM/dd/YYYY");
            headerParams.put("To Date: ", "MM/dd/YYYY");
            headerParams.put("Header Title", "WATER METER BILLING");
            QueryReportData<AccountLedgerReportDto> data = this::getData;
            PdfReport accountLedgerReport = new AccountLedgerReport(data, headerParams);
            byte[] result = accountLedgerReport.generate();
            File f = new File("new_reports/WaterBillReport.pdf");
            FileOutputStream output = new FileOutputStream(f);
            output.write(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void generateOtherARReport() {
        try {
            Map<String, String> headerParams = new LinkedHashMap<>();
            headerParams.put("Account Number: ", "1234-5555");
            headerParams.put("Concessionaire: ", "Test Concessionaire");
            headerParams.put("Address: ", "Iloilo City");
            headerParams.put("","");
            headerParams.put("From Date: ", "MM/dd/YYYY");
            headerParams.put("To Date: ", "MM/dd/YYYY");
            headerParams.put("Header Title", "OTHER ACCOUNTS RECEIVABLE");
            QueryReportData<AccountLedgerReportDto> data = this::getData;
            PdfReport accountLedgerReport = new AccountLedgerReport(data, headerParams);
            byte[] result = accountLedgerReport.generate();
            File f = new File("new_reports/OtherAccountsReceivableReport.pdf");
            FileOutputStream output = new FileOutputStream(f);
            output.write(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}