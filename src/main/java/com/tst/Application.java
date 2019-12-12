package com.tst;

public class Application {
    public static void main(String[]args){
        new AccountReport().start();
        new AccountLedgerReport().start();
        new PdfGenerator().start();
        new YTDConsumptionReport().start();
   }

}
