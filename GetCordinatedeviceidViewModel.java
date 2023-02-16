package akaalwebsoft.com.wsmtrackerinjava.ViewModle;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import akaalwebsoft.com.wsmtrackerinjava.common.CommonUtils;
import akaalwebsoft.com.wsmtrackerinjava.modle.GetCordinateDeviceId;
import akaalwebsoft.com.wsmtrackerinjava.modle.GetUserDeviceListModle;
import akaalwebsoft.com.wsmtrackerinjava.network.ApiInterface;
import akaalwebsoft.com.wsmtrackerinjava.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetCordinatedeviceidViewModel extends ViewModel {
    Context context;
    ApiInterface apiInterface = RetrofitClient.liveapiClient().create(ApiInterface.class);

    private MutableLiveData<GetCordinateDeviceId> getCordinateDeviceIdMutableLiveData;

    public LiveData<GetCordinateDeviceId> getCordinateDeviceIdLiveData(final Activity activity, String deviceid, String phoneserialnumber, String token, String auth) {

        getCordinateDeviceIdMutableLiveData = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {

            apiInterface.getcordinate(deviceid, phoneserialnumber, auth, token).enqueue(new Callback<GetCordinateDeviceId>() {
                @Override
                public void onResponse(Call<GetCordinateDeviceId> call, Response<GetCordinateDeviceId> response) {

                    if (response.body() != null) {
                        getCordinateDeviceIdMutableLiveData.postValue(response.body());
                    } else {
                        Toast.makeText(activity, response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<GetCordinateDeviceId> call, Throwable t) {

                    Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        return getCordinateDeviceIdMutableLiveData;

    }


}
