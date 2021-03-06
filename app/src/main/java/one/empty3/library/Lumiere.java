/*
 *  This file is part of Empty3.
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
 *     along with Empty3.  If not, see <https://www.gnu.org/licenses/>. 2
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

package one.empty3.library;

import one.empty3.feature.app.replace.java.awt.Color;

/*__
 * @author Atelier
 */
public abstract class Lumiere  extends Representable{
  // ambient specular diffuse shinyness
   protected Color La= (Color) Color.valueOf(0f,0f,0f),
          Ls= (Color) Color.valueOf(1f,1f,1f),
          Ld= (Color) Color.valueOf(1f,1f,1f);
   
   protected double S=0.5;

    public abstract int getCouleur(int base, Point3D p, Point3D n);
    public int getLa() {return La.toArgb();}
    public int getLs() {return Ls.toArgb();}
    public int getLd() {return Ld.toArgb();}

    public static double [] getRgb(Color c) {
       return new double[] {(c.red()/255f),
          (c.green()/255f),
          (c.blue()/255f)};
    }
  
  public static int  getInt(double [] d) {
       int res = 0xFF000000;
    for(int i=0 ;i<3;i++) {
        res += ((int)(float)(d[i]*0xff))<<((2-i)*8);
    }
    return res;//|0xFF000000;
  }

   public static double[] getDoubles(int c) {
       double [] res = new double[3];
       for(int i=0 ;i<3;i++) {
        res[i] = (((c&(0xff<<((2-i)*8)) )>>((2-i)*8)))/255.;
       }
       return res;  
    }
    public static Color getColorD(double[] d) {
        return (Color) Color.Color((float)(d[0]), (float)(d[1]),(float)(d[2]));
    }
}
