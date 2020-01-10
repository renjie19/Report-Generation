package com.tst.reports;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.tst.reportDtos.AccountsReceivableReportDto;
import com.tst.test2.PdfReport;
import com.tst.test2.QueryReportData;
import com.tst.test2.ReportUtil;

public class AccountsReceivableReport extends PdfReport {

    private QueryReportData<AccountsReceivableReportDto> data;

    public AccountsReceivableReport(Rectangle size, QueryReportData<AccountsReceivableReportDto> data) {
        super(size);
        this.data = data;
    }

    @Override
    protected void reportHeader() {
        try {
            getDocument().add(getDefaultHeader("LIST OF OTHER ACCOUNTS RECEIVABLE"));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void reportBody() {
        try {
            PdfPTable mainTable = ReportUtil.getPdfTable(10);
            String[] headers = {"Account No.", "Concessionaire", "Particular", "Date", "Date Billed", "Due Date", "Amount", "Amount Paid","Adjustment","Balance"};
            int[] widths = {9,14,9,10,10,10,6,9,8,6};
            mainTable.setWidths(widths);
            ReportUtil.addTableHeaders(headers,mainTable);
            data.query().forEach(ar -> {
                mainTable.addCell(ar.getAccountNumber());
                mainTable.addCell(ar.getConcessionaire());
                mainTable.addCell(ar.getParticular());
                mainTable.addCell(ar.getDate());
                mainTable.addCell(ar.getDateBilled());
                mainTable.addCell(ar.getDueDate());
                mainTable.addCell(String.valueOf(ar.getAmount()));
                mainTable.addCell(String.valueOf(ar.getAmountPaid()));
                mainTable.addCell(String.valueOf(ar.getAdjustment()));
                mainTable.addCell(String.valueOf(ar.getBalance()));
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
