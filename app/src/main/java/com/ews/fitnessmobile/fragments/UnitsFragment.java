package com.ews.fitnessmobile.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;

import com.ews.fitnessmobile.MainActivity;
import com.ews.fitnessmobile.R;
import com.ews.fitnessmobile.adapter.UnitsAdapter;
import com.ews.fitnessmobile.api.APIUtils;
import com.ews.fitnessmobile.api.FitnessAPI;
import com.ews.fitnessmobile.dao.UnidadeDAO;
import com.ews.fitnessmobile.model.Unidade;

import java.util.ArrayList;
import java.util.List;


public class UnitsFragment extends Fragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {

    private static final String TAG_LOG = "[UnitsFragment]";
    public static final String PUT_UNIT = "UNIT";

    private RecyclerView recyclerView;
    private UnitsAdapter unitsAdapter;
    private UnidadeDAO unidadeDAO;
    private FitnessAPI fitnessAPI;
    private Unidade unidade;
    private Context ctx;
    private ImageView imgViewOptions;

    private List<Unidade> unidades;


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

        this.unidadeDAO = new UnidadeDAO(view.getContext());
        this.unidades = this.unidadeDAO.getAll();

        this.unitsAdapter = new UnitsAdapter(this.unidades, view.getContext(), getActivity());
        this.recyclerView.setAdapter(this.unitsAdapter);

        setHasOptionsMenu(true);

        return view;
    }

    public void resetSearch() {
        unitsAdapter.update(this.unidades);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Search"); //TODO alterar para buscar so string com suporte aos idiomas portugues e ingles
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.viewMap :
                getFragmentManager().beginTransaction()
                        .replace(R.id.content_main, new UnitsMapActivity())
                        .addToBackStack(null)
                        .commit();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText == null || newText.trim().isEmpty()) {
            resetSearch();
            return false;
        }

        List<Unidade> filteredValues = new ArrayList<Unidade>(this.unidades);
        for (Unidade value : this.unidades) {

            if (!value.getCidade().toLowerCase().contains(newText.toLowerCase())) {
                filteredValues.remove(value);
            }

        }

        unitsAdapter.update(filteredValues);

        return false;
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return true;
    }

}