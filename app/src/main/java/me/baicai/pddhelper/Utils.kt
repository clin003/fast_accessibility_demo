package me.baicai.pddhelper


//import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import android.widget.Toast


/**
 * Author: CoderPig
 * Date: 2023-03-24
 * Desc:
 */
//@SuppressLint("UseCompatLoadingForDrawables")
fun Context.getDrawableRes(resId: Int): Drawable =
    applicationContext.resources.getDrawable(resId, null)

fun Context.getStringRes(resId: Int): String = applicationContext.resources.getString(resId, "")

fun Context.shortToast(msg: String) =
    Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()

const val TAG = "PddHelper"
const val SEGMENT_SIZE = 3072

/**
 * 支持超长日志输出的工具方法
 * */
fun logD(content: String) {
    if (content.length < SEGMENT_SIZE) {
        Log.d(TAG, content)
        return
    } else {
        Log.d(TAG, content.substring(0, SEGMENT_SIZE))
        logD(content.substring(SEGMENT_SIZE))
    }
}
fun logDT(tagStr:String,content: String) {
    var tag=tagStr
    if (tag.isEmpty()){tag= TAG}
    if (content.length < SEGMENT_SIZE) {
        Log.d(tag, content)
        return
    } else {
        Log.d(tag, content.substring(0, SEGMENT_SIZE))
        logDT(tag,content.substring(SEGMENT_SIZE))
    }
}


/**
 * 跳转其它APP
 * @param packageName 跳转APP包名
 * @param activityName 跳转APP的Activity名
 * @param errorTips 跳转页面不存在时的提示
 * */
fun Context.startApp(packageName: String, activityName: String, errorTips: String) {
    try {
        startActivity(Intent(Intent.ACTION_VIEW).apply {
            component = ComponentName(packageName, activityName)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        })
    } catch (e: ActivityNotFoundException) {
        shortToast(errorTips)
    } catch (e: Exception) {
        e.message?.let { logD(it) }
    }
}


fun getOsVersion():String{
    return Build.VERSION.RELEASE
}


