package ru.streltsov.todolist.ui.tasklist

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Task(
    var id: String? = null,
    val title: String? = null,
    val description: String = "",
    val createDate: Timestamp? = null,
    val status: Boolean = false,
    val dateStart: Timestamp? = null
) : Parcelable, Item