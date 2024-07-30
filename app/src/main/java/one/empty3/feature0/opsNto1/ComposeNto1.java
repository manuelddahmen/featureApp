/*
 * Copyright (c) 2024.
 *
 *
 *  Copyright 2023 Manuel Daniel Dahmen
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

package one.empty3.feature0.opsNto1;

import one.empty3.feature0.PixM;
import one.empty3.io.ProcessFile;

import javaAnd.awt.image.imageio.ImageIO;

import java.io.File;
import java.io.IOException;

public class ComposeNto1 extends ProcessFile {
    public boolean addEntry(Composer composer, ProcessFile... processFiles) {
        return false;
    }

    @Override
    public boolean process(File in, File out) {
        try {
            boolean success = false;
            PixM inpix = PixM.getPixM(ImageIO.read(in), maxRes);
            PixM outpix = PixM.getPixM(ImageIO.read(in), maxRes);
            //success = processMem(inpix, outpix);
            ImageIO.write(outpix.getImage(), "jpg", out, shouldOverwrite);
            return success;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}