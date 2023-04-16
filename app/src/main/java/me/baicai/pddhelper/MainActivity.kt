package me.baicai.pddhelper

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import me.baicai.cp_fast_accessibility.isAccessibilityEnable
import me.baicai.cp_fast_accessibility.requireAccessibility

class MainActivity : AppCompatActivity() {
    private val TARGET_APP_NAME= "com.xunmeng.pinduoduo"
    private val TARGET_APP_ACTIVITY = "com.xunmeng.pinduoduo.ui.activity.HomeActivity"

    private lateinit var mServiceStatusIv:ImageView
    private lateinit var mServiceStatusTv:TextView
    private lateinit var mOpenTargetAppBt:Button
    private val mClickListener=View.OnClickListener {
        when (it.id){
            R.id.iv_service_status->{
                if (isAccessibilityEnable) shortToast(getStringRes(R.string.service_is_enable_tips))
                else requireAccessibility()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    override fun onResume() {
        super.onResume()
        if (isAccessibilityEnable){
            mServiceStatusIv.setImageDrawable(getDrawableRes(R.drawable.ic_service_enable))
            mServiceStatusIv.contentDescription=getStringRes(R.string.service_status_enable)
            mServiceStatusTv.text=getStringRes(R.string.service_status_enable)
//            mOpenTargetAppBt.visibility=View.VISIBLE
        }else{
            mServiceStatusIv.setImageDrawable(getDrawableRes(R.drawable.ic_service_disable))
            mServiceStatusIv.contentDescription=getStringRes(R.string.service_status_disable)
            mServiceStatusTv.text=getStringRes(R.string.service_status_disable)
//            mOpenTargetAppBt.visibility=View.GONE
        }
    }
    private fun initView(){
        mServiceStatusIv=findViewById(R.id.iv_service_status)
        mServiceStatusTv=findViewById(R.id.tv_service_status)
        mOpenTargetAppBt=findViewById(R.id.bt_open_target_app)
        mServiceStatusIv.setOnClickListener(mClickListener)
    }
    fun saveConfig(view: View){
        val editText=findViewById<EditText>(R.id.et_myadmin_url)
        val message=editText.text.toString()
        logD(message )
        val intent = Intent(this,DisplayMessageActivity::class.java).apply{
            putExtra(EXTRA_MESSAGE,message)
        }
        startActivity(intent)
    }
//    fun saveConfig(view: View){
//        try {
//            val intent = Intent(Intent.ACTION_VIEW)
//            intent.addCategory(Intent.CATEGORY_BROWSABLE);
//            intent.data=Uri.parse("http://www.baidu.com")
//            startActivity(intent)
//        } catch (e: Exception) {
//            println("当前手机未安装浏览器")
//        }
//    }
    fun openTargetApp(view: View){
        if (isAccessibilityEnable){
            logD("打开应用: $TARGET_APP_NAME")
            startApp(TARGET_APP_NAME, TARGET_APP_ACTIVITY, "未安装 $TARGET_APP_NAME")
        }else {
            logD("未开启无障碍模式，跳转设置")
            requireAccessibility()
        }
    }
//    private fun addListener() {
//        addAccessibilityServiceListener(object :
//            AccessibilityServiceListeners {
//            override fun updateStatus(boolean: Boolean) {
//                val controlFragment = fragments[0] as ControlFragment
//                controlFragment.updateControlView(boolean)
//            }
//        }, WECHAT_SERVICE_NAME)
//    }
//    private fun getPermissions() {
//        requestPermission(100, object : PermissionListener {
//            override fun permissionSuccess() {}
//            override fun permissionFail() {}
//        }, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//    }
}


