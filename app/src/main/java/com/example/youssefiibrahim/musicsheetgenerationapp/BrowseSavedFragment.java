package com.example.youssefiibrahim.musicsheetgenerationapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.youssefiibrahim.musicsheetgenerationapp.Common.Common;
import com.example.youssefiibrahim.musicsheetgenerationapp.Model.User;

import java.util.ArrayList;
import java.util.List;

public class BrowseSavedFragment extends Fragment {

    List<String> listNotes = new ArrayList<String>();
    View browseFragment;

    public static BrowseSavedFragment newInstance() {
        BrowseSavedFragment browseFragment = new BrowseSavedFragment();
        return browseFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadNotes();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        browseFragment = inflater.inflate(R.layout.fragment_browse_saved, container, false);
        return browseFragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Activity frag = getActivity();
        System.out.print(frag.toString());
        ListView listLayout = (ListView) getActivity().findViewById(R.id.list_notes);
        if (listNotes.size() > 0) {
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    getActivity(),
                    R.layout.music_list,
                    listNotes);
            listLayout.setAdapter(arrayAdapter);
            listLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.d("THE CURRENT LINK: ", Common.currentUser.getSavedLinks().get(position));
                    Intent noteViewer = new Intent(getActivity(), NoteViewer.class);
                    Common.currentLink = Common.currentUser.getSavedLinks().get(position);
                    startActivity(noteViewer);
                }
            });
        }
    }


    private void loadNotes() {
        if (Common.currentUser.getSavedLinks() != null) {
            int size = Common.currentUser.getSavedLinks().size();
            if (size != 0) {
                for (int i = 0; i < size; i++) {
                    int index = i + 1;
                    listNotes.add("Music Sheet " + index);
                }
            }
        }
//        Collections.shuffle(Common.questionList);
    }
}
