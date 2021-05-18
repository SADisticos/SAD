package com.example.isomapp

import android.os.Parcel
import android.os.Parcelable

@Parcelize
data class Dades(var centre:String?, var pis:String?, var relevancia:String?, var causa:String?, var incidencia:String?, var entrada:String?) :
        Parcelable {

        constructor(parcel: Parcel) : this(
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString()
        ) {}

        fun enviar(): String{
                val str = StringBuilder("")
                str.append(centre)
                str.append("//")
                if(!pis.isNullOrEmpty()) str.append(pis)
                str.append("//")
                str.append(relevancia)
                str.append("//")
                str.append(causa)
                str.append("//")
                str.append(incidencia)
                str.append("//")
                str.append(entrada)
                return str.toString()
        }

        override fun toString(): String{
                var str = StringBuilder()
                str.appendln("Centre: $centre")
                if(!pis.isNullOrEmpty()) str.appendln("Pis: $pis")
                str.appendln("Relevancia: $relevancia")
                str.appendln("Causa: $causa")
                str.appendln("Incidencia: $incidencia")
                str.appendln("Data d'entrada: $entrada")
                return str.toString()
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeString(centre)
                parcel.writeString(pis)
                parcel.writeString(relevancia)
                parcel.writeString(causa)
                parcel.writeString(incidencia)
                parcel.writeString(entrada)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<Dades> {
                override fun createFromParcel(parcel: Parcel): Dades {
                        return Dades(parcel)
                }

                override fun newArray(size: Int): Array<Dades?> {
                        return arrayOfNulls(size)
                }
        }

}

annotation class Parcelize

