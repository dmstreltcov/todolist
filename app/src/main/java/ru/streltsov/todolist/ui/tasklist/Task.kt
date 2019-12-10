package ru.streltsov.todolist.ui.tasklist

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.Timestamp

data class Task(
    var id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val createDate: Timestamp? = null,
    val status: Boolean? = false,
    val dateStart: Timestamp? = null
    ) : Parcelable {
    @SuppressLint("NewApi")
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Timestamp::class.java.classLoader),
        parcel.readBoolean(),
        parcel.readParcelable(Timestamp::class.java.classLoader)
    ) {
    }

    @SuppressLint("NewApi")
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeParcelable(createDate, flags)
        parcel.writeBoolean(status!!)
        parcel.writeParcelable(dateStart, flags)
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