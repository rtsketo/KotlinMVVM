package eu.rtsketo.agileactors.viewmodel

import org.apache.commons.io.IOUtils

import java.io.IOException
import java.net.URL

internal class SiteReader(private val page: String) {
    @Throws(IOException::class)
    fun getContent(): String {
        val url = URL(page).openConnection()
        url.connect()

        val input = url.getInputStream()
        val response = IOUtils.toString(input, "UTF-8")

        input.close()
        return response
    }
}
