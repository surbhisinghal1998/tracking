package akaalwebsoft.com.wsmtrackerinjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import akaalwebsoft.com.wsmtrackerinjava.ViewModle.GetCordinatedeviceidViewModel;
import akaalwebsoft.com.wsmtrackerinjava.ViewModle.GetUserDeviceLIstViewModle;
import akaalwebsoft.com.wsmtrackerinjava.ViewModle.VerifyOtpViewModle;
import akaalwebsoft.com.wsmtrackerinjava.modle.GetCordinateDeviceId;
import akaalwebsoft.com.wsmtrackerinjava.modle.GetUserDeviceListModle;
import akaalwebsoft.com.wsmtrackerinjava.modle.VerifyOtpModle;

public class OtpScreen extends AppCompatActivity {
    VerifyOtpViewModle verifyOtpViewModle;
    GetUserDeviceLIstViewModle getUserDeviceLIstViewModle;
    GetCordinatedeviceidViewModel getCordinatedeviceidViewModel;
    String latitude, longitude;
    EditText otp;
    Button submit;
    String mobile, auth, deviceid, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_screen);
        verifyOtpViewModle = new ViewModelProvider(this).get(VerifyOtpViewModle.class);
        getUserDeviceLIstViewModle = new ViewModelProvider(this).get(GetUserDeviceLIstViewModle.class);
        getCordinatedeviceidViewModel = new ViewModelProvider(OtpScreen.this).get(GetCordinatedeviceidViewModel.class);

        getintentdata();
        ids();
        onclickonbutton();
    }

    private void onclickonbutton() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("id", "" + id);
                Log.e("auth", "" + auth);
                Log.e("device id", "" + deviceid);
                Log.e("otp", otp.getText().toString());
                verifyOtpViewModle.verifyOtpModleLiveData(OtpScreen.this, mobile, auth, deviceid, otp.getText().toString()).observe(OtpScreen.this, new Observer<VerifyOtpModle>() {
                    @Override
                    public void onChanged(VerifyOtpModle verifyOtpModle) {
                        if (verifyOtpModle.getStatuscode().equalsIgnoreCase("Success")) {
                            for (int i = 0; i < verifyOtpModle.getData().getUserList().size(); i++) {
                                getUserDeviceLIstViewModle.getUserDeviceListModleLiveData(OtpScreen.this, verifyOtpModle.getData().getUserList().get(i).getInstituteCode(), verifyOtpModle.getData().getUserList().get(i).getUserMasterId().toString(), verifyOtpModle.getData().getUserList().get(i).getUserType().toString(), deviceid, verifyOtpModle.getData().getAccessToken().toString(), auth).observe(OtpScreen.this, new Observer<GetUserDeviceListModle>() {
                                    @Override
                                    public void onChanged(GetUserDeviceListModle getUserDeviceListModle) {
                                        if (getUserDeviceListModle.getStatuscode().equalsIgnoreCase("Success")) {
                                            Intent in = new Intent(OtpScreen.this, MapScreen.class);
                                            for (int i = 0; i < getUserDeviceListModle.getData().size(); i++) {
                                                in.putExtra("id", getUserDeviceListModle.getData().get(i).getId().toString());
                                                in.putExtra("deviceid", deviceid);
                                                in.putExtra("auth", auth);
                                                in.putExtra("token", verifyOtpModle.getData().getAccessToken().toString());

                                            }
                                            startActivity(in);
                                        } else {
                                            Toast.makeText(OtpScreen.this, "" + getUserDeviceListModle.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }

                        } else {
                            Toast.makeText(OtpScreen.this, "" + verifyOtpModle.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void getintentdata() {
        mobile = getIntent().getStringExtra("mobile");
        auth = getIntent().getStringExtra("auth");
        deviceid = getIntent().getStringExtra("deviceid");
    }

    private void ids() {
        otp = findViewById(R.id.otp);
        submit = findViewById(R.id.submit);
    }
}