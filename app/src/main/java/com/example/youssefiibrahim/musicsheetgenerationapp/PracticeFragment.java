package com.example.youssefiibrahim.musicsheetgenerationapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.example.youssefiibrahim.musicsheetgenerationapp.Common.Common;
import com.example.youssefiibrahim.musicsheetgenerationapp.Common.CustomWebView;
import com.example.youssefiibrahim.musicsheetgenerationapp.Common.MyFocusChangeListener;
import com.example.youssefiibrahim.musicsheetgenerationapp.Model.User;
import com.example.youssefiibrahim.musicsheetgenerationapp.MusicGeneration.MusicGen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;


public class PracticeFragment extends Fragment {

    View practiceFragment;
    Activity parent;
    Context context;
    String setCategory;
    Button category1, category2, category3, category4, category5, category6, category7, category8, category9;
    Button saveSheet;
    String current_category;
    private CustomWebView view;
    // Create a storage reference from our app
    private StorageReference storageRef;

    public static PracticeFragment newInstance() {
        PracticeFragment practiceFragment = new PracticeFragment();
        return practiceFragment;
    }

    private class CustomeGestureDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            View viewer = parent.findViewById(R.id.navigation);
            viewer.getContext();
            BottomNavigationView bnv = (BottomNavigationView) viewer;
            if(e1 == null || e2 == null) return false;
            if(e1.getPointerCount() > 1 || e2.getPointerCount() > 1) return false;
            else {
                try {
                    if(e1.getY() - e2.getY() > 20 ) {
                        // Hide Actionbar

                        bnv.setVisibility(View.GONE);//.hide();
//                        view.invalidate();
                        return false;
                    }
                    else if (e2.getY() - e1.getY() > 20 ) {
                        // Show Actionbar
                        bnv.setVisibility(View.VISIBLE);
//                        view.invalidate();
                        return false;
                    }

                } catch (Exception e) {
                    view.invalidate();
                }
                return false;
            }


        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        showSignUpDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        practiceFragment = inflater.inflate(R.layout.fragment_practice, container, false);
        showSignUpDialog();
        return practiceFragment;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        saveSheet = (Button) getView().findViewById(R.id.saveSheet);
        saveSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storageRef = FirebaseStorage.getInstance().getReference();
                Uri file = Uri.fromFile(new File("/storage/emulated/0/Android/data/com.example.youssefiibrahim.musicsheetgenerationapp/files/music.html"));
                final StorageReference riversRef = storageRef.child(Common.currentUser.getUsername()+"/music_"+ getId() + "_" + current_category + "_" + getRandomId() + ".html");

                UploadTask uploadTask = riversRef.putFile(file);

                // Register observers to listen for when the download is done or if it fails
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                List<String> savedLinks;
                                if (Common.currentUser.getSavedLinks() != null) {
                                    savedLinks = Common.currentUser.getSavedLinks();
                                } else {
                                    savedLinks = new ArrayList<>();
                                }
                                savedLinks.add(uri.toString());
                                Common.currentUser.setSavedLinks(savedLinks);
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(Common.currentUser.getUsername());
                                User updateUser = Common.currentUser;
                                databaseReference.setValue(updateUser);
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
    }

    private String getRandomId() {
        double randomDouble = Math.random();
        randomDouble = randomDouble * Integer.MAX_VALUE + 1;
        int randomInt = (int) randomDouble;
        return Integer.toString(randomInt);

    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private void showSignUpDialog() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Choose Category");
//        alertDialog.setMessage("Please fill all the information");

        LayoutInflater inflater = this.getLayoutInflater();
        final View practice_layout = inflater.inflate(R.layout.practice_layout, null);

        alertDialog.setView(practice_layout);
        alertDialog.setIcon(R.drawable.ic_account_circle_black_24dp);

        final AlertDialog dialog = alertDialog.create();
        dialog.show();
        category1 = (Button) practice_layout.findViewById(R.id.btn_category1);
        category2 = (Button) practice_layout.findViewById(R.id.btn_category2);
        category3 = (Button) practice_layout.findViewById(R.id.btn_category3);
        category4 = (Button) practice_layout.findViewById(R.id.btn_category4);
        category5 = (Button) practice_layout.findViewById(R.id.btn_category5);
        category6 = (Button) practice_layout.findViewById(R.id.btn_category6);
        category7 = (Button) practice_layout.findViewById(R.id.btn_category7);
        category8 = (Button) practice_layout.findViewById(R.id.btn_category8);
        category9 = (Button) practice_layout.findViewById(R.id.btn_category9);

        category1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String setCategory = category1.getText().toString();
                current_category = setCategory;
                displayNote(setCategory);
                dialog.dismiss();
            }
        });

        category2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String setCategory = category2.getText().toString();
                current_category = setCategory;
                displayNote(setCategory);
                dialog.dismiss();

            }
        });

        category3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCategory = category3.getText().toString();
                current_category = setCategory;
                displayNote(setCategory);
                dialog.dismiss();

            }
        });

        category4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCategory = category4.getText().toString();
                current_category = setCategory;
                displayNote(setCategory);
                dialog.dismiss();

            }
        });

        category5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCategory = category5.getText().toString();
                current_category = setCategory;
                displayNote(setCategory);
                dialog.dismiss();

            }
        });

        category6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCategory = category6.getText().toString();
                current_category = setCategory;
                displayNote(setCategory);
                dialog.dismiss();

            }
        });

        category7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCategory = category7.getText().toString();
                current_category = setCategory;
                displayNote(setCategory);
                dialog.dismiss();

            }
        });

        category8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCategory = category8.getText().toString();
                current_category = setCategory;
                displayNote(setCategory);
                dialog.dismiss();

            }
        });

        category9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCategory = category9.getText().toString();
                current_category = setCategory;
                displayNote(setCategory);
                dialog.dismiss();

            }
        });

    }

    private void displayNote(String setCategory) {
        context = getContext();
        parent = getActivity();
        MusicGen music = new MusicGen();
        music.generateMusic(setCategory, context);
        view = (CustomWebView) practiceFragment.findViewById(R.id.webView);
        view.setGestureDetector(new GestureDetector(new CustomeGestureDetector()));
        view.getSettings().setJavaScriptEnabled(true);
        view.getSettings().setBuiltInZoomControls(true);
        view.getSettings().setLoadWithOverviewMode(true);
        view.getSettings().setUseWideViewPort(true);
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Log.d("ERROR LOADING JS.TAG ","SDk version above android L so forcibaly enabling ThirdPartyCookies");
            CookieManager.getInstance().setAcceptThirdPartyCookies(view,false);
        }
        view.loadUrl("file:///" +
                "/storage/emulated/0/Android/data/com.example.youssefiibrahim.musicsheetgenerationapp/files/music.html");
        view.setWebViewClient(new PracticeFragment.MyBrowser());
    }

}


