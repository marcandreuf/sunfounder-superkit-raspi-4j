/**
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Marc Andreu
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */
package org.mandfer.sunfunpi4j;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.wiringpi.Lcd;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Sample from 
 * https://github.com/Pi4J/pi4j/blob/master/pi4j-example/src/main/java/WiringPiLcdExample.java
 * 
 * @author marcandreuf
 */
public class Ex14_Lcd1602 extends BaseSketch {

    public final static int LCD_ROWS = 2;
    public final static int LCD_COLUMNS = 16;
    public final static int LCD_BITS = 4;
    
    private int lcdHandle;
    private SimpleDateFormat formatter;
    
    public Ex14_Lcd1602(GpioController gpio) {
        super(gpio);
    }

    public static void main(String[] args) throws InterruptedException {
        Ex14_Lcd1602 ex14_Lcd1602 = new Ex14_Lcd1602(GpioFactory.getInstance());
        ex14_Lcd1602.run(args);
    }
    
    @Override
    protected void setup() {
        lcdHandle= Lcd.lcdInit(
            LCD_ROWS,     // number of row supported by LCD
            LCD_COLUMNS,  // number of columns supported by LCD
            LCD_BITS,     // number of bits used to communicate to LCD 
            15,           // LCD RS pin
            16,           // LCD strobe pin
            0,            // LCD data bit 1
            1,            // LCD data bit 2
            2,            // LCD data bit 3
            3,            // LCD data bit 4
            0,            // LCD data bit 5 (set to 0 if using 4 bit communication)
            0,            // LCD data bit 6 (set to 0 if using 4 bit communication)
            0,            // LCD data bit 7 (set to 0 if using 4 bit communication)
            0);           // LCD data bit 8 (set to 0 if using 4 bit communication)

        // verify initialization
        if (lcdHandle == -1) {
            String msg = " ==>> LCD INIT FAILED";
            logger.error(msg);
            throw new RuntimeException(msg);
        }
        // clear LCD
        Lcd.lcdClear(lcdHandle);
        delay(1000);
        
        // write line 1 to LCD
        Lcd.lcdHome(lcdHandle);
        
        //Lcd.lcdPosition (lcdHandle, 0, 0) ; 
        Lcd.lcdPuts (lcdHandle, "The EX14 LCD1602") ;
        
        // write line 2 to LCD        
        Lcd.lcdPosition (lcdHandle, 0, 1) ; 
        Lcd.lcdPuts (lcdHandle, "----------------") ;
        
        logger.debug("LCD ready.");
    }

    @Override
    protected void loop(String[] args) throws InterruptedException {
        formatter = new SimpleDateFormat("HH:mm:ss");
        do{
            // write time to line 2 on LCD            
            Lcd.lcdPosition (lcdHandle, 0, 1) ; 
            Lcd.lcdPuts (lcdHandle, "--- " + formatter.format(new Date()) + " ---");
            delay(1000);
        } while (isNotInterrupted);
        Lcd.lcdClear(lcdHandle);        
    }  
}
