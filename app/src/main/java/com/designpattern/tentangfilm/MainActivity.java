package com.designpattern.tentangfilm;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.designpattern.tentangfilm.menu.DrawerAdapter;
import com.designpattern.tentangfilm.menu.DrawerItem;
import com.designpattern.tentangfilm.menu.SimpleItem;
import com.designpattern.tentangfilm.menu.SpaceItem;
import com.designpattern.tentangfilm.ui.favorite.FavoriteFragment;
import com.designpattern.tentangfilm.ui.home.HomeFragment;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener {

    private static final int POS_DASHBOARD = 0;
    private static final int POS_ACCOUNT = 1;

    private String[] screenTitles;
    private Drawable[] screenIcons;
    private Toolbar toolbar;
    private TextView tv1;


    private SlidingRootNav slidingRootNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setElevation(0);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);


        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer)
                .inject();


        tv1 = findViewById(R.id.textView);


        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_DASHBOARD).setChecked(true),
                createItemFor(POS_ACCOUNT),
                new SpaceItem(48)));
        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(POS_DASHBOARD);
    }

    @Override
    public void onItemSelected(int position) {
        boolean state = false;
        switch (position) {

            case POS_DASHBOARD:
                HomeFragment homeFragment = new HomeFragment();
                showFragment(homeFragment);

                break;

            case POS_ACCOUNT:
                state = true;
                FavoriteFragment favoriteFragment = new FavoriteFragment();
                showFragment(favoriteFragment);

                break;

        }


        showTitle(state, screenTitles[position]);
        slidingRootNav.closeMenu();

    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }


    private void showTitle(boolean state, String titleBar) {
        if (state) {
            toolbar.setTitle(" ");
            tv1.setVisibility(View.VISIBLE);
        } else {
            tv1.setVisibility(View.GONE);
            toolbar.setTitle(" ");
        }
    }

    @SuppressWarnings("rawtypes")
    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.black))
                .withTextTint(color(R.color.black))
                .withSelectedIconTint(color(R.color.teal_700))
                .withSelectedTextTint(color(R.color.teal_700));
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }
}
