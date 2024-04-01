package MinorMain;

import org.firmata4j.IODeviceEventListener;
import org.firmata4j.IOEvent;
import org.firmata4j.Pin;
import java.io.IOException;


public class SensorListener implements IODeviceEventListener {
    private final Pin moistureSensor;
    private final Pin pump;

    //constructor
    SensorListener(Pin moistureSensor, Pin pump) {
        this.moistureSensor = moistureSensor;
        this.pump = pump;

    }

    @Override
    public void onPinChange(IOEvent event) {
        //return if not from button
        if (event.getPin().getIndex() != moistureSensor.getIndex()) {
            return;
        }
        else if (this.moistureSensor.getValue() <= Values.moisturizedSoilUpper){
            System.out.println(this.moistureSensor.getValue());
            try {
                this.pump.setValue(0);;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (this.moistureSensor.getValue() > Values.drySoilLowerBound){
            System.out.println(this.moistureSensor.getValue());
            try {
                this.pump.setValue(1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onStart(IOEvent event) {

    }
    @Override
    public void onStop(IOEvent event) {
    }
    @Override
    public void onMessageReceive(IOEvent event, String message) {
    }
}
