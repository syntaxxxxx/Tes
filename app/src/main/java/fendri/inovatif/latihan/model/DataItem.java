package fendri.inovatif.latihan.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class DataItem implements Parcelable {

	@SerializedName("klik")
	private String klik;

	@SerializedName("tgl")
	private String tgl;

	@SerializedName("id")
	private String id;

	@SerializedName("judul")
	private String judul;

	@SerializedName("gambar")
	private String gambar;

	@SerializedName("isi")
	private String isi;

	@SerializedName("tampil")
	private String tampil;

	public void setKlik(String klik){
		this.klik = klik;
	}

	public String getKlik(){
		return klik;
	}

	public void setTgl(String tgl){
		this.tgl = tgl;
	}

	public String getTgl(){
		return tgl;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setJudul(String judul){
		this.judul = judul;
	}

	public String getJudul(){
		return judul;
	}

	public void setGambar(String gambar){
		this.gambar = gambar;
	}

	public String getGambar(){
		return gambar;
	}

	public void setIsi(String isi){
		this.isi = isi;
	}

	public String getIsi(){
		return isi;
	}

	public void setTampil(String tampil){
		this.tampil = tampil;
	}

	public String getTampil(){
		return tampil;
	}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.klik);
        dest.writeString(this.tgl);
        dest.writeString(this.id);
        dest.writeString(this.judul);
        dest.writeString(this.gambar);
        dest.writeString(this.isi);
        dest.writeString(this.tampil);
    }

    public DataItem() {
    }

    protected DataItem(Parcel in) {
        this.klik = in.readString();
        this.tgl = in.readString();
        this.id = in.readString();
        this.judul = in.readString();
        this.gambar = in.readString();
        this.isi = in.readString();
        this.tampil = in.readString();
    }

    public static final Parcelable.Creator<DataItem> CREATOR = new Parcelable.Creator<DataItem>() {
        @Override
        public DataItem createFromParcel(Parcel source) {
            return new DataItem(source);
        }

        @Override
        public DataItem[] newArray(int size) {
            return new DataItem[size];
        }
    };
}