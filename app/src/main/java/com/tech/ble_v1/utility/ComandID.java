package com.tech.ble_v1.utility;

public class ComandID {
    private int BREAKFAST = 1;
    private int LUNCH = 2;
    private int COFFE = 3;
    private int DINNER = 4;
    private int SUPPLY = 5;



    public byte[] byte_ManufacturerData(String rfid){

        int len = rfid.length();
        if (len<6){
            rfid = "0"+rfid;
            len = rfid.length ();
        }
        byte[] data = new byte[len/2];

        for (int i = 0; i < len; i+=2) {
            data[i / 2] = (byte) ((Character.digit(rfid.charAt(i), 16) << 4) + Character.digit(rfid.charAt(i+1), 16));
        }
        return data;
    }
}
