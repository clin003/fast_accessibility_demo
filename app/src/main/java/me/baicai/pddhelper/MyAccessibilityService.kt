package me.baicai.pddhelper


import android.accessibilityservice.GestureDescription
import android.accessibilityservice.GestureDescription.StrokeDescription
import android.view.accessibility.AccessibilityEvent
import android.widget.EditText
import me.baicai.cp_fast_accessibility.*
import java.util.*
import kotlin.concurrent.fixedRateTimer


class MyAccessibilityService:FastAccessibilityService() {
    private val TARGET_APP_NAME= "com.xunmeng.pinduoduo"
    private val TARGET_APP_ACTIVITY = "com.xunmeng.pinduoduo.ui.activity.HomeActivity"
//    private val TARGET_APP_ACTIVITY = "com.xunmeng.pinduoduo.activity.NewPageActivity"

    lateinit var timer:Timer
    private var isStartTimer:Boolean =false

    override val enableListenApp=true
    override fun analyzeCallBack(wrapper: EventWrapper?, result: AnalyzeSourceResult) {
        if (wrapper == null) return
        if (!isStartTimer) {
            startTimer()
            result.findAllTextNode(true).nodes.forEach{logD("-----analyzeCallBack: $wrapper | $it ")}
        }
        when (wrapper.eventType){
            AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED -> {
                logD("通知改变:$wrapper")
            }
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> {
                logD("界面改变:$wrapper")
//                windowClassName = wrapper.className.toString()
            }
            AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED -> {
                logD("内容改变:$wrapper")
            }
            AccessibilityEvent.CONTENT_CHANGE_TYPE_TEXT->{
                logD("内容2改变:$wrapper")
            }
        }
//        logD("填入 网址")
//        result.findNodeByText("搜索或输入网址").input("https://www.qq.com")
//           val res = result.findNodeByText("https://www.qq.com")
//        res.click()
//        logD( "---------id: $res")
//       val res2= result.findNodeById("com.android.chrome:id/url_bar")
//        res2.input("https://www.qq2.com")
//        res2.click()
//        logD( "---------id2: $res2")

    }

//    Looper用于封装了android线程中的消息循环，默认情况下一个线程是不存在消息循环（message loop）的，
//    需要调用Looper.prepare()来给线程创建一个消息循环，调用Looper.loop()来使消息循环起作用，
//    使用Looper.prepare()和Looper.loop()创建了消息队列就可以让消息处理在该线程中完成。
    private fun doTask() {
//        if (windowClassName != PDD_VIDEO_ACTIVITY) return
        var accessibilityNodeInfos =
                rootInActiveWindow?.findAccessibilityNodeInfosByViewId("com.android.chrome:id/url_bar") ?: return
        for (accessibilityNodeInfo in accessibilityNodeInfos){
            if (accessibilityNodeInfo.className == EditText::class.java.name){
                val nodeInfo =  NodeWrapper(
                    text = accessibilityNodeInfo.text.blankOrThis(),
                    id = accessibilityNodeInfo.viewIdResourceName.blankOrThis(),
                    className = accessibilityNodeInfo.className.blankOrThis(),
                    description = accessibilityNodeInfo.contentDescription.blankOrThis(),
                    clickable = accessibilityNodeInfo.isClickable,
                    scrollable = accessibilityNodeInfo.isScrollable,
                    editable = accessibilityNodeInfo.isEditable,
                    nodeInfo = accessibilityNodeInfo
                )
                nodeInfo.input("啤酒")
//                Looper.prepare();
//                shortToast("🍺啤酒")
//                Looper.loop()
                logD("---------sendVideo1: $nodeInfo")
                break
            }

//            if (accessibilityNodeInfo.className == EditText::class.java.name){
//                val arguments = Bundle()
//                arguments.putCharSequence(
//                    AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,
//                    "烟花"
//                )
//                accessibilityNodeInfo.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments)
//                logD( "---------sendVideo: ${accessibilityNodeInfo.toString()}")
//                break
//            }
        }
    }

//    private fun send() {
//        inputById("com.android.chrome:id/url_bar","芒果")
//        clickById("com.android.chrome:id/url_bar")
//    }

