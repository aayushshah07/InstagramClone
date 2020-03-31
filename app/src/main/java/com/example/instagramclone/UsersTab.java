package com.example.instagramclone;


import android.content.Intent;
import android.icu.text.Transliterator;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;


/**
 * A simple {@link Fragment} subclass.
 */


public class UsersTab extends Fragment implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener {

    private ListView listView;
    private ArrayList <String> arrayList;
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

        listView.setOnItemClickListener(UsersTab.this);
        listView.setOnItemLongClickListener(UsersTab.this);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent=new Intent(getContext(),UsersPost.class);
        intent.putExtra("username",arrayList.get(position));
        startActivity(intent);



    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        final ParseQuery<ParseUser>parseQuery=ParseUser.getQuery();

        parseQuery.whereEqualTo("username",arrayList.get(position));
        parseQuery.getFirstInBackground(new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser user, ParseException e) {
                    if(user!=null && e==null)
                    {
                        //FancyToast.makeText(UsersTab.this,user.get("profileProfession"), Toast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                        final PrettyDialog prettyDialog=new PrettyDialog(getContext());

                        prettyDialog.setTitle( user.getUsername()+"'s Info").setMessage("Bio :"+user.get("profileBio")+"\n"+"Profession: "+user.get("profileProfession")+"\n"+"Hobby: "+user.get("profileHobby"))
                        .setIcon(R.drawable.person)
                    .addButton(
                            "ok", R.color.mr_dynamic_dialog_header_text_color_light, R.color.pdlg_color_black,
                            new PrettyDialogCallback() {
                                @Override
                                public void onClick() {
                                    prettyDialog.dismiss();
                                }
                            }
                    ).show();






                    }


            }
        });



        return true;
    }
}