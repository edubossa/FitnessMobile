package com.ews.fitnessmobile.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ews.fitnessmobile.R;
import com.ews.fitnessmobile.fragments.UnitsAddFragment;
import com.ews.fitnessmobile.fragments.UnitsFragment;
import com.ews.fitnessmobile.model.Unidade;

import java.util.List;



/**
 * Created by wallace on 14/07/17.
 */
public class UnitsAdapter extends RecyclerView.Adapter<UnitsAdapter.UnitsAdapterViewHolder> {

    private List<Unidade> unidades;
    private Context ctx;
    private FragmentManager fragmentManager;

    public UnitsAdapter(List<Unidade> unidades, Context ctx, FragmentManager fragmentManager) {
        this.unidades = unidades;
        this.ctx = ctx;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public UnitsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.units, parent, false);
        return new UnitsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final UnitsAdapterViewHolder holder, int position) {
        final Unidade unidade = this.unidades.get(position);

        holder.tvNome.setText(unidade.getNome());
        holder.tvCidade.setText(unidade.getCidade());
        holder.tvEndereco.setText(unidade.getEndereco());
        holder.tvHorarioFuncionamento.setText(unidade.getHorarioFuncionamento());
        holder.tvTelefone.setText(unidade.getTelefone());

        holder.imgViewOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popup = new PopupMenu(ctx, holder.imgViewOptions);
                popup.inflate(R.menu.unit_menu);

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {

                            case R.id.menuAlter:
                                Bundle bundle = new Bundle();
                                bundle.putParcelable(UnitsFragment.PUT_UNIT, unidade);
                                UnitsAddFragment unitsFragment = new UnitsAddFragment();
                                unitsFragment.setArguments(bundle);
                                fragmentManager.beginTransaction()
                                        .replace(R.id.content_main, unitsFragment)
                                        .addToBackStack(null)
                                        .commit();
                                break;
                            case R.id.menuDelete:
                                Toast.makeText(ctx.getApplicationContext(), "Menu Delete Selected", Toast.LENGTH_SHORT).show();
                                break;

                            case R.id.menuCall:
                                Intent intentCall = new Intent(Intent.ACTION_CALL);
                                String phoneNumber = unidade.getTelefone().replaceAll("\\D", "");
                                intentCall.setData(Uri.parse("tel:" + phoneNumber));
                                if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    //ActivityCompat.requestPermissions(null, new String[]{Manifest.permission.CALL_PHONE});
                                }
                                ctx.startActivity(intentCall);
                                break;

                            case R.id.menuShare :
                                Intent intentShare = new Intent();
                                intentShare.setAction(Intent.ACTION_SEND);
                                intentShare.putExtra(Intent.EXTRA_TEXT, unidade.toString());
                                intentShare.setType("text/plain");
                                ctx.startActivity(intentShare);
                                break;
                        }

                        return false;
                    }
                });

                popup.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.unidades.size();
    }

    public void update(List<Unidade> unidades) {
        this.unidades = unidades;
        notifyDataSetChanged();
    }

    static class UnitsAdapterViewHolder extends RecyclerView.ViewHolder {

        //final ImageView imgAcademia;
        final TextView tvNome;
        final TextView tvCidade;
        final TextView tvEndereco;
        final TextView tvHorarioFuncionamento;
        final TextView tvTelefone;
        final ImageView imgViewOptions;

        public UnitsAdapterViewHolder(View itemView) {
            super(itemView);
            //this.imgAcademia = (ImageView) itemView.findViewById(R.id.imgAcademia);
            this.tvNome = (TextView) itemView.findViewById(R.id.tvNome);
            this.tvCidade = (TextView) itemView.findViewById(R.id.tvCidade);
            this.tvEndereco = (TextView) itemView.findViewById(R.id.tvEndereco);
            this.tvHorarioFuncionamento = (TextView) itemView.findViewById(R.id.tvHorarioFuncionamento);
            this.tvTelefone = (TextView) itemView.findViewById(R.id.tvTelefone);
            this.imgViewOptions = (ImageView) itemView.findViewById(R.id.imgViewOptions);
        }

    }


}
