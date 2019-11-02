package eu.rtsketo.agileactors;

import org.junit.BeforeClass;
import org.junit.Test;


import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import eu.rtsketo.agileactors.datamodel.Repository;
import eu.rtsketo.agileactors.viewmodel.ReposModel;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.ReplaySubject;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertTrue;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTest {
    private static AtomicBoolean completed;
    private static ArrayList<Repository> repoList;

    @BeforeClass
    public static void init() {
        repoList = new ArrayList<>();
        completed = new AtomicBoolean(false);
        ReplaySubject<Repository> repoSub = ReplaySubject.create();
        Observer<Repository> observer = new Observer<Repository>() {
            @Override public void onSubscribe(Disposable d) { }
            @Override public void onError(Throwable e) { }

            @Override
            public void onNext(Repository repository) {
                repoList.add(repository);
            }

            @Override
            public void onComplete() { completed.set(true); }
        };

        new ReposModel(repoSub, "https://api.github.com/users/rtsketo/repos");
        repoSub.subscribe(observer);
    }

    @Test
    public void repoExists() {
        waitInit();
        boolean exists = false;
        for (Repository repo : repoList)
            if (repo.getName().equals("FakeNewsDetection")) {
                exists = true;
                break;
            }

        assertTrue(exists);
    }

    @Test
    public void ownerExists() {
        waitInit();
        boolean exists = false;
        for (Repository repo : repoList)
            if (repo.getOwner() != null
                    && repo.getOwner().getName().equals("rtsketo")) {
                exists = true;
                break;
            }

        assertTrue(exists);
    }

    private void waitInit() {
        await().untilTrue(completed);
    }
}