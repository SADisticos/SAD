package com.example.isomapp

import java.net.Socket

class ConnectionTCP(val dades: Dades?): Thread() {
    val HOST: String = "10.38.133.220"  // ficar ip alberto
    val PORT: Int = 12345

    public override fun run(){
        val client = Socket(HOST, PORT)

        ConnectionState.state = "Enviando Petición"
        client.outputStream.write(dades!!.enviar()!!.toByteArray())
        ConnectionState.state = "Petición enviada: OK"
        client.close()
    }
}