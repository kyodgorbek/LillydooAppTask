package yodgorbekkomilov.edgar.lillydooapptask

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.activity_main.*
import yodgorbekkomilov.edgar.lillydooapptask.dialogs.Dialog1
import yodgorbekkomilov.edgar.lillydooapptask.dialogs.Dialog2
import yodgorbekkomilov.edgar.lillydooapptask.dialogs.IDialog1
import yodgorbekkomilov.edgar.lillydooapptask.dialogs.IDialog2
import yodgorbekkomilov.edgar.lillydooapptask.network.MyApi
import yodgorbekkomilov.edgar.lillydooapptask.network.NetworkConnectionInterceptor
import yodgorbekkomilov.edgar.lillydooapptask.network.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import yodgorbekkomilov.edgar.lillydooapptask.ModelLogin
class MainActivity : AppCompatActivity(), IDialog1, IDialog2 {

    private lateinit var mMyApi: MyApi
    private lateinit var mSessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mMyApi = MyApi(NetworkConnectionInterceptor(this))
        mSessionManager = SessionManager(this)

        api_text_btn.setOnClickListener { getUser() }

        ap_token_text_btn.setOnClickListener { getToken() }

        dialog_1_btn.setOnClickListener {
            val dialog = Dialog1(this)
            dialog.setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.SheetDialog1)
            dialog.show(supportFragmentManager, "Dialog1")
        }

        dialog_2_btn.setOnClickListener {
            val dialog = Dialog2(this)
            dialog.setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.SheetDialog2)
            dialog.show(supportFragmentManager, "Dialog2")
        }

    }

    private fun getUser(){

        mMyApi.login().enqueue(object : Callback<ModelUser>{
            override fun onFailure(call: Call<ModelUser>, t: Throwable) {

            }

            override fun onResponse(call: Call<ModelUser>, response: Response<ModelUser>) {
                val responseBody = response.body()

                // NOTE: API is in development so sometimes return 501 or something server related error so please try few more times
                // if you face response problem
                //
                // Also token expires not return 300 status code bcz it's just for testing purpose as mentioned in task documentation
                // that's why integrated to this app otherwise
                // it's for other my own project

                if (response.code() == 300){
                    getToken()
                }

            }

        })

    }

    private fun getToken(){

        val map = HashMap<String, String>()
        map.put("username", "dpatel19")
        map.put("password", "password")

        mMyApi.token(map).enqueue(object : Callback<ModelLogin> {

            override fun onFailure(call: Call<ModelLogin>, t: Throwable) {

            }

            override fun onResponse(call: Call<ModelLogin>, response: Response<ModelLogin>) {
                val responseBody = response.body()
                Log.e("Response : ", " -=> $responseBody")
                if (responseBody != null) {
                    mSessionManager.saveAuthToken(responseBody.access)
                }
            }

        })

    }

    override fun OnJaClicked() {

    }

    override fun OnNeinClicked() {

    }

    override fun OnPhoneClicked() {

    }

    override fun OnEmailClicked() {

    }

    override fun OnMessageClicked() {

    }

}