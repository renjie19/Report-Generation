package com.tst;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.Data;

import java.io.FileOutputStream;
import java.util.*;
import java.util.List;

public class AccountLedgerReport {
    private PdfWriter writer;
    private List<String> tableColumnHeaders;
    private Map<String,String> concessionaireInfo;

    public void start(){
        initializeData();

        try{
            Document document = new Document();
            writer = PdfWriter.getInstance(document, new FileOutputStream("AccountLedgerReport.pdf"));
            document.open();
            document.add(getHeader());
            document.add(getConcessionaireInfoTable());
            PdfPTable mainTable = getDefaultTableWithColumnHeaders();
            getDataList().forEach(accountLedgerDto -> {
                mainTable.addCell(new Phrase(accountLedgerDto.getDate()));
                mainTable.addCell(new Phrase(accountLedgerDto.getParticular()));
                mainTable.addCell(new Phrase(accountLedgerDto.getReading()));
                mainTable.addCell(new Phrase(accountLedgerDto.getUsage()));
                mainTable.addCell(new Phrase(accountLedgerDto.getDueDate()));
                mainTable.addCell(new Phrase(accountLedgerDto.getReference()));
                mainTable.addCell(new Phrase(accountLedgerDto.getDebit()));
                mainTable.addCell(new Phrase(accountLedgerDto.getCredit()));
                mainTable.addCell(new Phrase(accountLedgerDto.getAdvance()));
                mainTable.addCell(new Phrase(accountLedgerDto.getBalance()));
                mainTable.addCell(new Phrase(accountLedgerDto.getUser()));
            });
            document.add(mainTable);
            document.close();
            writer.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private PdfPTable getConcessionaireInfoTable() {
        PdfPTable concessionaireDetails = new PdfPTable(2);
        concessionaireDetails.setWidthPercentage(100);
        concessionaireDetails.getDefaultCell().setBorderWidth(0f);
        concessionaireDetails.setSpacingBefore(20);
        concessionaireDetails.setSpacingAfter(20);

        for(Map.Entry<String,String> entry: concessionaireInfo.entrySet()){
            concessionaireDetails.addCell(new Phrase(entry.getKey()+entry.getValue()));
        }
        return concessionaireDetails;
    }

    private void initializeData() {
        tableColumnHeaders = new ArrayList<>();
        tableColumnHeaders.add("Date ");
        tableColumnHeaders.add("Particular");
        tableColumnHeaders.add("Reading");
        tableColumnHeaders.add("Usage ");
        tableColumnHeaders.add("Due Date");
        tableColumnHeaders.add("Reference");
        tableColumnHeaders.add("Debit");
        tableColumnHeaders.add("Credit");
        tableColumnHeaders.add("Advance ");
        tableColumnHeaders.add("Balance");
        tableColumnHeaders.add("User ");

        concessionaireInfo = new LinkedHashMap<>();
        concessionaireInfo.put("Account No: ", "1234-5678");
        concessionaireInfo.put("Concessionaire: ", "Test Concessionaire");
        concessionaireInfo.put("Address: ", "Philippines");
        concessionaireInfo.put("","");
        concessionaireInfo.put("From Date: ", "MM/DD/YYYY");
        concessionaireInfo.put("To Date: ", "MM/DD/YYYY");
    }

    private PdfPTable getDefaultTableWithColumnHeaders(){
        try {
            PdfPTable table = new PdfPTable(11);
            table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.setSpacingBefore(20f);
            table.setWidthPercentage(100);
            table.setHeaderRows(1);
            int[] columnWidths = new int[tableColumnHeaders.size()];
            for(int x = 0;x<tableColumnHeaders.size();x++){
                columnWidths[x] = tableColumnHeaders.get(x).length();
            }
            table.setWidths(columnWidths);
            tableColumnHeaders.forEach(header -> {
                table.addCell(new Phrase(header));
            });
            return table;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<AccountLedgerDto> getDataList(){
        List<AccountLedgerDto> list = new ArrayList<>();
        for(int x=0;x<=20;x++){
            AccountLedgerDto accountLedgerDto = new AccountLedgerDto();
            accountLedgerDto.setDate("MM/DD/YYYY");
            accountLedgerDto.setParticular("Particular");
            accountLedgerDto.setReading(x*10+"");
            accountLedgerDto.setUsage(x+".00");
            accountLedgerDto.setDueDate("MM/dd/YYYY");
            accountLedgerDto.setReference("00000000");
            accountLedgerDto.setDebit("0.00");
            accountLedgerDto.setCredit("0.00");
            accountLedgerDto.setAdvance("0.00");
            accountLedgerDto.setBalance("0.00");
            accountLedgerDto.setUser("admin");
            list.add(accountLedgerDto);
        }
        return list;
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
            Paragraph pageTitle1 = new Paragraph("Account Ledger\nWater MeterBilling / Other Accounts Receivable");
            pageTitle1.setAlignment(Element.ALIGN_CENTER);
            header.add(pageTitle1);
            return header;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Data
    private class AccountLedgerDto{
        private String date;
        private String particular;
        private String reading;
        private String Usage;
        private String dueDate;
        private String reference;
        private String debit;
        private String credit;
        private String advance;
        private String balance;
        private String user;
    }
}
