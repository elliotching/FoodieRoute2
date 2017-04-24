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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elliotching on 28-Jun-16.
 */
public class Foodie_main extends AppCompatActivity {

    private static String isOpen_StringKey = "isOpen";
    public Context context = this;
    public AppCompatActivity mAppCompatActivity = this;
    List<DrawerItem> dataList;
    Menu mMenu;
    MenuItem mMenuItemAdd;
    MenuItem mMenuItemCheckTkn;
    MenuItem showProp;
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
        super.onCreate(savedInstanceState);

        String themeSetting = new UniversalLayoutInitToolbarAndTheme(this, R.layout.foodie_main, R.id.m_main_toolbar, false).getTheme();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initializing
        dataList = new ArrayList<>();
        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);

        addNew_ITEM_IntoDrawerList(dataList);

        adapter = new ElliotDrawerAdapter(context, R.layout.drawer_single_item_layout_foodie_main,
                dataList);


        mDrawerRecView = (RecyclerView) findViewById(R.id.left_drawer);
        mDrawerRecView.setLayoutManager(new LinearLayoutManager(context));
        ViewGroup.LayoutParams d = mDrawerRecView.getLayoutParams();

        /*
        * Following is to programmatically
        * set WIDTH navigation drawer of  layout width:
        * if screenwidth - appbarsize > 320dp, set 320dp max,
        *
        * else remain as MATCH_PARENT.
        *
        * maximum of NavDrawer Layout Width should be 320dp on tablet screen greater than that
        * this is according to : Material Design Guide
        * https://material.io/guidelines/patterns/navigation-drawer.html#navigation-drawer-specs*/
        int screenwidth = Screen.getWidth(context);
        int appbar = ResFR.dimenPx(context, R.dimen.d_elliot_action_bar_size);
        int dp320 = Screen.getPixels(context,320);
        if( (screenwidth - appbar) > dp320){
            d.width = dp320;
        }
        mDrawerRecView.setLayoutParams(d);
        mDrawerRecView.setAdapter(adapter);

        //if pref theme = dark / light;
        ResFR r = new ResFR(context);
        if(themeSetting.equals("Dark")) mDrawerRecView.setBackgroundResource((R.color.c_sdark_cyan));

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

    public void SelectItem(int position, int listSize) {

        if (position >= listSize) {
            position = listSize - 1;
        }

        Fragment fragment = getNewFragmentAt(position);

        /***********************************************
         * HOW TO PUT ARGUMENTS::
         * Bundle mBundleArguments = new Bundle();
         * mBundleArguments.put*Boolean(KEY, VALUE);
         * fragment.setArguments(mBundleArguments);
         * *********************************************/

        if (fragment != null) {
            new SwicthFragment(fragment).execute();
        }
        mDrawerLayout.closeDrawer(mDrawerRecView);

    }

    private void addNew_ITEM_IntoDrawerList(List<DrawerItem> dataList) {
        ResFR r = new ResFR(context);
        dataList.add(new DrawerItem(r.string(R.string.s_item1)));
        dataList.add(new DrawerItem(r.string(R.string.s_item2_map)));
        dataList.add(new DrawerItem(r.string(R.string.s_item3_rec_view)));
        dataList.add(new DrawerItem(r.string(R.string.s_item4_dev)));
        dataList.add(new DrawerItem(r.string(R.string.s_item5_loadimage)));
        dataList.add(new DrawerItem(r.string(R.string.s_item6_setting)));
    }


    private Fragment getNewFragmentAt(int position) {
        if (position == 0) {
            setTitle(R.string.fr_app_name);
            setDrawerItemChoosen(position);
            return new FragmentLogIn();
        } else if (position == 1) {
//            if (dataList.get(position).choosen) {
//                return null;
//            } else {
//                setTitle(R.string.s_title_toolbar_activity_maps);
//                setDrawerItemChoosen(position);
//                return new FragmentMaps();
//            }
            Intent intent = new Intent(context, ActivityMaps.class);
            startActivity(intent);
            return null;
        } else if (position == 2) {
            Intent intent = new Intent(context, ActivityFoodListingListViewElliot.class);
            startActivity(intent);
            return null;
        } else if (position == 3) {
            Intent intent = new Intent(context, ActivityRequestData.class);
            startActivity(intent);
            return null;
        } else if (position == 4) {
            Intent intent = new Intent(context, ActivityPostHTTPDataFirebaseTest.class);
            startActivity(intent);
            return null;
        }else if (position == 5) {
            Intent intent = new Intent(context, ActivitySetting.class);
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

    @Override
    public void setTitle(int ResStringID) {
        mTitle = context.getResources().getString(ResStringID);
        mAppCompatActivity.getSupportActionBar().setTitle(mTitle);
    }

    public void setTitle(CharSequence title) {
        mTitle = title;
        mAppCompatActivity.getSupportActionBar().setTitle(mTitle);
    }

    // For Activity Drawer
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    /** MENU METHODS... */
    /* Called this methods whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
//        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerListView);
        menu.findItem(R.id.add_team).setVisible(true);
        menu.findItem(R.id.f_team).setVisible(false);
//        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    // For Activity Option Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        mMenu = menu;
//        if (FragmentLogIn.isOpened) mMenu.findItem(R.id.add_team).setVisible(false);
//        menu.findItem(R.id.action_search).setVisible(false);
        mMenuItemAdd = menu.findItem(R.id.add_team);
        mMenuItemCheckTkn = mMenu.findItem(R.id.check_token);
        showProp = menu.findItem(R.id.screen);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        //ActionProvider text =(ActionProvider)item.getActionProvider();
        if (item == mMenuItemAdd) {
            Intent intent = new Intent(context, ActivityAddFood.class);
            context.startActivity(intent);
        }
        if (item == mMenuItemCheckTkn){
            new AsyncTokenSaveUpdate(context);
        }
        if(item == showProp){
//            new Dialog_AlertNotice(context,"Properties", ""+"\n").setPositiveKey("OK",null).show();
            String rID = FirebaseInstanceId.getInstance().getId();
            System.out.println("Registration_IDS: "+rID);
        }

        return false;
    }
    /******END MENU METHOD******/




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

    private class SwicthFragment extends AsyncTask<Void, Void, Void> {
        Fragment fm;

        SwicthFragment(Fragment f) {
            fm = f;
        }

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mDrawerRecView.setAdapter(new ElliotDrawerAdapter(context, R.layout.drawer_single_item_layout_foodie_main, dataList));
            FragmentManager frgManager = mCompatActivity.getSupportFragmentManager();
            frgManager.beginTransaction().replace(R.id.content_frame, fm).commit();
            invalidateOptionsMenu();
        }
    }
}

