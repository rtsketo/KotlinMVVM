package eu.rtsketo.agileactors.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.squareup.picasso.Picasso

import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date

import eu.rtsketo.agileactors.R
import eu.rtsketo.agileactors.datamodel.Repository

class ReposAdapter : RecyclerView.Adapter<ReposAdapter.ReposViewHolder>() {
    private val repoList: ArrayList<Repository> = ArrayList()
    private val sdf = SimpleDateFormat("dd/MM/yy")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.repos_item, parent, false)
        return ReposViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReposViewHolder, position: Int) {
        val repo = repoList[position]

        Picasso.get().load(repo.owner?.avatar).into(holder.ownerAvatar)
        holder.repoName.text = repo.name
        holder.description.text = repo.description
        holder.ownerName.text = repo.owner!!.name
        holder.language.text = repo.lang
        holder.stars.text = repo.stars.toString()
        holder.forks.text = repo.forks.toString()
        holder.issues.text = repo.issues.toString()
        holder.watchers.text = repo.watchers.toString()
        holder.updated.text = formatDate(repo.updated)
    }

    inner class ReposViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var repoName: TextView = view.findViewById(R.id.repoName)
        internal var description: TextView = view.findViewById(R.id.description)
        internal var ownerName: TextView = view.findViewById(R.id.ownerName)
        internal var language: TextView = view.findViewById(R.id.language)
        internal var stars: TextView = view.findViewById(R.id.stars)
        internal var forks: TextView = view.findViewById(R.id.forks)
        internal var issues: TextView = view.findViewById(R.id.issues)
        internal var watchers: TextView = view.findViewById(R.id.watchers)
        internal var updated: TextView = view.findViewById(R.id.updated)
        internal var ownerAvatar: ImageView = view.findViewById(R.id.ownerAvatar)
    }

    override fun getItemCount(): Int {
        return repoList.size
    }

    fun add(repo: Repository) {
        repoList.add(repo)
        notifyItemInserted(repoList.size - 1)
    }

    fun clear() {
        val size = repoList.size
        repoList.clear()
        notifyItemRangeRemoved(0, size)
    }

    private fun formatDate(date: Date?): String {
        return if (date != null) sdf.format(date) else ""
    }
}
