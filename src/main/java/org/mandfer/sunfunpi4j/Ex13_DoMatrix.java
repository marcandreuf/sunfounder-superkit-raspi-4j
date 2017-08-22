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

/**
 * @author marcandreuf
 */
public class Ex13_DoMatrix extends Common_74HC595 {
    
    // First pattern
    //private final short[] cathode_column = {0x00,0x7f,0x00,0xfe,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0xfe,0xfd,0xfb,0xf7,0xef,0xdf,0xbf,0x7f};
    //private final short[] anode_row =      {0x01,0xff,0x80,0xff,0x01,0x02,0x04,0x08,0x10,0x20,0x40,0x80,0xff,0xff,0xff,0xff,0xff,0xff,0xff,0xff};
    
    // Second pattern
    private final short[] cathode_column = {0x00,0x00,0x3c,0x42,0x42,0x3c,0x00,0x00};
    private final short[] anode_row = {0xff,0xe7,0xdb,0xdb,0xdb,0xdb,0xe7,0xff};
    
    // Third pattern
    //private final short[] cathode_column = {0xff,0xff,0xc3,0xbd,0xbd,0xc3,0xff,0xff};
    //private final short[] anode_row = {0x00,0x18,0x24,0x24,0x24,0x24,0x18,0x00};
    
    public Ex13_DoMatrix(GpioController gpio) {
        super(gpio);
    }

    public static void main(String[] args) throws InterruptedException {
        Ex13_DoMatrix ex13_doMatrix = new Ex13_DoMatrix(GpioFactory.getInstance());
        ex13_doMatrix.run(args);
    }

    @Override
    protected void setup() {
        init();
        logger.debug("Matrix is ready");
    }

    @Override
    protected void loop(String[] args) throws InterruptedException {
        do {
            for(int i=0; i<anode_row.length; i++){
                transferInputByteToShiftReg(cathode_column[i]);
                transferInputByteToShiftReg(anode_row[i]);
                latchShiftRegToStorageReg();
                delay(100);
            }
            
            // Fixed with -1
            for(int j=anode_row.length-1; j>=0; j--){
                transferInputByteToShiftReg(cathode_column[j]);
                transferInputByteToShiftReg(anode_row[j]);
                latchShiftRegToStorageReg();
                delay(100);
            }
        } while (isNotInterrupted);
    }
}
