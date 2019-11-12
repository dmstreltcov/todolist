package ru.streltsov.todolist.ui.tasklist

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.Timestamp

data class Task(
    var title: String? = null,
    var description: String? = null,
    var createDate: Timestamp? = Timestamp.now(),
    var status: String? = null,
    var tag: String? = null,
    var remind: Long? = null,
    var remindDate: Long? = null,
    var priority: Long? = null,
    var dateStart: Timestamp? = null,
    var dateEnd: Timestamp? = null
    ) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Timestamp::class.java.classLoader),
        parcel.readString(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readLong(),
        parcel.readLong(),
        parcel.readParcelable(Timestamp::class.java.classLoader),
        parcel.readParcelable(Timestamp::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeParcelable(createDate, flags)
        parcel.writeString(status)
        parcel.writeString(tag)
        remind?.let { parcel.writeLong(it) }
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