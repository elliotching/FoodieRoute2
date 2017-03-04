package unimas.fcsit.foodieroute;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elliotching on 28-Jun-16.
 */
public class Foodie_main extends AppCompatActivity {

    private static String isOpen_StringKey = "isOpen";
    public Context mContext = this;
    public AppCompatActivity mAppCompatActivity = this;
    List<DrawerItem> dataList;
    Menu mMenu;
    MenuItem mMenuItemAdd;
    MenuItem mMenuItemCheckTkn;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private RecyclerView mDrawerRecView;
    private ActionBarDrawerToggle mDrawerToggle;
    private Activity mActivity = this;
    private AppCompatActivity mCompatActivity = this;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private ElliotDrawerAdapter adapter;
    //    NavigationView mNavView;
    private boolean mShowVisible = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeDark);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.makan_place_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.m_main_toolbar);
        setSupportActionBar(toolbar);
        // Initializing
        dataList = new ArrayList<>();
        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);



        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);

        addNew_ITEM_IntoDrawerList(dataList);

        adapter = new ElliotDrawerAdapter(mContext, R.layout.custom_drawer_item,
                dataList);

        mDrawerRecView = (RecyclerView) findViewById(R.id.left_drawer);
        mDrawerRecView.setLayoutManager(new LinearLayoutManager(mContext));
        mDrawerRecView.setAdapter(adapter);
//        LayoutInflater inflater = LayoutInflater.from(mContext);
//        mDrawerListView.addHeaderView(inflater.inflate(R.id.));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open,
                R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        SelectItem(0, dataList.size());

        System.out.println("Elliot: Test this line, choosen"+ dataList.get(0).choosen);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
//        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerListView);
        menu.findItem(R.id.add_team).setVisible(true);
        menu.findItem(R.id.f_team).setVisible(false);
//        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

//    private String getResString(int R) {
//        return mContext.getResources().getString(R);
//    }

    public void SelectItem(int position, int listSize) {

        Fragment fragment = null;
        Bundle mBundleArguments = new Bundle();

        if (position >= listSize) {
            position = listSize - 1;
        }


        fragment = getNewFragmentAt(position);

        /***********************************************
         * HOW TO PUT ARGUMENTS::
         * mBundleArguments.put*Boolean(KEY, VALUE);
         * fragment.setArguments(mBundleArguments);
         * **********************************************/

        if (fragment != null) {
            new DoTask(fragment).execute();
        }
        mDrawerLayout.closeDrawer(mDrawerRecView);


    }

    private void addNew_ITEM_IntoDrawerList(List<DrawerItem> dataList) {
        dataList.add(new DrawerItem(ResString.get(mContext, R.string.s_item1)));
        dataList.add(new DrawerItem(ResString.get(mContext, R.string.s_item2_map)));
        dataList.add(new DrawerItem(ResString.get(mContext, R.string.s_item3_rec_view)));
        dataList.add(new DrawerItem(ResString.get(mContext, R.string.s_item4_dev)));
        dataList.add(new DrawerItem(ResString.get(mContext, R.string.s_item5_loadimage)));
    }



    private Fragment getNewFragmentAt(int position) {
        if (position == 0) {
            setTitle(R.string.fr_app_name);
            setDrawerItemChoosen(position);
            return new FragmentOne();
        } else if (position == 1) {
            if (dataList.get(position).choosen) {
                return null;
            } else {
                setTitle(R.string.s_title_activity_maps);
                setDrawerItemChoosen(position);
                return new MapsActivity();
            }
        } else if (position == 2) {
//            Intent intent = new Intent(mContext, ElliotListViewActivity.class);
//            startActivity(intent);
            return null;
        } else if (position == 3) {
            Intent intent = new Intent(mContext, ActivityRequestData.class);
            startActivity(intent);
            return null;
        } else if (position == 4) {
            Intent intent = new Intent(mContext, ActivityPostHTTPDataFirebaseTest.class);
            startActivity(intent);
            return null;
        } else {
            return null;
        }
    }

    private void setDrawerItemChoosen(int position) {
        for (int i = 0; i < dataList.size(); i++) {
            dataList.get(i).choosen = false;
        }
        dataList.get(position).choosen = true;
    }

//
//    public abstract class ElliotOnClick{
//        public
//    }

//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(newBase);
//        MultiDex.install(this);
//    }

    @Override
    public void setTitle(int ResStringID) {
        mTitle = mContext.getResources().getString(ResStringID);
        mAppCompatActivity.getSupportActionBar().setTitle(mTitle);
    }

    public void setTitle(CharSequence title) {
        mTitle = title;
        mAppCompatActivity.getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        mMenu = menu;
//        if (FragmentOne.isOpened) mMenu.findItem(R.id.add_team).setVisible(false);
//        menu.findItem(R.id.action_search).setVisible(false);
        mMenuItemAdd = menu.findItem(R.id.add_team);
        mMenuItemCheckTkn = mMenu.findItem(R.id.check_token);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        //ActionProvider s =(ActionProvider)item.getActionProvider();
        if (item == mMenuItemAdd) {
            Intent intent = new Intent(mContext, Add_Food.class);
            mContext.startActivity(intent);
        }
        if (item == mMenuItemCheckTkn){
            String token = FirebaseInstanceId.getInstance().getToken();
            System.out.println("token = "+token);
            Toast.makeText(mContext, token!=null?token.toString():"null", Toast.LENGTH_LONG).show();
        }
//            return true;

        return false;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

//    private void search_function(Menu mMenu) {
//        // Retrieve the SearchView and plug it into SearchManager
//        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(mMenu.findItem(R.id.action_search));
//        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//    }

    public class ElliotDrawerAdapter extends AdapterDrawerRecyclerViewElliot {

        public ElliotDrawerAdapter(Context context, int layoutResourceID, List<DrawerItem> listItems) {
            super(context, layoutResourceID, listItems);
        }

        @Override
        public void onClickElliot(int position, int size) {
            SelectItem(position, size);
        }
    }

    private class DoTask extends AsyncTask<Void, Void, Void> {
        Fragment fm;

        DoTask(Fragment f) {
            fm = f;
        }

        @Override
        protected Void doInBackground(Void... params) {
            publishProgress();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mDrawerRecView.setAdapter(new ElliotDrawerAdapter(mContext, R.layout.custom_drawer_item, dataList));
            FragmentManager frgManager = mCompatActivity.getSupportFragmentManager();
            frgManager.beginTransaction().replace(R.id.content_frame, fm).commit();
            invalidateOptionsMenu();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}

