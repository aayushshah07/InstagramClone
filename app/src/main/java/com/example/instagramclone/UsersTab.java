package com.example.instagramclone;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */

public class UsersTab extends Fragment {

    private ListView listView;
    private ArrayList arrayList;
    private ArrayAdapter arrayAdapter;// ArrayAdapter is controller which update our list view based on data

    public UsersTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_users_tab, container, false);
        listView=view.findViewById(R.id.listView);
        arrayList=new ArrayList();

        final TextView txtUpdate;
        txtUpdate=view.findViewById(R.id.txtUpadate);

        arrayAdapter=new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item,arrayList);
        // to get data from server or to fetvh data from server
        ParseQuery<ParseUser>parseQuery=ParseUser.getQuery();
        parseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());

        parseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e==null)
                {
                    if(objects.size()>0)
                    {
                        for(ParseUser user:objects)
                        {
                               arrayList.add(user.getUsername());
                        }

                        listView.setAdapter(arrayAdapter);
                        txtUpdate.animate().alpha(0f).setDuration(2000);
                        listView.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        return view;
    }

}
