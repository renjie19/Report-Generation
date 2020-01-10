package com.tst.reports;

import com.itextpdf.text.PageSize;
import com.tst.reportDtos.AccountsReceivableReportDto;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AccountsReceivableReportTest {

    private List<AccountsReceivableReportDto> getData() {
        List<AccountsReceivableReportDto> list = new ArrayList<>();
        for(int x = 0;x<10;x++) {
            AccountsReceivableReportDto dto = new AccountsReceivableReportDto();
            dto.setAccountNumber(String.format("%s - %s - %s",x,x,x));
            dto.setConcessionaire(String.format("Concessionaire %s",x));
            dto.setParticular(String.format("Particular %s",x));
            dto.setDate("MM/DD/YYYY");
            dto.setDateBilled("MM/DD/YYYY");
            dto.setDueDate("MM/DD/YYYY");
            dto.setAmount(x);
            dto.setAmountPaid(x);
            dto.setAdjustment(x);
            dto.setBalance(x);
            list.add(dto);
        }
        return list;
    }

    @Test
    public void generateAccountReceivableReportTest() throws IOException {
        AccountsReceivableReport accReceivableReport = new AccountsReceivableReport(PageSize.A4.rotate(),this::getData);
        byte[] result = accReceivableReport.generate();
        File f = new File("new_reports/AccountsReceivableReport.pdf");
        FileOutputStream output = new FileOutputStream(f);
        output.write(result);
    }
}