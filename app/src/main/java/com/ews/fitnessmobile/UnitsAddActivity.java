package com.ews.fitnessmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ews.fitnessmobile.dao.UnidadeDAO;
import com.ews.fitnessmobile.fragments.UnitsFragment;
import com.ews.fitnessmobile.model.Unidade;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UnitsAddActivity extends AppCompatActivity {

    @BindView(R.id.etName) EditText etName;
    @BindView(R.id.etCity) EditText etCity;
    @BindView(R.id.etAddress) EditText etAddress;
    @BindView(R.id.etPhone) EditText etPhone;
    @BindView(R.id.etScheduleOperation) EditText etScheduleOperation;
    @BindView(R.id.etLatitude) EditText etLatitude;
    @BindView(R.id.etLongitude) EditText etLongitude;
    @BindView(R.id.btSend)
    Button btSend;

    private UnidadeDAO unidadeDAO;
    private Unidade unidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_units_add);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.unidadeDAO = new UnidadeDAO(this);

        if (getIntent() != null && getIntent().getParcelableExtra(UnitsFragment.PUT_UNIT) != null) {
            this.unidade = getIntent().getParcelableExtra(UnitsFragment.PUT_UNIT); //getArguments().getParcelable(UnitsFragment.PUT_UNIT);
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
                Log.d("UnitsAddActivity", "onClick");
                if (unidade == null) unidade = new Unidade();
                unidade.setNome(etName.getText().toString());
                unidade.setCidade(etCity.getText().toString());
                unidade.setEndereco(etAddress.getText().toString());
                unidade.setTelefone(etPhone.getText().toString());
                unidade.setHorarioFuncionamento(etScheduleOperation.getText().toString());
                unidade.setLatitude(etLatitude.getText().toString());
                unidade.setLongitude(etLongitude.getText().toString());

                String result = unidadeDAO.insertOrUpdate(unidade);
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();

                startActivity(new Intent(UnitsAddActivity.this, MainActivity.class));
            }
        });
    }
}
