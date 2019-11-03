package eu.rtsketo.agileactors

import org.junit.BeforeClass
import org.junit.Test


import java.util.ArrayList
import java.util.concurrent.atomic.AtomicBoolean

import eu.rtsketo.agileactors.datamodel.Repository
import eu.rtsketo.agileactors.viewmodel.ReposModel
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.ReplaySubject

import org.awaitility.Awaitility.await
import org.junit.Assert.assertTrue


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class UnitTest {
    companion object {
        private val repoList: ArrayList<Repository> = ArrayList()
        private val completed: AtomicBoolean = AtomicBoolean(false)

        @JvmStatic
        @BeforeClass
        fun init() {
            val repoSub = ReplaySubject.create<Repository>()
            val observer = object : Observer<Repository> {
                override fun onSubscribe(d: Disposable) {}
                override fun onError(e: Throwable) {
                    completed.set(true)
                }

                override fun onNext(repository: Repository) {
                    repoList.add(repository)
                }

                override fun onComplete() {
                    completed.set(true)
                }
            }

            ReposModel(repoSub, "https://api.github.com/users/rtsketo/repos")
            repoSub.subscribe(observer)
        }
    }

    @Test
    fun repoExists() {
        waitInit()
        var exists = false
        for (repo in repoList)
            if (repo.name == "FakeNewsDetection") {
                exists = true
                break
            }

        assertTrue(exists)
    }

    @Test
    fun ownerExists() {
        waitInit()
        var exists = false
        for (repo in repoList)
            if (repo.owner != null && repo.owner!!.name == "rtsketo") {
                exists = true
                break
            }

        assertTrue(exists)
    }

    private fun waitInit() {
        await().untilTrue(completed)
    }
}