package com.example.secondexperiment.ui

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.example.retrofittest.App
import com.example.retrofittest.AppService
import com.example.secondexperiment.R
import com.example.secondexperiment.service.FrontService
import com.example.secondexperiment.utils.HttpCallbackListener
import com.example.secondexperiment.utils.HttpUtil
import com.example.secondexperiment.utils.ServiceCreator
import com.example.secondexperiment.utils.startActivity
import kotlinx.android.synthetic.main.activity_function.*
import kotlinx.android.synthetic.main.activity_http_request.*
import kotlinx.coroutines.delay
import okhttp3.Call
import okhttp3.Response
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import javax.security.auth.callback.Callback
import kotlin.Exception
import kotlin.concurrent.thread
import kotlin.math.log

class FunctionActivity : BaseActivity(), View.OnClickListener {
    private lateinit var downloadBinder: FrontService.DownloadBinder
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            downloadBinder = service as FrontService.DownloadBinder
            downloadBinder.startDownload()
            downloadBinder.getProgress()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d("Service", "onServiceDisconnected")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_function)
        btn_function_frontService.setOnClickListener(this)
        btn_startService.setOnClickListener(this)
        btn_stopService.setOnClickListener(this)
        btn_unbindService.setOnClickListener(this)
        btn_func_sendRequest.setOnClickListener(this)
        btn_sendWithRetrofit.setOnClickListener(this)

        Toast.makeText(this, "Function", Toast.LENGTH_SHORT).show()
    }


    override fun onClick(v: View?) {
        when (v) {

            btn_startService -> {
                val intent = Intent(this, FrontService::class.java)
                startService(intent)
            }
            btn_stopService -> {
                val intent = Intent(this, FrontService::class.java)
                stopService(intent)
            }
            btn_function_frontService -> {
                val intent = Intent(this, FrontService::class.java)
//                startService(intent)
                bindService(intent, connection, Context.BIND_AUTO_CREATE)
            }
            btn_unbindService -> {
                unbindService(connection)
            }


            btn_sendWithRetrofit -> {

                val appService = ServiceCreator.create<AppService>()
                appService.getAppData().enqueue(object: retrofit2.Callback<List<App>> {
                    override fun onResponse(call: retrofit2.Call<List<App>>, response: retrofit2.Response<List<App>>) {
                        val list = response.body()
                        val str = StringBuilder()
                        if (list != null) {
                            for (app in list) {
                                str.append(app.id + app.name + app.version)
                                Log.d("MainActivity", "id is ${app.id}")
                                Log.d("MainActivity", "name is ${app.name}")
                                Log.d("MainActivity", "version is ${app.version}")
                            }
                        }

                        responseText1.text = str
                    }

                    override fun onFailure(call: retrofit2.Call<List<App>>, t: Throwable) {

                    }

                })

            }


            btn_func_sendRequest -> {
//                startActivity<HttpRequestActivity>(this)
                val uri = edit_func_url.text.toString()
                Log.d("aaaaaaaaaaaaaaaaaaa","999")
//                sendRequestWithHttpURLConnection()
//               runOnUiThread {
                   HttpUtil.sendOkHttpRequest("http://10.0.2.2:3333", object : okhttp3.Callback {
                       //                    https://www.baidu.com"
                       override fun onResponse(call: Call, response: Response) {
//                        responseText1.text = response.body?.string()
                           val a = response.body?.string()
//                        Toast.makeText(this@FunctionActivity, "aaa", Toast.LENGTH_SHORT).show()
                        runOnUiThread {
//                                responseText1.text = response.body?.string()

                            if (a != null) {
                                Log.d("asdhsakjdhaufq", a)
                            }
                           responseText1.text = a
                        }
                           Log.d("bbbbbbbbbbbbbbbbbbbb","1")
                       }

                       //                    @SuppressLint("LongLogTag")
                       override fun onFailure(call: Call, e: IOException) {
//                        Log.d("cccccccccccccccccccccccccccc","1")
                       }
                   })
               }
////                HttpUtil.sendHttpRequest("http://10.0.2.2:3333", object : HttpCallbackListener {
////                    override fun onFinish(response: String) {
////                        runOnUiThread {
////                        Log.d("aaaaaaaaaa","1")
////                            responseText1.text = response
////                        }
////                    }
////
////                    override fun onError(e: Exception) {
////
////                    }
////                })
//            }

//            show_drawerLayout -> {
//                drawerLayout_function.openDrawer(GravityCompat.START)
//            }
        }
    }

    private fun sendRequestWithHttpURLConnection() {
        // 开启线程来发起网络请求
        thread {

            var connection: HttpURLConnection? = null
            try {
                val response = StringBuilder()
                val url = URL("http://10.0.2.2:3333")
                connection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 8000
                connection.readTimeout = 8000
                val input = connection.inputStream
                // 下面对获取到的输入流进行读取
                val reader = BufferedReader(InputStreamReader(input))
                reader.use {
                    reader.forEachLine {
                        response.append(it)
                    }
                }
                showRespond(response.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                connection?.disconnect()
            }
        }
    }

    private fun showRespond(response: String) {

//        Log.d("aaa", "4")
        val lock = ReentrantLock()

//        thread {
//            lock.lock()
//            startActivity<HttpRequestActivity>(this)
//            SystemClock.sleep(3000)
//            for (i in 0..10000) {
//                Log.d("aaa", "111111111111")
//            }
//            lock.unlock()
//        }

        runOnUiThread {
            lock.lock()
//            startActivity<HttpRequestActivity>(this)
//            SystemClock.sleep(3000)
            responseText1?.text = response
//            for (i in 0..10000) {
//                Log.d("bbb", "222222222")
//            }
            lock.unlock()


//            synchronized("hha") {
////                responseText.text = response
//                startActivity<HttpRequestActivity>(this)
//            }
//
//            synchronized("hha") {
//                responseText.text = response
//            }


//            responseText.text = response
//            Log.d("aaa", response)
        }
//        Log.d("aaa", "6")

//        thread(name = "startActivity") {
//
//            startActivity<HttpRequestActivity>(this)
//        }
    }
}