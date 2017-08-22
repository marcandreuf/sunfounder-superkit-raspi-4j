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

import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.GpioInterrupt;
import com.pi4j.wiringpi.GpioInterruptListener;
import com.pi4j.wiringpi.GpioUtil;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mandfer.categories.SlowTest;
import org.mockito.Matchers;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author marcandreuf
 */
@Category(SlowTest.class)
@RunWith(PowerMockRunner.class)
@PrepareForTest({Gpio.class, GpioInterrupt.class, GpioUtil.class})
public class Ex12_DiceTest extends Common_74HC595Test {    
    
    private GpioPinDigitalInput mocked_pinTouchPin;
    
    @Before
    public void setUp(){
        setUpCommon_74HC595_ScketchTest();
        mocked_pinTouchPin = mock(GpioPinDigitalInput.class);
        PowerMockito.mockStatic(Gpio.class);
        PowerMockito.mockStatic(GpioInterrupt.class);
        PowerMockito.mockStatic(GpioUtil.class);
        sketch = new Ex12_Dice(mocked_gpioController);
    }   
    
    @Test
    public void testSetupGpioPinsAndGpioButtonListener(){        
        when(mocked_gpioController.provisionDigitalInputPin(RaspiPin.GPIO_03)).thenReturn(mocked_pinTouchPin);
        when(mocked_pinTouchPin.getPin()).thenReturn(RaspiPin.GPIO_03);
        
        testGpioPinSetup(); 
        
        int pinNumber = RaspiPin.GPIO_03.getAddress();
        
        PowerMockito.verifyStatic();
        GpioInterrupt.addListener(Matchers.any(GpioInterruptListener.class));
        
        PowerMockito.verifyStatic();
        GpioUtil.export(pinNumber, GpioUtil.DIRECTION_IN);
        
        PowerMockito.verifyStatic();
        GpioUtil.setEdgeDetection(pinNumber, GpioUtil.EDGE_FALLING);
        
        PowerMockito.verifyStatic();
        Gpio.pinMode(pinNumber, Gpio.INPUT);
        
        PowerMockito.verifyStatic();
        Gpio.pullUpDnControl(pinNumber, Gpio.PUD_UP);
        
        PowerMockito.verifyStatic();
        GpioInterrupt.enablePinStateChangeCallback(pinNumber);        
    }
    
}
