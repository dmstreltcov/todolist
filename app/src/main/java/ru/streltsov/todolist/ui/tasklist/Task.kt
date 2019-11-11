package ru.streltsov.todolist.ui.tasklist

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.Timestamp
import java.util.*

data class Task(
    var id: Long = 0,
    var title: String? = "",
    var description: String? = "",
    var timestamp: Timestamp? = Timestamp(
        Date()
    )
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Timestamp::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeParcelable(timestamp, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Task> {
        override fun createFromParcel(parcel: Parcel): Task {
            return Task(parcel)
        }

        override fun newArray(size: Int): Array<Task?> {
            return arrayOfNulls(size)
        }
    }
}