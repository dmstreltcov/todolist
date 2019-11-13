package ru.streltsov.todolist.ui.tasklist

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.Timestamp
import java.util.*

data class Task(
    var id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val createDate: Timestamp? = null,
    val status: Long? = 0,
    val tag: String? = null,
    val remindDate: Long? = null,
    val priority: Long? = null,
    val dateStart: Timestamp? = null,
    val dateEnd: Timestamp? = null
    ) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Timestamp::class.java.classLoader),
        parcel.readLong(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readLong(),
        parcel.readParcelable(Timestamp::class.java.classLoader),
        parcel.readParcelable(Timestamp::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeParcelable(createDate, flags)
        status?.let { parcel.writeLong(it) }
        parcel.writeString(tag)
        remindDate?.let { parcel.writeLong(it) }
        priority?.let { parcel.writeLong(it) }
        parcel.writeParcelable(dateStart, flags)
        parcel.writeParcelable(dateEnd, flags)
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