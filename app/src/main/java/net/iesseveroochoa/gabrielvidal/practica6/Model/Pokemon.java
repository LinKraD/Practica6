package net.iesseveroochoa.gabrielvidal.practica6.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = Pokemon.TABLE_NAME,
        indices = {@Index(value = {Pokemon.NOMBRE},unique = true)})

public class Pokemon implements Parcelable {

    public static final String TABLE_NAME="pokemon";
    public static final String ID= BaseColumns._ID;
    public static final String NOMBRE="nombre";
    public static final String uriIMAGEN="uri";
    public static final String FECHA_COMPRA="fechacompra";
    public static final String urlIMAGEN="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/";

    @PrimaryKey
    @NonNull
    @ColumnInfo(name=ID)private int id;
    @ColumnInfo(name = NOMBRE)
    @NonNull
    private String nombre;
    @ColumnInfo(name = uriIMAGEN)
    @NonNull
    private String uri;
    @ColumnInfo(name = FECHA_COMPRA)
    @NonNull
    private Date fechaCompra;

    public Pokemon(int id, @NonNull String nombre,  @NonNull Date fechaCompra) {
        this.id = id;
        this.nombre = nombre.toUpperCase();
        this.uri = urlIMAGEN + id+ ".png";
        this.fechaCompra = fechaCompra;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getNombre() {
        return nombre;
    }

    public void setNombre(@NonNull String nombre) {
        this.nombre = nombre;
    }

    @NonNull
    public String getUri() {
        return uri;
    }

    public void setUri(@NonNull String uri) {
        this.uri = uri;
    }

    @NonNull
    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(@NonNull Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getFechaCompraFormatoLocal(){
        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
        return df.format(fechaCompra);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.nombre);
        dest.writeString(this.uri);
        dest.writeLong(this.fechaCompra != null ? this.fechaCompra.getTime() : -1);
    }

    protected Pokemon(Parcel in) {
        this.id = in.readInt();
        this.nombre = in.readString();
        this.uri = in.readString();
        long tmpFechaCompra = in.readLong();
        this.fechaCompra = tmpFechaCompra == -1 ? null : new Date(tmpFechaCompra);
    }

    public static final Parcelable.Creator<Pokemon> CREATOR = new Parcelable.Creator<Pokemon>() {
        @Override
        public Pokemon createFromParcel(Parcel source) {
            return new Pokemon(source);
        }

        @Override
        public Pokemon[] newArray(int size) {
            return new Pokemon[size];
        }
    };
}
