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
import com.pi4j.io.gpio.RaspiPin;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;
import org.mandfer.categories.FastTest;
import org.mandfer.categories.SlowTest;
import static org.mandfer.sunfunpi4j.Ex03_8Led.NUMOFLEDS;
import org.mockito.InOrder;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;

/**
 *
 * @author marcandreuf
 */
public class Ex03_8LedTest extends BaseSketchTest {
    private List<GpioPinDigitalOutput> mocked_gpioPinDigOutputs;
    private Ex03_8Led sketch;
    
    
    @Before
    public void setUp(){
        sketch = new Ex03_8Led(mocked_gpioController);
        mocked_gpioPinDigOutputs = setUpLedStrip();
    }    

    
    @Test
    @Category(FastTest.class)
    public void createListOfNPinOutputMode() throws Exception{
       
       sketch.createListOfPinOutputs(NUMOFLEDS);
       
       verify(mocked_gpioController).provisionDigitalOutputPin(RaspiPin.GPIO_00);
       verify(mocked_gpioController).provisionDigitalOutputPin(RaspiPin.GPIO_01);
       verify(mocked_gpioController).provisionDigitalOutputPin(RaspiPin.GPIO_02);
       verify(mocked_gpioController).provisionDigitalOutputPin(RaspiPin.GPIO_03);
       verify(mocked_gpioController).provisionDigitalOutputPin(RaspiPin.GPIO_04);
       verify(mocked_gpioController).provisionDigitalOutputPin(RaspiPin.GPIO_05);
       verify(mocked_gpioController).provisionDigitalOutputPin(RaspiPin.GPIO_06);
       verify(mocked_gpioController).provisionDigitalOutputPin(RaspiPin.GPIO_07);
    }
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    @Test
    @Category(FastTest.class)
    public void failIfNumOfPinsIsNotGraterThan1() throws Exception{        
        expectedException.expect(NumberFormatException.class);        
        sketch.createListOfPinOutputs(0);        
    }
    
    @Test
    @Category(FastTest.class)
    public void failIfNumOfPinsIsNegative() throws Exception{        
        expectedException.expect(NumberFormatException.class);        
        sketch.createListOfPinOutputs(-1);        
    }
    
    @Test
    @Category(FastTest.class)
    public void failIfNumOfPinsIsGraterThan20() throws Exception{        
        expectedException.expect(NumberFormatException.class);        
        sketch.createListOfPinOutputs(21);        
    }
    
    
    @Test
    @Category(SlowTest.class)
    public void testLedsOnFromLeftToRight_And_OffFromRightToLeft() throws InterruptedException{
        sketch.setup();
        sketch.setSketchInterruption();
        sketch.loop();
        
        InOrder inOrder;        
        for(GpioPinDigitalOutput led : mocked_gpioPinDigOutputs){
            inOrder = Mockito.inOrder(led);            
            inOrder.verify(led).low();
            inOrder.verify(led).high();
            inOrder.verify(led).low();
            inOrder.verify(led).high();
        }
    }
}
