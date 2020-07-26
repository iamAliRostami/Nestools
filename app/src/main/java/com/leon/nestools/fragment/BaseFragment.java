package com.leon.nestools.fragment;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import com.leon.nestools.activities.MainActivity;

import java.util.Objects;

public abstract class BaseFragment extends Fragment {
    @SuppressLint({"NewApi", "UseRequireInsteadOfGet"})
    public ActionBar getActionBar() {
        return ((MainActivity) Objects.requireNonNull(getActivity())).getSupportActionBar();
    }
}
