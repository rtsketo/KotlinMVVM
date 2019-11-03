package eu.rtsketo.agileactors.viewmodel

import android.util.Log
import com.google.gson.GsonBuilder
import eu.rtsketo.agileactors.datamodel.Repository
import io.reactivex.subjects.ReplaySubject
import java.io.IOException
import java.net.InetAddress
import java.net.UnknownHostException

class ReposModel(private val repoSubscription: ReplaySubject<Repository>, private val site: String) {
    init {
        val thread = Thread(Runnable { this.populateSubscription() })
        thread.start()
    }

    private fun populateSubscription() {
        try {
            val json = SiteReader(site).getContent()
            val builder = GsonBuilder()
            builder.excludeFieldsWithoutExposeAnnotation()
            val gson = builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create()

            for (repo in gson.fromJson(json, Array<Repository>::class.java))
                repoSubscription.onNext(repo)
            repoSubscription.onComplete()
        } catch (e: IOException) {
            val error: String =
                    if (!hasInternet()) "There's no internet connection!"
                    else "The requested repository can't be reached!"
            repoSubscription.onError(Throwable(error))
            Log.e("Connection Error", "Site can't be reached!")
            repoSubscription.onComplete()
        }

    }

    private fun hasInternet(): Boolean {
        try {
            val address = InetAddress.getByName("www.github.com")
            return !address.equals("")
        } catch (e: UnknownHostException) {
            Log.e("Connection Error", "No internet!", e)
        }

        return false
    }
}
