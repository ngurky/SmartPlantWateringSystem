package MinorMain;

import org.firmata4j.Pin;
import org.firmata4j.firmata.FirmataDevice;

import java.util.HashMap;
import java.util.Timer;
import edu.princeton.cs.introcs.StdDraw;
import org.firmata4j.ssd1306.SSD1306;

import java.io.IOException;

public class minorMain {
    public static void main(String[] args) throws IOException, InterruptedException {
        var num = 1;
        String port = "COM4";

        //Initialize board
        var board = new FirmataDevice(port);
        board.start();
        System.out.println("Board started.");
        board.ensureInitializationIsDone();

        //Initialize moisture sensor,MOSFET,OLED display
        var moistureSensor = board.getPin(Values.A1);
        var pump = board.getPin(Values.D7);
        pump.setMode(Pin.Mode.OUTPUT);
        var i2cObject = board.getI2CDevice(Values.I2C0);
        SSD1306 oledDisplay = new SSD1306(i2cObject, SSD1306.Size.SSD1306_128_64);
        oledDisplay.init();

        //Initialize and call event listener
        SensorListener sensorListener = new SensorListener(moistureSensor, pump);
        board.addEventListener(sensorListener);

        //Timer task (get moisture value every second and display it)
        var task = new Task(moistureSensor,oledDisplay);
        new Timer().schedule(task,0, 1000);

        //Set up Hashmap
        HashMap<Integer,Integer> data = new HashMap<>();

        //Set up graph
        StdDraw.setXscale(-13,100);
        StdDraw.setYscale(-30,1100);
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.line(0,0,0,1000);
        StdDraw.line(0,0,100,0);
        StdDraw.text(50,-30, "Time [s]");
        StdDraw.text(-9,500, "Moisture");
        StdDraw.text(50,1100,"Moisture vs Time Graph");

        //Graphing
        while (true){
            data.put((int)num, (int) task.getMoistureValue());
            Thread.sleep(1000);
            if (num <50){
                num++; //simply counting seconds
            }

            else{
                break;
            }
        }
        StdDraw.setPenColor(StdDraw.RED);
        data.forEach((xValue, yValue) -> StdDraw.text(xValue,yValue, "*"));
    }
}

