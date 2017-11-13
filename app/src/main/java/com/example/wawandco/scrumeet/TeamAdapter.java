package com.example.wawandco.scrumeet;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

/**
 * Created by wawandco on 11/9/17.
 */

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder> {

    private ArrayList<Team> teams;
    private Resources res;
    private Context contexto;
    private OnTeamClickListener clickListener;


    public TeamAdapter(Context contexto, ArrayList<Team> teams,
                            OnTeamClickListener clickListener){
        this.teams=teams;
        this.res=contexto.getResources();
        this.contexto=contexto;
        this.clickListener=clickListener;
    }


    @Override
    public TeamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_team,parent,false);
        return new TeamViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final TeamViewHolder holder, int position) {
        final Team t = teams.get(position);

        holder.name.setText(t.getName());
        holder.teamId.setText("ID: "+t.getId());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onTeamClick(t);
            }
        });

    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    public static class TeamViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView teamId;


        public TeamViewHolder(View item){
            super(item);
            name = (TextView) item.findViewById(R.id.teamName);
            teamId = (TextView) item.findViewById(R.id.teamId);
        }

    }

    public interface OnTeamClickListener{
        void onTeamClick(Team t);
    }

}
