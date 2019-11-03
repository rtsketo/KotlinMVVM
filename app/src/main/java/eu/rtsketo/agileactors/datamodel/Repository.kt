package eu.rtsketo.agileactors.datamodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

class Repository {
    @Expose
    var name: String = ""

    @Expose
    var owner: Owner? = null

    @Expose
    val description: String = ""

    @Expose
    @SerializedName("html_url")
    var html: String? = null

    @Expose
    @SerializedName("updated_at")
    var updated: Date? = null

    @Expose
    @SerializedName("stargazers_count")
    var stars: Int = 0

    @Expose
    @SerializedName("watchers_count")
    var watchers: Int = 0

    @Expose
    @SerializedName("language")
    var lang: String = ""

    @Expose
    @SerializedName("forks_count")
    var forks: Int = 0

    @Expose
    @SerializedName("open_issues_count")
    var issues: Int = 0
}
