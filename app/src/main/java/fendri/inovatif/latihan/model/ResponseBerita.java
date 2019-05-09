package fendri.inovatif.latihan.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class ResponseBerita{

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("isSuccess")
	private boolean isSuccess;

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}

	public void setIsSuccess(boolean isSuccess){
		this.isSuccess = isSuccess;
	}

	public boolean isIsSuccess(){
		return isSuccess;
	}
}