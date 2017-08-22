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

import com.pi4j.wiringpi.I2C;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mandfer.categories.SlowTest;
import org.mockito.Matchers;
import static org.mockito.Matchers.eq;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author marcandreuf
 */
@Category(SlowTest.class)
@RunWith(PowerMockRunner.class)
@PrepareForTest({I2C.class})
public class Ex15_Adxl345Test extends BaseSketchTest {
    
    Ex15_Adxl345 sketch;
    
    @Before
    public void setUp(){        
        PowerMockito.mockStatic(I2C.class);
        sketch = new Ex15_Adxl345(mocked_gpioController);
    }   
    
    @Test
    public void testSetupGpioPinsAndGpioButtonListener(){
        PowerMockito.when(I2C.wiringPiI2CSetup(Matchers.anyInt())).thenReturn(1);
        
        sketch.setup();
        
        PowerMockito.verifyStatic(Mockito.atLeast(18));
        I2C.wiringPiI2CWriteReg8(eq(1), Matchers.anyShort(), Matchers.anyShort());
    }
    
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    @Test
    public void testFailureWiringPiI2CSetup(){
        PowerMockito.when(I2C.wiringPiI2CSetup(Matchers.anyInt())).thenReturn(-1);       
        expectedException.expectMessage("I2C device setup error");
        expectedException.expect(RuntimeException.class);
        
        sketch.setup();
    }
    
    @Test
    public void learningTestAboutWiringPiReads(){
        
        // WiringPiI2CReadReg8 returns an int.
        int sampleValue = 0x20;
        
        int value = 0xff - sampleValue;
        
        int value2 = 0xff & sampleValue;
        
        logger.debug("Value: "+value);
        logger.debug("Value2: "+value2);
    }
}
