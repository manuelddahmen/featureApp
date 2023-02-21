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

package one.empty3.feature.app.maxSdk29.pro

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivityComposeV2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting3("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting3(name: String) {
    Text(
        text = "Bonjour et bienvenue dans \n" +
                "mon application de traitement d'images. \n" +
                "Vous pouvez ici choisir une image et la transformer. \n"
    )
}

@Composable
fun Greeting4(name: String) {
    Text(
        text = "Ici vous pouvez choisir une de vos photos et  <br/>" +
                "la transformer soit avec des filtres d'effets artistiques ou fantaisistes<br/>" +
                "pour transforrer votre photo, alors choisir un effet réaliste.. \n"
    )
}

@Composable
fun ButtonWithIcon() {
    val context = LocalContext.current
    Button(onClick = {
        context.startActivity(Intent(context, MyCameraActivity::class.java))

    }) {
        Image(
            painterResource(id = R.drawable.app_icon),
            contentDescription = "go to app",
            modifier = Modifier.size(20.dp)
        )

        Text(text = "go to app", Modifier.padding(start = 10.dp))
    }
}

@Composable
fun ButtonWithIcon2() {
    val context = LocalContext.current
    Button(onClick = {
        context.startActivity(Intent(context, MyCameraActivity::class.java))

    }) {
        Image(
            painterResource(id = R.drawable.app_icon),
            contentDescription = "add a photo",
            modifier = Modifier.size(20.dp)
        )

        Text(text = "add a photo", Modifier.padding(start = 10.dp))
    }
}

@Composable
fun ButtonWithIcon3() {
    val context = LocalContext.current
    Button(onClick = {
        context.startActivity(Intent(context, MyCameraActivity::class.java))

    }) {
        Image(
            painterResource(id = R.drawable.app_icon),
            contentDescription = "add a photo",
            modifier = Modifier.size(20.dp)
        )

        Text(text = "add a photo", Modifier.padding(start = 10.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    MaterialTheme {
        ButtonWithIcon()
        Greeting3("")
        Greeting4("")
        ButtonWithIcon2()
        ButtonWithIcon3()
//        Button(text="Prenez une capture")

    }
}