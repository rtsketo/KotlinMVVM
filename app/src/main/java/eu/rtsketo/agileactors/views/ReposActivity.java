package eu.rtsketo.agileactors.views;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import eu.rtsketo.agileactors.R;
import eu.rtsketo.agileactors.datamodel.Repository;
import eu.rtsketo.agileactors.viewmodel.ReposAdapter;
import eu.rtsketo.agileactors.viewmodel.ReposModel;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.ReplaySubject;

public class ReposActivity extends AppCompatActivity {
    String site = "https://api.github.com/orgs/square/repos";
    ReplaySubject<Repository> repoSubscription;
    FloatingActionButton fab;
    ReposModel reposModel;
    ReposAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repos_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> refresh());

        repoSubscription = ReplaySubject.create();
        RecyclerView recycler = findViewById(R.id.recyclerView);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReposAdapter();
        recycler.setAdapter(adapter);

        refresh();
    }


    private void refresh() {
        adapter.clear();
        reposModel = new ReposModel(repoSubscription, site);
        repoSubscription.subscribe(getObserver());
    }


    private Observer<Repository> getObserver() {
        return new Observer<Repository>() {
            @Override
            public void onSubscribe(Disposable d) {
                runOnUiThread(()-> fab.hide());
            }

            @Override
            public void onNext(Repository repository) {
                runOnUiThread(()->
                        adapter.add(repository));
            }

            @Override
            public void onError(Throwable e) { }

            @Override
            public void onComplete() {
                runOnUiThread(()-> fab.show());
            }
        };
    }
}
