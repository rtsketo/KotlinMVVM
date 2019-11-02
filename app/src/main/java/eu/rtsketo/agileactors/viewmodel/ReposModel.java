package eu.rtsketo.agileactors.viewmodel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import eu.rtsketo.agileactors.datamodel.Repository;
import io.reactivex.subjects.ReplaySubject;

public class ReposModel {
    ReplaySubject<Repository> repoSubscription;
    Thread thread;
    String site;

    public ReposModel(ReplaySubject<Repository> repoSubscription, String site) {
        this.repoSubscription = repoSubscription;
        thread = new Thread(this::populateSubscription);
        this.site = site;
        thread.start();
    }

    private void populateSubscription() {
        try {
            String json = new SiteReader(site).getContent();
            GsonBuilder builder = new GsonBuilder();
            builder.excludeFieldsWithoutExposeAnnotation();
            Gson gson = builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();

            for (Repository repo : gson.fromJson(json, Repository[].class))
                repoSubscription.onNext(repo);
            repoSubscription.onComplete();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
