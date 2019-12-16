package com.tst;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.Data;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AdjustmentMemo_ForOAR {
    private PdfWriter writer;
    private Map<String,String> adjustmentInfo;
    private List<String> statementOfAccountsColumnHeader;
    private List<String> meterReadingColumnHeaders;
    private List<MeterReadingDto> meterReadingDtoList;
    private List<StatementOfAccountDto> statementList;

    public void createBillAdjustmentMemoWithTwoSubreport(boolean hasMeter){
        initializeData();
        try{
            Document document = new Document();
            writer = PdfWriter.getInstance(document, new FileOutputStream("reports/AdjustmentMemo&AdjustmentMemoForOAR_two_sub.pdf"));
            document.open();
            document.add(getHeader());
            document.add(setAdjustmentReportDetails());
            if(hasMeter){
                document.add(getMeterReadingTable());
            }
            document.add(getStatementOfAccountsTable());
            document.close();
            writer.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private PdfPTable getMeterReadingTable() {
        PdfPTable meterReadingTable = getSubReportTable(meterReadingColumnHeaders);
        meterReadingDtoList.forEach( meter -> {
            meterReadingTable.addCell(new Phrase(meter.getCurrent()));
            meterReadingTable.addCell(new Phrase(meter.getPrevious()));
            meterReadingTable.addCell(new Phrase(meter.getConsumed()));
            meterReadingTable.addCell(new Phrase(meter.getAmount()));
        });
        return meterReadingTable;
    }

    private PdfPTable getStatementOfAccountsTable() {
        PdfPTable statementOfAccounts = getSubReportTable(statementOfAccountsColumnHeader);
        statementList.forEach( statement -> {
            statementOfAccounts.addCell(new Phrase(statement.getParticular()));
            statementOfAccounts.addCell(new Phrase(statement.getAccountSummary()));
            statementOfAccounts.addCell(new Phrase(statement.getPaid()));
            statementOfAccounts.addCell(new Phrase(statement.getBalance()));
            statementOfAccounts.addCell(new Phrase(statement.getAdjustmentBalance()));
            statementOfAccounts.addCell(new Phrase(statement.getAdjustment()));
            statementOfAccounts.addCell(new Phrase(statement.getReason()));
        });
        return statementOfAccounts;
    }

    private PdfPTable getSubReportTable(List<String> columnHeaders) {
        try{
            PdfPTable subReportTable = new PdfPTable(columnHeaders.size());
            subReportTable.setWidthPercentage(100);
            subReportTable.setSpacingBefore(10f);
            subReportTable.setSpacingAfter(10f);
            subReportTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            subReportTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

            int[] widths = new int[columnHeaders.size()];
            for (int x = 0;x<columnHeaders.size();x++){
                widths[x] = columnHeaders.get(x).length();
            }
            subReportTable.setWidths(widths);

            columnHeaders.forEach(header -> {
                subReportTable.addCell(new Phrase(header));
            });

            return subReportTable;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    private PdfPTable setAdjustmentReportDetails() {
        PdfPTable adjustmentDetails = new PdfPTable(2);
        adjustmentDetails.setSpacingBefore(10f);
        adjustmentDetails.setSpacingAfter(10f);
        adjustmentDetails.getDefaultCell().setBorderWidth(0f);
        adjustmentDetails.setWidthPercentage(100);
        for (Map.Entry<String,String> entry : adjustmentInfo.entrySet()){
            adjustmentDetails.addCell(new Phrase(entry.getKey()+entry.getValue()));
        }
        return adjustmentDetails;
    }

    private Paragraph getHeader(){
        try{
            Paragraph header = new Paragraph();
            header.setAlignment(Element.ALIGN_CENTER);
            header.add(new Paragraph());

            Image image = Image.getInstance("logo.jpg");
            image.scaleAbsolute(100f,80f);
            image.setAbsolutePosition(15,700);
            header.add(image);

            Paragraph runTimeDate = new Paragraph("Run Date : "+"MM/dd/yyyy",new Font(Font.FontFamily.HELVETICA,8));
            runTimeDate.setAlignment(Element.ALIGN_RIGHT);
            header.add(runTimeDate);

            Paragraph modifiedDate = new Paragraph("Run Time: "+"MM/dd/yyyy",new Font(Font.FontFamily.HELVETICA,8));
            modifiedDate.setAlignment(Element.ALIGN_RIGHT);
            header.add(modifiedDate);

            Paragraph companyName = new Paragraph("PATHLAND WATER SERVICES\nPathland Address\nPathland Tin Number\n Pathland Contact Number");
            companyName.setAlignment(Element.ALIGN_CENTER);
            header.add(companyName);
            header.add(new Paragraph());
            header.add(new Paragraph());
            Paragraph page = new Paragraph("Page N of "+writer.getPageNumber(),new Font(Font.FontFamily.HELVETICA,8));
            page.setAlignment(Element.ALIGN_RIGHT);
            header.add(page);
            Paragraph pageTitle1 = new Paragraph("BILL ADJUSTMENT MEMO");
            pageTitle1.setAlignment(Element.ALIGN_CENTER);
            header.add(pageTitle1);
            return header;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    private void initializeData() {
        adjustmentInfo = new LinkedHashMap<>();
        adjustmentInfo.put("Bill Adjustment Date: ","MM/DD/YYYY");
        adjustmentInfo.put("Adjustment Memo No. :","MM/DD/YYYY");
        adjustmentInfo.put("\nBILL ADJUSTMENT REFERENCE","");
        adjustmentInfo.put("","");
        adjustmentInfo.put("Billing Type : ","Water Bill");
        adjustmentInfo.put("Billing Date : ","MM/DD/YYYY");
        adjustmentInfo.put("Billing No. : ", "00-0000");
        adjustmentInfo.put("Billing Period : ", "Jan. 1, 2019 - Feb. 1, 2019");
        adjustmentInfo.put("Target Month : ", "JANUARY 2019");
        adjustmentInfo.put("Due Date : ", "MM/DD/YYYY");
        adjustmentInfo.put("Account No. : ", "00-000-000");
        adjustmentInfo.put("Concessionaire : ", "Test User");
        adjustmentInfo.put("Address : ", "Iloilo City");
        adjustmentInfo.put("Classification : ", "15mm");

        statementOfAccountsColumnHeader = new ArrayList<>();
        statementOfAccountsColumnHeader.add("Particular");
        statementOfAccountsColumnHeader.add("Account Summary");
        statementOfAccountsColumnHeader.add("Paid");
        statementOfAccountsColumnHeader.add("Balance");
        statementOfAccountsColumnHeader.add("Adjustment Balance");
        statementOfAccountsColumnHeader.add("Adjustment");
        statementOfAccountsColumnHeader.add("  Reason  ");

        meterReadingColumnHeaders = new ArrayList<>();
        meterReadingColumnHeaders.add("Current");
        meterReadingColumnHeaders.add("Previous");
        meterReadingColumnHeaders.add("Consumed");
        meterReadingColumnHeaders.add("Amount");

        statementList = new ArrayList<>();
        for(int x = 0;x<3;x++){
            StatementOfAccountDto statement = new StatementOfAccountDto();
            statement.setParticular("Water Sales");
            statement.setAccountSummary("Summary");
            statement.setPaid("0.00");
            statement.setBalance("0.00");
            statement.setAdjustmentBalance("0.00");
            statement.setAdjustment("0.00");
            statement.setReason("Nothing");
            statementList.add(statement);
        }

        meterReadingDtoList = new ArrayList<>();
        for(int x =0;x<2;x++){
            MeterReadingDto dto = new MeterReadingDto();
            dto.setCurrent("0.0");
            dto.setAmount("0.0");
            dto.setConsumed("0.0");
            dto.setPrevious("0.0");
            meterReadingDtoList.add(dto);
        }
    }


    @Data
    public class StatementOfAccountDto {
        private String particular;
        private String accountSummary;
        private String paid;
        private String balance;
        private String adjustmentBalance;
        private String adjustment;
        private String reason;
    }

    @Data
    public class MeterReadingDto{
        private String current;
        private String Previous;
        private String Consumed;
        private String Amount;
    }
}
