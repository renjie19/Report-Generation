package com.tst;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.Data;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class AccountReport {

    private List<String> tableColumnHeaders;
    private PdfWriter writer;

    public void start(){
        initializeData();
        try{
            Document document = new Document();
            writer = PdfWriter.getInstance(document, new FileOutputStream("reports/AccountReport.pdf"));
            document.open();
            document.add(getHeader());
            PdfPTable mainTable = getDefaultTableWithColumnHeaders();
            getDataList().forEach( accountsDto -> {
                mainTable.addCell(new Phrase(accountsDto.getAccountType()));
                mainTable.addCell(new Phrase(accountsDto.getAccountName()));
                mainTable.addCell(new Phrase(accountsDto.getAccountCode()));
                mainTable.addCell(new Phrase(accountsDto.getStatus()));
            });
            document.add(mainTable);
            document.close();
            writer.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private List<AccountsDto> getDataList() {
        List<AccountsDto> list = new ArrayList<>();
        for(int x=0;x<15;x++){
            AccountsDto dto = new AccountsDto();
            dto.setAccountType("Income");
            dto.setAccountName("Water Sales");
            dto.setAccountCode(x+"-"+x);
            dto.setStatus("Active");
            list.add(dto);
        }
        return list;
    }

    private void initializeData() {
        tableColumnHeaders = new ArrayList<>();
        tableColumnHeaders.add("Account Type");
        tableColumnHeaders.add("Account Name");
        tableColumnHeaders.add("Account Code");
        tableColumnHeaders.add("Status");
    }

    private PdfPTable getDefaultTableWithColumnHeaders(){
        try {
            PdfPTable table = new PdfPTable(tableColumnHeaders.size());
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
            Paragraph pageTitle1 = new Paragraph("CHART OF ACCOUNTS");
            pageTitle1.setAlignment(Element.ALIGN_CENTER);
            header.add(pageTitle1);
            return header;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Data
    public class AccountsDto{
        private String accountType;
        private String accountName;
        private String accountCode;
        private String status;
    }
}
