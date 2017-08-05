package com.ews.fitnessmobile.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wallace on 13/07/17.
 */
public class Unidade  implements Parcelable{

    private Integer id;
    private String nome;
    private String cidade;
    private String endereco;
    private String telefone;
    private String horarioFuncionamento;
    private String urlImagem;
    private String latitude;
    private String longitude;

    public Unidade() {}

    protected Unidade(Parcel in) {
        nome = in.readString();
        cidade = in.readString();
        endereco = in.readString();
        telefone = in.readString();
        horarioFuncionamento = in.readString();
        urlImagem = in.readString();
        latitude = in.readString();
        longitude = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nome);
        dest.writeString(cidade);
        dest.writeString(endereco);
        dest.writeString(telefone);
        dest.writeString(horarioFuncionamento);
        dest.writeString(urlImagem);
        dest.writeString(latitude);
        dest.writeString(longitude);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Unidade> CREATOR = new Creator<Unidade>() {
        @Override
        public Unidade createFromParcel(Parcel in) {
            return new Unidade(in);
        }

        @Override
        public Unidade[] newArray(int size) {
            return new Unidade[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getHorarioFuncionamento() {
        return horarioFuncionamento;
    }

    public void setHorarioFuncionamento(String horarioFuncionamento) {
        this.horarioFuncionamento = horarioFuncionamento;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


    @Override
    public String toString() {
        return  nome + "\n" +
                cidade + "\n" +
                endereco + "\n" +
                horarioFuncionamento + "\n" +
                telefone;
    }

}
