package MinorMain;

import org.firmata4j.IODevice;
import org.firmata4j.Pin;
import org.firmata4j.ssd1306.SSD1306;

import java.io.IOException;
import java.util.TimerTask;

public class Task extends TimerTask {
    private Pin moistureSensor;
    private SSD1306 oledDisplay;
    private long moistureValue;

    public Task(Pin moistureSensor, SSD1306 oledDisplay) {
        this.moistureSensor = moistureSensor;
        this.oledDisplay = oledDisplay;
    }

    public long getMoistureValue() {
        return this.moistureValue;
    }

    @Override
    public void run(){
        this.moistureValue = this.moistureSensor.getValue();
        this.oledDisplay.getCanvas().setTextsize(2);
        this.oledDisplay.getCanvas().clear();
        if (this.moistureValue <= Values.moisturizedSoilUpper){
            this.oledDisplay.getCanvas().drawString(0,0,"Pump Off\nMoist soil");
            this.oledDisplay.display();
        }
        else if (this.moistureValue > Values.drySoilLowerBound){
            this.oledDisplay.getCanvas().drawString(0,0,"Pump On\nDry soil");
            this.oledDisplay.display();
        }
    }
}
