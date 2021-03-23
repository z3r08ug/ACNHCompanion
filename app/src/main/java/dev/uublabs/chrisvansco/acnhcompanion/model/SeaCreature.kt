package dev.uublabs.chrisvansco.acnhcompanion.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "seacreatures_table")
data class SeaCreature(@PrimaryKey @NonNull @ColumnInfo(name = "name") val name: String, @ColumnInfo(name = "monthNH") val monthNH: String, @ColumnInfo(name = "monthSH") val monthSH: String, @ColumnInfo(name = "time") val time: String, @ColumnInfo(name = "shadow") val shadow: String, @ColumnInfo(name = "speed") val speed: String, @ColumnInfo(name = "price") val price: String, @ColumnInfo(name = "caught") var caught: String = "false"): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(monthNH)
        parcel.writeString(monthSH)
        parcel.writeString(time)
        parcel.writeString(shadow)
        parcel.writeString(speed)
        parcel.writeString(price)
        parcel.writeString(caught)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SeaCreature> {
        override fun createFromParcel(parcel: Parcel): SeaCreature {
            return SeaCreature(parcel)
        }

        override fun newArray(size: Int): Array<SeaCreature?> {
            return arrayOfNulls(size)
        }
    }
}
/*
Seaweed
October to July
April to January
All Day
Large
Still
600
*/
