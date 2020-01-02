package com.tst;

import java.util.List;

public interface PdfFormat<T> {
    void generate();
    void addTableColumnHeaders(String header);
    void setData(List<T> list);
}
