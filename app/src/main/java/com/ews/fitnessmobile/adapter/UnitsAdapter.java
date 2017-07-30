package com.ews.fitnessmobile.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ews.fitnessmobile.R;
import com.ews.fitnessmobile.model.Unidade;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by wallace on 14/07/17.
 */
public class UnitsAdapter extends RecyclerView.Adapter<UnitsAdapter.UnitsAdapterViewHolder> {

    private List<Unidade> unidades;
    private OnItemClickListener listener;

    public UnitsAdapter(List<Unidade> unidades, OnItemClickListener listener) {
        this.unidades = unidades;
        this.listener = listener;
    }

    @Override
    public UnitsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.units, parent, false);
        return new UnitsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UnitsAdapterViewHolder holder, int position) {
        final Unidade unidade = this.unidades.get(position);

        /*Picasso.with(holder.itemView.getContext())
                .load(unidade.getUrlImagem())
                .placeholder(R.mipmap.ic_launcher) //load enquanto carrega a imagem
                .error(R.mipmap.ic_launcher) //TODO rever caso nao carregue a imagem colocar outro icone
                .into(holder.imgAcademia);*/

        holder.tvNome.setText(unidade.getNome());
        holder.tvCidade.setText(unidade.getCidade());
        holder.tvEndereco.setText(unidade.getEndereco());
        holder.tvHorarioFuncionamento.setText(unidade.getHorarioFuncionamento());
        holder.tvTelefone.setText(unidade.getTelefone());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(unidade);
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

        final ImageView imgAcademia;
        final TextView tvNome;
        final TextView tvCidade;
        final TextView tvEndereco;
        final TextView tvHorarioFuncionamento;
        final TextView tvTelefone;

        public UnitsAdapterViewHolder(View itemView) {
            super(itemView);
            this.imgAcademia = (ImageView) itemView.findViewById(R.id.imgAcademia);
            this.tvNome = (TextView) itemView.findViewById(R.id.tvNome);
            this.tvCidade = (TextView) itemView.findViewById(R.id.tvCidade);
            this.tvEndereco = (TextView) itemView.findViewById(R.id.tvEndereco);
            this.tvHorarioFuncionamento = (TextView) itemView.findViewById(R.id.tvHorarioFuncionamento);
            this.tvTelefone = (TextView) itemView.findViewById(R.id.tvTelefone);
        }

    }


}
