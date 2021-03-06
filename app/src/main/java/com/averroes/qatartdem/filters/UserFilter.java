package com.averroes.qatartdem.filters;

import android.widget.Filter;

import com.averroes.qatartdem.adapters.UserAdapter;
import com.averroes.qatartdem.modals.User;

import java.util.ArrayList;

public class UserFilter extends Filter {

    private UserAdapter adapter;
    private ArrayList<User> filteredUsers;

    public UserFilter(UserAdapter adapter, ArrayList<User> users) {
        this.adapter = adapter;
        this.filteredUsers = users;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {

        FilterResults results = new FilterResults();

        if(charSequence != null && charSequence.length() > 0){
            charSequence = charSequence.toString().toUpperCase();
            ArrayList<User> filtered = new ArrayList<>();
            for(User user : filteredUsers){

                if(user.getFullname().toLowerCase().contains(charSequence)){
                    filtered.add(user);
                }
            }
            results.count = filtered.size();
            results.values = filtered;
        }
        else{
            results.count = filteredUsers.size();
            results.values = filteredUsers;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        adapter.users = (ArrayList<User>)filterResults.values;
        adapter.notifyDataSetChanged();
    }
}
