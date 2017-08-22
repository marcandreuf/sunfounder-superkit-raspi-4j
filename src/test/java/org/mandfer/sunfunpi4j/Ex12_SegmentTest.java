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

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mandfer.categories.SlowTest;

/**
 * @author marcandreuf
 */
@Category(SlowTest.class)
public class Ex12_SegmentTest extends Common_74HC595Test {
        
    @Before
    public void setUp(){
        setUpCommon_74HC595_ScketchTest();
        sketch = new Ex12_Segment(mocked_gpioController);
    }   
    
    @Test
    public void testSetupGpioPinsAndGpioButtonListener(){
        testGpioPinSetup();
    }    
    
    @Test
    @Ignore
    public void learningTestSegmentCalculations(){
        char[] segCode = {0x3f,0x06,0x5b,0x4f,0x66,0x6d,0x7d,0x07,0x7f,0x6f,0x77,0x7c,0x39,0x5e,0x79,0x71,0x80};
        short result;
        calcSDI((short)segCode[0]);
        System.out.println("      ");
        calcSDI((short)segCode[1]);
        System.out.println("      ");
        calcSDI((short)segCode[2]);
        System.out.println("      ");
        
    }

    protected void calcSDI(short dat) {
        short result;
        for(short i=0; i<8 ;i++){
            result = (short)((short)0x80 & (dat << i));
            System.out.println("SDI for "+dat+" on "+i+" = "+ result);
        }
    }
}
