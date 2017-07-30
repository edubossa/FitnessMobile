package com.ews.fitnessmobile.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ews.fitnessmobile.R;
import com.ews.fitnessmobile.dao.UnidadeDAO;
import com.ews.fitnessmobile.model.Unidade;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UnitsAddFragment extends Fragment {

    @BindView(R.id.etName) EditText etName;
    @BindView(R.id.etCity) EditText etCity;
    @BindView(R.id.etAddress) EditText etAddress;
    @BindView(R.id.etPhone) EditText etPhone;
    @BindView(R.id.etScheduleOperation) EditText etScheduleOperation;
    @BindView(R.id.etLatitude) EditText etLatitude;
    @BindView(R.id.etLongitude) EditText etLongitude;
    @BindView(R.id.btSend) Button btSend;

    private UnidadeDAO unidadeDAO;
    private Unidade unidade;

    public UnitsAddFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_units_add, container, false);
        ButterKnife.bind(this, view);
        this.unidadeDAO = new UnidadeDAO(view.getContext());

        if (getArguments() != null) {
            this.unidade = getArguments().getParcelable(UnitsFragment.PUT_UNIT);
            etName.setText(unidade.getNome());
            etCity.setText(unidade.getCidade());
            etAddress.setText(unidade.getEndereco());
            etPhone.setText(unidade.getTelefone());
            etScheduleOperation.setText(unidade.getHorarioFuncionamento());
            etLatitude.setText(unidade.getLatitude());
            etLongitude.setText(unidade.getLongitude());
        }

        this.btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("UnitsAddFragment", "onClick");
                if (unidade == null) unidade = new Unidade();
                unidade.setNome(etName.getText().toString());
                unidade.setCidade(etCity.getText().toString());
                unidade.setEndereco(etAddress.getText().toString());
                unidade.setTelefone(etPhone.getText().toString());
                unidade.setHorarioFuncionamento(etScheduleOperation.getText().toString());
                unidade.setLatitude(etLatitude.getText().toString());
                unidade.setLongitude(etLongitude.getText().toString());

                String result = unidadeDAO.insertOrUpdate(unidade);
                Toast.makeText(view.getContext(), result, Toast.LENGTH_SHORT).show();

                getFragmentManager().beginTransaction()
                        .replace(R.id.content_main, new UnitsFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }

}
