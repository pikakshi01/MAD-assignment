package com.example.mad_question5

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MyData(val id: Int, val name: String) : Parcelable
