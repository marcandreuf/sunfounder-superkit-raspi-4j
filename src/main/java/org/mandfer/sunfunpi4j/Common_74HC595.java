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
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

/**
 * @author marcandreuf
 */
public abstract class Common_74HC595 extends BaseSketch {

    protected GpioPinDigitalOutput pinSerailDataInput_DS;
    protected GpioPinDigitalOutput pinStorageClock_STCP;
    protected GpioPinDigitalOutput pinShiftRegClock_SHCP;
    
    
    public Common_74HC595(GpioController gpio) {
        super(gpio);
    }
    
    protected void init(){
        pinSerailDataInput_DS = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00);
        pinStorageClock_STCP = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01);
        pinShiftRegClock_SHCP = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02);
        pinSerailDataInput_DS.setState(PinState.LOW);
        pinStorageClock_STCP.setState(PinState.LOW);
        pinShiftRegClock_SHCP.setState(PinState.LOW);
    }

    protected void latchShiftRegToStorageReg() {
        pulse(pinStorageClock_STCP);
    }

    protected void pulse(GpioPinDigitalOutput pin) {
        pin.setState(PinState.LOW);
        pin.setState(PinState.HIGH);
    }
    
    protected void transferInputByteToShiftReg(short byteValue) {
        short readingBitPosition;
        for (short bitPosition=0; bitPosition<8; bitPosition++) {
            readingBitPosition = (short) (0x80 >> bitPosition);
            pinSerailDataInput_DS.setState( (byteValue & readingBitPosition) > 0 );
            pulse(pinShiftRegClock_SHCP);
        }
    }
    
    /**
     * http://www.arduino.cc/en/Tutorial/ShftOut21
     * @param dataOut 
     */
    protected void shiftOut(short dataOut){
       // This shifts 8 bits out MSB first, on the rising edge of the clock, clock idles low.
       
       // Clear everything out just in case to prepare shift register for bit shifting.
       pinSerailDataInput_DS.setState(PinState.LOW);
       pinShiftRegClock_SHCP.setState(PinState.LOW);
       
       //For each bit in the byte myDataOut,NOTICE THAT WE ARE COUNTING DOWN in our for loop
       //This means that %00000001 or "1" will go through such that it will be pin Q0 that lights. 
       for(short bitMask=7; bitMask>=0; bitMask--){
           pinShiftRegClock_SHCP.setState(PinState.LOW);
           
           //if the value passed to myDataOut and a bitmask result true
           //then... so if we are at i=6 and our value is %11010100 it would the code 
           //compares it to %01000000 and proceeds to set pinState to 1.
           pinSerailDataInput_DS.setState( (dataOut & (1 << bitMask)) > 0 );
           pinShiftRegClock_SHCP.setState(PinState.HIGH);
           // Zero the data pin after shift to prevent bleed through
           pinSerailDataInput_DS.setState(PinState.LOW);
       }
       
       // Stop shifting
       pinShiftRegClock_SHCP.setState(PinState.LOW);
    }
    
    
}
