package gifse.thomas.com.br.gifse.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.util.ArrayList;
import gifse.thomas.com.br.gifse.R;
import gifse.thomas.com.br.gifse.data.dao.GifDao;
import gifse.thomas.com.br.gifse.data.dao.SearchDao;
import gifse.thomas.com.br.gifse.remote.manager.ApiDataManager;
import gifse.thomas.com.br.gifse.ui.adapter.CardAdapter;
import io.realm.Realm;
import rx.Subscriber;

public class MainActivity extends BaseActivity {
    private FloatingActionButton fab_search;
    private EditText input_search;
    private TextInputLayout input_layout_search;
    private Realm realm;
    private ApiDataManager dataManager;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static final int MY_PERMISSIONS_REQUEST_INTERNET = 0;
    ProgressBar pgb_loading;
    ArrayList<String> searchData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input_search = (EditText) findViewById(R.id.input_search);
        fab_search = (FloatingActionButton) findViewById(R.id.fab_search);
        input_layout_search = (TextInputLayout) findViewById(R.id.input_layout_search);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_results);
        pgb_loading = (ProgressBar) findViewById(R.id.pgb_loading);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        searchData = new ArrayList();


        dataManager = new ApiDataManager(MainActivity.this);
        fab_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnline()) {
                    if (input_search.getText().length() <= 0) {
                        input_layout_search.setError(getResources().getString(R.string.insert_a_text));
                    } else {
                        search();
                    }
                } else {
                    Toast.makeText(MainActivity.this, getString(R.string.no_connection), Toast.LENGTH_LONG).show();
                }
            }
        });

        if (isOnline()) {
            getTrending();
        } else {
            Toast.makeText(this, "No internet connection!",Toast.LENGTH_LONG).show();

        }
    }

    private void getTrending() {
        setupToolbar("Trending Gifs");
        //Insert the Search word on database
        pgb_loading.setVisibility(View.VISIBLE);
        input_layout_search.setError(null);
//        InputMethodManager inputManager = (InputMethodManager) MainActivity.this.getSystemService(MainActivity.this.INPUT_METHOD_SERVICE);
//        inputManager.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

        // Clear list data
        if (mRecyclerView.getAdapter() != null) {
            searchData.clear();
            mAdapter.notifyDataSetChanged();
        }

        realm = Realm.getDefaultInstance();
        dataManager.getTrendings()
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        realm.close();
                        // When finish load all data, put it into the recycler
                        pgb_loading.setVisibility(View.INVISIBLE);
                        mAdapter = new CardAdapter(MainActivity.this, searchData);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                    @Override
                    public void onError(Throwable e) {
                        realm.close();
                        Log.e(">> ERROR", e.getMessage());
                        pgb_loading.setVisibility(View.INVISIBLE);
                    }
                    @Override
                    public void onNext(String value) {
                        // If valid data, add it to data list
                        if (value != null) {
                            searchData.add(value);
                            GifDao dao = new GifDao(realm);
                            dao.insert(value);
                        }
                    }
                });
    }

    private void search() {
        setupToolbar(" ");
        //Insert the Search word on database
        pgb_loading.setVisibility(View.VISIBLE);
        input_layout_search.setError(null);

        realm = Realm.getDefaultInstance();
        SearchDao dao = new SearchDao(realm);
        dao.insert(input_search.getText().toString());

        InputMethodManager inputManager = (InputMethodManager) MainActivity.this.getSystemService(MainActivity.this.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

        // Clean list data
        if (mRecyclerView.getAdapter() != null) {
            searchData.clear();
            mAdapter.notifyDataSetChanged();
        }

        dataManager.search(input_search.getText().toString())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        realm.close();
                        // When finish load all data, put it into the recycler
                        pgb_loading.setVisibility(View.INVISIBLE);
                        mAdapter = new CardAdapter(MainActivity.this, searchData);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                    @Override
                    public void onError(Throwable e) {
                        realm.close();
                        Log.e(">> ERROR", e.getMessage());
                        pgb_loading.setVisibility(View.INVISIBLE);
                    }
                    @Override
                    public void onNext(String value) {
                        // If valid data, add it to data list
                        if (value != null) {
                            searchData.add(value);
                            GifDao dao = new GifDao(realm);
                            dao.insert(value);
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isOnline()) {
            Toast.makeText(MainActivity.this, getString(R.string.no_connection), Toast.LENGTH_LONG).show();
        }
    }

    private void setupToolbar(String sTitle) {
        final android.support.v7.app.ActionBar ab = getActionBarToolbar();
        ab.setDisplayHomeAsUpEnabled(false);
        ab.setTitle(sTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Add options menu on toolbar
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //Enable to open the activity to show the Search history
            case R.id.action_search_history:
                //This is because it is possible to Search for used terms again
                startActivityForResult(new Intent(MainActivity.this, HistoryActivity.class),1);
                return true;
            case R.id.action_trending:
                //This is because it is possible to Search for used terms again
                getTrending();
                return true;
            case R.id.action_downloaded_gifs:
                //This is because it is possible to Search for used terms again
                startActivityForResult(new Intent(MainActivity.this, DownloadedActivity.class),1);
                return true;
            case R.id.action_see_random:
                //This is because it is possible to Search for used terms again
                startActivityForResult(new Intent(MainActivity.this, RandomActivity.class),1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //When an item from the list is selected, the activity Search fot it again
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                input_search.setText(data.getStringExtra("search_word"));
                search();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_INTERNET: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getTrending();
                } else {
                    Toast.makeText(MainActivity.this,"Can't make request without internet permission",Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }


    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(MainActivity.this.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}

