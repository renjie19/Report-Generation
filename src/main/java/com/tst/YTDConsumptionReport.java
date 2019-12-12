package com.tst;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.Data;

import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class YTDConsumptionReport {

    private DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    private DateFormat timeFormat = new SimpleDateFormat("hh:mm aa");

    private String date = dateFormat.format(new Date());
    private String time = timeFormat.format(new Date());

    private Font timeFont = new Font(Font.FontFamily.HELVETICA,8);
    private Font dateFont = new Font(Font.FontFamily.HELVETICA,10);
    private PdfWriter writer;

    private List<String> tableColumnHeaders;



    public void start(){
        Map<String, String> details = new HashMap<>();
        details.put("zone", "1");
        details.put("meter", "15mm");
        details.put("classification", "Residential");
        details.put("from", "January 2019");
        details.put("to", "December 2019");

        tableColumnHeaders = new ArrayList<>();
        tableColumnHeaders.add("Account Number");
        tableColumnHeaders.add("Concessionaire");
        tableColumnHeaders.add("Consumption (cu.m)");
        tableColumnHeaders.add("Bill Amount (Php)");
        tableColumnHeaders.add("Unpaid Balance");

        try{
            Document document = new Document();
            writer = PdfWriter.getInstance(document,new FileOutputStream("YTDConsumptionReport.pdf"));
            document.open();

            PdfPTable detailsTable = new PdfPTable(2);
            detailsTable.getDefaultCell().setBorderWidth(0f);
            detailsTable.setSpacingBefore(20f);
            detailsTable.setWidthPercentage(100);
            detailsTable.addCell(new Phrase("Zone: "+details.get("zone"),dateFont));
            detailsTable.addCell(new Phrase("Meter Size: "+details.get("meter"),dateFont));
            detailsTable.addCell(new Phrase("Classification: "+details.get("classification"),dateFont));
            detailsTable.addCell(new Phrase());
            detailsTable.addCell(new Phrase("From: "+details.get("from"),dateFont));
            detailsTable.addCell(new Phrase("To: "+details.get("to"),dateFont));

            PdfPTable mainTable = getDefaultTableWithColumnHeaders();
            setTableData(mainTable);
            Map<String,String> signatures = getFooter();

            PdfPTable footer = new PdfPTable(3);
            footer.setWidthPercentage(100);
            footer.getDefaultCell().setBorderWidth(0f);
            footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            footer.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            footer.setSpacingBefore(30);
            for(Map.Entry<String,String> entry: signatures.entrySet()){
                footer.addCell(entry.getValue());
            }

            document.add(getHeader());
            document.add(detailsTable);
            document.add(mainTable);
            document.add(footer);
            document.close();
            writer.close();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private List<YearToDateReportDto> getList(){
        List<YearToDateReportDto> list = new ArrayList<>();
        for(int x = 0;x<=10;x++){
            YearToDateReportDto ytd = new YearToDateReportDto();
            ytd.setAccountNumber("00000000"+x);
            ytd.setBillAmount(((int)(Math.random()*10))+"");
            ytd.setConcessionaire("User "+x);
            ytd.setConsumption(((int)(Math.random()*10))+"");
            ytd.setUnpaidBalance(((int)(Math.random()*10))+"");
            list.add(ytd);
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

            Paragraph runTimeDate = new Paragraph("Run Date : "+date,timeFont);
            runTimeDate.setAlignment(Element.ALIGN_RIGHT);
            header.add(runTimeDate);

            Paragraph modifiedDate = new Paragraph("Run Time: "+time,timeFont);
            modifiedDate.setAlignment(Element.ALIGN_RIGHT);
            header.add(modifiedDate);

            Paragraph companyName = new Paragraph("PATHLAND WATER SERVICES\nPathland Address\nPathland Tin Number\n Pathland Contact Number");
            companyName.setAlignment(Element.ALIGN_CENTER);
            header.add(companyName);
            header.add(new Paragraph());
            header.add(new Paragraph());
            Paragraph page = new Paragraph("Page N of "+writer.getPageNumber(),timeFont);
            page.setAlignment(Element.ALIGN_RIGHT);
            header.add(page);
            Paragraph pageTitle1 = new Paragraph("YTD Consumption Report");
            pageTitle1.setAlignment(Element.ALIGN_CENTER);
            header.add(pageTitle1);
            return header;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    private PdfPTable getDefaultTableWithColumnHeaders() throws Exception{
        PdfPTable table = new PdfPTable(tableColumnHeaders.size());
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.setSpacingBefore(40f);
        table.setWidthPercentage(100);
        table.setHeaderRows(1);
        int[] columnWidth = new int[tableColumnHeaders.size()];
        int counter = 0;
        for(String headerTitle : tableColumnHeaders){
            columnWidth[counter] = headerTitle.length();
            counter++;
        }
        table.setWidths(columnWidth);
        for(String header : tableColumnHeaders){
            table.addCell(new Phrase(header));
        }
        return table;
    }

    private void setTableData(PdfPTable table){
        List<YearToDateReportDto> list = getList();
        list.forEach(ytd -> {
            table.addCell(new Phrase(ytd.getAccountNumber()));
            table.addCell(new Phrase(ytd.getConcessionaire()));
            table.addCell(new Phrase(ytd.getConsumption()));
            table.addCell(new Phrase(ytd.getBillAmount()));
            table.addCell(new Phrase(ytd.getUnpaidBalance()));
        });
    }

    private Map<String,String> getFooter(){
        Map<String,String> signatures = new LinkedHashMap<>();
        signatures.put("preparedBy","Prepared By");
        signatures.put("notedBy", "Noted By");
        signatures.put("verifiedBy", "Verified By");
        signatures.put("sig1", "Signature1");
        signatures.put("sig2", "Signature2");
        signatures.put("sig3", "Signature3");
        signatures.put("des1", "Designation1");
        signatures.put("des2", "Designation2");
        signatures.put("des3", "Designation3");
        return signatures;
    }

    //Dto class
    @Data
    private class YearToDateReportDto {
        private String accountNumber;
        private String concessionaire;
        private String consumption;
        private String billAmount;
        private String unpaidBalance;
    }
}
