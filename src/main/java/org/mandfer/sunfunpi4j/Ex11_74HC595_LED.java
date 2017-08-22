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
import java.util.ArrayList;
import java.util.List;

/**
 * @author marcandreuf
 */
public class Ex11_74HC595_LED extends Common_74HC595 {
    
    private final List<Led> leds;

    private class Led {
        short byteValue;
        public Led(short value) {
            this.byteValue = value;
        }
        public short getValue() {
            return byteValue;
        }
    }

    public Ex11_74HC595_LED(GpioController gpio) {
        super(gpio);

        leds = new ArrayList<>();
        leds.add(new Led((short) 0x01)); //1
        leds.add(new Led((short) 0x02)); //2
        leds.add(new Led((short) 0x04)); //4
        leds.add(new Led((short) 0x08)); //8
        leds.add(new Led((short) 0x10)); //16
        leds.add(new Led((short) 0x20)); //32
        leds.add(new Led((short) 0x40)); //64
        leds.add(new Led((short) 0x80)); //128
    }

    public static void main(String[] args) throws InterruptedException {
        Ex11_74HC595_LED ex11_74HC595 = new Ex11_74HC595_LED(GpioFactory.getInstance());
        ex11_74HC595.run(args);
    }

    @Override
    protected void setup() {
        init();
    }

    @Override
    protected void loop(String[] args) throws InterruptedException {
        do {
            for (Led led : leds) {
                transferInputByteToShiftReg(led.getValue());
                latchShiftRegToStorageReg();
                delay(150);
            }
            delay(500);

            for (int i=0; i<3; i++) {
                transferInputByteToShiftReg((short) 0xff);
                latchShiftRegToStorageReg();
                delay(100);
                transferInputByteToShiftReg((short) 0x00);
                latchShiftRegToStorageReg();
                delay(100);
            }
            delay(500);

            for (int i=0; i<8; i++) {
                transferInputByteToShiftReg(leds.get(8-i-1).getValue());
                latchShiftRegToStorageReg();
                delay(150);
            }
            delay(500);

            for (int i=0; i<3; i++) {
                transferInputByteToShiftReg((short) 0xff);
                latchShiftRegToStorageReg();
                delay(100);
                transferInputByteToShiftReg((short) 0x00);
                latchShiftRegToStorageReg();
                delay(100);
            }
            delay(500);

        } while (isNotInterrupted);
    }
}
