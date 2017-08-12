package com.ews.fitnessmobile.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ews.fitnessmobile.MainActivity;
import com.ews.fitnessmobile.R;
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
    private Unidade unidade;
    private Context ctx;
    private ImageView imgViewOptions;


    public UnitsFragment() {
        // Required empty public constructor
        this.fitnessAPI = APIUtils.getFitnessAPI();
        MainActivity.fabAdd.setVisibility(FloatingActionButton.VISIBLE);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_units, container, false);
        this.ctx = view.getContext();

        this.recyclerView = (RecyclerView) view.findViewById(R.id.unitsRecyclerView);
        this.imgViewOptions = (ImageView) view.findViewById(R.id.imgViewOptions);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setHasFixedSize(true);

        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL);
        this.recyclerView.addItemDecoration(itemDecoration);

        this.unitsAdapter = new UnitsAdapter(new ArrayList<Unidade>(), view.getContext(), getActivity());
        this.recyclerView.setAdapter(this.unitsAdapter);

        this.unidadeDAO = new UnidadeDAO(view.getContext());

        loadUnits();

        return view;
    }

    private void loadUnits() {
        unitsAdapter.update(this.unidadeDAO.getAll());
    }


}
