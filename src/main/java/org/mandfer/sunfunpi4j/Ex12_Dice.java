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
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.GpioInterrupt;
import com.pi4j.wiringpi.GpioInterruptEvent;
import com.pi4j.wiringpi.GpioInterruptListener;
import com.pi4j.wiringpi.GpioUtil;
import java.util.concurrent.ThreadLocalRandom;
import static org.mandfer.sunfunpi4j.BaseSketch.logger;

/**
 * @author marcandreuf
 */
public class Ex12_Dice extends Common_74HC595 {
    private static GpioPinDigitalInput pinTouchPin;
    private static boolean isBtnPressed = false;
    private final short[] segCode = {0x06, 0x5b, 0x4f, 0x66, 0x6d, 0x7d};

    public Ex12_Dice(GpioController gpio) {
        super(gpio);
    }

    public static void main(String[] args) throws InterruptedException {
        Ex12_Dice ex12_dice = new Ex12_Dice(GpioFactory.getInstance());
        ex12_dice.run(args);
    }

    @Override
    protected void setup() {
        init();
        pinTouchPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_03);
        Ex12_Dice.buttonIsrSetup(pinTouchPin.getPin().getAddress());
        logger.debug("Dice is ready.");
    }

    protected static final void buttonIsrSetup(final int pinNumber) {        
        GpioInterrupt.addListener(new GpioInterruptListener() {
            @Override
            public void pinStateChange(GpioInterruptEvent event) {
                if (event.getPin() == pinNumber) {
                    logger.debug("Button pressed.");
                    isBtnPressed = true;
                }
            }
        });
        GpioUtil.export(pinNumber, GpioUtil.DIRECTION_IN);
        GpioUtil.setEdgeDetection(pinNumber, GpioUtil.EDGE_FALLING);
        Gpio.pinMode(pinNumber, Gpio.INPUT);
        Gpio.pullUpDnControl(pinNumber, Gpio.PUD_UP);
        GpioInterrupt.enablePinStateChangeCallback(pinNumber);
    }

    @Override
    protected void loop(String[] args) throws InterruptedException {
        do {
            if (isBtnPressed) {
                getDiceNumber();
                isBtnPressed = false;
            }
            rollingDice();
        } while (isNotInterrupted);
    }

    protected void getDiceNumber() {
        int num = ThreadLocalRandom.current().nextInt(6);
        transferInputByteToShiftReg(segCode[num]);
        latchShiftRegToStorageReg();
        delay(2000);
    }

    protected void rollingDice() {
        int num;
        for (int i=0; i<6; i++) {
            num = ThreadLocalRandom.current().nextInt(6);
            transferInputByteToShiftReg(segCode[num]);
            latchShiftRegToStorageReg();
            delay(60);
        }
    }

}
