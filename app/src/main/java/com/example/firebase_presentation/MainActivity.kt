package com.example.firebase_presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.firebase_presentation.data.dataStore
import com.example.firebase_presentation.ui.theme.Firebase_presentationTheme
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Firebase_presentationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var user by remember { mutableStateOf("") }
    var dataShow by remember { mutableStateOf(false) }
    var favouriteAnimal by remember { mutableStateOf("") }
    var data: String by remember { mutableStateOf("") }

    var database: DatabaseReference = Firebase.database.reference
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(
            value = user,
            onValueChange = { user = it },
            label = { Text("User") }

        )

        TextField(
            value = favouriteAnimal,
            onValueChange = { favouriteAnimal = it },
            label = { Text("Favourite Animal") }
        )

        Button(onClick = { sendDataToFirebase(user, favouriteAnimal) }) {
            Text("Send")
        }
        Button(onClick = { dataShow = !dataShow }) {
            Text("Read")
        }
        if (dataShow) {
            database.child("user").get().addOnSuccessListener {
                Log.d("Value", "${it.value}")
                data = "${it.value}"
            }.addOnFailureListener {
                data = "Error"
            }
            Text(
                text = data
            )
        }
    }
}

fun sendDataToFirebase(user: String, animal: String) {
    var database: DatabaseReference = Firebase.database.reference

    val dataToSend = dataStore(user, animal);

    database.child("user").child(user).setValue(dataToSend)
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Firebase_presentationTheme {
        Greeting("Android")
    }
}