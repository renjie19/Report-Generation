package com.tst;

import lombok.Data;

@Data
public class ConcessionaireDto {
    private String accountNumber;
    private String concessionaire;
    private int zone;
    private String location;
    private String classification;
    private String meterSize;
    private String meterNumber;
    private String dateChange;
}
