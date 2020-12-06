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
        return null;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

    }
}
