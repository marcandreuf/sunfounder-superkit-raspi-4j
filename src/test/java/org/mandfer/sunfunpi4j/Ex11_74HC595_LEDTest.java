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
public class Ex11_74HC595_LEDTest extends Common_74HC595Test {
    
    @Before
    public void setUp(){
        setUpCommon_74HC595_ScketchTest();
        sketch = new Ex11_74HC595_LED(mocked_gpioController);
    }    
    
    @Test
    public void testSetupGpioPins(){
        testGpioPinSetup();        
    }
    
    @Test
    @Ignore
    public void learningTestPrintTestBitwise(){
      
      System.out.println("0x80: "+0x80);
      for (int i = 0; i < 8; i++) {
            System.out.println(0x80 >> i);
        }  
        
    }
}
