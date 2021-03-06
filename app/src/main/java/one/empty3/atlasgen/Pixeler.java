/*
 *  This file is part of one.empty3.
 *
 *     Empty3 is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Empty3 is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with one.empty3.  If not, see <https://www.gnu.org/licenses/>. 2
 */

/*
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>
 */

package one.empty3.atlasgen;


import android.graphics.Bitmap;

import one.empty3.feature.app.replace.java.awt.Color;
import one.empty3.feature.app.replace.java.awt.Point;
import one.empty3.feature.app.replace.java.awt.image.BufferedImage;

/*__
 * Created by manuel on 19-06-18.
 */
public class Pixeler {
    private Bitmap image;
    private int width;
    private int height;


    public Pixeler(Bitmap image) {
        this.image = image;
        width = image.getWidth();
        height = image.getHeight();
    }

    public void pixelize(int x, int y, Color color) {
        image.setPixel(x < 0 ? 0 : (x > width - 1 ? width - 1 : x),
                y < 0 ? 0 : (y > width - 1 ? width - 1 : y),
                color.toArgb());
    }

    public Point convert(double ratioX, double ratioY) {
        return new Point((int) (ratioX * width), (int) (ratioY * height));
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
