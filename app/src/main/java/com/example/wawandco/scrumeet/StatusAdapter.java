package com.example.wawandco.scrumeet;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by wawandco on 11/13/17.
 */

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.UpdateViewHolder>  {

    private ArrayList<StatusUpdate> userUpdates;
    private Resources res;
    private Context contexto;
    private OnStatusClickListener clickListener;


    public StatusAdapter(Context contexto, ArrayList<StatusUpdate> userUpdates,
                         OnStatusClickListener clickListener){
        this.userUpdates=userUpdates;
        this.res=contexto.getResources();
        this.contexto=contexto;
        this.clickListener=clickListener;
    }


    @Override
    public UpdateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_status_update,parent,false);
        return new UpdateViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final UpdateViewHolder holder, int position) {
        final StatusUpdate s = userUpdates.get(position);

        holder.name.setText(s.getUsername());
        holder.did.setText(s.getDid());
        holder.willDo.setText(s.getWilldo());
        holder.blockers.setText(s.getBlockers());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onStatusClick(s);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userUpdates.size();
    }

    public static class UpdateViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView did;
        private TextView willDo;
        private TextView blockers;


        public UpdateViewHolder(View item){
            super(item);
            name = (TextView) item.findViewById(R.id.username);
            did = (TextView) item.findViewById(R.id.did_value);
            willDo = (TextView) item.findViewById(R.id.will_do_value);
            blockers = (TextView) item.findViewById(R.id.blockers_value);

        }

    }

    public interface OnStatusClickListener{
        void onStatusClick(StatusUpdate userUpdates);
    }
}
