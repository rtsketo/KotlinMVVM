package eu.rtsketo.agileactors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import eu.rtsketo.agileactors.gitPOJO.Repository;

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.ReposViewHolder> {

    private List<Repository> repoList;
    View selectBank;


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
        holder.bankName.setText(repoList.get(position).getName());
    }


    @Override
    public int getItemCount() {
        return repoList.size();
    }

    public class ReposViewHolder extends RecyclerView.ViewHolder {
        TextView bankName;

        public ReposViewHolder(View view) {
            super(view);
            bankName =  view.findViewById(R.id.repoName);
        }
    }
}
