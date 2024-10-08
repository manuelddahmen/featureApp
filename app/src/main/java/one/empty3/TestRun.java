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

package one.empty3;

import one.empty3.library.core.testing.*;

import java.util.*;
import java.io.*;

public class TestRun {
    //Map<String, String> properties = new HashMap<>();
    public static void runTest(TestObjet to, Properties p) {
        Pojo.setProperties(to, p);
        new Thread(to).start();
    }

    public static void main(String[] args) {
        Properties p = new Properties();
        System.out.println(args.length + " arguments :");
        System.out.println(" arguments class=className" + "\nSETTERproperty (resx, resy, filename1,etc");

        Object t = null;

        // P properties -< args.foreach.split
        Class cl;
        int resx;
        int resy;
        int maxFrames;
        int i = 0;
        for (String arg : args) {

            System.out.println(arg);
            String[] kv = arg.split("=");
            String key = kv[0];
            String value = "";
            if (kv.length > 1)
                value = kv[1];

            if (kv.length == 2) {
                System.out.println("argument : key=value;\n (key=value) = (" + kv[0] + "; " + kv[1] + ") " + (i++) + "/" + kv.length);


                switch (key) {
                    case "class":
                        try {
                            cl = Class.forName(value);

                            t = cl.newInstance();
                            if (!(t instanceof TestObjet))
                                return;
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        break;
                }
                p.setProperty(key, value);
            }
        }

        Pojo.setProperties((Object) t, p);
        // if(cl instanceof TestObjet) {
        if (t != null)
            runTest((TestObjet) t, p);
//    }


    }
} 
