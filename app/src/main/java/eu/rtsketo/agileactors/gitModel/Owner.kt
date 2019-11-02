package eu.rtsketo.agileactors.gitModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Owner {
    @Expose @SerializedName("login")
    val name: String? = null

    @Expose @SerializedName("avatar_url")
    val avatar: String? = null

}