    fun clickById(viewId:String) {
        val accessibilityNodeInfos =
            rootInActiveWindow?.findAccessibilityNodeInfosByViewId(viewId) ?: return
        for (accessibilityNodeInfo in accessibilityNodeInfos){
            if (accessibilityNodeInfo.isClickable){
                val nodeInfo =  NodeWrapper(
                    text = accessibilityNodeInfo.text.blankOrThis(),
                    id = accessibilityNodeInfo.viewIdResourceName.blankOrThis(),
                    className = accessibilityNodeInfo.className.blankOrThis(),
                    description = accessibilityNodeInfo.contentDescription.blankOrThis(),
                    clickable = accessibilityNodeInfo.isClickable,
                    scrollable = accessibilityNodeInfo.isScrollable,
                    editable = accessibilityNodeInfo.isEditable,
                    nodeInfo = accessibilityNodeInfo
                )
                nodeInfo.click()
                logD("---------clickById: $nodeInfo")
                break
            }
        }
    }
    fun clickByIdWithText(viewId:String,textStr:String) {
        val accessibilityNodeInfos =
            rootInActiveWindow?.findAccessibilityNodeInfosByViewId(viewId) ?: return
        for (accessibilityNodeInfo in accessibilityNodeInfos){
            if (accessibilityNodeInfo.isClickable && accessibilityNodeInfo.text==textStr){
                val nodeInfo =  NodeWrapper(
                    text = accessibilityNodeInfo.text.blankOrThis(),
                    id = accessibilityNodeInfo.viewIdResourceName.blankOrThis(),
                    className = accessibilityNodeInfo.className.blankOrThis(),
                    description = accessibilityNodeInfo.contentDescription.blankOrThis(),
                    clickable = accessibilityNodeInfo.isClickable,
                    scrollable = accessibilityNodeInfo.isScrollable,
                    editable = accessibilityNodeInfo.isEditable,
                    nodeInfo = accessibilityNodeInfo
                )
                nodeInfo.click()
                logD("---------clickById: $nodeInfo")
                break
            }
        }
    }



    private fun inputById(viewId:String, content:String) {
        val accessibilityNodeInfos =
            rootInActiveWindow?.findAccessibilityNodeInfosByViewId(viewId) ?: return
        for (accessibilityNodeInfo in accessibilityNodeInfos){
            if (accessibilityNodeInfo.isEditable || accessibilityNodeInfo.className == EditText::class.java.name){
                val nodeInfo =  NodeWrapper(
                    text = accessibilityNodeInfo.text.blankOrThis(),
                    id = accessibilityNodeInfo.viewIdResourceName.blankOrThis(),
                    className = accessibilityNodeInfo.className.blankOrThis(),
                    description = accessibilityNodeInfo.contentDescription.blankOrThis(),
                    clickable = accessibilityNodeInfo.isClickable,
                    scrollable = accessibilityNodeInfo.isScrollable,
                    editable = accessibilityNodeInfo.isEditable,
                    nodeInfo = accessibilityNodeInfo
                )
                nodeInfo.input(content)
                logD("---------inputById: $nodeInfo")
                break
            }
        }
    }

    private var isOne=false
    private fun startTimer(){
        isStartTimer=true
//        延迟delay毫秒后，执行一次task: 1秒后运行，间隔10秒运行一次
        timer= fixedRateTimer("",false,100,300000){
            logD("Timering_doing...")
//            doTask()
            doUploadVideo()
        }
    }
    private fun stopTimer(){
        timer.cancel()
        isStartTimer=false
        logD("Timering!!!")
    }

    private fun doUploadVideo() {
        clickByIdWithText("com.xunmeng.pinduoduo:id/pdd","多多视频")
        clickByIdWithText("com.xunmeng.pinduoduo:id/pdd","消息")
//        clickById("com.android.chrome:id/url_bar")
    }
}
