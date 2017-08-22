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

import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mandfer.categories.SlowTest;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author marcandreuf
 */
@Category(SlowTest.class)
public class Common_74HC595Test extends BaseSketchTest {
    
    protected GpioPinDigitalOutput mocked_pinSerailDataInput_DS;
    protected GpioPinDigitalOutput mocked_pinStorageClock_STCP;
    protected GpioPinDigitalOutput mocked_pinShiftRegClock_SHCP;
    protected BaseSketch sketch;

    public void setUpCommon_74HC595_ScketchTest(){
        mocked_pinSerailDataInput_DS = mock(GpioPinDigitalOutput.class);
        mocked_pinShiftRegClock_SHCP = mock(GpioPinDigitalOutput.class);
        mocked_pinStorageClock_STCP = mock(GpioPinDigitalOutput.class);
    } 
    
    public void testGpioPinSetup(){
        when(mocked_gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_00))
          .thenReturn(mocked_pinSerailDataInput_DS);
        when(mocked_gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_01))
          .thenReturn(mocked_pinStorageClock_STCP);
        when(mocked_gpioController.provisionDigitalOutputPin(RaspiPin.GPIO_02))
          .thenReturn(mocked_pinShiftRegClock_SHCP);        
        
        sketch.setup();
        
        verify(mocked_gpioController).provisionDigitalOutputPin(RaspiPin.GPIO_00);
        verify(mocked_gpioController).provisionDigitalOutputPin(RaspiPin.GPIO_01);
        verify(mocked_gpioController).provisionDigitalOutputPin(RaspiPin.GPIO_02);
        verify(mocked_pinSerailDataInput_DS).setState(PinState.LOW);
        verify(mocked_pinStorageClock_STCP).setState(PinState.LOW);
        verify(mocked_pinShiftRegClock_SHCP).setState(PinState.LOW);        
    }
}
