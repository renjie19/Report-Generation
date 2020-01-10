package com.tst.reports;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.tst.reportDtos.AccountLedgerDto;
import com.tst.test2.PdfReport;
import com.tst.test2.QueryReportData;
import com.tst.test2.ReportUtil;

import java.util.List;
import java.util.Map;


public class AccountLedgerReport extends PdfReport {

    public static final String REPORT_HEADER_TITLE = "Header Title";
    private final QueryReportData<AccountLedgerDto> data;
    private final Map<String,String> headerParams;


    public AccountLedgerReport(QueryReportData<AccountLedgerDto> data, Map<String, String> headerParams) {
        super(PageSize.A4.rotate());
        this.data = data;
        this.headerParams = headerParams;
    }

    @Override
    protected void reportHeader() {
        try {
            getDocument().add(getDefaultHeader(headerParams.get(REPORT_HEADER_TITLE)));
            PdfPTable headerDetails = ReportUtil.getPdfTable(2);
            headerDetails.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
            headerDetails.getDefaultCell().setBorderWidth(0f);
            for(Map.Entry<String, String> set : headerParams.entrySet()) {
                headerDetails.addCell(new Phrase(String.format(("%s %s"), set.getKey(), set.getValue())));
            }

            int total = data.query().stream().reduce(0,(counter, item)-> counter + item.getBalance(), Integer::sum);
            Paragraph runningBalance = new Paragraph("Running Balance: "+ total);
            runningBalance.setAlignment(Element.ALIGN_RIGHT);

            getDocument().add(headerDetails);
            getDocument().add(runningBalance);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void reportBody() {
        try {
            Font font = new Font(Font.FontFamily.HELVETICA,9);
            PdfPTable mainTable = ReportUtil.getPdfTable(11);
            String[] headers = {"Date ","Particular","Reading","Usage ","Due Date","Reference No.","Debit","Credit",
                    "Advance Payment","Balance","User "};
            ReportUtil.setColumnWidths(headers,mainTable);
            ReportUtil.addTableHeaders(headers,mainTable);
            data.query().forEach(ledger -> {
                mainTable.addCell(new Phrase(ledger.getDate(),font));
                mainTable.addCell(new Phrase(ledger.getParticular(),font));
                mainTable.addCell(new Phrase(ledger.getReading(),font));
                mainTable.addCell(new Phrase(ledger.getUsage(),font));
                mainTable.addCell(new Phrase(ledger.getDueDate(),font));
                mainTable.addCell(new Phrase(ledger.getReference(),font));
                mainTable.addCell(new Phrase(ledger.getDebit(),font));
                mainTable.addCell(new Phrase(ledger.getCredit(),font));
                mainTable.addCell(new Phrase(ledger.getAdvance(),font));
                mainTable.addCell(new Phrase(ledger.getBalance()+"",font));
                mainTable.addCell(new Phrase(ledger.getUser(),font));
            });
            getDocument().add(mainTable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void reportFooter() {

    }
}
