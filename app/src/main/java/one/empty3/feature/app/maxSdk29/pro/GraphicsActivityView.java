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

package one.empty3.feature.app.maxSdk29.pro;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javaAnd.awt.image.BufferedImage;
import javaAnd.awt.image.imageio.ImageIO;
import one.empty3.apps.tree.altree.AlgebraicFormulaSyntaxException;
import one.empty3.apps.tree.altree.AlgebricTree;
import one.empty3.apps.tree.altree.TreeNodeEvalException;
import one.empty3.feature20220726.PixM;

public class GraphicsActivityView extends ActivitySuperClass {
    final String[] cord = new String[]{"x", "y", "z", "r", "g", "b", "a", "t", "u", "v"};
    final String[] formulas = new String[]{"x", "y", "z", "r", "g", "b", "a", "t", "u", "v"};
    final double[] values = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    final AlgebricTree[] algebricTree = new AlgebricTree[cord.length];
    HashMap<String, Double> stringDoubleHashMap;
    private int maxRes = 300;
    private PixM current;
    private int MAX_RES = maxRes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graphics_view);

        maxRes = new Utils().getMaxRes(this, savedInstanceState);


        Intent intent = getIntent();
        if (getIntent() != null && getIntent().getData() != null) {
            if (intent.getData() != null) {
                Uri data = intent.getData();
                currentFile = new File(data.getPath());
                System.err.println("File returned from effects' list = " + data);

                if (currentFile != null) {
                    BufferedImage bi = ImageIO.read(currentFile);
                    if (bi != null) {
                        Bitmap bitmap = bi.getBitmap();
                        if (bitmap != null) {
                        }
                    }
                }
            }
        } else {
            System.err.println("intent data Main==null");
        }


        stringDoubleHashMap = new HashMap<>();

        for (int i = 0; i < cord.length; i++) {
            if (getIntent().getStringExtra(cord[i]) != null) {
                formulas[i] = getIntent().getStringExtra(cord[i]);
                stringDoubleHashMap.put(cord[i], values[i]);
            } else {
                Toast.makeText(getApplicationContext(), "paramètre null: " + cord[i],
                        Toast.LENGTH_SHORT).show();
            }
        }


        Button buttonDraw = findViewById(R.id.buttonDraw);
        buttonDraw.setOnClickListener(view -> {
            {
                draw();
            }
        });

        Button back = findViewById(R.id.buttonBack2);
        back.setOnClickListener(view -> {
            Intent intentGraphics = new Intent();
            if (currentFile != null)
                intentGraphics.setDataAndType(Uri.fromFile(currentFile), "image/jpg");

            intentGraphics.setClass(getApplicationContext(), MyCameraActivity.class);
            intentGraphics.putExtra("maxRes", maxRes);
            int j = 0;
            for (j = 0; j < cord.length; j++) {
                if (intentGraphics.getExtras() != null) {
                    intentGraphics.putExtra(cord[j], formulas[j]);
                }
            }
            startActivity(intentGraphics);

        });


        printValues();
        Logger.getAnonymousLogger().log(Level.INFO,
                "currentFile=" + getClass().toString() + " " + currentFile);
    }

    private void printValues() {

        stringDoubleHashMap.forEach((s, aDouble) -> System.out.println(s + ":=" + aDouble));
        for (int i = 0; i < formulas.length; i++) {
            System.out.println(cord[i] + ":=" + formulas[i]);
        }

    }


    private void draw() {
        Logger.getAnonymousLogger().log(Level.INFO,
                "currentFile=" + getClass().toString() + " " + currentFile);


        ImageViewSelection image = findViewById(R.id.currentImageView);

        int w = getMaxRes();
        int h = getMaxRes();

        current = null;

        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_MEDIA_IMAGES}, 38203820);

        if (currentFile != null) {
            if (getMaxRes() > 0) {
                current = PixM.getPixM(ImageIO.read(currentFile), getMaxRes());
            } else {
                current = new PixM(ImageIO.read(currentFile));
            }
        }
        if (current == null) {
            if (getMaxRes() <= 0) {
                current = new PixM(MAX_RES, MAX_RES);
            } else {
                current = new PixM(w, h);
            }
        }
        w = current.getColumns();
        h = current.getLines();

        for (int i = 0; i < values.length; i++) {
            algebricTree[i] = new AlgebricTree(formulas[i], stringDoubleHashMap);
            try {
                algebricTree[i].construct();
            } catch (AlgebraicFormulaSyntaxException e) {
                printValues();
                throw new RuntimeException(e);
            }
        }
        double[] rgba = new double[4];

        double t = 0;
        int progress = 0;
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                try {
                    double[] v = current.getValues(x, y);
                    for (int j = 0; j < algebricTree.length; j++) {
                        algebricTree[j].setParameter("r", v[0]);
                        algebricTree[j].setParameter("g", v[1]);
                        algebricTree[j].setParameter("b", v[2]);
                        algebricTree[j].setParameter("u", 1.0 * x / w);
                        algebricTree[j].setParameter("v", 1.0 * y / h);
                        algebricTree[j].setParameter("w", (double) w);
                        algebricTree[j].setParameter("h", (double) h);
                        algebricTree[j].setParameter("x", (double) x);
                        algebricTree[j].setParameter("y", (double) y);
                        algebricTree[j].setParameter("z", (double) 0.0);
                        algebricTree[j].setParameter("t", (double) t);
                        algebricTree[j].setParameter("a", 0.0);
                    }
/*
                    for (int c = 0; c < cord.length; c++) {
                        stringDoubleHashMap.put(cord[c], algebricTree[c].eval());
                    }
*/

                    double x2 = algebricTree[0].eval();
                    double y2 = algebricTree[1].eval();

                    for (int c = 0; c < 4; c++) {
                        rgba[c] = algebricTree[c + 3].eval();
                    }
                    double[] finalColors = new double[3];
                    for (int i = 0; i < 3; i++) {
                        finalColors[i] = v[i] * rgba[3] + rgba[i] * (1 - rgba[3]);
                    }
                    current.setValues((int) Math.round(x2), (int) Math.round(y2),
                            finalColors[0], finalColors[1], finalColors[2]);

                } catch (TreeNodeEvalException | AlgebraicFormulaSyntaxException e) {
                    //printValues();
                    //throw new RuntimeException(e);
                }
            }
            progress = (int) (100. * y / h);
        }
        progress = 100;

        Bitmap bitmap = current.normalize(0, 1).getBitmap();
        File graphics_math = new Utils().writePhoto(this, bitmap, "graphics_math");
        this.currentFile = graphics_math;
        new Utils().setImageView(image, bitmap);
    }

    private int getMaxRes() {
        return maxRes;
    }

}
