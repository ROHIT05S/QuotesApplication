package com.example.quotesapp.data

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import javax.annotation.processing.Generated

@Parcelize
data class QuotesResult (
    @SerializedName("q")
    val quote: String,

    @SerializedName("a")
    val author: String,

    @SerializedName("c")
    val count:String,

    @SerializedName("h")
    var heading: String
):Parcelable