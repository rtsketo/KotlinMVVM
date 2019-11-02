package eu.rtsketo.agileactors.gitPOJO

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Repository {
    @Expose
    val name: String = ""

    @Expose
    val owner: Owner? = null

    @Expose
    val description: String = ""

    @Expose
    @SerializedName("html_url")
    val html: String? = null

    @Expose
    @SerializedName("updated_at")
    val updated: String? = null

    @Expose
    @SerializedName("stargazers_count")
    val stars: Int = 0

    @Expose
    @SerializedName("watchers_count")
    val watchers: Int = 0

    @Expose
    @SerializedName("language")
    val lang: String = ""

    @Expose
    @SerializedName("forks_count")
    val forks: Int = 0

    @Expose
    @SerializedName("open_issues_count")
    val issues: Int = 0
}
