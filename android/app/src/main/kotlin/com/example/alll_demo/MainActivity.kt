package com.example.alll_demo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.annotation.NonNull
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.PluginRegistry

class MainActivity : FlutterActivity(), MethodChannel.MethodCallHandler, FlutterPlugin {
    private lateinit var channel: MethodChannel

    private var receiver: MyBroadcast? = null

    private val CHANNEL = "samples.flutter.dev/brodcast"

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        channel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL)

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler(fun(call: MethodCall, result: MethodChannel.Result) {
            when (call.method) {
                "start_cast" -> {
                    result.success("receiver start ")
                    receiver = MyBroadcast()
                    val filter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
                    registerReceiver(receiver, filter)

                }
                "stop_cast" -> {
                    try {
                        unregisterReceiver(receiver)
                        result.success("receiver stop ")
                    } catch (e: Exception) {
                        result.error("unregisterReceiver error ", "asd", e.toString())
                    }

                }
                "location" -> {
                    Log.e("location call ", ": method call location")
                    result.success("locationMap")
                }
                else -> {
                    result.notImplemented()
                }
            }


        })


    }


    inner class MyBroadcast : BroadcastReceiver() {
        override fun onReceive(p0: Context?, intent: Intent?) {

            val isAirplaneModeEnabled = intent?.getBooleanExtra("state", false) ?: return
            if (isAirplaneModeEnabled) {
                // showing the toast message if airplane mode is enabled
                Toast.makeText(context, "Airplane Mode Enabled", Toast.LENGTH_LONG).show()
            } else {
                // showing the toast message if airplane mode is disabled
                Toast.makeText(context, "Airplane Mode Disabled", Toast.LENGTH_LONG).show()
            }

            Log.e("TAG", "onReceive:=-===== $isAirplaneModeEnabled")
            val data = HashMap<String, Any>()

            data["result"] = isAirplaneModeEnabled

            channel.invokeMethod("data", data, null)
        }
    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when (call.method) {
            "start_cast" -> result.success("hhhhhhh")
            else -> result.notImplemented()
        }
    }

    override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) {

        Log.e("TAG", "onAttachedToEngine: ")
        channel = MethodChannel(binding.binaryMessenger, CHANNEL)
        channel.setMethodCallHandler(this)

        receiver = MyBroadcast()

        /*  LocalBroadcastManager.getInstance(context).registerReceiver(
              receiver!!,
              IntentFilter(LocationUpdatesService.ACTION_BROADCAST)
          )*/
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

}



