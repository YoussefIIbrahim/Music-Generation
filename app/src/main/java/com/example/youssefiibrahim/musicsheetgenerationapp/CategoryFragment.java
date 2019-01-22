package com.example.youssefiibrahim.musicsheetgenerationapp;

import android.content.Intent;
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
import android.widget.Toast;

import com.example.youssefiibrahim.musicsheetgenerationapp.Common.Common;
import com.example.youssefiibrahim.musicsheetgenerationapp.Interface.ItemClickListener;
import com.example.youssefiibrahim.musicsheetgenerationapp.Model.Category;
import com.example.youssefiibrahim.musicsheetgenerationapp.ViewHolder.CategoryViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class CategoryFragment extends Fragment {

    View myFragment;

    RecyclerView listCategory;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Category, CategoryViewHolder> adapter;
    FirebaseDatabase database;
    DatabaseReference categories;

    public static CategoryFragment newInstance() {
        CategoryFragment categoryFragment = new CategoryFragment();
        return categoryFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        categories = database.getReference("Category");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myFragment = inflater.inflate(R.layout.fragment_category, container, false);
        listCategory = (RecyclerView) myFragment.findViewById(R.id.listCategory);
        listCategory.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(container.getContext());
        listCategory.setLayoutManager(layoutManager);

        loadCategories();
        return myFragment;
    }

    private void loadCategories() {
        adapter = new FirebaseRecyclerAdapter<Category, CategoryViewHolder>(
                Category.class,
                R.layout.category_layout,
                CategoryViewHolder.class,
                categories
        ) {

            @Override
            protected void populateViewHolder(CategoryViewHolder viewHolder, final Category model, int position) {

                viewHolder.category_name.setText(model.getName());
                Picasso.get()
                        .load(model.getImage())
                        .into(viewHolder.category_image);
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onCLick(View view, int position, boolean isLongClick) {
//                        Toast.makeText(getActivity(), model.getName(), Toast.LENGTH_SHORT).show();
                        Intent startQuiz = new Intent(getActivity(), Start.class);
                        Common.categoryId = adapter.getRef(position).getKey();
                        Common.categoryName = model.getName();
                        startActivity(startQuiz);
                    }
                });
            }
        };
        adapter.notifyDataSetChanged();
        listCategory.setAdapter(adapter);
    }
}
