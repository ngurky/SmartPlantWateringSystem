package MinorMain;

public class Values {
    static final int A0 = 14; // Potentiometer
    static final int A1 = 15; // Moisture Sensor
    static final int A2 = 16; // Sound
    static final int D6 = 6; // Button
    static final int D7 = 7; //MOSFET
    static final int D4 = 4; // red LED
    static final int D13 = 13; // default LED on arduino
    static final byte I2C0 = 0x3C; // OLED Display
    static final int drySoilUpperBound = 748;
    static final int drySoilLowerBound = 724;
    static final int moisturizedSoilUpper = 701;
    static final int moisturizedSoilLower = 676;

    private Values() {
    }
}