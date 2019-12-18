package com.tst.reports;

import com.tst.reportDtos.AccountLedgerReportDto;
import com.tst.test2.PdfReport;
import com.tst.test2.QueryReportData;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AccountLedgerReportTest {

    public List<AccountLedgerDto> getData() {
        List<AccountLedgerDto> list = new ArrayList<>();
        for (int x = 0; x < 15; x++) {
            AccountLedgerDto ledgerDto = new AccountLedgerDto();
            ledgerDto.setAdvance("0");
            ledgerDto.setBalance(0);
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

            QueryReportData<AccountLedgerDto> data = this::getData;
            PdfReport accountLedgerReport = new AccountLedgerReport(data, headerParams);
            byte[] result = accountLedgerReport.generate();
            File f = new File("new_reports/WaterBillReport.pdf");
            FileOutputStream output = new FileOutputStream(f);
            output.write(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}