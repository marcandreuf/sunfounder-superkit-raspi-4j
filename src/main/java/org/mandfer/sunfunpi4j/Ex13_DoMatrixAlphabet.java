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
 * 
 * Based on this work. http://arduino.vn/bai-viet/256-hien-thi-hinh-anh-voi-led-matrix-8x8
 * 
 * TODO: Implement LedMatrix display library to write scrolling text directly to a matrix
 * of size 8x8. Potentially expandable to other matrix sizes.  
 * 
 * @author marcandreuf
 */
public class Ex13_DoMatrixAlphabet extends Common_74HC595 {
    
    short[] row = {0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80};
    short[] column= {0x80, 0x40, 0x20, 0x10, 0x08, 0x04, 0x02, 0x01};
    
    short[][] characterHEX = {
    {0x18,0x3C,0x66,0x66,0x7E,0x66,0x66,0x66},//A
    {0x78,0x64,0x68,0x78,0x64,0x66,0x66,0x7C},//B
    {0x3C,0x62,0x60,0x60,0x60,0x62,0x62,0x3C},//C
    {0x78,0x64,0x66,0x66,0x66,0x66,0x64,0x78},//D
    {0x7E,0x60,0x60,0x7C,0x60,0x60,0x60,0x7E},//E
    {0x7E,0x60,0x60,0x7C,0x60,0x60,0x60,0x60},//F
    {0x3C,0x62,0x60,0x60,0x66,0x62,0x62,0x3C},//G
    {0x66,0x66,0x66,0x7E,0x66,0x66,0x66,0x66},//H
    {0x7E,0x18,0x18,0x18,0x18,0x18,0x18,0x7E},//I
    {0x7E,0x18,0x18,0x18,0x18,0x18,0x1A,0x0C},//J
    {0x62,0x64,0x68,0x70,0x70,0x68,0x64,0x62},//K
    {0x60,0x60,0x60,0x60,0x60,0x60,0x60,0x7E},//L
    {0xC3,0xE7,0xDB,0xDB,0xC3,0xC3,0xC3,0xC3},//M
    {0x62,0x62,0x52,0x52,0x4A,0x4A,0x46,0x46},//N
    {0x3C,0x66,0x66,0x66,0x66,0x66,0x66,0x3C},//O
    {0x7C,0x62,0x62,0x7C,0x60,0x60,0x60,0x60},//P
    {0x38,0x64,0x64,0x64,0x64,0x6C,0x64,0x3A},//Q
    {0x7C,0x62,0x62,0x7C,0x70,0x68,0x64,0x62},//R
    {0x1C,0x22,0x30,0x18,0x0C,0x46,0x46,0x3C},//S
    {0x7E,0x18,0x18,0x18,0x18,0x18,0x18,0x18},//T
    {0x66,0x66,0x66,0x66,0x66,0x66,0x66,0x3C},//U
    {0x66,0x66,0x66,0x66,0x66,0x66,0x3C,0x18},//V
    {0x81,0x81,0x81,0x81,0x81,0x99,0x99,0x66},//W
    {0x42,0x42,0x24,0x18,0x18,0x24,0x42,0x42},//X
    {0xC3,0x66,0x3C,0x18,0x18,0x18,0x18,0x18},//Y
    {0x7E,0x02,0x04,0x08,0x10,0x20,0x40,0x7E},//Z
    {0x3C,0x66,0x66,0x6E,0x76,0x66,0x66,0x3C},//0
    {0x18,0x38,0x58,0x18,0x18,0x18,0x18,0x7E},//1
    {0x3C,0x66,0x66,0x0C,0x18,0x30,0x7E,0x7E},//2
    {0x7E,0x0C,0x18,0x3C,0x06,0x06,0x46,0x3C},//3
    {0x0C,0x18,0x30,0x6C,0x6C,0x7E,0x0C,0x0C},//4
    {0x7E,0x60,0x60,0x7C,0x06,0x06,0x46,0x3C},//5
    {0x04,0x08,0x10,0x38,0x6C,0x66,0x66,0x3C},//6
    {0x7E,0x46,0x0C,0x18,0x18,0x18,0x18,0x18},//7
    {0x3C,0x66,0x66,0x3C,0x66,0x66,0x66,0x3C},//8
    {0x3C,0x66,0x66,0x36,0x1C,0x08,0x10,0x20},//9
    {0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00},// space
    {0x00,0x66,0xFF,0xFF,0x7E,0x3C,0x18,0x00} }; // '&amp;'

    char[] character = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q',
        'R','S','T','U','V','W','X','Y','Z','0','1','2','3','4','5','6','7','8','9',' ','&'};

    protected void blinkImage(short image[], int times, int on, int off) {
        for (int i = 0; i < times; i++) {
            displayImage(image, on);
            clearImage(off);
        }
    }

    protected void displayImage(short image[], int duration) {
        for (int hold = 0; hold < duration; hold++) {
            for (int a = 0; a < 8; a++) {
                shiftOut(image[a]); //Column
                shiftOut(row[a]); //Row
                latchShiftRegToStorageReg();
                delay(1);
            }
        }
    }

    void clearImage(int duration) {
        for (int hold = 0; hold < duration; hold++) {
            for (int a = 0; a < 8; a++) {
                shiftOut((short) 0xFF); //Column
                shiftOut(row[a]); //Row
                latchShiftRegToStorageReg();
                delay(1);
            }
        }
    }
    
    void scrollImage(short image[]) {
        short shift, hold, a;
        for (shift = 0; shift < 9; shift++) {
            for (hold = 0; hold < 30; hold++) {
                for (a = 0; a < 8; a++) {
                    shiftOut((short) (image[a] << shift)); //Column
                    shiftOut(row[a]); //Row
                    latchShiftRegToStorageReg();
                    delay(1);
                }
            }
        }
    }    
        
    public Ex13_DoMatrixAlphabet(GpioController gpio) {
        super(gpio);
    }

    public static void main(String[] args) throws InterruptedException {
        Ex13_DoMatrixAlphabet ex13_doMatrix = new Ex13_DoMatrixAlphabet(GpioFactory.getInstance());
        ex13_doMatrix.run(args);
    }

    @Override
    protected void setup() {
        init();
        //TODO: Java console
        //logger.info("Enter a string: ");
    }

    @Override
    protected void loop(String[] args) throws InterruptedException {
        String sampleText = "HELLO MARC";
        do {
            for(int k=0; k<sampleText.length(); k++) {
              for(int i=0; i<character.length; i++) {
                  if(sampleText.charAt(k) == character[i]) {
                     scrollImage(characterHEX[i]);
                     break;
                  }
                  if((i == (character.length - 1)) && (sampleText.charAt(k) != character[i])) {
                      logger.debug("Invalid character "+sampleText.charAt(k));
                }
              }
            }
            delay(1000);            
        } while (isNotInterrupted);
    }
}
