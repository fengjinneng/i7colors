package com.company.qcy.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.company.qcy.R;
import com.mancj.materialsearchbar.MaterialSearchBar;

public class SearchActivity extends AppCompatActivity implements MaterialSearchBar.OnSearchActionListener {

    private MaterialSearchBar mMaterialsearchbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
    }

    private void initView() {
        mMaterialsearchbar = (MaterialSearchBar) findViewById(R.id.materialsearchbar);

        mMaterialsearchbar.setOnSearchActionListener(this);
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {

    }

    @Override
    public void onButtonClicked(int buttonCode) {

    }
}
