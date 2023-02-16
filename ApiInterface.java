package akaalwebsoft.com.wsmtrackerinjava.network;

import akaalwebsoft.com.wsmtrackerinjava.common.Constants;
import akaalwebsoft.com.wsmtrackerinjava.modle.GetCoordinateByDeviceIdListmodle;
import akaalwebsoft.com.wsmtrackerinjava.modle.GetCordinateDeviceId;
import akaalwebsoft.com.wsmtrackerinjava.modle.GetLimitedCoordinatemodle;
import akaalwebsoft.com.wsmtrackerinjava.modle.GetUserDeviceListModle;
import akaalwebsoft.com.wsmtrackerinjava.modle.VerifyMobileNumberModle;
import akaalwebsoft.com.wsmtrackerinjava.modle.VerifyOtpModle;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiInterface {

    @Headers("Accept:application/json")
    @GET("ValidateMobileNumber")
    Call<VerifyMobileNumberModle> varifymobile(@Query(Constants.MOBILE_NUMBER) String mobilenumber,
                                               @Header(Constants.AUTHORIZATION) String authorization,
                                               @Query(Constants.PHONESERIALNUMBER) String phoneserialnumber);

    @Headers("Accept:application/json")
    @GET("ValidateLoginOTP")
    Call<VerifyOtpModle> verifyotp(@Query(Constants.MOBILE_NUMBER) String mobilenumber,
                                   @Header(Constants.AUTHORIZATION) String authorization,
                                   @Query(Constants.PHONESERIALNUMBER) String phoneserialnumber,
                                   @Query(Constants.LOGINOTP) String loginotp
    );

    @Headers("Accept:application/json")
    @GET("GetUserDeviceList")
    Call<GetUserDeviceListModle> getuserdevicelist(@Query(Constants.INSTITTECODE) String InstituteCode,
                                                   @Query(Constants.USERMASTERID) String UserMasterId,
                                                   @Query(Constants.USERTYPE) String UserType,
                                                   @Query(Constants.PHONESERIALNUMBER) String PhoneSerialNo,
                                                   @Header(Constants.AUTHORIZATION) String authorization,
                                                   @Header(Constants.TOKEN) String token
    );

    @Headers("Accept:application/json")
    @GET("GetCoordinatesByDeviceId")
    Call<GetCordinateDeviceId> getcordinate(@Query(Constants.DEVIEID) String DeviceId,
                                            @Query(Constants.PHONESERIALNUMBER) String PhoneSerialNo,
                                            @Header(Constants.AUTHORIZATION) String authorization,
                                            @Header(Constants.TOKEN) String token
    );

    @Headers("Accept:application/json")
    @GET("GetCoordinatesByDeviceIdList")
    Call<GetCoordinateByDeviceIdListmodle> getcordinatedevidlist(@Query(Constants.DEVIEIDS) String DeviceId,
                                                        @Query(Constants.PHONESERIALNUMBER) String PhoneSerialNo,
                                                        @Header(Constants.AUTHORIZATION) String authorization,
                                                        @Header(Constants.TOKEN) String token);

    @Headers("Accept:application/json")
    @GET("GetLimitedCoordinatesByDeviceId")
    Call<GetLimitedCoordinatemodle> getlimitcordinate(@Query(Constants.DEVIEID) String DeviceId,
                                                      @Query(Constants.PHONESERIALNUMBER) String PhoneSerialNo,
                                                      @Query(Constants.LIMITS) String limit,
                                                      @Query(Constants.ENTRYDATE) String EntryDate,
                                                      @Header(Constants.AUTHORIZATION) String authorization,
                                                      @Header(Constants.TOKEN) String token);

}
