package com.example.dentall.beans;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dentall.R;

import java.util.List;

public class PWAdapter extends RecyclerView.Adapter<PWAdapter.PWViewHolder> {

    private List<PW> pwList;

    public void setPWs(List<PW> pwList) {
        this.pwList = pwList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PWViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pw, parent, false);
        return new PWViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PWViewHolder holder, int position) {
        if (pwList != null) {
            PW pw = pwList.get(position);
            holder.bind(pw);
        }
    }

    @Override
    public int getItemCount() {
        return pwList != null ? pwList.size() : 0;
    }

    static class PWViewHolder extends RecyclerView.ViewHolder {

        private final TextView titleTextView;
        private final TextView ObjectifTextView;

        public PWViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            ObjectifTextView = itemView.findViewById(R.id.objectifTextView);
        }

        public void bind(PW pw) {
            titleTextView.setText(pw.getTitle());
            ObjectifTextView.setText(pw.getObjectif());
        }
    }
}
