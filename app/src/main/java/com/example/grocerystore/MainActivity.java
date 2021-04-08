package com.example.grocerystore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationView navigationView = findViewById(R.id.navigation_drawer);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new homefragment());


        drawerLayout = findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.start,R.string.close);


        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new homefragment()).commit();
            navigationView.setCheckedItem(R.id.home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


        switch (menuItem.getItemId())
        {

            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new homefragment()).commit();
                break;

            case R.id.addproduct:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new addproduct()).commit();
                break;

            case  R.id.viewproducts:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new viewproduct()).commit();
                break;

            case R.id.updateproduct:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new GetDetails()).commit();
                break;

            case R.id.deleteproduct:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new deleteproduct()).commit();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if (toggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}