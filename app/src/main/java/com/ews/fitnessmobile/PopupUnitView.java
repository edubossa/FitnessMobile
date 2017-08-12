package com.ews.fitnessmobile;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ews.fitnessmobile.dao.UnidadeDAO;
import com.ews.fitnessmobile.model.Unidade;
import com.google.android.gms.maps.model.Marker;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by wallace on 12/08/17.
 */
public class PopupUnitView {

    public static final String TAG = "[PopupUnitView]";

    private final TextView tvNome;
    private final TextView tvCidade;
    private final TextView tvEndereco;
    private final TextView tvHorarioFuncionamento;
    private final TextView tvTelefone;

    private PopupWindow popupWindow;

    public PopupUnitView(Context context, Marker marker, UnidadeDAO dao) {
        Log.d(TAG, "Snippet --> " + marker.getSnippet() + " | Title --> " + marker.getTitle() + " | TAG --> "  + marker.getTag().toString());

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popup_layout_units,null);

        Unidade unidade = dao.getById(Integer.valueOf(marker.getTag().toString()));

        this.tvNome = (TextView) view.findViewById(R.id.tvNome);
        this.tvCidade = (TextView) view.findViewById(R.id.tvCidade);
        this.tvEndereco = (TextView) view.findViewById(R.id.tvEndereco);
        this.tvHorarioFuncionamento = (TextView) view.findViewById(R.id.tvHorarioFuncionamento);
        this.tvTelefone = (TextView) view.findViewById(R.id.tvTelefone);

        tvNome.setText(unidade.getNome());
        tvCidade.setText(unidade.getCidade());
        tvEndereco.setText(unidade.getEndereco());
        tvHorarioFuncionamento.setText(unidade.getHorarioFuncionamento());
        tvTelefone.setText(unidade.getTelefone());

        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        if(Build.VERSION.SDK_INT>=21){
            popupWindow.setElevation(5.0f);
        }

        ImageView closeButton = (ImageView) view.findViewById(R.id.imgClose);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        // Exibir o popup no centro
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }
}
