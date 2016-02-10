package com.example.narcis.sportnews;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.narcis.sportnews.adapter.SlidingMenuAdapter;
import com.example.narcis.sportnews.fragment.Fragment1;
import com.example.narcis.sportnews.fragment.Fragment2;
import com.example.narcis.sportnews.fragment.Fragment3;
import com.example.narcis.sportnews.model.ItemSlideMenu;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener {


    private List<ItemSlideMenu> listSliding;
    private SlidingMenuAdapter adapter;
    private ListView listViewSliding;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    UserLocalStore userLocalStore;
    EditText etName, etAge, etUsername;
    Button bLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etName = (EditText) findViewById(R.id.etName);
        etAge = (EditText) findViewById(R.id.etAge);
        bLogout = (Button) findViewById(R.id.bLogout);

        bLogout.setOnClickListener(this);

        userLocalStore = new UserLocalStore(this);

        // Sliding Menu
        listViewSliding = (ListView) findViewById(R.id.lv_sliding_menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listSliding = new ArrayList<>();
        listSliding.add(new ItemSlideMenu(R.drawable.ic_action_settings, "Settings"));
        listSliding.add(new ItemSlideMenu(R.drawable.ic_action_about, "About"));
        adapter = new SlidingMenuAdapter(this, listSliding);
        listViewSliding.setAdapter(adapter);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(listSliding.get(0).getTitle());

        listViewSliding.setItemChecked(0, true);

        drawerLayout.closeDrawer(listViewSliding);

        listViewSliding.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setTitle(listSliding.get(position).getTitle());

                listViewSliding.setItemChecked(position, true);

                drawerLayout.closeDrawer(listViewSliding);
            }
        });

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_opened, R.string.drawer_closed) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);


        String url = "http://m.gsp.ro";
        WebView view = (WebView) this.findViewById(R.id.webView);
        view.getSettings().setJavaScriptEnabled(true);
        view.loadUrl(url);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bLogout:
                userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(false);
                Intent loginIntent = new Intent(this, Login.class);
                startActivity(loginIntent);
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (authenticate() == true) {
            displayUserDetails();
        }
    }

    private boolean authenticate() {
        if (userLocalStore.getLoggedInUser() == null) {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            return false;
        }
        return true;
    }

    private void displayUserDetails() {
        User user = userLocalStore.getLoggedInUser();
        etUsername.setText(user.username);
        etName.setText(user.name);
        etAge.setText(user.age + "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    private void replaceFragment(int pos) {
        Fragment fragment = null;
        switch (pos) {
            case 0:
                fragment = new Fragment1();
                break;
            case 1:
                fragment = new Fragment2();
                break;
            case 2:
                fragment = new Fragment3();
                break;
            default:
                fragment = new Fragment1();
                break;
        }

        if(null!=fragment) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main_content, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

    }
}
