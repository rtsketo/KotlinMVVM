package eu.rtsketo.agileactors.viewmodel;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import eu.rtsketo.agileactors.R;
import eu.rtsketo.agileactors.datamodel.Repository;

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.ReposViewHolder> {

    private List<Repository> repoList;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
    SimpleDateFormat zTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
    public ReposAdapter(List<Repository> repoList) {
        this.repoList = repoList;
}


    @Override
    public ReposViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.repos_item, parent, false);
        return new ReposViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReposViewHolder holder, int position) {
        Repository repo = repoList.get(position);

        if (repo.getOwner() != null && repo.getOwner().getAvatar() != null)
            Picasso.get().load(repo.getOwner().getAvatar()).into(holder.ownerAvatar);

        holder.repoName.setText(repo.getName());
        holder.description.setText(repo.getDescription());
        holder.ownerName.setText(repo.getOwner().getName());
        holder.language.setText(repo.getLang());
        holder.stars.setText(String.valueOf(repo.getStars()));
        holder.forks.setText(String.valueOf(repo.getForks()));
        holder.issues.setText(String.valueOf(repo.getIssues()));
        holder.watchers.setText(String.valueOf(repo.getWatchers()));
        holder.updated.setText(formatDate(repo.getUpdated()));
    }


    @Override
    public int getItemCount() {
        return repoList.size();
    }

    public class ReposViewHolder extends RecyclerView.ViewHolder {
        TextView repoName, description, ownerName, language,
                stars, forks, issues, watchers, updated;
        ImageView ownerAvatar;


        public ReposViewHolder(View view) {
            super(view);
            repoName = view.findViewById(R.id.repoName);
            description = view.findViewById(R.id.description);
            ownerName = view.findViewById(R.id.ownerName);
            language = view.findViewById(R.id.language);
            stars = view.findViewById(R.id.stars);
            forks = view.findViewById(R.id.forks);
            issues = view.findViewById(R.id.issues);
            watchers = view.findViewById(R.id.watchers);
            updated = view.findViewById(R.id.updated);
            ownerAvatar = view.findViewById(R.id.ownerAvatar);
        }
    }

    private String formatDate(String date) {
        String updated = "";
        if (date != null)
            try {
                updated = sdf.format(zTime.parse(date));
            } catch (ParseException e) {
                Log.e("Date parse exception!", e.toString());
            }

        return updated;
    }
}
