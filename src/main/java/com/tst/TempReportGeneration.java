package com.tst;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TempReportGeneration<T> {
    private List<String> tableHeaders;
    private List<T> tableData;
    private Map<String,String> footer;
    private List<String> reportTitle;

    public void addTableColumnHeader(String header){
        if(this.tableHeaders == null){
            this.tableHeaders = new ArrayList<>();
        }
        this.tableHeaders.add(header);
    }

    public void  setTableData(List<T> data){
        this.tableData = data;
    }

    public void setFooters(Map<String,String> footer){
        this.footer = footer;
    }

    public void addReportTitle(String reportTitle){
        if(this.reportTitle == null){
            this.reportTitle = new ArrayList<>();
        }
        this.reportTitle.add(reportTitle);
    }

    public void generateReport(){
        
    }

}
