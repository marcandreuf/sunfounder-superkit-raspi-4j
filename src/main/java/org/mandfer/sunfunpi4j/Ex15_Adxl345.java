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
import com.pi4j.wiringpi.I2C;

/**
 * 
 * Datasheet ADXL 345
 * http://www.analog.com/media/en/technical-documentation/data-sheets/ADXL345.pdf
 * 
 * @author marcandreuf
 */
public class Ex15_Adxl345 extends BaseSketch {

    private static final int DEV_ADD = 0x53; // 7 bit address 0 1010011
    private int fd;

    public Ex15_Adxl345(GpioController gpio) {
        super(gpio);
    }

    public static void main(String[] args) throws InterruptedException {
        Ex15_Adxl345 ex15_adsl345 = new Ex15_Adxl345(GpioFactory.getInstance());
        ex15_adsl345.run(args);
    }

    @Override
    protected void setup() {
        fd = I2C.wiringPiI2CSetup(DEV_ADD);

        if (-1 == fd) {
            String msg = "I2C device setup error";
            logger.error(msg);
            throw new RuntimeException(msg);
        }
        
        adxl345_init(fd);
    }
    
    private static final int REG_RW_DATA_FORMAT = 0x31; // Data format control
    private static final int REG_RW_POWER_CTL = 0x2d; // Power-saving features control
    private static final int REG_RW_OFSX = 0x1e; // X-axis offset
    private static final int REG_RW_OFSY = 0x1f; // Y-axis offset
    private static final int REG_RW_OFSZ = 0x20; // Z-axis offset
    private static final int REG_RW_DUR = 0x21; // Tap duration
    private static final int REG_RW_LATENT = 0x22; // Tap latency
    private static final int REG_RW_WINDOW = 0x23; // Tap window
    private static final int REG_RW_THRESH_ACT = 0x24; // Activity threshold
    private static final int REG_RW_THRESH_INACT = 0x25; // Inactivity threshold
    private static final int REG_RW_TIME_INACT = 0x26; // Inactivity time
    private static final int REG_RW_ACT_INACT_CTL = 0x27; // Axis enable control for activity and inactivity detection
    private static final int REG_RW_THRESH_FF = 0x28; // Free-fall threshold
    private static final int REG_RW_TIME_FF = 0x29; // Free-fall time
    private static final int REG_RW_TAP_AXES = 0x2a; // Axis control for single tap/double tap
    private static final int REG_RW_BW_RATE = 0x2c; // Data rate and power mode control
    private static final int REG_RW_INT_MAP = 0x2f; // Interrupt mapping control
    private static final int REG_RW_FIFO_CTL = 0x38; // FIFO control
    
    private static final int REG_R_DATAX0 = 0x32; // X-Axis Data 0
    private static final int REG_R_DATAX1 = 0x33; // X-Axis Data 1
    private static final int REG_R_DATAY0 = 0x34; // Y-Axis Data 0
    private static final int REG_R_DATAY1 = 0x35; // Y-Axis Data 1
    private static final int REG_R_DATAZ0 = 0x36; // Z-Axis Data 0
    private static final int REG_R_DATAZ1 = 0x37; // Z-Axis Data 1
    
    
    private void adxl345_init(int fd) {

        I2C.wiringPiI2CWriteReg8(fd, REG_RW_DATA_FORMAT, 0x0b); // Full resolution, right-justified and +-16g
        I2C.wiringPiI2CWriteReg8(fd, REG_RW_POWER_CTL, 0x08);

        I2C.wiringPiI2CWriteReg8(fd, REG_RW_OFSX, 0x00);
        I2C.wiringPiI2CWriteReg8(fd, REG_RW_OFSY, 0x00);
        I2C.wiringPiI2CWriteReg8(fd, REG_RW_OFSZ, 0x00);

        I2C.wiringPiI2CWriteReg8(fd, REG_RW_DUR, 0x00);
        I2C.wiringPiI2CWriteReg8(fd, REG_RW_LATENT, 0x00);
        I2C.wiringPiI2CWriteReg8(fd, REG_RW_WINDOW, 0x00);

        I2C.wiringPiI2CWriteReg8(fd, REG_RW_THRESH_ACT, 0x01);
        I2C.wiringPiI2CWriteReg8(fd, REG_RW_THRESH_INACT, 0x0f);
        I2C.wiringPiI2CWriteReg8(fd, REG_RW_TIME_INACT, 0x2b);
        I2C.wiringPiI2CWriteReg8(fd, REG_RW_ACT_INACT_CTL, 0x00);

        I2C.wiringPiI2CWriteReg8(fd, REG_RW_THRESH_FF, 0x09);
        I2C.wiringPiI2CWriteReg8(fd, REG_RW_TIME_FF, 0xff);
        I2C.wiringPiI2CWriteReg8(fd, REG_RW_TAP_AXES, 0x80);
        I2C.wiringPiI2CWriteReg8(fd, REG_RW_BW_RATE, 0x0a);
        I2C.wiringPiI2CWriteReg8(fd, REG_RW_INT_MAP, 0x00);
        I2C.wiringPiI2CWriteReg8(fd, REG_RW_FIFO_CTL, 0x9f); // Set to stream mode
    }
    
    @Override        
    protected void loop(String[] args) throws InterruptedException {
        XYZData xyz;
        do {
            xyz = adxl345_read_values(fd);
            logger.debug("x: "+xyz.x+"  y: "+xyz.y+"  z: "+xyz.z);
            delay(1000);
        } while (isNotInterrupted);
    }


    protected XYZData adxl345_read_values(int fd) {
        short x0, y0, z0, x1, y1, z1;
        int newX, newY, newZ;

        x0 = readRightJustifiedByte(I2C.wiringPiI2CReadReg8(fd, REG_R_DATAX0));
        x1 = readRightJustifiedByte(I2C.wiringPiI2CReadReg8(fd, REG_R_DATAX1));
        y0 = readRightJustifiedByte(I2C.wiringPiI2CReadReg8(fd, REG_R_DATAY0));
        y1 = readRightJustifiedByte(I2C.wiringPiI2CReadReg8(fd, REG_R_DATAY1));
        z0 = readRightJustifiedByte(I2C.wiringPiI2CReadReg8(fd, REG_R_DATAZ0));
        z1 = readRightJustifiedByte(I2C.wiringPiI2CReadReg8(fd, REG_R_DATAZ1));

        newX = composeNewValue(x1, x0); // (x1 << 8) + x0;
        newY = composeNewValue(y1, y0); // (y1 << 8) + y0;
        newZ = composeNewValue(z1, z0); // (z1 << 8) + z0;

        return new XYZData(newX, newY, newZ);
    }
    
    private short readRightJustifiedByte(int value){
        return (short) (0xff - value);
    }
    
    private int composeNewValue(short msb, short lsb){
        return (msb << 8) + lsb;
    }

    protected class XYZData {

        private final int x, y, z;

        public XYZData(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }
    }
}
