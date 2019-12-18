package com.tst.test2;

import java.util.List;

/**
 * @author Michael Ryan A. Paclibar <michael@satellite.com.ph>
 */
@FunctionalInterface
public interface QueryReportData<T> {

    List<T> query();

}
