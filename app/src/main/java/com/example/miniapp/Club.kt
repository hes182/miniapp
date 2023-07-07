package com.example.miniapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.miniapp.databinding.ActivityClubBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.entity.StringEntity
import cz.msebera.android.httpclient.message.BasicHeader
import cz.msebera.android.httpclient.protocol.HTTP
import org.json.JSONException
import org.json.JSONObject

class Club : AppCompatActivity() {
    private lateinit var binding: ActivityClubBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tilClubcity.editText!!.filters = arrayOf(InputFilter.AllCaps())
        binding.tilClubname.editText!!.filters = arrayOf(InputFilter.AllCaps())

        binding.btnSaveClub.setOnClickListener {
            cekValidValue()
        }
    }

    fun cekValidValue() {
        binding.tilClubname.error = null
        binding.tilClubcity.error = null

        var cancel = false
        var reqView: View? = null

        if (binding.tilClubname.editText!!.text.toString().isNullOrEmpty() || TextUtils.isEmpty(binding.tilClubname.editText!!.text.toString())) {
            Toast.makeText(this, "Nama Club Tidak Boleh Kosong.", Toast.LENGTH_SHORT).show()
            cancel = true
            reqView = binding.tilClubname
            binding.tilClubname.error = "Nama Club Harus Diisi"
        }
        if (binding.tilClubcity.editText!!.text.toString().isNullOrEmpty() || TextUtils.isEmpty(binding.tilClubcity.editText!!.text.toString())) {
            Toast.makeText(this, "Kota Club Tidak Boleh Kosong", Toast.LENGTH_SHORT).show()
            cancel = true
            reqView = binding.tilClubcity
            binding.tilClubcity.error = "Kota Club Harus Diisi"
        }

        if (cancel) {
            if (reqView != null) {
                reqView.requestFocus()
            }
        } else {
            val jsonParam = RequestParams()
            jsonParam.put("clubname", binding.tilClubname.editText!!.text.toString())
            jsonParam.put("city", binding.tilClubcity.editText!!.text.toString())
            setClub(jsonParam)
        }
    }

    fun setClub(json: RequestParams) {
        val client = AsyncHttpClient()
        client.setTimeout(15000)
        client.post( "http://127.0.0.1/teskoding/club/setClub", json,  object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val results = responseBody?.let { String(it) }
                Log.e( "setClub: ", results.toString())
                try {
                    val json = JSONObject(results)
                    val status_code = json.getString("status_code")
                    val status_desc = json.getString("status_desc")
                    when (status_code) {
                        "1" -> {
                            Toast.makeText(this@Club, status_desc, Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        "2" -> {
                            Toast.makeText(this@Club, status_desc, Toast.LENGTH_SHORT).show()
                        }
                        "8" -> {
                            Toast.makeText(this@Club, status_desc, Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(this@Club, status_desc, Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: JSONException) {
                    Toast.makeText(this@Club, "SetClub : Sedang Terjadi Masalah, Silahkan Hubungi Pengembang", Toast.LENGTH_SHORT ).show()
                } catch (e: Exception) {
                    Toast.makeText(this@Club, "SetClub : Sistem Sedang Bermasalah, Silahkan Hubungi Pengembang", Toast.LENGTH_SHORT ).show()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Toast.makeText(this@Club, "Error : " + statusCode + "\nSetClub : Sedang Dalam Maintenance", Toast.LENGTH_SHORT ).show()
            }

        })
    }

}