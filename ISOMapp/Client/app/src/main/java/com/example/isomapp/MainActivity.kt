package com.example.isomapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.Snackbar
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class MainActivity : AppCompatActivity() {

    private val dades = Dades(null, null, null, null, null, null)

    /* MAIN*/
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Centre
        val listCentre = mutableListOf("ISOM", "Minerva", "Sants", "Urrutia", "Centre Obert", "Espai Familiar", "Santa Coloma", "Gorg", "Piferrer", "Bufala", "Mediterrania 1", "Mediterrania 2", "Pisos BCN", "Pisos Lleida")
        generateSpinner(R.id.Centre, listCentre, "Selecciona el centre", "centre")

        // Pis
        generateEditText(R.id.Pis, "Nom del pis (només pisos BCN/Lleida", "pis")

        // Relevancia
        val listRelevancia= mutableListOf("No Urgent", "Urgent", "Molt urgent", "Millora/Altre", "Normal")
        generateSpinner(R.id.Relevancia, listRelevancia, "Selecciona el nivell de relevància", "relevancia")

        // Causa
        val listCausa = mutableListOf("Manteniment", "Vandalisme", "Millora", "Avaria", "Accident", "Desgast")
        generateSpinner(R.id.Causa, listCausa, "Selecciona la causa", "causa")

        // Incidència
        generateEditText(R.id.Incidencia, "Descrigui la incidència", "incidencia")

        // Botó
        val submit: Button = findViewById(R.id.Submit)
        submit.setText("Enviar")

    }

    /* FUNCTIONS */
    private fun generateSpinner(id: Int, list: MutableList<String>, hint: String, type: String){
        val spinner: Spinner = findViewById(id)
        list.sort()

        // Hint
        list.add(0, hint)

        val adapter:ArrayAdapter<String> = object: ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                list
        ) {
            override fun getDropDownView(
                    position: Int,
                    convertView: View?,
                    parent: ViewGroup
            ): View {
                val view: TextView = super.getDropDownView(
                        position,
                        convertView,
                        parent
                ) as TextView
                // set item text bold
                view.setTypeface(view.typeface, Typeface.BOLD)

                // set selected item style
                if (position == spinner.selectedItemPosition && position != 0) {
                    view.background = ColorDrawable(Color.parseColor("#F7E7CE"))
                    view.setTextColor(Color.parseColor("#333399"))
                }

                // make hint item color gray
                if (position == 0) {
                    view.setTextColor(Color.LTGRAY)
                }

                return view
            }

            override fun isEnabled(position: Int): Boolean {
                // disable first item
                // first item is display as hint
                return position != 0
            }
        }

        // finally, data bind spinner with adapter
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
            ) {
                if(position != 0){
                    when(type){
                        "centre" -> dades.centre = parent.getItemAtPosition(position).toString()
                        "relevancia" -> dades.relevancia = parent.getItemAtPosition(position).toString()
                        "causa" -> dades.causa = parent.getItemAtPosition(position).toString()
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?){
                // another interface callback
            }

        }
    }

    private fun generateEditText(id: Int, hint: String, type: String){
        val editText: EditText = findViewById(id)
        editText.setHint(hint)

        editText.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable){
                when(type) {
                    "pis" -> dades.pis = s.toString()
                    "incidencia" -> dades.incidencia = s.toString()
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after:Int){}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int){}
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun sendMessage(view: View){
        if(dades.centre.isNullOrEmpty() || dades.relevancia.isNullOrEmpty() || dades.causa.isNullOrEmpty() || dades.incidencia.isNullOrEmpty()){
            val mySnackbar = Snackbar.make(view, "Faltan Datos", Snackbar.LENGTH_LONG)
            mySnackbar.show()
            return
        }

        if(dades.centre.equals("Pisos BCN") || dades.centre.equals("Pisos Lleida"))
            if(dades.pis.isNullOrEmpty()){
                val mySnackbar = Snackbar.make(view, "Falta Dirección", Snackbar.LENGTH_LONG)
                mySnackbar.show()
                return
            }

        // Date: dd/mm/aa
        dades.entrada = LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT))

        val intent = Intent(this, DisplayMessageActivity::class.java)
        intent.putExtra("Dades",dades)
        this.startActivity(intent)
    }
}
