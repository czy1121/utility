# utility

各种工具函数：context,toast,dimen,file,format,random...

## context

```kotlin
// 获取当前进程名称
fun Context.resolveCurrentProcessName(): String?

// 是否在主进程
fun Context.isInMainProcess(): Boolean

// 当前应用是否可调试
fun Context.isDebuggable(): Boolean

// 读取/设置 剪切板内容
var Context.clipboardPrimaryText: String

// 获取应用的版本名称
fun Context.getVersionName(): String 

// 获取应用的版本号
fun Context.getVersionCode(): Long 
```

## 系统分享(share)

```kotlin
// 弹出选择器，分享文本到用户选择的应用
fun Context.shareText(text: String, title: String = "分享")
// 弹出选择器，分享图片到用户选择的应用
fun Context.shareImage(image: File, title: String = "分享") 

// 分享文本到指定应用
fun Context.sendText(toPackageName: String, message: String)
// 分享图片到指定应用
fun Context.sendImage(toPackageName: String, image: File) 
```

## 委托属性

视图查找

```kotlin  
fun <T : View> Activity.view(@IdRes id: Int): Lazy<T> = lazy { findViewById(id) }
fun <T : View> Fragment.view(@IdRes id: Int): Lazy<T> = lazy { requireView().findViewById(id) }
fun <T : View> Dialog.view(@IdRes id: Int): Lazy<T> = lazy { findViewById(id) }
fun <T : View> View.view(@IdRes id: Int): Lazy<T> = lazy { findViewById(id) }
```
 

`androidx.activity.viewModels` 与 `androidx.fragment.app.viewModels` 获取到的是不同的 ViewModel 实例     

使用 `activityViewModels` 可以方便的在 `Activity/Fragment/Dialog/View` 中获取 `ViewModel` 的同一实例以共享状态    

```kotlin
inline fun <reified VM : ViewModel> ComponentActivity.activityViewModels(): Lazy<VM>
inline fun <reified VM : ViewModel> Dialog.activityViewModels(): Lazy<VM>
inline fun <reified VM : ViewModel> View.activityViewModels(): Lazy<VM>
```

用法

```kotlin
class SomeActivity: ComponentActivity {
    val btnAction by view<TextView>(R.id.btn_action)
     
    val someVM by activityViewModels<SomeViewModel>()
} 
 
```



## 尺寸(dimen)

`dp/sp` 转 `px` 工具函数

```kotlin
inline val Int.dp: Int get() = Resources.getSystem().dp(this)
inline val Int.sp: Int get() = Resources.getSystem().sp(this)
inline val Float.dp: Float get() = Resources.getSystem().dp(this)
inline val Float.sp: Float get() = Resources.getSystem().sp(this)

inline fun Context.dp(value: Int): Int = resources.dp(value)
inline fun Context.sp(value: Int): Int = resources.sp(value)
inline fun Context.dp(value: Float): Float = resources.dp(value)
inline fun Context.sp(value: Float): Float = resources.sp(value)

inline fun Fragment.dp(value: Int): Int = resources.dp(value)
inline fun Fragment.sp(value: Int): Int = resources.sp(value)
inline fun Fragment.dp(value: Float): Float = resources.dp(value)
inline fun Fragment.sp(value: Float): Float = resources.sp(value)

inline fun Dialog.dp(value: Int): Int = context.resources.dp(value)
inline fun Dialog.sp(value: Int): Int = context.resources.sp(value)
inline fun Dialog.dp(value: Float): Float = context.resources.dp(value)
inline fun Dialog.sp(value: Float): Float = context.resources.sp(value) 

inline fun View.dp(value: Int): Int = resources.dp(value)
inline fun View.sp(value: Int): Int = resources.sp(value)
inline fun View.dp(value: Float): Float = resources.dp(value)
inline fun View.sp(value: Float): Float = resources.sp(value)

fun Resources.dp(value: Int): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value.toFloat(), displayMetrics).toInt()
fun Resources.sp(value: Int): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value.toFloat(), displayMetrics).toInt()
fun Resources.dp(value: Float): Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, displayMetrics)
fun Resources.sp(value: Float): Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, displayMetrics)
```

使用

```kotlin  
dp(10) 
sp(10) 
dp(10f) 
sp(10f) 

10.dp 
10.sp 
10f.dp 
10f.sp 
```
 


## 格式化(format)

**格式化字节数**

```kotlin
/** 
*
* compact = true 
* 123GB, 12.3GB, 1.23GB
* 123MB, 12.3MB, 1.23MB
* 123KB, 12.3KB, 1.23KB
* 123B, 12B, 1B 
*
* compact = false 总是保留最多2位小数 
* #.##GB
* #.##MB
* #.##KB
* #B 
*/
fun Long.formatBytes(compact:Boolean = false): String
```



**格式化时间**

```kotlin
fun Date.format(pattern: String, timezone: TimeZone)
```

在 `Date` 和 `Long` 上扩展了以下方法

- `formatDateTimeMs` = `format("yyyy-MM-dd HH:mm:ss.SSS", timezone)`
- `formatDateTime` = `format("yyyy-MM-dd HH:mm:ss", timezone)`
- `formatDate` = `format("yyyy-MM-dd", timezone)`
- `formatShortDate` = `format("MM-dd", timezone)`
- `formatTime` = `format("HH:mm:ss", timezone)`
- `formatShortTime` = `format("HH:mm", timezone)`

```kotlin 
/** 
* 半分钟内，显示 刚刚
* 一分钟内，显示 xx秒前
* 一小时内，显示 xx分钟前
* 一天内，显示 xx小时前
* 一月内，显示 xx天前
* 其余，显示 2019-05-05 11:11 
*/
fun Long.formatTimeAgo(context: Context, years: Boolean = true): String


/** 
* 上午 9:11
* 下午 4:22
* 昨天 上午 9:11
* 昨天 下午 4:22
* 周三 上午 9:11
* 周三 下午 4:22
* 5月21日 上午 9:11
* 5月21日 下午 4:22
* 2018年5月21日 上午 9:11
* 2018年5月21日 下午 4:22 
*/
fun Long.formatTimeChat(): String


/** 
* [d天][h小时][m分钟][s秒]
*
* 30秒
* 23分钟48秒
* 2小时10秒
* 1小时40分钟25秒
* 1天5秒
* 1天10分钟5秒
* 1天20小时
* 1天20小时10分钟5秒 
*/
fun Long.formatTimeSeconds(): String
```

## Gradle

``` groovy
repositories {
    maven { url "https://gitee.com/ezy/repo/raw/cosmo/"}
}
dependencies {
    implementation "me.reezy.cosmo:utility:0.10.1"
}
```

## LICENSE

The Component is open-sourced software licensed under the [Apache license](LICENSE).