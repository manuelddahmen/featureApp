package one.empty3.feature.app.maxSdk29.pro;

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

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import javaAnd.awt.image.imageio.ImageIO
import one.empty3.Main2022
import one.empty3.feature20220726.Mix
import one.empty3.io.ProcessFile
import java.io.File
import java.util.UUID


class ChooseEffectsActivity2 : ActivitySuperClass() {
    private var unauthorized: Boolean = false
    private val READ_WRITE_STORAGE: Int = 15165516
    var listEffects: HashMap<String, ProcessFile>? = null
    private lateinit var classnames: ArrayList<String>
    private lateinit var effectApply: Button
    private lateinit var recyclerView: RecyclerView
    private var hasRun = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        classnames = ArrayList()

        Main2022.effects = classnames
        Main2022.indices = ArrayList()
        Main2022.listOfFactors()
        setContentView(R.layout.recycler_view_effect_activity)

        //maxRes = Utils().getMaxRes(this)

        recyclerView = findViewById(R.id.recycler_view_effect)
        val processFileArrayAdapter = ProcessFileArrayAdapter()
        processFileArrayAdapter.setMainAnd(Main2022(), recyclerView, this)
        processFileArrayAdapter.setCurrentActivity(this)
        recyclerView.adapter = processFileArrayAdapter
        listEffects = Main2022.initListProcesses()
        Log.i("effects#logging", "create Effect Activity")
        effectApply = findViewById(R.id.applyEffects)
        init(savedInstanceState)
    }


    fun init(savedInstanceState: Bundle?) {
        requestPermissions(
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_MEDIA_IMAGES
            ), READ_WRITE_STORAGE
        )
        if (!hasRun) {
            applyEffects()
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == READ_WRITE_STORAGE) {
            var g: Int = 0
            for (granted in grantResults)
                if ((granted == PackageManager.PERMISSION_GRANTED))
                    g = g + 1


            try {

                unauthorized = false



                //applyEffects()
                initAuthorized()
                hasRun = true

            } catch (ex: RuntimeException) {

                ex.printStackTrace()

            }

        }
    }
    private fun initAuthorized() {
        var index = 0
        effectApply.setOnClickListener {

            if(currentFile==null)
                return@setOnClickListener
            try {
                currentFile = Utils().writePhoto(
                    this, ImageIO.read(currentFile).getBitmap(),
                    "before-effect"
                )
            } catch (ex:RuntimeException ) {
                return@setOnClickListener
            }
            classnames = Main2022.effects

            classnames.forEachIndexed { index1, it1 ->
                classnames[index1] = listEffects?.get(it1)?.javaClass?.name
            }


            run {
                var totalOutput: File = currentFile

                println("Clicked on Effect button, running effects")
                val fileIn: File = File(currentFile.toString())

                Log.d("Initial input file", fileIn.toString())
                Log.d(
                    "Initial input file exists?", "Exists?"
                            + ((fileIn.exists()).toString())
                )
                Log.d(packageName, "\"Effects' list size:" + classnames.size)


                var dirRoot: String = filesDir.absolutePath
                // + File.separator + "data/files"//!!!?
                /*uri = FileProvider.getUriForFile(
            this@MyCameraActivity,
            BuildConfig.APPLICATION_ID + ".provider",
            currentFile
        )
        dirRoot =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
*/


                var dir = ""

                dir = "photoDir"
                dirRoot = currentFile.toString()
                    .substring(0, currentFile.toString().lastIndexOf(File.separator))

                var currentProcessFile: File = fileIn
                val currentProcessDir = File(
                    fileIn.absolutePath.substring(
                        0,
                        fileIn.absolutePath.lastIndexOf("/")
                    )
                )
                var currentOutputFile: File = currentFile
                val currentOutputDir: File = currentProcessDir
                index = -1
                val name = currentProcessFile.name
                //dir = "appDir"
                if (classnames.size == 0) {

                    return@setOnClickListener;
                }

                var processFile: ProcessFile? = null

                var currentProcessInFile: File = currentFile
                classnames.forEach { it1 ->
                    if (it1 == null || it1.isBlank()) {
                        return@forEach
                    }
                    val effectListStr: String = it1
                    val trim = it1.trim()
                    if (effectListStr.contains(trim)) {
                        processFile = Class.forName(it1).newInstance() as ProcessFile
                        currentProcessFile = currentProcessInFile
                        if (processFile != null) {
                            currentOutputFile = File(
                                nextFile(
                                    currentProcessInFile.parentFile!!.absolutePath,
                                    "effect-" + UUID.randomUUID(), "jpg"
                                )
                            )
                            currentOutputDir.mkdirs()
                            println("Effect class           : $trim")
                            println("In picture             : $currentProcessFile")
                            println("In picture directory   : $currentProcessDir")
                            println("Out  picture           : $currentOutputFile")
                            println("Out picture directory  : $currentOutputDir")
                            try {
                                if (currentProcessFile.exists()
                                // &&!currentOutputFile.exists()
                                ) {
                                    val lastCurrentProcessFile = currentProcessFile

                                    processFile!!.setMaxRes(maxRes)
                                    if (!(processFile!!.process(
                                            currentProcessFile,
                                            currentOutputFile
                                        ))
                                    ) {

                                        println("Error processing file.Error processing file.")
                                        println("Error in " + processFile!!.javaClass.name)
                                        Toast.makeText(
                                            applicationContext,
                                            ("Error while applying filter" + (processFile!!.javaClass.name)),
                                            Toast.LENGTH_LONG
                                        ).show()
                                        return@setOnClickListener
                                    } else {

                                        try {
                                            val mix = Mix()
                                            val currentOutputFile1 = File(
                                                nextFile(
                                                    currentProcessInFile.parentFile!!.absolutePath,
                                                    "alpha-" + UUID.randomUUID(),
                                                    "jpg"
                                                )
                                            )
                                            mix.progressColor = Mix.MAX_PROGRESS
                                            val pf = processFile!!.javaClass.simpleName
                                            if (Main2022.effectsFactors != null && Main2022.effectsFactors[pf] != null)
                                                mix.progressColor = Main2022.effectsFactors[pf]!!

                                            println("mix.progressColor="+mix.progressColor);
                                            mix.processFiles(
                                                currentOutputFile1,
                                                lastCurrentProcessFile,
                                                currentOutputFile
                                            )

                                            //System.err.println(""+javaClass+" "+it1+" progress : "+mix.progressColor)

                                            currentOutputFile = currentOutputFile1
                                            totalOutput = currentOutputFile
                                        } catch (ex: RuntimeException) {
                                            ex.printStackTrace()
                                        }
                                    }
                                } else {
                                    println(
                                        "Success\n" +
                                                "File in doesn't exists, or File out exists\n" +
                                                "\ncurrentProcessDir  exists?: " + currentProcessDir.exists() +
                                                "\ncurrentProcessFile exists?: " + currentProcessFile.exists() +
                                                "\ncurrentOutputDir   exists?: " + currentOutputDir.exists() +
                                                "\ncurrentOutputFile  exists?: " + currentOutputFile.exists()
                                    )
                                    Toast.makeText(
                                        applicationContext,
                                        ("Source file doesn't exist" + (processFile!!.javaClass.name)),
                                        Toast.LENGTH_LONG
                                    ).show()
                                    return@setOnClickListener
                                }
                            } catch (ex: Exception) {
                                val errMessage = "Error processing file. Exception :$ex"
                                println(errMessage)
                                ex.printStackTrace()

                                return@setOnClickListener
                            }
                            currentProcessFile = currentOutputFile
                            currentProcessInFile = currentProcessFile
                        }

                    }
                    index++


                }
                if (processFile != null && totalOutput != null) {
                    Toast.makeText(
                        applicationContext,
                        ("Applied effect:" + (processFile!!.javaClass.name)),
                        Toast.LENGTH_LONG
                    ).show()

                    currentFile =
                        Utils().writePhoto(this, ImageIO.read(totalOutput).bitmap, "effect-");

                    val intent2 = Intent(applicationContext, MyCameraActivity::class.java)
                    passParameters(intent2)
                }
            }
        }
    }

    fun comaStringList(str: String): String {
        return "";
    }

    private fun nextFile(directory: String, filenameBase: String, extension: String): String {
        return directory + File.separator + filenameBase + "--" + UUID.randomUUID() + "." + extension
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.i("effects#logging", "save Effect Activity")
        outState.putStringArrayList("classname", classnames)
        super.onSaveInstanceState(outState)
    }

    fun autoCompleteTextViewGettext(): String {
        var str: String = ""
        classnames.forEach {
            var it1 = it.trim().toString()
            if (str == "")
                str += it1
            else
                str += ", $it1"
        }
        return str;
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Log.i("effects#logging", "restore Effect Activity")
        val string = savedInstanceState.getStringArray("classnames")
        classnames = ArrayList()
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onDestroy() {
        Log.i("effects#logging", "destroy Effect Activity")
        super.onDestroy()
    }

    private fun applyEffects() {

        val effectViewModel = EffectViewModel(application)
        effectViewModel.passVariables(this);
        effectViewModel.applyBlur(0)

//        System.out.println(effectViewModel.outputUri);
//        currentFile =
//            Utils().writePhoto(this, ImageIO.read(totalOutput).bitmap, "effect-");
//
//        val intent2 = Intent(applicationContext, MyCameraActivity::class.java)
//        passParameters(intent2)
    }


}


