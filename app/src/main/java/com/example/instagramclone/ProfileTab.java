package com.example.instagramclone;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileTab extends Fragment {

    private EditText edtProfileName,edtProfileBio,edtProfileHobby,edtProfileProfession;
    private Button btnUpdateInfo;


    public ProfileTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_tab, container, false);//This method is same like setContent view in MainActivity.java
        edtProfileName = view.findViewById(R.id.edtProfileName);
        edtProfileBio = view.findViewById(R.id.edtProfileBio);
        edtProfileHobby = view.findViewById(R.id.edtProfileHobby);
        edtProfileProfession = view.findViewById(R.id.edtProfileProfession);
        btnUpdateInfo = view.findViewById(R.id.btnUpdateInfo);

        final ParseUser parseUser = ParseUser.getCurrentUser();
        if (parseUser.get("profileName") == null) {
            edtProfileName.setText("");

        } else {
            edtProfileName.setText(parseUser.get("profileName").toString());

        }
        if (parseUser.get("profileBio") == null) {
            edtProfileBio.setText("");
        } else {
            edtProfileBio.setText(parseUser.get("profileBio").toString());

        }
        if(parseUser.get("profileProfession")==null)
        {
            edtProfileProfession.setText("");
        }
        else
        {
            edtProfileProfession.setText(parseUser.get("profileProfession").toString());

        }
        if(parseUser.get("profileHobby")==null)
        {
            edtProfileHobby.setText("");
        }
        else
        edtProfileHobby.setText(parseUser.get("profileHobby").toString());


        btnUpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                parseUser.put("profileName",edtProfileName.getText()+"");
                parseUser.put("profileBio",edtProfileBio.getText()+"");
                parseUser.put("profileHobby",edtProfileHobby.getText()+"");
                parseUser.put("profileProfession",edtProfileProfession.getText()+"");

                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null)
                        {
                            FancyToast.makeText(getContext(),"Information Updated",
                                    Toast.LENGTH_SHORT,
                                    FancyToast.INFO, true).show();
                        }
                        else
                        {
                            FancyToast.makeText(getContext(),e.getMessage(),
                                    Toast.LENGTH_SHORT,
                                    FancyToast.ERROR, true).show();

                        }
                    }
                });
            }
        });

        return view;
    }

}
