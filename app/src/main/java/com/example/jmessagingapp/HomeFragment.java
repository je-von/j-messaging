package com.example.jmessagingapp;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jmessagingapp.adapter.UserAdapter;
import com.example.jmessagingapp.data.model.User;
import com.example.jmessagingapp.data.repository.UserRepository;
import com.example.jmessagingapp.interfaces.MainListener;

import java.util.List;
import java.util.Vector;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements MainListener<List<User>> {

    private RecyclerView rvUsers;
    private List<User> users = new Vector<>();
    private UserAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        init(view);
        return view;
    }

    private void init(View view){
        rvUsers = view.findViewById(R.id.rv_chats);
        rvUsers.setLayoutManager(new LinearLayoutManager(view.getContext()));

        adapter = new UserAdapter(users);
        rvUsers.setAdapter(adapter);

        UserRepository.getAllUsers(this);

//        ((Activity) view.getContext()).getActionBar().setTitle("JMessaging");
    }

    @Override
    public void onFinish(List<User> data, String message) {
        for(User u : data){
            Log.d(u.getName(), u.getEmail() + " - " + u.getId());
        }

        users = data;
        if(users != null){
            adapter.users = users;
            adapter.notifyDataSetChanged();
        }



    }
}