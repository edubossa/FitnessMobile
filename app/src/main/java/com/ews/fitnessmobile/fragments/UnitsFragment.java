package com.ews.fitnessmobile.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ews.fitnessmobile.R;
import com.ews.fitnessmobile.adapter.OnItemClickListener;
import com.ews.fitnessmobile.adapter.UnitsAdapter;
import com.ews.fitnessmobile.api.APIUtils;
import com.ews.fitnessmobile.api.FitnessAPI;
import com.ews.fitnessmobile.dao.UnidadeDAO;
import com.ews.fitnessmobile.model.Unidade;

import java.util.ArrayList;


public class UnitsFragment extends Fragment {

    private static final String TAG_LOG = "[UnitsFragment]";
    public static final String PUT_UNIT = "UNIT";

    private RecyclerView recyclerView;
    private UnitsAdapter unitsAdapter;
    private UnidadeDAO unidadeDAO;
    private FitnessAPI fitnessAPI;


    public UnitsFragment() {
        // Required empty public constructor
        this.fitnessAPI = APIUtils.getFitnessAPI();
    }

    OnItemClickListener listener = new OnItemClickListener() {
        @Override
        public void onItemClick(Unidade item) {
            Log.d(TAG_LOG, item.toString());
            Bundle bundle = new Bundle();
            bundle.putParcelable(PUT_UNIT, item);
            UnitsAddFragment unitsFragment = new UnitsAddFragment();
            unitsFragment.setArguments(bundle);
            getFragmentManager().beginTransaction()
                    .replace(R.id.content_main, unitsFragment)
                    .addToBackStack(null)
                    .commit();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_units, container, false);

        this.recyclerView = (RecyclerView) view.findViewById(R.id.unitsRecyclerView);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setHasFixedSize(true);

        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL);
        this.recyclerView.addItemDecoration(itemDecoration);

        this.unitsAdapter = new UnitsAdapter(new ArrayList<Unidade>(), listener);
        this.recyclerView.setAdapter(this.unitsAdapter);

        this.unidadeDAO = new UnidadeDAO(view.getContext());

        loadUnits();

        return view;
    }

    private void loadUnits() {
        unitsAdapter.update(this.unidadeDAO.getAll());
    }


}
