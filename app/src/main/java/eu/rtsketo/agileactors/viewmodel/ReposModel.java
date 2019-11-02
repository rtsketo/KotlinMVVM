package eu.rtsketo.agileactors.viewmodel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import eu.rtsketo.agileactors.ReposActivity;
import eu.rtsketo.agileactors.datamodel.Repository;

public class ReposModel {
    public ReposModel(ReposActivity activity) throws IOException {

        String json = new SiteReader("https://api.github.com/orgs/square/repos").getContent();

        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        Gson gson = builder.create();

        Repository[] repos = gson.fromJson(json, Repository[].class);
        System.out.println(repos[0].getOwner().getName());

        activity.runOnUiThread(()->
        activity.setData(repos));
    }



}
