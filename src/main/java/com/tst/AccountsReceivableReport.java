package com.tst;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.Data;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class AccountsReceivableReport {

    private PdfWriter writer;
    private List<String> tableColumnHeaders;
    private List<AccountsReceivableReportDto> dataList;
    double[] totals = new double[4];

    public void start(){
        initializeData();
        try{
            Document document = new Document(PageSize.A4.rotate());
            writer = PdfWriter.getInstance(document, new FileOutputStream("reports/AccountsReceivableReport.pdf"));
            document.open();
            document.add(getHeader());
            PdfPTable mainTable = getDefaultTableWithColumnHeaders();
            dataList = new ArrayList<>();
            dataList = getListData();

            dataList.forEach(dto -> {
                mainTable.addCell(new Phrase(dto.getAccountNumber()));
                mainTable.addCell(new Phrase(dto.getConcessionaire()));
                mainTable.addCell(new Phrase(dto.getParticular()));
                mainTable.addCell(new Phrase(dto.getDate()));
                mainTable.addCell(new Phrase(dto.getDateBilled()));
                mainTable.addCell(new Phrase(dto.getDueDate()));
                mainTable.addCell(new Phrase(dto.getAmount()));
                mainTable.addCell(new Phrase(dto.getAmountPaid()));
                mainTable.addCell(new Phrase(dto.getAdjustment()));
                mainTable.addCell(new Phrase(dto.getBalance()));
                updateTotalValues(dto);
            });
            for(int x=0;x<5;x++){
                mainTable.addCell(new Phrase(""));
            }
            mainTable.addCell(new Phrase("Total: "));
            mainTable.addCell(new Phrase(String.valueOf(totals[0])));
            mainTable.addCell(new Phrase(String.valueOf(totals[1])));
            mainTable.addCell(new Phrase(String.valueOf(totals[2])));
            mainTable.addCell(new Phrase(String.valueOf(totals[3])));
            document.add(mainTable);
            document.close();
            writer.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void updateTotalValues(AccountsReceivableReportDto dto) {
        totals[0] += Double.parseDouble(dto.getAmount());
        totals[1] += Double.parseDouble(dto.getAmountPaid());
        totals[2] += Double.parseDouble(dto.getAdjustment());
        totals[3] += Double.parseDouble(dto.getBalance());
    }

    private void initializeData() {
        tableColumnHeaders = new ArrayList<>();
        tableColumnHeaders.add("Account No.");
        tableColumnHeaders.add("Concessionaire");
        tableColumnHeaders.add("Particular");
        tableColumnHeaders.add("Date");
        tableColumnHeaders.add("Date Billed");
        tableColumnHeaders.add("Due Date");
        tableColumnHeaders.add("Amount");
        tableColumnHeaders.add("Amount Paid");
        tableColumnHeaders.add("Adjustment");
        tableColumnHeaders.add("Balance");
    }

    private List<AccountsReceivableReportDto> getListData(){
        List<AccountsReceivableReportDto> list = new ArrayList<>();
        for(int x=0;x<15;x++){
            AccountsReceivableReportDto dto = new AccountsReceivableReportDto();
            dto.setAccountNumber(x+"-0-"+x);
            dto.setConcessionaire("user "+x);
            dto.setParticular("Particular Type-"+x);
            dto.setDate("DD/MM/YYYY");
            dto.setDateBilled("DD/MM/YYYY");
            dto.setDueDate("DD/MM/YYYY");
            dto.setAmount(String.valueOf(x));
            dto.setAmountPaid(String.valueOf(x));
            dto.setAdjustment(String.valueOf(x));
            dto.setBalance(String.valueOf(x+x+x));
            list.add(dto);
        }
        return list;
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
            Paragraph pageTitle1 = new Paragraph("LIST OF OTHER ACCOUNTS RECEIVABLE");
            pageTitle1.setAlignment(Element.ALIGN_CENTER);
            header.add(pageTitle1);
            return header;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Data
    private class AccountsReceivableReportDto{
        private String accountNumber;
        private String concessionaire;
        private String particular;
        private String date;
        private String dateBilled;
        private String dueDate;
        private String amount;
        private String amountPaid;
        private String adjustment;
        private String balance;
    }
}
