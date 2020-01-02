package com.tst;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.Data;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class BillAdjustmentMemoReport implements PdfFormat {
    private PdfWriter writer;
    private List<String> tableColumnHeaders;
    private List<AdjustmentMemo> adjustmentList;

    @Override
    public void addTableColumnHeaders(String header) {
        if(tableColumnHeaders == null) {
            this.tableColumnHeaders = new ArrayList<>();
        }
        tableColumnHeaders.add(header);
    }

    @Override
    public void setData(List adjustmentList) {
        this.adjustmentList = adjustmentList;
    }

    @Override
    public void generate() {

        try{
            Document document = new Document();
            writer = PdfWriter.getInstance(document, new FileOutputStream("reports/Bill Adjustment Memo.pdf"));
            document.open();
            document.setPageSize(PageSize.A4.rotate());
            document.add(getHeader());
            PdfPTable mainTable = getDefaultTable();
            AtomicReference<Double> totalAdjustment = new AtomicReference<>((double) 0);
            adjustmentList.forEach(adjustmentMemo -> {
                mainTable.addCell(new Phrase(adjustmentMemo.getBillAdjustmentDate()));
                mainTable.addCell(new Phrase(adjustmentMemo.getBillAdjustmentMemoNo()));
                mainTable.addCell(new Phrase(adjustmentMemo.getReferenceBillingNo()));
                mainTable.addCell(new Phrase(adjustmentMemo.getAccountNo()));
                mainTable.addCell(new Phrase(adjustmentMemo.getConcessionaire()));
                mainTable.addCell(new Phrase(adjustmentMemo.getAddress()));
                mainTable.addCell(new Phrase(adjustmentMemo.getAdjustment()));
                mainTable.addCell(new Phrase(adjustmentMemo.getUser()));
                totalAdjustment.updateAndGet(v -> (double) (v + Double.parseDouble(adjustmentMemo.getAdjustment())));
            });
            document.add(mainTable);
            document.add(new Paragraph(String.format("Total Adjustment: %s", totalAdjustment)));
            document.close();
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private PdfPTable getDefaultTable() throws DocumentException {
        PdfPTable table = new PdfPTable(tableColumnHeaders.size());
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.setWidthPercentage(100);
        table.setHeaderRows(1);

        int[] widths = new int[tableColumnHeaders.size()];
        for(int x = 0; x<tableColumnHeaders.size();x++){
            widths[x] = tableColumnHeaders.get(x).length();
        }
        table.setWidths(widths);
        tableColumnHeaders.forEach(header -> {
            table.addCell(new Phrase(header));
        });
        return table;
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
            Paragraph pageTitle1 = new Paragraph("BILL ADJUSTMENT MEMO REPORT");
            pageTitle1.setAlignment(Element.ALIGN_CENTER);
            header.add(pageTitle1);
            return header;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Data
    public static class AdjustmentMemo{
        private String billAdjustmentDate;
        private String billAdjustmentMemoNo;
        private String referenceBillingNo;
        private String accountNo;
        private String concessionaire;
        private String address;
        private String adjustment;
        private String user;
    }
}
