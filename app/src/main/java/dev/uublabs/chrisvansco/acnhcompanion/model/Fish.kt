package dev.uublabs.chrisvansco.acnhcompanion.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fish_table")
data class Fish(@PrimaryKey @NonNull @ColumnInfo(name = "name") val name: String, @ColumnInfo(name = "price") val sellPrice: String, @ColumnInfo(name = "shadow") val shadowSize: String, @ColumnInfo(name = "location") val location: String, @ColumnInfo(name = "time") val time: String, @ColumnInfo(name = "monthNH") val monthNH: String, @ColumnInfo(name = "monthSH") val monthSH: String, @ColumnInfo(name = "caught") var caught: String = "false"):Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(sellPrice)
        parcel.writeString(shadowSize)
        parcel.writeString(location)
        parcel.writeString(time)
        parcel.writeString(monthNH)
        parcel.writeString(monthSH)
        parcel.writeString(caught)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Fish> {
        override fun createFromParcel(parcel: Parcel): Fish {
            return Fish(parcel)
        }

        override fun newArray(size: Int): Array<Fish?> {
            return arrayOfNulls(size)
        }
    }
}
/*
                Bitterling
900
Tiny
River
All Day
Northern Hemisphere: November, December, January, February, March
Southern Hemisphere: May, June, July, August, September
                 */