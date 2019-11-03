package eu.rtsketo.agileactors.viewmodel

import android.util.Log
import com.google.gson.GsonBuilder
import eu.rtsketo.agileactors.datamodel.Repository
import io.reactivex.subjects.ReplaySubject
import org.apache.commons.io.IOUtils
import java.io.IOException
import java.net.URL

class ReposModel(private val repoSubscription: ReplaySubject<Repository>, private val site: String) {
    init {
        val thread = Thread(Runnable { this.populateSubscription() })
        thread.start()
    }

    /**
     * Function for emitting the fetched
     * repositories to the subscribers.
     */
    private fun populateSubscription() {
        try {
            val json = getSiteContent()
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
        }

    }

    /**
     * Function for fetching the
     * contents of the given site.
     */
    private fun getSiteContent(): String {
        val url = URL(site).openConnection()
        url.connect()

        val input = url.getInputStream()
        val response = IOUtils.toString(input, "UTF-8")

        input.close()
        return response
    }

    /**
     * Function for checking there's
     * an internet connection.
     */
    private fun hasInternet(): Boolean {
        return try {
            val host = URL("https://www.github.com").openConnection()
            host.connectTimeout = 10000
            host.connect()
            true
        } catch (e: Exception) {
            Log.w("Connection Error", "No internet!", e)
            false
        }
    }
}
