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

import org.junit.Assert;
import org.junit.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javaAnd.awt.image.BufferedImage;
import one.empty3.feature20220726.GoogleFaceDetection;

public class GoogleFaceDetectTest {
    @Test
    public void loadSaveTest()   {
        BufferedImage image = new BufferedImage();

        try {
            GoogleFaceDetection googleFaceDetection = new GoogleFaceDetection();
            FileOutputStream fileOutputStream = new FileOutputStream("text.fac");
            googleFaceDetection.encode(new DataOutputStream(fileOutputStream));
            fileOutputStream.close();

            FileInputStream fileOutputStream1 = new FileInputStream("text.fac");
            GoogleFaceDetection googleFaceDetection1=
                    (GoogleFaceDetection) googleFaceDetection.decode(new DataInputStream(fileOutputStream1));
            fileOutputStream1.close();

        } catch (IOException e6) {
            e6.printStackTrace();
            Assert.fail();
        }
    }
}