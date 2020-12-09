package com.averroes.qatartdem.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.averroes.qatartdem.R;
import com.averroes.qatartdem.filters.UserFilter;
import com.averroes.qatartdem.modals.User;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> implements Filterable {

    private Context context;
    public ArrayList<User> users;
    ArrayList<User> filteredUsers;

    UserFilter filter;

    public UserAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
        this.filteredUsers = users;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserHolder(LayoutInflater.from(context).inflate(R.layout.user_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, final int position) {

        holder.fullnameTV.setText(users.get(position).getFullname());
        String locationText = users.get(position).getDayra() + ", " + users.get(position).getWilaya();
        holder.locationTV.setText(locationText);
        holder.bloodTypeTV.setText(users.get(position).getBlood());

        try{
            Picasso.get().load(users.get(position).getPicture()).placeholder(R.drawable.ic_person_grey).into(holder.pictureIV);
        }catch(Exception e){
            holder.pictureIV.setImageResource(R.drawable.ic_person_grey);
        }

        holder.seeProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDetails(users.get(position));
            }
        });
    }

    private void showDetails(final User user) {

        final BottomSheetDialog dialog = new BottomSheetDialog(context);

        View view = LayoutInflater.from(context).inflate(R.layout.user_details, null);
        dialog.setContentView(view);

        ImageButton back;
        TextView fullnameTV,typeTV,bloodTypeTV,locationTV,birthDateTV,genderTV,heightTV,weightTV,phoneTV,emailTV,diseasesTV,donationsTV;
        ImageView pictureIV;
        LinearLayout diseasesLayout,donationsLayout;

        back = view.findViewById(R.id.backBtn);
        fullnameTV = view.findViewById(R.id.fullnameTV);
        typeTV = view.findViewById(R.id.typeTV);
        bloodTypeTV = view.findViewById(R.id.bloodTypeTV);
        locationTV = view.findViewById(R.id.locationTV);
        birthDateTV = view.findViewById(R.id.birthDateTV);
        genderTV = view.findViewById(R.id.genderTV);
        heightTV = view.findViewById(R.id.heightTV);
        weightTV = view.findViewById(R.id.weightTV);
        phoneTV = view.findViewById(R.id.phoneTV);
        emailTV = view.findViewById(R.id.emailTV);
        diseasesTV = view.findViewById(R.id.diseasesTV);
        donationsTV = view.findViewById(R.id.donationsTV);
        pictureIV = view.findViewById(R.id.pictureIV);
        diseasesLayout = view.findViewById(R.id.diseasesLayout);
        donationsLayout = view.findViewById(R.id.donationsLayout);

        String locationText = user.getDayra() + ", " + user.getWilaya();

        fullnameTV.setText(user.getFullname());
        typeTV.setText(user.getType());
        bloodTypeTV.setText(user.getBlood());
        locationTV.setText(locationText);
        birthDateTV.setText(user.getBirth_date());
        genderTV.setText(user.getGender());
        heightTV.setText(user.getHeight());
        weightTV.setText(user.getWeight());
        phoneTV.setText(user.getPhone());
        emailTV.setText(user.getPhone());

        if(!user.getDiseases().isEmpty()){
            diseasesLayout.setVisibility(View.VISIBLE);
            diseasesTV.setText(user.getDiseases());
        }
        else{
            diseasesLayout.setVisibility(View.GONE);
        }

        if(!user.getDonations().isEmpty()){
            donationsLayout.setVisibility(View.VISIBLE);
            donationsTV.setText(user.getDonations());
        }
        else {
            donationsLayout.setVisibility(View.GONE);
        }

        try{
            Picasso.get().load(user.getPicture()).placeholder(R.drawable.ic_person_grey).into(pictureIV);
        }catch(Exception e){
            pictureIV.setImageResource(R.drawable.ic_person_grey);
        }

        dialog.show();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    @Override
    public Filter getFilter() {
        if(filter == null){
            filter = new UserFilter(this, filteredUsers);
        }
        return filter;
    }

    class UserHolder extends RecyclerView.ViewHolder{

        private ImageView pictureIV;
        private TextView fullnameTV,locationTV,bloodTypeTV;
        private ImageButton seeProfileBtn;

        public UserHolder(@NonNull View itemView) {
            super(itemView);

            pictureIV = itemView.findViewById(R.id.pictureIV);
            fullnameTV = itemView.findViewById(R.id.fullnameTV);
            locationTV = itemView.findViewById(R.id.locationTV);
            bloodTypeTV = itemView.findViewById(R.id.bloodTypeTV);
            seeProfileBtn = itemView.findViewById(R.id.seeProfileBtn);

        }
    }
}

