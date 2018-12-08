package target.com.targettestproj.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GitAccount(@SerializedName("username") val username : String,
                      @SerializedName("name") val name : String,
                      @SerializedName("url") val url : String,
                      @SerializedName("avatar") val avatar : String,
                      @SerializedName("repo") val repo : Repo) : Parcelable

@Parcelize
data class Repo (@SerializedName("name") val name : String,
                 @SerializedName("description") val description : String,
                 @SerializedName("url") val url : String) : Parcelable