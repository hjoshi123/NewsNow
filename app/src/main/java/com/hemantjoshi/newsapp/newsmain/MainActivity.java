package com.hemantjoshi.newsapp.newsmain;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.*;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hemantjoshi.newsapp.NewsDetailsActivity;
import com.hemantjoshi.newsapp.R;
import com.hemantjoshi.newsapp.model.NewsModel;
import com.hemantjoshi.newsapp.utils.DividerItemDecoration;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * @author HemantJ
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, MainActivityView {

    /**
    * Declaration of required variables
    * @variable newsData for storing news from the required API in the presenter
    * @variable favNews for storing favorites from the firebase realtime database
    * @constant GOOGLE_NEWS_SEARCH is an identifier for the presenter to get the required url for API to call
    */
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    private TextView navName, navEmail, nothingText;
    private MainPresenter presenter;
    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private ArrayList<NewsModel> newsData;
    private ArrayList<String> favNews;
    private ImageView imageView;
    private ProgressBar pb;
    private CheckBox box;
    private MenuItem item;
    private DatabaseReference reference,userRef,ref;
    private FirebaseRecyclerAdapter<NewsModel,FirebaseViewHolder> adapter;
    private static final int GOOGLE_NEWS_SEARCH = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        recyclerView = findViewById(R.id.recyclerView);
        imageView = findViewById(R.id.imageView);
        nothingText = findViewById(R.id.nothing);

        mAuth = FirebaseAuth.getInstance();
        pb = findViewById(R.id.progressBar);
        ref = FirebaseDatabase.getInstance().getReference();
        reference = FirebaseDatabase.getInstance().getReference();
        favNews = new ArrayList<>();

        final DatabaseReference favRef = ref.child(mAuth.getUid()).child("fav");
        favRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                NewsModel model = dataSnapshot.getValue(NewsModel.class);
                Log.d("MainActivity",model.getTitle());
                favNews.add(model.getTitle());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("News Now");
        imageView.setVisibility(View.GONE);

        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        navName = header.findViewById(R.id.displayName);
        navEmail = header.findViewById(R.id.nav_bar_email);

        presenter = new MainPresenter(mAuth, navName, navEmail,MainActivity.this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
        presenter.setUserData();

        newsAdapter = new NewsAdapter(MainActivity.this);
        recyclerView.setAdapter(newsAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this));
        newsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            drawer.closeDrawer(GravityCompat.START);
            moveTaskToBack(true);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.politics){
            getSupportActionBar().setTitle("Politics");
            executeFunctionsForMenu(id);
            return true;
        }else if(id == R.id.tech){
            getSupportActionBar().setTitle("Tech");
            executeFunctionsForMenu(id);
            return true;
        }else if(id == R.id.sports){
            getSupportActionBar().setTitle("Sports");
            executeFunctionsForMenu(id);
            return true;
        }else if(id == R.id.entertainment){
            getSupportActionBar().setTitle("Entertainment");
            executeFunctionsForMenu(id);
            return true;
        }else if(id == R.id.buzz){
            getSupportActionBar().setTitle("BuzzFeed");
            executeFunctionsForMenu(id);
            return true;
        }else if(id == R.id.science){
            getSupportActionBar().setTitle("Science");
            executeFunctionsForMenu(id);
            return true;
        }else if(id == R.id.world){
            getSupportActionBar().setTitle("World");
            executeFunctionsForMenu(id);
            return true;
        }else if(id == R.id.business){
            getSupportActionBar().setTitle("Business");
            executeFunctionsForMenu(id);
            return true;
        }else if(id == R.id.fav){
            imageView.setVisibility(View.GONE);
            nothingText.setVisibility(View.GONE);
            getSupportActionBar().setTitle("Favorites");
            drawer.closeDrawer(GravityCompat.START);
            getNewsFromFirebase();
            adapter.notifyDataSetChanged();
            return true;
        }
        return false;
    }

    /**
     * Helper function to call when anything in the nav drawer is selected except the favorites
     *
     * @return void parameters
     */
    private void executeFunctionsForMenu(int id){
        nothingText.setVisibility(View.GONE);
        recyclerView.setAdapter(newsAdapter);
        imageView.setVisibility(View.VISIBLE);
        newsData.clear();
        presenter.getNewsFromNewsAPI(id);
        drawer.closeDrawer(GravityCompat.START);
    }

    /**
     * Gets favorite News from the Firebase real-time Database
     * using firebase recycler adapter
     * @FirebaseNodes
     * root
     * ->->uid
     * ->->->->fav
     * ->->->->->->randomNumber
     * ->->->->->->->->->->->->NewsModel Object
     * @return void
     */
    private void getNewsFromFirebase() {
        userRef = reference.child(mAuth.getUid()).child("fav");

        adapter = new FirebaseRecyclerAdapter<NewsModel, FirebaseViewHolder>
                (NewsModel.class,
                        R.layout.news_item,
                        FirebaseViewHolder.class,
                        userRef) {
            @Override
            protected void populateViewHolder(FirebaseViewHolder viewHolder, NewsModel model, int position) {
                viewHolder.bindNews(model.getTitle());
                viewHolder.bindImage(model.getImageUrl());
            }
        };

        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        item = menu.findItem(R.id.action_search);
        final android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("MainActivity",query);
                newsData.clear();
                presenter.getNewsFromNewsAPI(GOOGLE_NEWS_SEARCH, query);
                if(imageView.getDrawable() != null)
                    imageView.setVisibility(View.VISIBLE);
                newsAdapter.notifyDataSetChanged();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void addNewsData(ArrayList<NewsModel> news) {
        for(NewsModel model : news){
            newsAdapter.add(model);
        }
        newsAdapter.notifyDataSetChanged();
    }


    /*
     * Adapter for the news listing from the API
     */
    private class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
        private Context mContext;

        @Override
        public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.news_item,parent,false);
            ViewHolder viewHolder = new ViewHolder(view);

            return viewHolder;
        }

        public NewsAdapter(Context context){
            mContext = context;
            newsData = new ArrayList<>();
        }

        @Override
        public void onBindViewHolder(NewsAdapter.ViewHolder holder, int position) {
            pb.setVisibility(View.VISIBLE);

            final NewsModel news = newsData.get(position);
            if(favNews.contains(news.getTitle()) && !favNews.isEmpty())
                box.setChecked(true);
            if(position == 0){
                holder.itemView.setLayoutParams(new LinearLayout.LayoutParams(0,0));
                Picasso.with(mContext)
                        .load(news.getImageUrl())
                        .into(imageView, new Callback() {
                            @Override
                            public void onSuccess() {
                                pb.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError() {
                                pb.setVisibility(View.GONE);
                            }
                        });
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String web = news.getLinkToWeb();
                        Intent intent = new Intent(MainActivity.this, NewsDetailsActivity.class);
                        intent.putExtra("link", web);
                        startActivity(intent);
                    }
                });
                getItemCount();
            }else{
                pb.setVisibility(View.VISIBLE);
                TextView newsTitle = holder.newsTitle;
                final ImageView newsImage = holder.newsThumbnail;

                newsTitle.setText(news.getTitle());
                Picasso.with(mContext)
                        .load(news.getImageUrl())
                        .resize(400,400)
                        .into(newsImage, new Callback() {
                            @Override
                            public void onSuccess() {
                                pb.setVisibility(View.GONE);
                                newsImage.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onError() {
                                newsImage.setVisibility(View.GONE);
                            }
                        });
            }
        }

        public void add(NewsModel news){
            newsData.add(news);
        }

        @Override
        public int getItemCount() {
            return newsData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
            private TextView newsTitle;
            private View mView;
            private ImageView newsThumbnail;
            private Random random;

            public ViewHolder(View itemView) {
                super(itemView);
                mView = itemView;
                newsTitle = itemView.findViewById(R.id.newsTitle);
                newsThumbnail = itemView.findViewById(R.id.newsImage);
                box = itemView.findViewById(R.id.favClick);
                random = new Random();

                itemView.setOnClickListener(this);
                box.setOnCheckedChangeListener(this);
            }

            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                //Getting the link here
                String web = newsData.get(position).getLinkToWeb();
                Intent intent = new Intent(MainActivity.this, NewsDetailsActivity.class);
                intent.putExtra("link", web);
                startActivity(intent);
            }

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    NewsModel model = newsData.get(getAdapterPosition());
                    if(!favNews.contains(model.getTitle()))
                        ref.child(mAuth.getUid()).child("fav").
                                child((String.valueOf(random.nextInt()))).setValue(model);
                }else{
                    final DatabaseReference favRef = ref.child(mAuth.getUid()).child("fav");
                    favRef.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            NewsModel model = dataSnapshot.getValue(NewsModel.class);
                            if(favNews.contains(model.getTitle())){
                                favNews.remove(model.getTitle());
                            }
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        }
    }

    /*
     * ViewHolder for favorites acquired from Firebase
     */
    public static class FirebaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private View mView;
        private CheckBox checkBox;

        public FirebaseViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            checkBox = itemView.findViewById(R.id.favClick);
            itemView.setOnClickListener(this);
        }

        public void bindNews(String desc){
            checkBox.setChecked(true);
            TextView notesDesc = mView.findViewById(R.id.newsTitle);
            notesDesc.setText(desc);
        }

        public void bindImage(String mImageUrl){
            final ImageView im = mView.findViewById(R.id.newsImage);
            Picasso.with(mView.getContext())
                    .load(mImageUrl)
                    .resize(400,400)
                    .into(im, new Callback() {
                        @Override
                        public void onSuccess() {
                            im.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError() {
                            im.setVisibility(View.GONE);
                        }
            });
        }

        @Override
        public void onClick(View v) {
            //TODO when the item is clicked you can view the details and edit it
        }
    }
}
