package com.example.youssefiibrahim.musicsheetgenerationapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.youssefiibrahim.musicsheetgenerationapp.Common.Common;
import com.example.youssefiibrahim.musicsheetgenerationapp.Interface.ItemClickListener;
import com.example.youssefiibrahim.musicsheetgenerationapp.Interface.RankingCallback;
import com.example.youssefiibrahim.musicsheetgenerationapp.Model.QuestionScore;
import com.example.youssefiibrahim.musicsheetgenerationapp.Model.Ranking;
import com.example.youssefiibrahim.musicsheetgenerationapp.ViewHolder.RankingViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class RankingFragment extends Fragment {
    View myFragment;

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    FirebaseRecyclerAdapter<Ranking, RankingViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference questionScore, rankingTable;

    int sum = 0;

    public static RankingFragment newInstance() {
        RankingFragment rankingFragment = new RankingFragment();
        return rankingFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();
        questionScore = database.getReference("Question_Score");
        rankingTable = database.getReference("Ranking");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_ranking, container, false);

        // Init View
        recyclerView = (RecyclerView)myFragment.findViewById(R.id.rankingList);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        //Since OrderByChild method sorts lists ascendingly
        //the recycler's data will be reversed in the llmanager
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        //Implement callback
        updatedScore(Common.currentUser.getUsername(), new RankingCallback<Ranking>() {
            @Override
            public void callback(Ranking ranking) {
                //Update Ranking table
                rankingTable.child(ranking.getUsername())
                        .setValue(ranking);
                //showRanking(); //sort ranking table and display result
            }
        });

        //Adapter
        adapter  = new FirebaseRecyclerAdapter<Ranking, RankingViewHolder>(
                Ranking.class,
                R.layout.layout_ranking,
                RankingViewHolder.class,
                rankingTable.orderByChild("score")
        ) {
            @Override
            protected void populateViewHolder(RankingViewHolder viewHolder, final Ranking model, int position) {
                viewHolder.txt_name.setText(model.getUsername());
                viewHolder.txt_score.setText(String.valueOf(model.getScore()));

                //Fixed crash on click item
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onCLick(View view, int position, boolean isLongClick) {
                        Intent intent = new Intent(getActivity(), ScoreDetail.class);
                        intent.putExtra("viewUser", model.getUsername());
                        startActivity(intent);

                    }
                });
            }
        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        return myFragment;
    }


    private void updatedScore(final String username, final RankingCallback<Ranking> callback) {
        questionScore.orderByChild("user").equalTo(username)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot data:dataSnapshot.getChildren())
                        {
                            QuestionScore question_score = data.getValue(QuestionScore.class);
                            sum +=Integer.parseInt(question_score.getScore());
                        }
                        // After summing all score, process sum variable here
                        // since Firebase is async, and prcossing outside this scope
                        // will reset the score value to 0
                        Ranking ranking = new Ranking(username, sum);
                        callback.callback(ranking);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
