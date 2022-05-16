package com.example.jmessagingapp.data.repository;

import androidx.annotation.NonNull;

import com.example.jmessagingapp.data.model.User;
import com.example.jmessagingapp.interfaces.MainListener;
import com.example.jmessagingapp.util.CryptHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class UserRepository {
    private static FirebaseDatabase db = FirebaseDatabase.getInstance();
    private static DatabaseReference userRef = db.getReference("user");

    public static User CURRENT_USER = null;

    public static void insertUser(String name, String email, String password, String gender) {
        String id = userRef.push().getKey();
        User user =  new User(id, name, email, CryptHelper.hashPassword(password), gender);

        userRef.child(id).setValue(user.toMap());
    }

    public static void login(String email, String password, MainListener<User> listener) {
        Query query = userRef.orderByChild("email").equalTo(email).limitToFirst(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                User user = transformSnapshot(snapshot);
                if(user == null) {
                    if(listener != null)
                        listener.onFinish(null, "Email is not registered yet!");
                }
                else {
                    if(!CryptHelper.checkPassword(password, user.getPassword())) {
                        if(listener != null)
                            listener.onFinish(null, "Password is incorrect!");
                    }
                    else {
                        if(listener != null)
                            listener.onFinish(user, "Success");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    public static void getUserById(String id, MainListener<User> listener) {
        Query query = userRef.orderByKey().equalTo(id).limitToFirst(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                User user = transformSnapshot(snapshot);
                if (listener != null)
                    listener.onFinish(user, null);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    public static void getAllUsers(MainListener<List<User>> listener) {
        Query query = userRef.orderByKey();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                List<User> users = transformAllSnapshot(snapshot);
                if (listener != null)
                    listener.onFinish(users, null);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    private static User transformSnapshot(DataSnapshot snapshot) {
        if(!snapshot.exists()) return null;

        String email, name, password, gender;
        snapshot = snapshot.getChildren().iterator().next();
        email = snapshot.child("email").getValue(String.class);
        name = snapshot.child("name").getValue(String.class);
        password = snapshot.child("password").getValue(String.class);
        gender = snapshot.child("gender").getValue(String.class);

        return new User(snapshot.getKey(), name, email, password, gender);
    }

    private static List<User> transformAllSnapshot(DataSnapshot snapshot) {
        if(!snapshot.exists()) return null;

        Vector<User> users = new Vector<>();
        Iterator<DataSnapshot> snapshots = snapshot.getChildren().iterator();
        while(snapshots.hasNext()){
            snapshot = snapshots.next();
            if(CURRENT_USER != null && CURRENT_USER.getId().equals(snapshot.getKey()))
                continue;

            String email, name, password, gender;
            email = snapshot.child("email").getValue(String.class);
            name = snapshot.child("name").getValue(String.class);
            password = snapshot.child("password").getValue(String.class);
            gender = snapshot.child("gender").getValue(String.class);

            users.add(new User(snapshot.getKey(), name, email, password, gender));
        }
        return users;
    }
}
