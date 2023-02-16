package akaalwebsoft.com.wsmtrackerinjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

import akaalwebsoft.com.wsmtrackerinjava.ViewModle.GetCordinatedeviceidViewModel;
import akaalwebsoft.com.wsmtrackerinjava.ViewModle.GetUserDeviceLIstViewModle;
import akaalwebsoft.com.wsmtrackerinjava.ViewModle.VeifyMobileNumberViewModle;
import akaalwebsoft.com.wsmtrackerinjava.modle.GetCordinateDeviceId;
import akaalwebsoft.com.wsmtrackerinjava.modle.GetUserDeviceListModle;
import akaalwebsoft.com.wsmtrackerinjava.modle.VerifyMobileNumberModle;

public class LoginScreen extends AppCompatActivity {
    VeifyMobileNumberViewModle veifyMobileNumberViewModle;

    GetUserDeviceLIstViewModle getUserDeviceLIstViewModle;
    EditText contact;
    Button submit;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        veifyMobileNumberViewModle = new ViewModelProvider(this).get(VeifyMobileNumberViewModle.class);
        getUserDeviceLIstViewModle = new ViewModelProvider(this).get(GetUserDeviceLIstViewModle.class);

        ids();
        useapi();
    }

    private void useapi() {
        //device id
        String ID = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);

        //authorization
        String name = "AKAAL";
        String password = "SnVuIDEwLCAyMDIy";
        String base = name + ":" + password;
        byte[] en = new byte[0];
        try {
            en = base.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String base64 = android.util.Base64.encodeToString(en, Base64.NO_WRAP);
        String auth = "Basic " + base64;
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                veifyMobileNumberViewModle.verifyMobileNumberModleLiveData(LoginScreen.this, contact.getText().toString(), auth, ID).observe(LoginScreen.this, new Observer<VerifyMobileNumberModle>() {
                    @Override
                    public void onChanged(VerifyMobileNumberModle verifyMobileNumberModle) {
                        if (verifyMobileNumberModle.getStatuscode().equalsIgnoreCase("Success")) {
                            if (verifyMobileNumberModle.getData().getIsAlreadyLoggedIn().toString().equals("false")) {
                                Intent in = new Intent(LoginScreen.this, OtpScreen.class);
                                in.putExtra("mobile", contact.getText().toString());
                                in.putExtra("auth", auth);
                                in.putExtra("deviceid", ID);
                                startActivity(in);
                            } else {
                                for (int i = 0; i < verifyMobileNumberModle.getData().getUserList().size(); i++) {
                                    getUserDeviceLIstViewModle.getUserDeviceListModleLiveData(LoginScreen.this, verifyMobileNumberModle.getData().getUserList().get(i).getInstituteCode(), verifyMobileNumberModle.getData().getUserList().get(i).getUserMasterId().toString(), verifyMobileNumberModle.getData().getUserList().get(i).getUserType().toString(), ID, verifyMobileNumberModle.getData().getAccessToken().toString(), auth).observe(LoginScreen.this, new Observer<GetUserDeviceListModle>() {
                                        @Override
                                        public void onChanged(GetUserDeviceListModle getUserDeviceListModle) {
                                            if (getUserDeviceListModle.getStatuscode().equalsIgnoreCase("Success")) {

                                                Intent in = new Intent(LoginScreen.this, MapScreen.class);
                                                in.putExtra("deviceid", ID);
                                                in.putExtra("auth", auth);
                                                in.putExtra("token", verifyMobileNumberModle.getData().getAccessToken().toString());
                                                if (getUserDeviceListModle.getData().size() == 1) {
                                                    in.putExtra("id", getUserDeviceListModle.getData().get(0).getId().toString());
                                                } else if (getUserDeviceListModle.getData().size() > 1) {
                                                    id = getUserDeviceListModle.getData().get(0).getId().toString();
                                                    for (int i = 1; i < getUserDeviceListModle.getData().size(); i++) {
                                                        id = id + "," + getUserDeviceListModle.getData().get(i).getId().toString();
                                                    }
                                                    in.putExtra("id", id);
                                                }

                                                startActivity(in);


                                            } else {

//                                                Log.e("fialed",getUserDeviceListModle.getMessage().toString());
                                                Toast.makeText(LoginScreen.this, getUserDeviceListModle.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                }
                            }

                        } else {

                            Toast.makeText(LoginScreen.this, verifyMobileNumberModle.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }


    private void ids() {
        contact = findViewById(R.id.contact);
        submit = findViewById(R.id.submit);

    }
}