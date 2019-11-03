package eu.rtsketo.agileactors.datamodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/** POJO representing the
 *  owner of a repository.
 */
class Owner {
    @Expose @SerializedName("login")
    var name: String = ""

    @Expose @SerializedName("avatar_url")
    var avatar: String? = null
}
