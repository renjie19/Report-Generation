package com.tst;

public class Application {
    public static void main(String[]args){
//        new AccountLedgerReport_OtherAccountsReceivable().start();
//        new AccountReport().start();
//        new AccountsReceivableReport().start();
        new AdjustmentMemo_ForOAR().createBillAdjustmentMemoWithTwoSubreport(true);
//        new PdfGenerator().start();
//        new YTDConsumptionReport().start();
   }

}
