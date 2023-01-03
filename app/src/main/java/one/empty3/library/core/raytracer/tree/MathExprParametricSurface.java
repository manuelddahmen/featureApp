/*
 * Copyright (c) 2023.
 *
 *
 *  Copyright 2012-2023 Manuel Daniel Dahmen
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */

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

package one.empty3.library.core.raytracer.tree;

import one.empty3.library.Point3D;
import one.empty3.library.core.nurbs.ParametricSurface;

import java.util.HashMap;

/*__
 * Created by manuel on 05-02-17.
 */
public class MathExprParametricSurface extends ParametricSurface {
    /*__
     * a, b: parameters
     */
    private final String[] exprStringXyz;
    AlgebricTree[] algebricTree;

    public MathExprParametricSurface(String[] exprStringXyz) {
        this.exprStringXyz = exprStringXyz;
        algebricTree = new AlgebricTree[exprStringXyz.length];
        for (int i = 0; i < exprStringXyz.length; i++) {
            try {
                algebricTree[i] = new AlgebricTree(exprStringXyz[i]);
            } catch (AlgebraicFormulaSyntaxException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public Point3D calculerPoint3D(double u, double v) {
        Point3D p = new Point3D();
        HashMap<String, Double> stringDoubleHashMap = new HashMap<>();
        stringDoubleHashMap.put("a", u);
        stringDoubleHashMap.put("b", v);
        for (int i = 0; i < algebricTree.length; i++) {
            algebricTree[i].getParametersValues().putAll(stringDoubleHashMap);
            try {
                p.set(i, (Double) algebricTree[i].eval());
            } catch (TreeNodeEvalException e) {
                e.printStackTrace();
            } catch (AlgebraicFormulaSyntaxException e) {
                e.printStackTrace();
            }
        }
        return p;
    }

}
