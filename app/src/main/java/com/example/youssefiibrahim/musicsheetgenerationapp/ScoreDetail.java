package com.example.youssefiibrahim.musicsheetgenerationapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.youssefiibrahim.musicsheetgenerationapp.Model.QuestionScore;
import com.example.youssefiibrahim.musicsheetgenerationapp.ViewHolder.ScoreDetailViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ScoreDetail extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference question_score;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<QuestionScore, ScoreDetailViewHolder> adapter;
    String viewUser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_detail);

        //Firebase
        database = FirebaseDatabase.getInstance();
        question_score = database.getReference("Question_Score");

        //View
        recyclerView = (RecyclerView) findViewById(R.id.scoreList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if (getIntent() != null)
            viewUser = getIntent().getStringExtra("viewUser");
        if (!viewUser.isEmpty())
            loadScoreDetail(viewUser);
    }

    private void loadScoreDetail(String viewUser) {
        adapter = new FirebaseRecyclerAdapter<QuestionScore, ScoreDetailViewHolder>(
                QuestionScore.class,
                R.layout.score_detail_layout,
                ScoreDetailViewHolder.class,
                question_score.orderByChild("user").equalTo(viewUser)
        ) {
            @Override
            protected void populateViewHolder(ScoreDetailViewHolder viewHolder, QuestionScore model, int position) {
                Log.d("DEBUGER", model.getScore());
                viewHolder.txt_name.setText(model.getCategoryName());
                viewHolder.txt_score.setText(model.getScore());
            }
        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

}
