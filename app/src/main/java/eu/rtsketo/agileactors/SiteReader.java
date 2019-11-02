package eu.rtsketo.agileactors;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.net.URL;

public class SiteReader {
    private String site;
    private String content;

    public SiteReader(String site) {
        this.site = site;
    }

    public String getContent() throws IOException {
        if (content == null)
            content = getPage(site);
        return content;
    }

    private String getPage(String page) throws IOException {
        URLConnection url = new URL(page).openConnection();
        url.connect();

        InputStream input = url.getInputStream();
        String response = IOUtils.toString(input, "UTF-8");

        input.close();
        return response;
    }

}
