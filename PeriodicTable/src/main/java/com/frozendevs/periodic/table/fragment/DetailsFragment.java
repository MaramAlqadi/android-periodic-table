package com.frozendevs.periodic.table.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.frozendevs.periodic.table.R;
import com.frozendevs.periodic.table.model.adapter.DetailsListAdapter;

public class DetailsFragment extends Fragment {

    private int atomicNumber;

    public DetailsFragment(int atomicNumber) {
        super();

        this.atomicNumber = atomicNumber;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.details_fragment, container, false);

        ListView listView = (ListView)layout.findViewById(R.id.details_list);
        listView.setAdapter(new DetailsListAdapter(getActivity(), atomicNumber));

        return layout;
    }
}
