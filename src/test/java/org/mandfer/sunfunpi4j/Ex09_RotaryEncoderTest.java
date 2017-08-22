/**
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Marc Andreu
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.mandfer.sunfunpi4j;

import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import static org.junit.Assert.assertTrue;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mandfer.categories.SlowTest;
import static org.mockito.Mockito.verify;

/**
 * 
 * @author marcandreuf
 */
@Category(SlowTest.class)
public class Ex09_RotaryEncoderTest extends BaseSketchTest {
    
    
    @Test
    public void testSetupGpioPins(){
        Ex09_RotaryEncoder sketch = new Ex09_RotaryEncoder(mocked_gpioController);
        
        sketch.setup();
        
        verify(mocked_gpioController).provisionDigitalInputPin(RaspiPin.GPIO_00, PinPullResistance.PULL_UP);
        verify(mocked_gpioController).provisionDigitalInputPin(RaspiPin.GPIO_01, PinPullResistance.PULL_UP);
    }

    @Test
    @Ignore
    public void learningTestCalcDelta() {
        assertTrue(Ex09_RotaryEncoder_SMPC.calcDelta(3, 0) == -3); //0
        assertTrue(Ex09_RotaryEncoder_SMPC.calcDelta(0, 5) == 1); //6
        assertTrue(Ex09_RotaryEncoder_SMPC.calcDelta(5, 6) == 1); //12
        assertTrue(Ex09_RotaryEncoder_SMPC.calcDelta(6, 3) == -3); //6
        assertTrue(Ex09_RotaryEncoder_SMPC.calcDelta(0, 6) == 2); //8
        assertTrue(Ex09_RotaryEncoder_SMPC.calcDelta(5, 3) == -2); //6
        assertTrue(Ex09_RotaryEncoder_SMPC.calcDelta(6, 0) == -2); //4
        assertTrue(Ex09_RotaryEncoder_SMPC.calcDelta(3, 5) == 2); //10

        assertTrue(Ex09_RotaryEncoder_SMPC.calcDelta(3, 6) == 3); //12
        assertTrue(Ex09_RotaryEncoder_SMPC.calcDelta(6, 5) == -1); //10
        assertTrue(Ex09_RotaryEncoder_SMPC.calcDelta(5, 0) == -1);
        assertTrue(Ex09_RotaryEncoder_SMPC.calcDelta(0, 3) == 3);
        assertTrue(Ex09_RotaryEncoder_SMPC.calcDelta(6, 0) == -2);
        assertTrue(Ex09_RotaryEncoder_SMPC.calcDelta(3, 5) == 2);
        assertTrue(Ex09_RotaryEncoder_SMPC.calcDelta(0, 6) == 2);
        assertTrue(Ex09_RotaryEncoder_SMPC.calcDelta(5, 3) == -2);
    }

    @Test
    @Ignore
    public void learningTestRotaryEncoderStateTest() {
        assertTrue(Ex09_RotaryEncoder_SMPC.rotaryEncoderState(0, 0) == 0);
        assertTrue(Ex09_RotaryEncoder_SMPC.rotaryEncoderState(1, 0) == 5);
        assertTrue(Ex09_RotaryEncoder_SMPC.rotaryEncoderState(1, 1) == 6);
        assertTrue(Ex09_RotaryEncoder_SMPC.rotaryEncoderState(0, 1) == 3);
    }

    @Test
    @Ignore
    public void learningTestRotarySeqTest() {
        System.out.println("0,0: " + Ex09_RotaryEncoder_SMPC.calcSeq(0, 0));
        System.out.println("1,0: " + Ex09_RotaryEncoder_SMPC.calcSeq(1, 0));
        System.out.println("1,1: " + Ex09_RotaryEncoder_SMPC.calcSeq(1, 1));
        System.out.println("0,1: " + Ex09_RotaryEncoder_SMPC.calcSeq(0, 1));
    }

    @Test
    @Ignore
    public void learningTestEncodedValues() {
        System.out.println("0,0: " + Ex09_RotaryEncoder_SMPC.calcEncoded(0, 0));
        System.out.println("1,0: " + Ex09_RotaryEncoder_SMPC.calcEncoded(1, 0));
        System.out.println("1,1: " + Ex09_RotaryEncoder_SMPC.calcEncoded(1, 1));
        System.out.println("0,1: " + Ex09_RotaryEncoder_SMPC.calcEncoded(0, 1));
    }    
    
    @Test
    @Ignore
    public void learningTestPinStateValues() {
        System.out.println("0,1: " + Ex09_RotaryEncoder_SMPC.calcPinState(0, 1));
        System.out.println("0,0: " + Ex09_RotaryEncoder_SMPC.calcPinState(0, 0));
        System.out.println("1,0: " + Ex09_RotaryEncoder_SMPC.calcPinState(1, 0));
        System.out.println("1,1: " + Ex09_RotaryEncoder_SMPC.calcPinState(1, 1));
    }
    
    @Test
    @Ignore
    public void learningTestPinStateRevValues() {
        System.out.println("0,1: " + Ex09_RotaryEncoder_SMPC.calcPinStateRev(0, 1));
        System.out.println("0,0: " + Ex09_RotaryEncoder_SMPC.calcPinStateRev(0, 0));
        System.out.println("1,0: " + Ex09_RotaryEncoder_SMPC.calcPinStateRev(1, 0));
        System.out.println("1,1: " + Ex09_RotaryEncoder_SMPC.calcPinStateRev(1, 1));
    }
}
