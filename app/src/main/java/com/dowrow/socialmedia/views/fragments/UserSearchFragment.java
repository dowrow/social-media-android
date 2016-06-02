package com.dowrow.socialmedia.views.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dowrow.socialmedia.R;
import com.dowrow.socialmedia.controllers.UserSearchController;

public class UserSearchFragment extends Fragment {

    private static UserSearchFragment instance = null;

    private UserSearchController controller;

    public UserSearchFragment() {
    }

    public static UserSearchFragment newInstance() {
        if (instance == null) {
            instance = new UserSearchFragment();
        }
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = UserSearchController.getInstance(this);
        Button searchButton = (Button)view.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.setQuery(((EditText) view.findViewById(R.id.search_text)).getText().toString());
                controller.refresh();
                try {
                    InputMethodManager inputMethodManager = (InputMethodManager)view.getContext()
                            .getSystemService(Activity.INPUT_METHOD_SERVICE);
                    if (inputMethodManager != null) {
                        inputMethodManager
                                .hideSoftInputFromWindow(((Activity)view.getContext())
                                        .getCurrentFocus().getWindowToken(), 0);
                    }
                } catch (Exception e) {
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_search, container, false);
    }

}
