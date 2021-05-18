package com.example.isomapp

import android.media.session.MediaSession
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import java.net.Socket

class DisplayMessageActivity : AppCompatActivity() {
    val str:StringBuilder = StringBuilder("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_message)

        // Get the Intent that started this activity and extract the string
        val dades: Dades? = intent.getParcelableExtra("Dades")

        val message: TextView = findViewById(R.id.Message)
        message.setText(dades?.toString())

        val status: TextView = findViewById(R.id.Status)

        ConnectionState.refreshListListeners.add{refreshList(status)}

        val connection = ConnectionTCP(dades)
        connection.start()
    }

    fun refreshList(status: TextView ){
        str.appendln(ConnectionState.state)
        status.setText(str.toString())
    }
}

