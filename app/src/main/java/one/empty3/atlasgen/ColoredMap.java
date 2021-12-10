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

import  one.empty3.feature.app.replace.javax.imageio.ImageIO;


import java.io.File;
import java.io.IOException;

public class ColoredMap {

    public static void main(String[] args) {
        System.out.println(
                "Colored Map"
        );
        BufferedImage image = BufferedImage.BufferedImage(1800, 1600,
Bitmap.Config.RGB565);
        Graphics graphics = image.getGraphics();
        graphics.setColor( one.empty3.feature.app.replace.java.awt.Color.Color(Color.TRANSLUCENT));
        graphics.fillRect(0, 0, image.getWidth() - 1, image.getHeight() - 1);
        Pixeler pixeler = new Pixeler(image);
        CsvReader csvReader = new CsvReader(new File("allCountries/allCountries.txt"),
                "" + '\t', "" + '\n', false);
        csvReader.setAction(new DrawPerCountryAction(pixeler));
        csvReader.process();
        try {
            ImageIO.write(pixeler.getImage(), "jpg", Seriald.newOutputFile("ColoredMap"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}