package com.tst;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class PdfFormatTest {

    @Test
    public void generate() {
        PdfFormat<BillAdjustmentMemoReport.AdjustmentMemo> pdf = new BillAdjustmentMemoReport();
        pdf.addTableColumnHeaders("Bill Adjustment\nDate");
        pdf.addTableColumnHeaders("Bill Adjustment\nMemo No.");
        pdf.addTableColumnHeaders("Reference\nBilling No.");
        pdf.addTableColumnHeaders("Account No.");
        pdf.addTableColumnHeaders("Concessionaire");
        pdf.addTableColumnHeaders("Address");
        pdf.addTableColumnHeaders("Adjustment");
        pdf.addTableColumnHeaders("User");
        pdf.setData(generateData());
        pdf.generate();
    }
    private List<BillAdjustmentMemoReport.AdjustmentMemo> generateData(){
        List<BillAdjustmentMemoReport.AdjustmentMemo> list = new ArrayList<>();
        for(int x = 0;x<10;x++){
            BillAdjustmentMemoReport.AdjustmentMemo adjustmentMemo = new BillAdjustmentMemoReport.AdjustmentMemo();
            adjustmentMemo.setBillAdjustmentDate("YYYY-MM-DD");
            adjustmentMemo.setBillAdjustmentMemoNo("1234");
            adjustmentMemo.setAccountNo("4321");
            adjustmentMemo.setAddress("Address");
            adjustmentMemo.setConcessionaire("Test");
            adjustmentMemo.setUser("User");
            adjustmentMemo.setReferenceBillingNo("98765");
            adjustmentMemo.setAdjustment("1");
            list.add(adjustmentMemo);
        }
        return list;
    }
}