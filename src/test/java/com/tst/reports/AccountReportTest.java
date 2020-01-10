package com.tst.reports;

import com.itextpdf.text.PageSize;
import com.tst.reportDtos.AccountReportDto;
import com.tst.test2.QueryReportData;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AccountReportTest {

    private List<AccountReportDto> generateMockData() {
        List<AccountReportDto> list = new ArrayList<>();
        for(int x = 0;x<10;x++) {
            AccountReportDto dto = new AccountReportDto();
            dto.setAccountCode(String.format("0000-%s",(int)(x*Math.random())));
            if(x%2==0) {
                dto.setAccountName("Water Sales");
            } else {
                dto.setAccountName("Previous Balance");
            }
            dto.setAccountType("Income");
            dto.setStatus("Active");
            list.add(dto);
        }
        return list;
    }


    @Test
    public void generateReport() throws IOException {
        QueryReportData<AccountReportDto> data = this::generateMockData;
        AccountReport accountReport = new AccountReport(PageSize.A4,data);
        byte[] result = accountReport.generate();
        File f = new File("new_reports/AccountReport.pdf");
        FileOutputStream output = new FileOutputStream(f);
        output.write(result);
    }

}