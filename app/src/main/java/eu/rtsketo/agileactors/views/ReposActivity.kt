package eu.rtsketo.agileactors.views

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.internal.NavigationMenu
import com.google.android.material.navigation.NavigationView

import eu.rtsketo.agileactors.R
import eu.rtsketo.agileactors.datamodel.Repository
import eu.rtsketo.agileactors.viewmodel.ReposAdapter
import eu.rtsketo.agileactors.viewmodel.ReposModel
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.ReplaySubject
import kotlinx.android.synthetic.main.repos_activity.*

class ReposActivity : AppCompatActivity() {
    private val site = "https://api.github.com/orgs/square/repos"
    private val adapter = ReposAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.repos_activity)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { v -> refresh() }

        val recycler = findViewById<RecyclerView>(R.id.recyclerView)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        refresh()
    }

    private fun refresh() {
        adapter.clear()
        val repoSubscription: ReplaySubject<Repository> = ReplaySubject.create()
        ReposModel(repoSubscription, site)
        repoSubscription.subscribe(observer)
    }

    private fun displayError(message: String?) {
        AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton("OK") {
                    d, i -> d.dismiss()
                }
                .show()
        fab.show()
    }

    private val observer: Observer<Repository>
        get() = object : Observer<Repository> {
            override fun onComplete() {
                runOnUiThread { fab.show() }
            }

            override fun onSubscribe(d: Disposable) {
                runOnUiThread { fab.hide() }
            }

            override fun onNext(repository: Repository) {
                runOnUiThread { adapter.add(repository) }
            }

            override fun onError(e: Throwable) {
                runOnUiThread { displayError(e.message) }
            }
        }

}
