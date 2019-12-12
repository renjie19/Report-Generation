package com.tst;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import lombok.Data;

import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PdfGenerator extends PdfPageEventHelper{
    private static Font timeFont = new Font(Font.FontFamily.HELVETICA,7);
    private static Font headerFont = new Font(Font.FontFamily.HELVETICA,10,Font.BOLD);
    private static Font content = new Font(Font.FontFamily.HELVETICA,10);
    private PdfWriter writer;

    private DateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
    private DateFormat dateFormat = new SimpleDateFormat("MM/dd/YYYY");
    private String time = timeFormat.format(new Date());
    private String date = dateFormat.format(new Date());

    public void start(){
        try{
            Document document = new Document();
            writer = PdfWriter.getInstance(document,new FileOutputStream("test.pdf"));
            document.open();

            PdfPTable table = new PdfPTable(8);
            table.setSpacingBefore(20f);
            table.setWidthPercentage(100);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.setHeaderRows(1);
            List<String> headers = new ArrayList<>();
            headers.add("Account No.");
            headers.add("Concessionaire");
            headers.add("Zone ");
            headers.add("Location");
            headers.add("Classification");
            headers.add("Meter Size");
            headers.add("Meter No.");
            headers.add("Date Status Changed");
            int[] f = new int[headers.size()];
            int counter = 0;
            for(String headerTitle : headers){
                f[counter] = headerTitle.length();
                counter++;
            }
            table.setWidths(f);
            for(String headerTitle : headers){
                table.addCell(prepareHeader(headerTitle));
            }
            List<ConcessionaireDto> concessionaireList = new ArrayList<>();
            for(int x =0;x<=60;x++){
                ConcessionaireDto con = new ConcessionaireDto();
                con.setAccountNumber("00-00-0"+x);
                con.setClassification("Household");
                con.setConcessionaire("User "+x);
                con.setDateChange(date);
                con.setLocation("Location "+x);
                con.setMeterNumber(((int)(Math.random()*100))+"");
                con.setMeterSize("15mm");
                con.setZone(1);
                concessionaireList.add(con);
            }
            for(ConcessionaireDto con : concessionaireList){
                table.addCell(new PdfPCell(prepareContent(con.getAccountNumber())));
                table.addCell(new PdfPCell(prepareContent(con.getConcessionaire())));
                table.addCell(new PdfPCell(prepareContent(con.getZone()+"")));
                table.addCell(new PdfPCell(prepareContent(con.getLocation())));
                table.addCell(new PdfPCell(prepareContent(con.getClassification())));
                table.addCell(new PdfPCell(prepareContent(con.getMeterSize())));
                table.addCell(new PdfPCell(prepareContent(con.getMeterNumber())));
                table.addCell(new PdfPCell(prepareContent(con.getDateChange())));
            }
            document.add(getHeader());
            document.add(table);
            document.close();
            writer.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private PdfPCell prepareContent(String header){
        PdfPCell cell = new PdfPCell(new Phrase(header,content));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        return cell;
    }
    private PdfPCell prepareHeader(String header){
        PdfPCell cell = new PdfPCell(new Phrase(header,headerFont));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        return cell;
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
            Paragraph pageTitle1 = new Paragraph("List of New Concessionaires");
            pageTitle1.setAlignment(Element.ALIGN_CENTER);
            header.add(pageTitle1);
            Paragraph pageTitle2 = new Paragraph("For the Month of October 2019 to October 2019");
            pageTitle2.setAlignment(Element.ALIGN_CENTER);
            header.add(pageTitle2);
            return header;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    //Dto class
    @Data
    private class ConcessionaireDto {
        private String accountNumber;
        private String concessionaire;
        private int zone;
        private String location;
        private String classification;
        private String meterSize;
        private String meterNumber;
        private String dateChange;
    }
}
