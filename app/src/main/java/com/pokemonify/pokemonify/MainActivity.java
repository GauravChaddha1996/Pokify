package com.pokemonify.pokemonify;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.pokemonify.pokemonify.UIComponents.CommonAdapter;
import com.pokemonify.pokemonify.fragments.MainFragment;
import com.pokemonify.pokemonify.fragments.PokemonDetailFragment;
import com.pokemonify.pokemonify.fragments.PokemonListFragment;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CommonAdapter.OnGetViewListener<String> {
    Toolbar toolbar;
    MaterialSearchView searchView;
    Fragment currentFragment;
    boolean submitFlag = false;
    AppBarLayout mAppBarLayout;
    Uri cameraUri;
    ContentValues values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar();
        setNavigationView();
        setSearch();
        currentFragment = new MainFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainFrameLayout, currentFragment);
        fragmentTransaction.commitAllowingStateLoss();
        supportInvalidateOptionsMenu();
    }

    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setNavigationView() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemTextAppearance(android.R.style.TextAppearance_DeviceDefault_Large);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void setSearch() {

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                submitFlag = true;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (submitFlag) {
                    submitFlag = !submitFlag;
                } else {
                    Log.d("abad", "onTextQueryChanged");
                    search(newText);
                }
                return true;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                if (!(currentFragment instanceof PokemonListFragment)) {
                    changeFrag(new PokemonListFragment());
                } else {
                    supportInvalidateOptionsMenu();
                }
            }

            @Override
            public void onSearchViewClosed() {
            }
        });
    }

    public void search(String query) {
        if (currentFragment instanceof PokemonListFragment) {
            PokemonListFragment pokemonListFragment = (PokemonListFragment) currentFragment;
            pokemonListFragment.search(query);
        }
    }

    public void hideSearch() {
        searchView.closeSearch();
    }

    public void changeFrag(Fragment fragment) {
        currentFragment = fragment;
        if (currentFragment instanceof MainFragment) {
            mAppBarLayout = (AppBarLayout) findViewById(R.id.appbarlayout);
            mAppBarLayout.setExpanded(true);
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out);
        fragmentTransaction.replace(R.id.mainFrameLayout, fragment);
        fragmentTransaction.commitAllowingStateLoss();
        supportInvalidateOptionsMenu();
    }

    public void startPokeImagePicker() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select source");
        final CommonAdapter<String> commonAdapter = new CommonAdapter<>(this);
        List<String> list = new ArrayList<>();
        list.add("Camera");
        list.add("Gallery");
        commonAdapter.setList(list);
        builder.setAdapter(commonAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if ("Camera".equals(commonAdapter.getItem(i))) {
                    values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, "New Picture");
                    values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                    cameraUri = getContentResolver().insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
                    startActivityForResult(intent, 23);
                } else {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 22);
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 22 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();
            CropImage.activity(uri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
        }
        if (requestCode == 23 && resultCode == RESULT_OK) {
            CropImage.activity(cameraUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),result.getUri());
                    PokemonDetailFragment pokemonDetailFragment = (PokemonDetailFragment) currentFragment;
                    pokemonDetailFragment.setPokemonImage(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    @Override
    public View getView(View convertView, String item, int position) {
        MyDialogViewHolder myDialogViewHolder;
        if (convertView == null) {
            myDialogViewHolder = new MyDialogViewHolder();
            convertView = LayoutInflater.from(this).inflate(R.layout.activity_imagepicker, null);
            myDialogViewHolder.mTextView = (TextView) convertView.findViewById(R.id.dialogListText);
            myDialogViewHolder.mImageView = (ImageView) convertView.findViewById(R.id.dialogListImage);
            convertView.setTag(myDialogViewHolder);
        } else {
            myDialogViewHolder = (MyDialogViewHolder) convertView.getTag();
        }
        if (item.equals("Camera")) {
            myDialogViewHolder.mTextView.setText("Camera");
            myDialogViewHolder.mImageView.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_camera));
        } else {
            myDialogViewHolder.mTextView.setText("Gallery");
            myDialogViewHolder.mImageView.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_gallery));
        }
        return convertView;
    }

    private class MyDialogViewHolder {
        TextView mTextView;
        ImageView mImageView;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            if (!(currentFragment instanceof MainFragment)) {
                changeFrag(new MainFragment());
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (currentFragment instanceof MainFragment) {
            getMenuInflater().inflate(R.menu.main, menu);
        } else if (currentFragment instanceof PokemonDetailFragment) {
            getMenuInflater().inflate(R.menu.pokemondetail, menu);
        } else {
            getMenuInflater().inflate(R.menu.other, menu);
        }
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share_myPokemon) {
            return true;
        } else if (id == R.id.action_make_current_pokemon) {
            PokemonDetailFragment pokemonDetailFragment = (PokemonDetailFragment) currentFragment;
            pokemonDetailFragment.setThisAsCurrentPokemon();
        } else if (id == R.id.action_share) {
            PokemonDetailFragment pokemonDetailFragment = (PokemonDetailFragment) currentFragment;
            pokemonDetailFragment.shareThisPokemon();
        } else if (id == R.id.action_edit) {
            PokemonDetailFragment pokemonDetailFragment = (PokemonDetailFragment) currentFragment;
            if (pokemonDetailFragment.getEditing()) {
                item.setIcon(android.R.drawable.ic_menu_edit);
            } else {
                item.setIcon(android.R.drawable.ic_menu_camera);
            }
            pokemonDetailFragment.toggleShouldEdit();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            if (!(currentFragment instanceof MainFragment)) {
                changeFrag(new MainFragment());
            }
        } else if (id == R.id.nav_poke_list) {
            if (!(currentFragment instanceof PokemonListFragment)) {
                changeFrag(new PokemonListFragment());
            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
