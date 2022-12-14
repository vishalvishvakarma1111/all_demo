package com.example.alll_demo

 import android.content.Intent
import android.os.Build
import androidx.annotation.NonNull
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity : FlutterActivity() {
    private val CHANNEL = "example_service"

    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler {
            // Note: this method is invoked on the main thread.
                call, result ->
            when (call.method) {
                "startExampleService" -> {
                    startService(Intent(this, ExampleService::class.java))

                    result.success("hello how are  Started!")
                }
                "stopExampleService" -> {
                    stopService(Intent(this, ExampleService::class.java))
                    result.success("Stopped!")
                }
                "check_os" -> {
                    result.success("${Build.VERSION.SDK_INT}")
                }
                else -> {
                    result.notImplemented()
                }
            }
        }
    }
}
