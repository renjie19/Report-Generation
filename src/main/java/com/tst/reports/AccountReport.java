package com.tst.reports;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.tst.reportDtos.AccountReportDto;
import com.tst.test2.PdfReport;
import com.tst.test2.QueryReportData;
import com.tst.test2.ReportUtil;

public class AccountReport extends PdfReport {
    private QueryReportData<AccountReportDto> data;

    public AccountReport(Rectangle size, QueryReportData<AccountReportDto> data) {
        super(size);
        this.data = data;
    }

    @Override
    protected void reportHeader() {
        try {
            getDocument().add(getDefaultHeader("Chart Of Accounts"));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void reportBody() {
        try {
            PdfPTable mainTable = ReportUtil.getPdfTable(4);
            String[] headers = {"Account Type","Account Name","Account Code","Status"};
            ReportUtil.addTableHeaders(headers,mainTable);
            data.query().forEach(account -> {
                mainTable.addCell(account.getAccountType());
                mainTable.addCell(account.getAccountName());
                mainTable.addCell(account.getAccountCode());
                mainTable.addCell(account.getStatus());
            });
            getDocument().add(mainTable);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void reportFooter() {

    }
}
