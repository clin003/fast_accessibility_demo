package me.baicai.pddhelper

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.view.accessibility.AccessibilityEvent
import androidx.core.os.HandlerCompat
import me.baicai.cp_fast_accessibility.FastAccessibilityService
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

private val NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors()
val executorService: ExecutorService = Executors.newFixedThreadPool(NUMBER_OF_CORES)
val mainThreadHandler: Handler = HandlerCompat.createAsync(Looper.getMainLooper())

class MyApp:Application() {
    companion object{
        lateinit var instance:MyApp
        private set
    }

    override fun onCreate() {
        super.onCreate()
        logD("MyApp 初始化 onCreate")
        instance=this
        FastAccessibilityService.init(
          instance,MyAccessibilityService::class.java, arrayListOf(
//                窗口状态改变 -表示的打开事件PopupWindow ， Menu ， Dialog等
                AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED,
                AccessibilityEvent.TYPE_VIEW_CLICKED,
//                窗口内容已更改 - 表示窗口内容发生更改。 此更改可以添加/删除视图，更改视图大小等。
//                注意：此事件仅针对最后一个辅助功能事件的窗口源与TYPE_NOTIFICATION_STATE_CHANGED不同，其目的是通知客户端用户交互窗口的内容已更改。
                AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED,
//                Windows已更改 - 代表屏幕上显示的窗口发生更改，如窗口出现，窗口消失，窗口大小更改，窗口层更改等等。
                AccessibilityEvent.TYPE_WINDOWS_CHANGED,
//                查看悬停输入 - 表示开始悬停在View上的View 。 可以通过触摸或通过定位设备浏览屏幕来生成悬停。
                AccessibilityEvent.TYPE_VIEW_HOVER_ENTER,
//                触摸互动开始 - 表示开始触摸互动的事件，即用户开始触摸屏幕。
                AccessibilityEvent.TYPE_TOUCH_INTERACTION_START,
//                触摸交互结束 - 表示结束触摸交互的事件，即用户停止触摸屏幕。
            AccessibilityEvent.TYPE_TOUCH_INTERACTION_END,
//                通知状态已更改 - 表示显示Notification的事件。
            AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED,



            )
        )
    }
}