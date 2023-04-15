package com.example.mya3


import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.provider.Settings.Global.getString
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.json.JSONObject

import java.util.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            MainGame()
//            MainMe()
            Main()
            database_exam_list_map_get()
            database_exam_user_info_list_map_get()
        }
    }
}

@Composable
fun database_exam_list_map_get(){
    val context = LocalContext.current//重要，资源路径
    val items = mutableMapOf<String, String>()
    var sharedPref = context.getSharedPreferences("my_preferences2", Context.MODE_PRIVATE)
    val value1 = sharedPref.getString("exam_list","none")
    if (value1 == "none"){
        println("初始化数据,数据写入 getSharedPreferences 放进database_exam_list")
        //初始化数据,数据写入 getSharedPreferences 放进database_exam_list
        val jsonStr = JSONObject(exam_list).toString() //json数据，然后转字符串
        val editor = sharedPref.edit()
        editor.putString("exam_list", jsonStr)
        editor.apply()
        for (i in exam_list){
            items.put(i.key,i.value)
        }
        myData.value.database_exam_list = items
    }else{
        println("读取数据 读取getSharedPreferences里面的数据 放进database_exam_list")
        //读取数据 读取getSharedPreferences里面的数据 放进database_exam_list
        var jsonObject = JSONObject(value1)
        for (i in jsonObject.keys()){
            items.put(i, jsonObject.getString(i))
        }
        myData.value.database_exam_list = items
    }
}
@Composable
fun database_exam_user_info_list_map_get(){
    val context = LocalContext.current//重要，资源路径
    val items = mutableMapOf<String, String>()
    var sharedPref = context.getSharedPreferences("my_preferences2", Context.MODE_PRIVATE)
    val value1 = sharedPref.getString("exam_user_info_list","none")
    if (value1 == "none"){
        println("初始化数据,数据写入 getSharedPreferences 放进database_exam_list")
        //初始化数据,数据写入 getSharedPreferences 放进database_exam_list
        val jsonStr = JSONObject(exam_user_info_list).toString() //json数据，然后转字符串
        val editor = sharedPref.edit()
        editor.putString("exam_user_info_list", jsonStr)
        editor.apply()
        for (i in exam_user_info_list){
            items.put(i.key,i.value)
        }
        myData.value.database_exam_user_info_list = items
    }else{
        println("读取数据 读取getSharedPreferences里面的数据 放进database_exam_list")
        //读取数据 读取getSharedPreferences里面的数据 放进database_exam_list
        var jsonObject = JSONObject(value1)
        for (i in jsonObject.keys()){
            items.put(i, jsonObject.getString(i))
        }
        myData.value.database_exam_user_info_list = items
    }
}
@Composable
fun database_userinfo_map_add_one_bak(name1:String){
    val context = LocalContext.current//重要，资源路径
    var t1 = myData.value.database_exam_list //先读取数据
    var a1:Int = t1[name1]!!.toInt() + 1
    t1[name1] = a1.toString()
    // 将可变 Map 对象转换为不可变的 Map 对象
    val immutableMap = t1.toMap()
    //写入getSharedPreferences里面
    val jsonStr = JSONObject(immutableMap).toString()
    var sharedPref = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
    val editor = sharedPref.edit()
    editor.putString("exam_list", jsonStr)
    editor.apply()
    //写入运行数据database
    myData.value.database_exam_list = t1
}


var main_io = mutableStateOf("1")


//var exam_case :MutableList<MutableMap<String,Any>> = mutableListOf()
//var exam_case_len:Int = 0
//var exam_case_xy:String = "3_1_0"

data class MyData(
    var database_exam_list:MutableMap<String,String> = mutableMapOf(),
    var database_exam_user_info_list:MutableMap<String,String> = mutableMapOf(),

    var exam_case :MutableList<MutableMap<String,MutableList<List<String>>>> = mutableListOf(),
    var exam_case_len:Int = 0,
    var exam_case_xy:String = "3_1_0",

    var case1_background_list:MutableList<Color> = mutableListOf(Color.White, Color.White, Color.White),//重要,判断位置
    var case1_button_location:String = "0",//底部4个按钮的显示

    var case2_background_list:MutableList<Color> = mutableListOf(Color.White, Color.White, Color.White,Color.White, Color.White, Color.White),//重要,判断位置
    var case2_left_right_list:MutableList<String> = mutableListOf("left", "left", "left","right", "right", "right"),//重要,判断左右
    var case2_button_location:String = "0",//底部4个按钮的显示

    var case3_background_list:MutableList<Color> = mutableListOf(Color.White, Color.White,Color.White, Color.White,Color.White, Color.White, Color.White,Color.White, Color.White, Color.White),//重要,判断位置
    var case3_left_right_list:MutableList<String> = mutableListOf("left","left", "left","left", "left","right","right","right","right","right"),//重要,判断左右
    var case3_button_location:String = "0",//底部4个按钮的显示

    var case4_background_list:MutableList<Color> = mutableListOf(Color.White, Color.White, Color.White),//重要,判断位置
    var case4_button_location:String = "0",//底部4个按钮的显示
)

var myData = mutableStateOf(MyData())

var myState = MyData()
@Composable
fun Main() {
    var listState0 = rememberLazyListState()
    var listState1 = rememberLazyListState()
    val itemsList = (0..100).toList()
    val itemsIndexedList = listOf("A", "B", "C")

    if (main_io.value == "1"){
//        MainGame()
        Column (){
            Column (
                Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(Color.White)){
                my_top2()
                Divider(thickness = 1.dp, color = Color.Gray)
            }

            LazyColumn(
                Modifier
                    .fillMaxWidth()
                    .weight(1f),
                state = listState0
            ) {
                item {
                    my_main()
                }
            }

            Column (
                Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(Color.White)){
                Divider(thickness = 1.dp, color = Color.Gray)
                my_menu2()
            }

        }
    }
    if (main_io.value == "2"){
        MainMe()
    }
    if (main_io.value == "3"){
        my_exam()
    }
    if (main_io.value == "4"){
        my_seting()
    }
}

@Composable
fun my_seting(){
    val context = LocalContext.current//重要，资源路径

    fun re_set(){
        val items = mutableMapOf<String, String>()
        var sharedPref = context.getSharedPreferences("my_preferences2", Context.MODE_PRIVATE)
        //初始化数据,数据写入 getSharedPreferences 放进database_exam_list
        val jsonStr = JSONObject(exam_list).toString() //json数据，然后转字符串
        val editor = sharedPref.edit()
        editor.putString("exam_list", jsonStr)
        editor.apply()
        for (i in exam_list){
            items.put(i.key,i.value)
        }
        myData.value.database_exam_list = items
    }

    Column {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(47.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {},
                        onTap = {
                            main_io.value = "2"
                        }
                    )
                },
        ) {
            Image(
                painter = painterResource(id = R.drawable.round_chevron_left_20),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.Gray),
                modifier = Modifier
                    .size(32.dp)
            )
        }
        Divider(thickness = 0.8.dp, color = Color.Gray)
        Column(modifier = Modifier.height(60.dp)) {}
        Text(
            text = "关于我们",
            fontSize = 18.sp,
            textAlign = TextAlign.Left,
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth()
                .padding(8.dp)
        )
        Text(
            text = "联系我们,反馈程序错误,联系方式",
            fontSize = 16.sp,
            textAlign = TextAlign.Left,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        Text(
            text = "info@dengzilou.com",
            fontSize = 16.sp,
            textAlign = TextAlign.Left,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        Column(modifier = Modifier.height(60.dp)) {}
        Text(
            text = "重置数据",
            fontSize = 18.sp,
            textAlign = TextAlign.Left,
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth()
                .padding(8.dp)
        )
        Divider(thickness = 0.4.dp, color = Color.Gray)
        Row(
            modifier = Modifier
                .padding(60.dp)
                .height(48.dp)
                .fillMaxWidth()
                .background(Color.Red, shape = RoundedCornerShape(8.dp))
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {},
                        onTap = {
                            //
                            re_set()
                            main_io.value = "1"

                        }
                    )
                },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = "再玩一次", color = Color.White)
        }
    }
}

@Composable
fun PlayButton(resourceName: String, resourceType: String) {
//  var mediaPlayer = MediaPlayer.create(context, R.raw.end)
//  mediaPlayer.start()
    val context = LocalContext.current
    val isPlaying = remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .size(48.dp)
            .clickable(onClick = {
                val resources = context.resources
                val resourceId =
                    resources.getIdentifier(resourceName, resourceType, context.packageName)
                val mediaPlayer = MediaPlayer.create(context, resourceId)
                mediaPlayer.start()
                mediaPlayer.setOnCompletionListener {
                    it.release()
                    isPlaying.value = false
                }
                isPlaying.value = true
            })
    ) {
        val icon: ImageVector = if (isPlaying.value) {
            Icons.Filled.Add
        } else {
            Icons.Filled.PlayArrow
        }
        Icon(icon, contentDescription = "play button")
    }
}

@Composable
fun my_exam() {
    val context = LocalContext.current//重要，资源路径

    //写入database数据 exam_list
    fun database_userinfo_map_add_one(name1:String){
        var t1 = myData.value.database_exam_list //先读取数据
        var a1:Int = t1[name1]!!.toInt() + 1
        t1[name1] = a1.toString()
        // 将可变 Map 对象转换为不可变的 Map 对象
        val immutableMap = t1.toMap() //如果 t1 中的值被改变了，immutableMap 的值也会跟着改变，因为它们存储的是同一个对象的引用
        //写入getSharedPreferences里面
        val jsonStr = JSONObject(immutableMap).toString()
        var sharedPref = context.getSharedPreferences("my_preferences2", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("exam_list", jsonStr)
        editor.apply()
        //写入运行数据database
        myData.value.database_exam_list = t1
        //------------------------------------
        var t2 = myData.value.database_exam_user_info_list //先读取数据
        var a2_keep_score:Int = t2["keep_score"]!!.toInt() - 20
        t2["keep_score"] = a2_keep_score.toString()
        var a2_keep_wealth:Int = t2["keep_wealth"]!!.toInt() + 10
        t2["keep_wealth"] = a2_keep_wealth.toString()
        var a2_keep_time:Int = t2["keep_time"]!!.toInt() + 5
        t2["keep_time"] = a2_keep_time.toString()
        var a2_keep_words:Int = t2["keep_words"]!!.toInt() + 1
        t2["keep_words"] = a2_keep_words.toString()
        val immutableMap2 = t2.toMap()
        //写入getSharedPreferences里面
        val jsonStr2 = JSONObject(immutableMap2).toString()
        var sharedPref2 = context.getSharedPreferences("my_preferences2", Context.MODE_PRIVATE)
        val editor2 = sharedPref2.edit()
        editor2.putString("exam_user_info_list", jsonStr2)
        editor.apply()
        //写入运行数据database
        myData.value.database_exam_user_info_list = t2
    }

    var count by remember { mutableStateOf(0) } // 使用remember 刷新UI
    println(count) //刷新UI

    fun tool_sound(e1: String,f1:String){
        val resources = context.resources
        val resourceId = resources.getIdentifier(e1, f1, context.packageName)
        val mediaPlayer = MediaPlayer.create(context, resourceId)
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener {
            it.release()
        }
    }

    //case1
    fun hepler_case1_select(xy: Int) {
        var l1 = myData.value.exam_case[0]["question"]!![xy][2]
        println(l1)
        tool_sound(e1=l1, f1 = "raw")//发出声音
        if (myData.value.case1_button_location == "2" ||myData.value.case1_button_location == "3"){
            //锁定界面
        }else{
            myData.value.case1_background_list = mutableListOf(
                Color.White,
                Color.White,
                Color.White
            )
            myData.value.case1_background_list[xy] = Color.Blue
            myData.value.case1_button_location = "1"//辅助 改变按钮
        }
        count++//刷新UI 辅助
    }
    fun hepler_case1_verify_result(){
        println("hepler_case1_verify_result()")
        println(myData.value.case1_background_list.indexOfFirst { it == Color.Blue})
        var xy:Int = myData.value.case1_background_list.indexOfFirst { it == Color.Blue}
        if (myData.value.exam_case[0]["answer"]!![0][0] == myData.value.exam_case[0]["question"]!![xy][0]){
            println("hepler_case1_verify_result() -----right")
            count++//刷新UI 辅助
            //答对
            myData.value.case1_button_location = "2"//辅助 改变按钮
            tool_sound(e1="ok", f1 = "raw")//发出声音
        }else{
            println("hepler_case1_verify_result() -----wrong")
            count++//刷新UI 辅助
            //答错
            myData.value.case1_button_location = "3"//辅助 改变按钮
            tool_sound(e1="fail", f1 = "raw")//发出声音
        }
    }
    fun hepler_case1_next_items_push(e1: String){
        println("hepler_case1_next_items_push()")
        if (e1 == "right"){ //告诉函数 从HTML过来的是对的
//            new_list.shift(); //答对了,消除一个元素
            println(myData.value.exam_case)
            myData.value.exam_case.removeAt(0)
            println(myData.value.exam_case)
        }else{
            var add1 = myData.value.exam_case[0]
            myData.value.exam_case.removeAt(0)
            myData.value.exam_case.add(add1)
//            new_list.push(t1)//答错了,元素移动到后面
        }
        if (myData.value.exam_case.size <=0){
            println("结束考试")
            myData.value.case1_background_list = mutableListOf(
                Color.White,
                Color.White,
                Color.White
            )//重要,判断位置
            myData.value.case1_button_location = "0"//底部4个按钮的显示
            database_userinfo_map_add_one(name1 = myData.value.exam_case_xy)
            tool_sound(e1="end", f1 = "raw")//发出声音
        }else{
            println("继续考试")
            myData.value.case1_background_list = mutableListOf(
                Color.White,
                Color.White,
                Color.White
            )//重要,判断位置
            myData.value.case1_button_location = "0"//底部4个按钮的显示
        }
        count++//刷新UI 辅助
    }
    //case2
    fun hepler_case2_select(xy: Int){
        var l1 = myData.value.exam_case[0]["question"]!![xy][2]
        println(l1)
        tool_sound(e1=l1, f1 = "raw")//发出声音
        fun do_white(){
            val result = myData.value.case2_background_list.contains(Color.Blue)//看看有没有蓝色在列表
            if (result == false){
                //A 全场没有蓝色元素
                myData.value.case2_background_list[xy] = Color.Blue
            }else{
                //B 全场有蓝色元素
                println("全场有蓝色元素")
                var a1 = myData.value.case2_background_list.indexOf(Color.Blue)
                if (myData.value.case2_left_right_list[a1] == myData.value.case2_left_right_list[xy]){
                    //B-1 同边有蓝色元素
                    myData.value.case2_background_list[a1] = Color.White
                    myData.value.case2_background_list[xy] = Color.Blue
                }else{
                    //B-2 对边有蓝色元素 ,执行配对
                    if (myData.value.exam_case[0]["question"]!![a1] != myData.value.exam_case[0]["question"]!![xy]){
                        println("//B-2-1 配对错误")
                        myData.value.case2_background_list[a1] = Color.Red
                        myData.value.case2_background_list[xy] = Color.Red
                        Timer().schedule(object : TimerTask() {
                            override fun run() {
                                myData.value.case2_background_list[a1] = Color.White
                                myData.value.case2_background_list[xy] = Color.White
                                count++ //刷UI
                            }
                        }, 500)
                    }else{
                        println("//B-2-2 配对成功")
                        myData.value.case2_background_list[a1] = Color.Green
                        myData.value.case2_background_list[xy] = Color.Green
                        val result = myData.value.case2_background_list.all({ it == Color.Green })//是否列表全部是绿色了
                        if (result){myData.value.case2_button_location = "2"};
                    }
                }
            }
            count++ //刷UI
        }
        fun do_blue(){
            myData.value.case2_background_list[xy] = Color.White// 点击自己，变回白色
        }
        when (myData.value.case2_background_list[xy]) {
            Color.Gray -> println("Gray")
            Color.Red -> println("Red")
            Color.Green -> println("Green")
            Color.Blue -> do_blue()
            Color.White -> do_white()
            else ->{ println("else")}
        }
        count++ //刷UI
    }
    fun hepler_case2_next_items_push(e1: String){
        println("hepler_case2_next_items_push")
        tool_sound(e1="ok", f1 = "raw")//发出声音
        myData.value.exam_case.removeAt(0)
        if (myData.value.exam_case.size <=0){
            //考试结束
            println("考试结束")
            myData.value.case2_background_list = mutableListOf(Color.White, Color.White, Color.White,Color.White, Color.White, Color.White)//重要,判断位置
            myData.value.case2_left_right_list = mutableListOf("left", "left", "left","right", "right", "right")
            myData.value.case2_button_location = "0"//底部4个按钮的显示
            database_userinfo_map_add_one(name1 = myData.value.exam_case_xy)
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    tool_sound(e1="end", f1 = "raw")//发出声音
                    count++ //刷UI
                }
            }, 500)

        }else{
            //考试继续
            println("考试继续")
            myData.value.case2_background_list = mutableListOf(Color.White, Color.White, Color.White,Color.White, Color.White, Color.White)//重要,判断位置
            myData.value.case2_left_right_list = mutableListOf("left", "left", "left","right", "right", "right")
            myData.value.case2_button_location = "0"//底部4个按钮的显示
        }
        count++
    }
    //case3
    fun hepler_case3_select(xy: Int){
        var l1 = myData.value.exam_case[0]["question"]!![xy][2]
        println(l1)
        tool_sound(e1=l1, f1 = "raw")//发出声音
        fun do_white(){
            val result = myData.value.case3_background_list.contains(Color.Blue)//看看有没有蓝色在列表
            if (result == false){
                //A 全场没有蓝色元素
                myData.value.case3_background_list[xy] = Color.Blue
            }else{
                //B 全场有蓝色元素
                println("全场有蓝色元素")
                var a1 = myData.value.case3_background_list.indexOf(Color.Blue)
                if (myData.value.case3_left_right_list[a1] == myData.value.case3_left_right_list[xy]){
                    //B-1 同边有蓝色元素
                    myData.value.case3_background_list[a1] = Color.White
                    myData.value.case3_background_list[xy] = Color.Blue
                }else{
                    //B-2 对边有蓝色元素 ,执行配对
                    if (myData.value.exam_case[0]["question"]!![a1] != myData.value.exam_case[0]["question"]!![xy]){
                        println("//B-2-1 配对错误")
                        myData.value.case3_background_list[a1] = Color.Red
                        myData.value.case3_background_list[xy] = Color.Red
                        Timer().schedule(object : TimerTask() {
                            override fun run() {
                                myData.value.case3_background_list[a1] = Color.White
                                myData.value.case3_background_list[xy] = Color.White
                                count++ //刷UI
                            }
                        }, 500)
                    }else{
                        println("//B-2-2 配对成功")
                        myData.value.case3_background_list[a1] = Color.Green
                        myData.value.case3_background_list[xy] = Color.Green
                        val result = myData.value.case3_background_list.all({ it == Color.Green })//是否列表全部是绿色了
                        if (result){myData.value.case3_button_location = "2"};
                    }
                }
            }
            count++ //刷UI
        }
        fun do_blue(){
            myData.value.case3_background_list[xy] = Color.White// 点击自己，变回白色
        }
        when (myData.value.case3_background_list[xy]) {
            Color.Gray -> println("Gray")
            Color.Red -> println("Red")
            Color.Green -> println("Green")
            Color.Blue -> do_blue()
            Color.White -> do_white()
            else ->{ println("else")}
        }
        count++ //刷UI
    }
    fun hepler_case3_next_items_push(e1: String){
        println("hepler_case3_next_items_push")
        tool_sound(e1="ok", f1 = "raw")//发出声音
        myData.value.exam_case.removeAt(0)
        if (myData.value.exam_case.size <=0){
            //考试结束
            println("考试结束")
            myData.value.case3_background_list = mutableListOf(Color.White, Color.White,Color.White, Color.White,Color.White, Color.White, Color.White,Color.White, Color.White, Color.White)//重要,判断位置
            myData.value.case3_left_right_list = mutableListOf("left","left", "left","left", "left","right","right","right","right","right")//重要,判断左右
            myData.value.case3_button_location = "0"//底部4个按钮的显示
            database_userinfo_map_add_one(name1 = myData.value.exam_case_xy)
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    tool_sound(e1="end", f1 = "raw")//发出声音
                    count++ //刷UI
                }
            }, 500)

        }else{
            //考试继续
            println("考试继续")
            myData.value.case3_background_list = mutableListOf(Color.White, Color.White,Color.White, Color.White,Color.White, Color.White, Color.White,Color.White, Color.White, Color.White)//重要,判断位置
            myData.value.case3_left_right_list = mutableListOf("left","left", "left","left", "left","right","right","right","right","right")//重要,判断左右
            myData.value.case3_button_location = "0"//底部4个按钮的显示
        }
        count++
    }

    fun hepler_case4_select(xy: Int) {
        var l1 = myData.value.exam_case[0]["question"]!![xy][2]
        println(l1)
        tool_sound(e1=l1, f1 = "raw")//发出声音
        if (myData.value.case4_button_location == "2" ||myData.value.case4_button_location == "3"){
            //锁定界面
        }else{
            myData.value.case4_background_list = mutableListOf(
                Color.White,
                Color.White,
                Color.White
            )
            myData.value.case4_background_list[xy] = Color.Blue
            myData.value.case4_button_location = "1"//辅助 改变按钮
        }
        count++//刷新UI 辅助
    }
    fun hepler_case4_verify_result(){
        var xy:Int = myData.value.case4_background_list.indexOfFirst { it == Color.Blue}
        if (myData.value.exam_case[0]["answer"]!![0][0] == myData.value.exam_case[0]["question"]!![xy][0]){
            println("hepler_case1_verify_result() -----right")
            count++//刷新UI 辅助
            //答对
            myData.value.case4_button_location = "2"//辅助 改变按钮
            tool_sound(e1="ok", f1 = "raw")//发出声音
        }else{
            println("hepler_case1_verify_result() -----wrong")
            count++//刷新UI 辅助
            //答错
            myData.value.case4_button_location = "3"//辅助 改变按钮
            tool_sound(e1="fail", f1 = "raw")//发出声音
        }
    }
    fun hepler_case4_next_items_push(e1: String){

        if (e1 == "right"){ //告诉函数 从HTML过来的是对的
//            new_list.shift(); //答对了,消除一个元素
            println(myData.value.exam_case)
            myData.value.exam_case.removeAt(0)
            println(myData.value.exam_case)
        }else{
            var add1 = myData.value.exam_case[0]
            myData.value.exam_case.removeAt(0)
            myData.value.exam_case.add(add1)
//            new_list.push(t1)//答错了,元素移动到后面
        }
        if (myData.value.exam_case.size <=0){
            println("结束考试")
            myData.value.case4_background_list = mutableListOf(
                Color.White,
                Color.White,
                Color.White
            )//重要,判断位置
            myData.value.case4_button_location = "0"//底部4个按钮的显示
            database_userinfo_map_add_one(name1 = myData.value.exam_case_xy)
            tool_sound(e1="end", f1 = "raw")//发出声音
        }else{
            println("继续考试")
            myData.value.case4_background_list = mutableListOf(
                Color.White,
                Color.White,
                Color.White
            )//重要,判断位置
            myData.value.case4_button_location = "0"//底部4个按钮的显示
        }
        count++//刷新UI 辅助
    }

    Column {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(47.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {},
                        onTap = {
                            main_io.value = "1"
                            //恢复case1的数据
                            myData.value.case1_background_list = mutableListOf(
                                Color.White,
                                Color.White,
                                Color.White
                            )//重要,判断位置
                            myData.value.case1_button_location = "0"//底部4个按钮的显示
                            //恢复case2的数据
                            myData.value.case2_background_list = mutableListOf(
                                Color.White,
                                Color.White,
                                Color.White,
                                Color.White,
                                Color.White,
                                Color.White
                            )//重要,判断位置
                            myData.value.case2_left_right_list = mutableListOf(
                                "left",
                                "left",
                                "left",
                                "right",
                                "right",
                                "right"
                            )//重要,判断左右
                            myData.value.case2_button_location = "0"//底部4个按钮的显示
                            //恢复case3的数据
                            myData.value.case3_background_list = mutableListOf(
                                Color.White,
                                Color.White,
                                Color.White,
                                Color.White,
                                Color.White,
                                Color.White,
                                Color.White,
                                Color.White,
                                Color.White,
                                Color.White
                            )//重要,判断位置
                            myData.value.case3_left_right_list = mutableListOf(
                                "left",
                                "left",
                                "left",
                                "left",
                                "left",
                                "right",
                                "right",
                                "right",
                                "right",
                                "right"
                            )//重要,判断左右
                            myData.value.case3_button_location = "0"//底部4个按钮的显示
                            //恢复case4的数据
                            myData.value.case4_background_list = mutableListOf(
                                Color.White,
                                Color.White,
                                Color.White
                            )//重要,判断位置
                            myData.value.case4_button_location = "0"//底部4个按钮的显示
                        }
                    )
                },
        ) {
            Image(
                painter = painterResource(id = R.drawable.round_chevron_left_20),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.Gray),
                modifier = Modifier
                    .size(32.dp)
            )
        }
        Divider(thickness = 0.8.dp, color = Color.Gray)


        var whith0:Float = (myData.value.exam_case_len.toFloat()-myData.value.exam_case.size.toFloat())/myData.value.exam_case_len.toFloat()
        var whith1:Float = 0.4f
        println(whith0)
        //外进度条
        Column(
            modifier = Modifier
                .padding(20.dp)
                .height(28.dp)
                .fillMaxWidth()
                .border(width = 0.8.dp, color = Color.Gray, RoundedCornerShape(14.dp))
        ) {
            //内进度条
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(whith0)
                    .background(
                        Color.Green,
                        shape = RoundedCornerShape(14.dp)
                    )
            ) {

            }
        }

        //防止空数据
        if (myData.value.exam_case.size == 0) {
            Row(
                modifier = Modifier
                    .padding(40.dp)
                    .height(48.dp)
                    .fillMaxWidth()
//                                .background(Color.Gray)
                    .background(color = Color.Green, shape = RoundedCornerShape(8.dp))
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                main_io.value = "1"
                                //恢复case1的数据
                                myData.value.case1_background_list = mutableListOf(
                                    Color.White,
                                    Color.White,
                                    Color.White
                                )//重要,判断位置
                                myData.value.case1_button_location = "0"//底部4个按钮的显示
                                //恢复case2的数据
                                myData.value.case2_background_list = mutableListOf(
                                    Color.White,
                                    Color.White,
                                    Color.White,
                                    Color.White,
                                    Color.White,
                                    Color.White
                                )//重要,判断位置
                                myData.value.case2_left_right_list = mutableListOf(
                                    "left",
                                    "left",
                                    "left",
                                    "right",
                                    "right",
                                    "right"
                                )//重要,判断左右
                                myData.value.case2_button_location = "0"//底部4个按钮的显示
                                //恢复case3的数据
                                myData.value.case3_background_list = mutableListOf(
                                    Color.White,
                                    Color.White,
                                    Color.White,
                                    Color.White,
                                    Color.White,
                                    Color.White,
                                    Color.White,
                                    Color.White,
                                    Color.White,
                                    Color.White
                                )//重要,判断位置
                                myData.value.case3_left_right_list = mutableListOf(
                                    "left",
                                    "left",
                                    "left",
                                    "left",
                                    "left",
                                    "right",
                                    "right",
                                    "right",
                                    "right",
                                    "right"
                                )//重要,判断左右
                                myData.value.case3_button_location = "0"//底部4个按钮的显示
                                //恢复case4的数据
                                myData.value.case4_background_list = mutableListOf(
                                    Color.White,
                                    Color.White,
                                    Color.White
                                )//重要,判断位置
                                myData.value.case4_button_location = "0"//底部4个按钮的显示
                            }
                        )
                    },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,

                ) {
                Text(text = "顺利通过")
            }
        }
        ////防止空数据
        if (myData.value.exam_case.size > 0) {
            //case1 模版
            if (myData.value.exam_case[0]["style"]!![0][0] == "1") {
                var t1: String = "单词:" + myData.value.exam_case[0]["answer"]!![0][1]
                Text(
                    text = t1,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .height(47.dp)
                        .fillMaxWidth()
                        .padding(top = 18.dp)
                )
                Row(
                    modifier = Modifier
                        .padding(20.dp)
                        .height(48.dp)
                        .fillMaxWidth()
                        .background(
                            myData.value.case1_background_list[0],
                            shape = RoundedCornerShape(8.dp)
                        )
                        .border(width = 1.dp, color = Color.Blue, RoundedCornerShape(8.dp))
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onLongPress = {},
                                onTap = {
                                    hepler_case1_select(xy = 0)
                                }
                            )
                        },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = myData.value.exam_case[0]["question"]!![0][0],
                        color = if (myData.value.case1_background_list[0] == Color.White) Color.Black else Color.White
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(20.dp)
                        .height(48.dp)
                        .fillMaxWidth()
                        .background(
                            myData.value.case1_background_list[1],
                            shape = RoundedCornerShape(8.dp)
                        )
                        .border(width = 1.dp, color = Color.Blue, RoundedCornerShape(8.dp))
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onLongPress = {},
                                onTap = {
                                    hepler_case1_select(xy = 1)
                                }
                            )
                        },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = myData.value.exam_case[0]["question"]!![1][0],
                        color = if (myData.value.case1_background_list[1] == Color.White) Color.Black else Color.White
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(20.dp)
                        .height(48.dp)
                        .fillMaxWidth()
                        .background(
                            myData.value.case1_background_list[2],
                            shape = RoundedCornerShape(8.dp)
                        )
                        .border(width = 1.dp, color = Color.Blue, RoundedCornerShape(8.dp))
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onLongPress = {},
                                onTap = {
                                    hepler_case1_select(xy = 2)
                                }
                            )
                        },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = myData.value.exam_case[0]["question"]!![2][0],
                        color = if (myData.value.case1_background_list[2] == Color.White) Color.Black else Color.White
                    )
                }


                when (myData.value.case1_button_location) {
                    "0" ->
                        Row(
                            modifier = Modifier
                                .padding(40.dp)
                                .height(48.dp)
                                .fillMaxWidth()
//                                .background(Color.Gray)
                                .background(color = Color.Gray, shape = RoundedCornerShape(8.dp))
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = {},
                                        onTap = {
                                            //
                                        }
                                    )
                                },
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,

                        ) {
                            Text(text = "选择答案")
                        }


                    "1" ->
                        Row(
                            modifier = Modifier
                                .padding(40.dp)
                                .height(48.dp)
                                .fillMaxWidth()
                                .background(Color.Cyan, shape = RoundedCornerShape(8.dp))
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = {},
                                        onTap = {
                                            //
                                            hepler_case1_verify_result()
                                        }
                                    )
                                },
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(text = "提交答案", color = Color.White)
                        }
                    "2" ->
                        Row(
                            modifier = Modifier
                                .padding(40.dp)
                                .height(48.dp)
                                .fillMaxWidth()
                                .background(Color.Green, shape = RoundedCornerShape(8.dp))
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = {},
                                        onTap = {
                                            //
                                            hepler_case1_next_items_push(e1 = "right")
                                        }
                                    )
                                },
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(text = "答题正确,下一题", color = Color.White)
                        }
                    "3" ->
                        Row(
                            modifier = Modifier
                                .padding(40.dp)
                                .height(48.dp)
                                .fillMaxWidth()
                                .background(Color.Red, shape = RoundedCornerShape(8.dp))
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = {},
                                        onTap = {
                                            //
                                            hepler_case1_next_items_push(e1 = "wrong")
                                        }
                                    )
                                },
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(text = "答题错误,下一题", color = Color.White)
                        }
                    else -> { // 注意这个块
                        print("x is neither 1 nor 2")
                    }
                }

            }
            //case2 模版
            if (myData.value.exam_case[0]["style"]!![0][0] == "2") {
                    Row() {
                        //左边三个
                        Column(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(20.dp)
                                    .height(48.dp)
                                    .fillMaxWidth()
                                    .background(
                                        myData.value.case2_background_list[0],
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = Color.Blue,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .pointerInput(Unit) {
                                        detectTapGestures(
                                            onLongPress = {},
                                            onTap = {
                                                hepler_case2_select(xy = 0)
                                            }
                                        )
                                    },
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = myData.value.exam_case[0]["question"]!![0][0],
                                    color = if (myData.value.case2_background_list[0] == Color.White) Color.Black else Color.White
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .padding(20.dp)
                                    .height(48.dp)
                                    .fillMaxWidth()
                                    .background(
                                        myData.value.case2_background_list[1],
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = Color.Blue,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .pointerInput(Unit) {
                                        detectTapGestures(
                                            onLongPress = {},
                                            onTap = {
                                                hepler_case2_select(xy = 1)
                                            }
                                        )
                                    },
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = myData.value.exam_case[0]["question"]!![1][0],
                                    color = if (myData.value.case2_background_list[1] == Color.White) Color.Black else Color.White
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .padding(20.dp)
                                    .height(48.dp)
                                    .fillMaxWidth()
                                    .background(
                                        myData.value.case2_background_list[2],
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = Color.Blue,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .pointerInput(Unit) {
                                        detectTapGestures(
                                            onLongPress = {},
                                            onTap = {
                                                hepler_case2_select(xy = 2)
                                            }
                                        )
                                    },
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = myData.value.exam_case[0]["question"]!![2][0],
                                    color = if (myData.value.case2_background_list[2] == Color.White) Color.Black else Color.White
                                )
                            }
                        }
                        //右边三个
                        Column(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(20.dp)
                                    .height(48.dp)
                                    .fillMaxWidth()
                                    .background(
                                        myData.value.case2_background_list[3],
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = Color.Blue,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .pointerInput(Unit) {
                                        detectTapGestures(
                                            onLongPress = {},
                                            onTap = {
                                                hepler_case2_select(xy = 3)
                                            }
                                        )
                                    },
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = myData.value.exam_case[0]["question"]!![3][1],
                                    color = if (myData.value.case2_background_list[3] == Color.White) Color.Black else Color.White
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .padding(20.dp)
                                    .height(48.dp)
                                    .fillMaxWidth()
                                    .background(
                                        myData.value.case2_background_list[4],
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = Color.Blue,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .pointerInput(Unit) {
                                        detectTapGestures(
                                            onLongPress = {},
                                            onTap = {
                                                hepler_case2_select(xy = 4)
                                            }
                                        )
                                    },
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = myData.value.exam_case[0]["question"]!![4][1],
                                    color = if (myData.value.case2_background_list[4] == Color.White) Color.Black else Color.White
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .padding(20.dp)
                                    .height(48.dp)
                                    .fillMaxWidth()
                                    .background(
                                        myData.value.case2_background_list[5],
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = Color.Blue,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .pointerInput(Unit) {
                                        detectTapGestures(
                                            onLongPress = {},
                                            onTap = {
                                                hepler_case2_select(xy = 5)
                                            }
                                        )
                                    },
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = myData.value.exam_case[0]["question"]!![5][1],
                                    color = if (myData.value.case2_background_list[5] == Color.White) Color.Black else Color.White
                                )
                            }
                        }
                    }
                    when (myData.value.case2_button_location) {
                    "0" ->
                        Row(
                            modifier = Modifier
                                .padding(40.dp)
                                .height(48.dp)
                                .fillMaxWidth()
//                                .background(Color.Gray)
                                .background(color = Color.Gray, shape = RoundedCornerShape(8.dp))
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = {},
                                        onTap = {
                                            //
                                        }
                                    )
                                },
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,

                            ) {
                            Text(text = "选择答案")
                        }
                    "1" ->
                        Row(
                            modifier = Modifier
                                .padding(40.dp)
                                .height(48.dp)
                                .fillMaxWidth()
                                .background(Color.Cyan, shape = RoundedCornerShape(8.dp))
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = {},
                                        onTap = {
                                            //

                                        }
                                    )
                                },
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(text = "提交答案", color = Color.White)
                        }
                    "2" ->
                        Row(
                            modifier = Modifier
                                .padding(40.dp)
                                .height(48.dp)
                                .fillMaxWidth()
                                .background(Color.Green, shape = RoundedCornerShape(8.dp))
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = {},
                                        onTap = {
                                            //
                                            hepler_case2_next_items_push(e1 = "right")
                                        }
                                    )
                                },
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(text = "答题正确,下一题", color = Color.White)
                        }
                    "3" ->
                        Row(
                            modifier = Modifier
                                .padding(40.dp)
                                .height(48.dp)
                                .fillMaxWidth()
                                .background(Color.Red, shape = RoundedCornerShape(8.dp))
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = {},
                                        onTap = {
                                            //
                                            hepler_case2_next_items_push(e1 = "wrong")
                                        }
                                    )
                                },
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(text = "答题错误,下一题", color = Color.White)
                        }
                    else -> { // 注意这个块
                        print("x is neither 1 nor 2")
                    }
                    }
            }
            //case3 模版
            if (myData.value.exam_case[0]["style"]!![0][0] == "3"){
                Row() {
                    //左边5个
                    Column(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(20.dp)
                                .height(48.dp)
                                .fillMaxWidth()
                                .background(
                                    myData.value.case3_background_list[0],
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .border(
                                    width = 1.dp,
                                    color = Color.Blue,
                                    RoundedCornerShape(8.dp)
                                )
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = {},
                                        onTap = {
                                            hepler_case3_select(xy = 0)
                                        }
                                    )
                                },
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = myData.value.exam_case[0]["question"]!![0][0],
                                color = if (myData.value.case3_background_list[0] == Color.White) Color.Black else Color.White
                            )
                        }
                        Row(
                            modifier = Modifier
                                .padding(20.dp)
                                .height(48.dp)
                                .fillMaxWidth()
                                .background(
                                    myData.value.case3_background_list[1],
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .border(
                                    width = 1.dp,
                                    color = Color.Blue,
                                    RoundedCornerShape(8.dp)
                                )
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = {},
                                        onTap = {
                                            hepler_case3_select(xy = 1)
                                        }
                                    )
                                },
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = myData.value.exam_case[0]["question"]!![1][0],
                                color = if (myData.value.case3_background_list[1] == Color.White) Color.Black else Color.White
                            )
                        }
                        Row(
                            modifier = Modifier
                                .padding(20.dp)
                                .height(48.dp)
                                .fillMaxWidth()
                                .background(
                                    myData.value.case3_background_list[2],
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .border(
                                    width = 1.dp,
                                    color = Color.Blue,
                                    RoundedCornerShape(8.dp)
                                )
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = {},
                                        onTap = {
                                            hepler_case3_select(xy = 2)
                                        }
                                    )
                                },
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = myData.value.exam_case[0]["question"]!![2][0],
                                color = if (myData.value.case3_background_list[2] == Color.White) Color.Black else Color.White
                            )
                        }
                        Row(
                            modifier = Modifier
                                .padding(20.dp)
                                .height(48.dp)
                                .fillMaxWidth()
                                .background(
                                    myData.value.case3_background_list[3],
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .border(
                                    width = 1.dp,
                                    color = Color.Blue,
                                    RoundedCornerShape(8.dp)
                                )
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = {},
                                        onTap = {
                                            hepler_case3_select(xy = 3)
                                        }
                                    )
                                },
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = myData.value.exam_case[0]["question"]!![3][0],
                                color = if (myData.value.case3_background_list[3] == Color.White) Color.Black else Color.White
                            )
                        }
                        Row(
                            modifier = Modifier
                                .padding(20.dp)
                                .height(48.dp)
                                .fillMaxWidth()
                                .background(
                                    myData.value.case3_background_list[4],
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .border(
                                    width = 1.dp,
                                    color = Color.Blue,
                                    RoundedCornerShape(8.dp)
                                )
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = {},
                                        onTap = {
                                            hepler_case3_select(xy = 4)
                                        }
                                    )
                                },
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = myData.value.exam_case[0]["question"]!![4][0],
                                color = if (myData.value.case3_background_list[4] == Color.White) Color.Black else Color.White
                            )
                        }
                    }
                    //右边三个
                    Column(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(20.dp)
                                .height(48.dp)
                                .fillMaxWidth()
                                .background(
                                    myData.value.case3_background_list[5],
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .border(
                                    width = 1.dp,
                                    color = Color.Blue,
                                    RoundedCornerShape(8.dp)
                                )
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = {},
                                        onTap = {
                                            hepler_case3_select(xy = 5)
                                        }
                                    )
                                },
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = myData.value.exam_case[0]["question"]!![5][1],
                                color = if (myData.value.case3_background_list[5] == Color.White) Color.Black else Color.White
                            )
                        }
                        Row(
                            modifier = Modifier
                                .padding(20.dp)
                                .height(48.dp)
                                .fillMaxWidth()
                                .background(
                                    myData.value.case3_background_list[6],
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .border(
                                    width = 1.dp,
                                    color = Color.Blue,
                                    RoundedCornerShape(8.dp)
                                )
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = {},
                                        onTap = {
                                            hepler_case3_select(xy = 6)
                                        }
                                    )
                                },
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = myData.value.exam_case[0]["question"]!![6][1],
                                color = if (myData.value.case3_background_list[6] == Color.White) Color.Black else Color.White
                            )
                        }
                        Row(
                            modifier = Modifier
                                .padding(20.dp)
                                .height(48.dp)
                                .fillMaxWidth()
                                .background(
                                    myData.value.case3_background_list[7],
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .border(
                                    width = 1.dp,
                                    color = Color.Blue,
                                    RoundedCornerShape(8.dp)
                                )
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = {},
                                        onTap = {
                                            hepler_case3_select(xy = 7)
                                        }
                                    )
                                },
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = myData.value.exam_case[0]["question"]!![7][1],
                                color = if (myData.value.case3_background_list[7] == Color.White) Color.Black else Color.White
                            )
                        }
                        Row(
                            modifier = Modifier
                                .padding(20.dp)
                                .height(48.dp)
                                .fillMaxWidth()
                                .background(
                                    myData.value.case3_background_list[8],
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .border(
                                    width = 1.dp,
                                    color = Color.Blue,
                                    RoundedCornerShape(8.dp)
                                )
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = {},
                                        onTap = {
                                            hepler_case3_select(xy = 8)
                                        }
                                    )
                                },
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = myData.value.exam_case[0]["question"]!![8][1],
                                color = if (myData.value.case3_background_list[8] == Color.White) Color.Black else Color.White
                            )
                        }
                        Row(
                            modifier = Modifier
                                .padding(20.dp)
                                .height(48.dp)
                                .fillMaxWidth()
                                .background(
                                    myData.value.case3_background_list[9],
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .border(
                                    width = 1.dp,
                                    color = Color.Blue,
                                    RoundedCornerShape(8.dp)
                                )
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = {},
                                        onTap = {
                                            hepler_case3_select(xy = 9)
                                        }
                                    )
                                },
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = myData.value.exam_case[0]["question"]!![9][1],
                                color = if (myData.value.case3_background_list[9] == Color.White) Color.Black else Color.White
                            )
                        }
                    }
                }
                when (myData.value.case3_button_location) {
                    "0" ->
                        Row(
                            modifier = Modifier
                                .padding(40.dp)
                                .height(48.dp)
                                .fillMaxWidth()
//                                .background(Color.Gray)
                                .background(color = Color.Gray, shape = RoundedCornerShape(8.dp))
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = {},
                                        onTap = {
                                            //
                                        }
                                    )
                                },
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,

                            ) {
                            Text(text = "选择答案")
                        }
                    "1" ->
                        Row(
                            modifier = Modifier
                                .padding(40.dp)
                                .height(48.dp)
                                .fillMaxWidth()
                                .background(Color.Cyan, shape = RoundedCornerShape(8.dp))
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = {},
                                        onTap = {
                                            //

                                        }
                                    )
                                },
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(text = "提交答案", color = Color.White)
                        }
                    "2" ->
                        Row(
                            modifier = Modifier
                                .padding(40.dp)
                                .height(48.dp)
                                .fillMaxWidth()
                                .background(Color.Green, shape = RoundedCornerShape(8.dp))
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = {},
                                        onTap = {
                                            //
                                            hepler_case3_next_items_push(e1 = "right")
                                        }
                                    )
                                },
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(text = "答题正确,下一题", color = Color.White)
                        }
                    "3" ->
                        Row(
                            modifier = Modifier
                                .padding(40.dp)
                                .height(48.dp)
                                .fillMaxWidth()
                                .background(Color.Red, shape = RoundedCornerShape(8.dp))
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = {},
                                        onTap = {
                                            //
                                            hepler_case3_next_items_push(e1 = "wrong")
                                        }
                                    )
                                },
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(text = "答题错误,下一题", color = Color.White)
                        }
                    else -> { // 注意这个块
                        print("x is neither 1 nor 2")
                    }
                }
            }
            //case4 模版
            if (myData.value.exam_case[0]["style"]!![0][0] == "4") {
                var t1: String = "单词:" + myData.value.exam_case[0]["answer"]!![0][1]
                Text(
                    text = t1,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .height(47.dp)
                        .fillMaxWidth()
                        .padding(top = 18.dp)
                )
                Row() {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(20.dp)
                                .height(168.dp)
                                .fillMaxWidth()
                                .background(
                                    Color.White,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .border(
                                    width = 1.dp,
                                    color = Color.Blue,
                                    RoundedCornerShape(8.dp)
                                )
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = {},
                                        onTap = {
                                            hepler_case4_select(xy = 0)
                                        }
                                    )
                                },
//                            horizontalArrangement = Arrangement.Center,
//                            verticalAlignment = Alignment.CenterVertically,
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Image(
                                painter = painterResource(
                                    LocalContext.current.resources.getIdentifier(
                                        myData.value.exam_case[0]["question"]!![0][2],
                                        "drawable",
                                        LocalContext.current.packageName
                                    )
                                ),
                                contentDescription = "left1",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(128.dp)

                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(40.dp)
                                    .background(
                                        myData.value.case4_background_list[0],
                                        RoundedCornerShape(0.dp, 0.dp, 8.dp, 8.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = myData.value.exam_case[0]["question"]!![0][0],
                                    color = if (myData.value.case4_background_list[0] == Color.White) Color.Black else Color.White,
                                )
                            }
                        }
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(20.dp)
                                .height(168.dp)
                                .fillMaxWidth()
                                .background(
                                    Color.White,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .border(
                                    width = 1.dp,
                                    color = Color.Blue,
                                    RoundedCornerShape(8.dp)
                                )
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = {},
                                        onTap = {
                                            hepler_case4_select(xy = 1)
                                        }
                                    )
                                },
//                            horizontalArrangement = Arrangement.Center,
//                            verticalAlignment = Alignment.CenterVertically,
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Image(
                                painter = painterResource(
                                    LocalContext.current.resources.getIdentifier(
                                        myData.value.exam_case[0]["question"]!![1][2],
                                        "drawable",
                                        LocalContext.current.packageName
                                    )
                                ),
                                contentDescription = "right1",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(128.dp)

                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(40.dp)
                                    .background(
                                        myData.value.case4_background_list[1],
                                        RoundedCornerShape(0.dp, 0.dp, 8.dp, 8.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = myData.value.exam_case[0]["question"]!![1][0],
                                    color = if (myData.value.case4_background_list[1] == Color.White) Color.Black else Color.White,
                                )
                            }
                        }
                    }
                }
                Row() {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(20.dp)
                                .height(168.dp)
                                .fillMaxWidth()
                                .background(
                                    Color.White,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .border(
                                    width = 1.dp,
                                    color = Color.Blue,
                                    RoundedCornerShape(8.dp)
                                )
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = {},
                                        onTap = {
                                            hepler_case4_select(xy = 2)
                                        }
                                    )
                                },
//                            horizontalArrangement = Arrangement.Center,
//                            verticalAlignment = Alignment.CenterVertically,
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Image(
                                painter = painterResource(
                                    LocalContext.current.resources.getIdentifier(
                                        myData.value.exam_case[0]["question"]!![2][2],
                                        "drawable",
                                        LocalContext.current.packageName
                                    )
                                ),
                                contentDescription = "left1",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(128.dp)

                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(40.dp)
                                    .background(
                                        myData.value.case4_background_list[2],
                                        RoundedCornerShape(0.dp, 0.dp, 8.dp, 8.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = myData.value.exam_case[0]["question"]!![2][0],
                                    color = if (myData.value.case4_background_list[2] == Color.White) Color.Black else Color.White,
                                )
                            }
                        }
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                    }
                }
                when (myData.value.case4_button_location) {
                    "0" ->
                        Row(
                            modifier = Modifier
                                .padding(40.dp)
                                .height(48.dp)
                                .fillMaxWidth()
//                                .background(Color.Gray)
                                .background(color = Color.Gray, shape = RoundedCornerShape(8.dp))
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = {},
                                        onTap = {
                                            //
                                        }
                                    )
                                },
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,

                            ) {
                            Text(text = "选择答案")
                        }


                    "1" ->
                        Row(
                            modifier = Modifier
                                .padding(40.dp)
                                .height(48.dp)
                                .fillMaxWidth()
                                .background(Color.Cyan, shape = RoundedCornerShape(8.dp))
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = {},
                                        onTap = {
                                            //
                                            hepler_case4_verify_result()
                                        }
                                    )
                                },
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(text = "提交答案", color = Color.White)
                        }
                    "2" ->
                        Row(
                            modifier = Modifier
                                .padding(40.dp)
                                .height(48.dp)
                                .fillMaxWidth()
                                .background(Color.Green, shape = RoundedCornerShape(8.dp))
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = {},
                                        onTap = {
                                            //
                                            hepler_case4_next_items_push(e1 = "right")
                                        }
                                    )
                                },
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(text = "答题正确,下一题", color = Color.White)
                        }
                    "3" ->
                        Row(
                            modifier = Modifier
                                .padding(40.dp)
                                .height(48.dp)
                                .fillMaxWidth()
                                .background(Color.Red, shape = RoundedCornerShape(8.dp))
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = {},
                                        onTap = {
                                            //
                                            hepler_case4_next_items_push(e1 = "wrong")
                                        }
                                    )
                                },
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(text = "答题错误,下一题", color = Color.White)
                        }
                    else -> { // 注意这个块
                        print("x is neither 1 nor 2")
                    }
                }
            }

        }
    }
}
@Composable
fun  MainMe() {
    val context = LocalContext.current//重要，资源路径

    fun database_userinfo_map_add_one_2(name1:String){
        var t1 = myData.value.database_exam_list //先读取数据
        var a1:Int = t1[name1]!!.toInt() + 1
        t1[name1] = a1.toString()
        // 将可变 Map 对象转换为不可变的 Map 对象
        val immutableMap = t1.toMap() //如果 t1 中的值被改变了，immutableMap 的值也会跟着改变，因为它们存储的是同一个对象的引用
        //写入getSharedPreferences里面
        val jsonStr = JSONObject(immutableMap).toString()
        var sharedPref = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("exam_list", jsonStr)
        editor.apply()
        //写入运行数据database
        myData.value.database_exam_list = t1
    }

    Column (){
        LazyColumn(
            Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            item {
                Text(
                    text = "资料",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .height(48.dp)
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                Divider(thickness = 1.dp, color = Color.Gray)
                Row() {
                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                            .height(128.dp)
                            .weight(1f)
                            .background(
                                Color.White,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = Color.Gray,
                                RoundedCornerShape(8.dp)
                            ),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(text = myData.value.database_exam_user_info_list["keep_score"]?:"0")
                        Text(text = "能量剩余")
                    }
                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                            .height(128.dp)
                            .weight(1f)
                            .background(
                                Color.White,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = Color.Gray,
                                RoundedCornerShape(8.dp)
                            ),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(text = myData.value.database_exam_user_info_list["keep_wealth"]?:"0")
                        Text(text = "游戏积分")
                    }
                }
                Row() {
                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                            .height(128.dp)
                            .weight(1f)
                            .background(
                                Color.White,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = Color.Gray,
                                RoundedCornerShape(8.dp)
                            ),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(text = myData.value.database_exam_user_info_list["keep_time"]?:"0")
                        Text(text = "学习时间")
                    }
                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                            .height(128.dp)
                            .weight(1f)
                            .background(
                                Color.White,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = Color.Gray,
                                RoundedCornerShape(8.dp)
                            ),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(text = myData.value.database_exam_user_info_list["keep_words"]?:"0")
                        Text(text = "闯关次数")
                    }
                }
                Divider(thickness = 1.dp, color = Color.Gray)
                Column(
                    verticalArrangement = Arrangement.Center,
//                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .height(48.dp)
                        .fillMaxWidth()
                        .padding(8.dp)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onLongPress = {},
                                onTap = {
                                    main_io.value = "4"
                                }
                            )
                        },
                ) {
                    Text(
                        text = "设置",
                        fontSize = 18.sp,
                    )
                }
                Divider(thickness = 1.dp, color = Color.Gray)
            }
        }

        //底部导航条
        Column (
            Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Color.White)
        ){
            Divider(thickness = 1.dp, color = Color.Gray)
            my_menu()
        }

    }
}

@Composable
fun my_top2(){
    Column (){
        Text(
            text = "GAME . 游戏",
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .height(47.dp)
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
    }
}

@Composable
fun my_main2(){

    Column (

    ){
        for (i in 1..100) {
            Text(text = "垂直排列 main for in A")
        }
    }
}



@Composable
fun my_menu2(){
    Row (){
        val imageModifier1 = Modifier
            .size(32.dp)
            .weight(1f)
            .padding(top = 8.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {},
                    onTap = {
                        main_io.value = "1"
                        println("333")
                    }
                )
            }
        val imageModifier2 = Modifier
            .size(32.dp)
            .weight(1f)
            .padding(top = 8.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {},
                    onTap = {
                        main_io.value = "2"
                        println("444")
                    }
                )
            }
        Image(
            painter = painterResource(id = R.drawable.exam2),
            contentDescription = null,
            modifier = imageModifier1
        )
        Image(
            painter = painterResource(id = R.drawable.me1),
            contentDescription = null,
            modifier = imageModifier2
        )
    }
}



fun helper_choose_exam_data(e1: String): Unit {
    main_io.value = "3" //页面转换
    myData.value.exam_case_xy = e1 //赋值 那个单元
    //MARK:创建中心数据
    //3年级 上册
    if (e1 == "3_1_1" && myData.value.database_exam_list[e1] == "1" ){helper_creat_data_case4(my_answer = word_3_1_1, my_question = word_3_1_1)};
    if (e1 == "3_1_1" && myData.value.database_exam_list[e1] == "2" ){helper_creat_data_case2(my_answer = word_3_1_1, my_question = word_3_1_1)};
    if (e1 == "3_1_1" && myData.value.database_exam_list[e1] == "3" ){helper_creat_data_case3(my_answer = word_3_1_1, my_question = word_3_1_1)};
    if (e1 == "3_1_1" && myData.value.database_exam_list[e1] == "4" ){helper_creat_data_caseA(my_answer = word_3_1_1, my_question = word_3_1_1)};
    if (e1 == "3_1_2" && myData.value.database_exam_list[e1] == "1" ){helper_creat_data_case4(my_answer = word_3_1_2, my_question = word_3_1_2)};
    if (e1 == "3_1_2" && myData.value.database_exam_list[e1] == "2" ){helper_creat_data_case2(my_answer = word_3_1_2, my_question = word_3_1_2)};
    if (e1 == "3_1_2" && myData.value.database_exam_list[e1] == "3" ){helper_creat_data_case3(my_answer = word_3_1_2, my_question = word_3_1_2)};
    if (e1 == "3_1_2" && myData.value.database_exam_list[e1] == "4" ){helper_creat_data_caseA(my_answer = word_3_1_2, my_question = word_3_1_2)};
    if (e1 == "3_1_3" && myData.value.database_exam_list[e1] == "1" ){helper_creat_data_case4(my_answer = word_3_1_3, my_question = word_3_1_3)};
    if (e1 == "3_1_3" && myData.value.database_exam_list[e1] == "2" ){helper_creat_data_case2(my_answer = word_3_1_3, my_question = word_3_1_3)};
    if (e1 == "3_1_3" && myData.value.database_exam_list[e1] == "3" ){helper_creat_data_case3(my_answer = word_3_1_3, my_question = word_3_1_3)};
    if (e1 == "3_1_3" && myData.value.database_exam_list[e1] == "4" ){helper_creat_data_caseA(my_answer = word_3_1_3, my_question = word_3_1_3)};
    if (e1 == "3_1_4" && myData.value.database_exam_list[e1] == "1" ){helper_creat_data_case4(my_answer = word_3_1_4, my_question = word_3_1_4)};
    if (e1 == "3_1_4" && myData.value.database_exam_list[e1] == "2" ){helper_creat_data_case2(my_answer = word_3_1_4, my_question = word_3_1_4)};
    if (e1 == "3_1_4" && myData.value.database_exam_list[e1] == "3" ){helper_creat_data_case3(my_answer = word_3_1_4, my_question = word_3_1_4)};
    if (e1 == "3_1_4" && myData.value.database_exam_list[e1] == "4" ){helper_creat_data_caseA(my_answer = word_3_1_4, my_question = word_3_1_4)};
    if (e1 == "3_1_5" && myData.value.database_exam_list[e1] == "1" ){helper_creat_data_case4(my_answer = word_3_1_5, my_question = word_3_1_5)};
    if (e1 == "3_1_5" && myData.value.database_exam_list[e1] == "2" ){helper_creat_data_case2(my_answer = word_3_1_5, my_question = word_3_1_5)};
    if (e1 == "3_1_5" && myData.value.database_exam_list[e1] == "3" ){helper_creat_data_case3(my_answer = word_3_1_5, my_question = word_3_1_5)};
    if (e1 == "3_1_5" && myData.value.database_exam_list[e1] == "4" ){helper_creat_data_caseA(my_answer = word_3_1_5, my_question = word_3_1_5)};
    if (e1 == "3_1_6" && myData.value.database_exam_list[e1] == "1" ){helper_creat_data_case4(my_answer = word_3_1_6, my_question = word_3_1_6)};
    if (e1 == "3_1_6" && myData.value.database_exam_list[e1] == "2" ){helper_creat_data_case2(my_answer = word_3_1_6, my_question = word_3_1_6)};
    if (e1 == "3_1_6" && myData.value.database_exam_list[e1] == "3" ){helper_creat_data_case3(my_answer = word_3_1_6, my_question = word_3_1_6)};
    if (e1 == "3_1_6" && myData.value.database_exam_list[e1] == "4" ){helper_creat_data_caseA(my_answer = word_3_1_6, my_question = word_3_1_6)};
        //3年级 下册
    if (e1 == "3_2_1" && myData.value.database_exam_list[e1] == "1" ){helper_creat_data_case1(my_answer = word_3_2_1, my_question = word_3_2_1)};
    if (e1 == "3_2_1" && myData.value.database_exam_list[e1] == "2" ){helper_creat_data_case2(my_answer = word_3_2_1, my_question = word_3_2_1)};
    if (e1 == "3_2_1" && myData.value.database_exam_list[e1] == "3" ){helper_creat_data_case3(my_answer = word_3_2_1, my_question = word_3_2_1)};
    if (e1 == "3_2_1" && myData.value.database_exam_list[e1] == "4" ){helper_creat_data_caseA(my_answer = word_3_2_1, my_question = word_3_2_1)};
    if (e1 == "3_2_2" && myData.value.database_exam_list[e1] == "1" ){helper_creat_data_case1(my_answer = word_3_2_2, my_question = word_3_2_2)};
    if (e1 == "3_2_2" && myData.value.database_exam_list[e1] == "2" ){helper_creat_data_case2(my_answer = word_3_2_2, my_question = word_3_2_2)};
    if (e1 == "3_2_2" && myData.value.database_exam_list[e1] == "3" ){helper_creat_data_case3(my_answer = word_3_2_2, my_question = word_3_2_2)};
    if (e1 == "3_2_2" && myData.value.database_exam_list[e1] == "4" ){helper_creat_data_caseA(my_answer = word_3_2_2, my_question = word_3_2_2)};
    if (e1 == "3_2_3" && myData.value.database_exam_list[e1] == "1" ){helper_creat_data_case1(my_answer = word_3_2_3, my_question = word_3_2_3)};
    if (e1 == "3_2_3" && myData.value.database_exam_list[e1] == "2" ){helper_creat_data_case2(my_answer = word_3_2_3, my_question = word_3_2_3)};
    if (e1 == "3_2_3" && myData.value.database_exam_list[e1] == "3" ){helper_creat_data_case3(my_answer = word_3_2_3, my_question = word_3_2_3)};
    if (e1 == "3_2_3" && myData.value.database_exam_list[e1] == "4" ){helper_creat_data_caseA(my_answer = word_3_2_3, my_question = word_3_2_3)};
    if (e1 == "3_2_4" && myData.value.database_exam_list[e1] == "1" ){helper_creat_data_case1(my_answer = word_3_2_4, my_question = word_3_2_4)};
    if (e1 == "3_2_4" && myData.value.database_exam_list[e1] == "2" ){helper_creat_data_case2(my_answer = word_3_2_4, my_question = word_3_2_4)};
    if (e1 == "3_2_4" && myData.value.database_exam_list[e1] == "3" ){helper_creat_data_case3(my_answer = word_3_2_4, my_question = word_3_2_4)};
    if (e1 == "3_2_4" && myData.value.database_exam_list[e1] == "4" ){helper_creat_data_caseA(my_answer = word_3_2_4, my_question = word_3_2_4)};
    if (e1 == "3_2_5" && myData.value.database_exam_list[e1] == "1" ){helper_creat_data_case1(my_answer = word_3_2_5, my_question = word_3_2_5)};
    if (e1 == "3_2_5" && myData.value.database_exam_list[e1] == "2" ){helper_creat_data_case2(my_answer = word_3_2_5, my_question = word_3_2_5)};
    if (e1 == "3_2_5" && myData.value.database_exam_list[e1] == "3" ){helper_creat_data_case3(my_answer = word_3_2_5, my_question = word_3_2_5)};
    if (e1 == "3_2_5" && myData.value.database_exam_list[e1] == "4" ){helper_creat_data_caseA(my_answer = word_3_2_5, my_question = word_3_2_5)};
    if (e1 == "3_2_6" && myData.value.database_exam_list[e1] == "1" ){helper_creat_data_case1(my_answer = word_3_2_6, my_question = word_3_2_6)};
    if (e1 == "3_2_6" && myData.value.database_exam_list[e1] == "2" ){helper_creat_data_case2(my_answer = word_3_2_6, my_question = word_3_2_6)};
    if (e1 == "3_2_6" && myData.value.database_exam_list[e1] == "3" ){helper_creat_data_case3(my_answer = word_3_2_6, my_question = word_3_2_6)};
    if (e1 == "3_2_6" && myData.value.database_exam_list[e1] == "4" ){helper_creat_data_caseA(my_answer = word_3_2_6, my_question = word_3_2_6)};
    //4年级 上册
    if (e1 == "4_1_1" && myData.value.database_exam_list[e1] == "1" ){helper_creat_data_case4(my_answer = word_4_1_1, my_question = word_4_1_1)};
    if (e1 == "4_1_1" && myData.value.database_exam_list[e1] == "2" ){helper_creat_data_case2(my_answer = word_4_1_1, my_question = word_4_1_1)};
    if (e1 == "4_1_1" && myData.value.database_exam_list[e1] == "3" ){helper_creat_data_case3(my_answer = word_4_1_1, my_question = word_4_1_1)};
    if (e1 == "4_1_1" && myData.value.database_exam_list[e1] == "4" ){helper_creat_data_caseA(my_answer = word_4_1_1, my_question = word_4_1_1)};
    if (e1 == "4_1_2" && myData.value.database_exam_list[e1] == "1" ){helper_creat_data_case4(my_answer = word_4_1_2, my_question = word_4_1_2)};
    if (e1 == "4_1_2" && myData.value.database_exam_list[e1] == "2" ){helper_creat_data_case2(my_answer = word_4_1_2, my_question = word_4_1_2)};
    if (e1 == "4_1_2" && myData.value.database_exam_list[e1] == "3" ){helper_creat_data_case3(my_answer = word_4_1_2, my_question = word_4_1_2)};
    if (e1 == "4_1_2" && myData.value.database_exam_list[e1] == "4" ){helper_creat_data_caseA(my_answer = word_4_1_2, my_question = word_4_1_2)};
    if (e1 == "4_1_3" && myData.value.database_exam_list[e1] == "1" ){helper_creat_data_case4(my_answer = word_4_1_3, my_question = word_4_1_3)};
    if (e1 == "4_1_3" && myData.value.database_exam_list[e1] == "2" ){helper_creat_data_case2(my_answer = word_4_1_3, my_question = word_4_1_3)};
    if (e1 == "4_1_3" && myData.value.database_exam_list[e1] == "3" ){helper_creat_data_case3(my_answer = word_4_1_3, my_question = word_4_1_3)};
    if (e1 == "4_1_3" && myData.value.database_exam_list[e1] == "4" ){helper_creat_data_caseA(my_answer = word_4_1_3, my_question = word_4_1_3)};
    if (e1 == "4_1_4" && myData.value.database_exam_list[e1] == "1" ){helper_creat_data_case4(my_answer = word_4_1_4, my_question = word_4_1_4)};
    if (e1 == "4_1_4" && myData.value.database_exam_list[e1] == "2" ){helper_creat_data_case2(my_answer = word_4_1_4, my_question = word_4_1_4)};
    if (e1 == "4_1_4" && myData.value.database_exam_list[e1] == "3" ){helper_creat_data_case3(my_answer = word_4_1_4, my_question = word_4_1_4)};
    if (e1 == "4_1_4" && myData.value.database_exam_list[e1] == "4" ){helper_creat_data_caseA(my_answer = word_4_1_4, my_question = word_4_1_4)};
    if (e1 == "4_1_5" && myData.value.database_exam_list[e1] == "1" ){helper_creat_data_case4(my_answer = word_4_1_5, my_question = word_4_1_5)};
    if (e1 == "4_1_5" && myData.value.database_exam_list[e1] == "2" ){helper_creat_data_case2(my_answer = word_4_1_5, my_question = word_4_1_5)};
    if (e1 == "4_1_5" && myData.value.database_exam_list[e1] == "3" ){helper_creat_data_case3(my_answer = word_4_1_5, my_question = word_4_1_5)};
    if (e1 == "4_1_5" && myData.value.database_exam_list[e1] == "4" ){helper_creat_data_caseA(my_answer = word_4_1_5, my_question = word_4_1_5)};
    if (e1 == "4_1_6" && myData.value.database_exam_list[e1] == "1" ){helper_creat_data_case4(my_answer = word_4_1_6, my_question = word_4_1_6)};
    if (e1 == "4_1_6" && myData.value.database_exam_list[e1] == "2" ){helper_creat_data_case2(my_answer = word_4_1_6, my_question = word_4_1_6)};
    if (e1 == "4_1_6" && myData.value.database_exam_list[e1] == "3" ){helper_creat_data_case3(my_answer = word_4_1_6, my_question = word_4_1_6)};
    if (e1 == "4_1_6" && myData.value.database_exam_list[e1] == "4" ){helper_creat_data_caseA(my_answer = word_4_1_6, my_question = word_4_1_6)};
    //4年级 下册
    if (e1 == "4_2_1" && myData.value.database_exam_list[e1] == "1" ){helper_creat_data_case1(my_answer = word_4_2_1, my_question = word_4_2_1)};
    if (e1 == "4_2_1" && myData.value.database_exam_list[e1] == "2" ){helper_creat_data_case2(my_answer = word_4_2_1, my_question = word_4_2_1)};
    if (e1 == "4_2_1" && myData.value.database_exam_list[e1] == "3" ){helper_creat_data_case3(my_answer = word_4_2_1, my_question = word_4_2_1)};
    if (e1 == "4_2_1" && myData.value.database_exam_list[e1] == "4" ){helper_creat_data_caseA(my_answer = word_4_2_1, my_question = word_4_2_1)};
    if (e1 == "4_2_2" && myData.value.database_exam_list[e1] == "1" ){helper_creat_data_case1(my_answer = word_4_2_2, my_question = word_4_2_2)};
    if (e1 == "4_2_2" && myData.value.database_exam_list[e1] == "2" ){helper_creat_data_case2(my_answer = word_4_2_2, my_question = word_4_2_2)};
    if (e1 == "4_2_2" && myData.value.database_exam_list[e1] == "3" ){helper_creat_data_case3(my_answer = word_4_2_2, my_question = word_4_2_2)};
    if (e1 == "4_2_2" && myData.value.database_exam_list[e1] == "4" ){helper_creat_data_caseA(my_answer = word_4_2_2, my_question = word_4_2_2)};
    if (e1 == "4_2_3" && myData.value.database_exam_list[e1] == "1" ){helper_creat_data_case1(my_answer = word_4_2_3, my_question = word_4_2_3)};
    if (e1 == "4_2_3" && myData.value.database_exam_list[e1] == "2" ){helper_creat_data_case2(my_answer = word_4_2_3, my_question = word_4_2_3)};
    if (e1 == "4_2_3" && myData.value.database_exam_list[e1] == "3" ){helper_creat_data_case3(my_answer = word_4_2_3, my_question = word_4_2_3)};
    if (e1 == "4_2_3" && myData.value.database_exam_list[e1] == "4" ){helper_creat_data_caseA(my_answer = word_4_2_3, my_question = word_4_2_3)};
    if (e1 == "4_2_4" && myData.value.database_exam_list[e1] == "1" ){helper_creat_data_case1(my_answer = word_4_2_4, my_question = word_4_2_4)};
    if (e1 == "4_2_4" && myData.value.database_exam_list[e1] == "2" ){helper_creat_data_case2(my_answer = word_4_2_4, my_question = word_4_2_4)};
    if (e1 == "4_2_4" && myData.value.database_exam_list[e1] == "3" ){helper_creat_data_case3(my_answer = word_4_2_4, my_question = word_4_2_4)};
    if (e1 == "4_2_4" && myData.value.database_exam_list[e1] == "4" ){helper_creat_data_caseA(my_answer = word_4_2_4, my_question = word_4_2_4)};
    if (e1 == "4_2_5" && myData.value.database_exam_list[e1] == "1" ){helper_creat_data_case1(my_answer = word_4_2_5, my_question = word_4_2_5)};
    if (e1 == "4_2_5" && myData.value.database_exam_list[e1] == "2" ){helper_creat_data_case2(my_answer = word_4_2_5, my_question = word_4_2_5)};
    if (e1 == "4_2_5" && myData.value.database_exam_list[e1] == "3" ){helper_creat_data_case3(my_answer = word_4_2_5, my_question = word_4_2_5)};
    if (e1 == "4_2_5" && myData.value.database_exam_list[e1] == "4" ){helper_creat_data_caseA(my_answer = word_4_2_5, my_question = word_4_2_5)};
    if (e1 == "4_2_6" && myData.value.database_exam_list[e1] == "1" ){helper_creat_data_case1(my_answer = word_4_2_6, my_question = word_4_2_6)};
    if (e1 == "4_2_6" && myData.value.database_exam_list[e1] == "2" ){helper_creat_data_case2(my_answer = word_4_2_6, my_question = word_4_2_6)};
    if (e1 == "4_2_6" && myData.value.database_exam_list[e1] == "3" ){helper_creat_data_case3(my_answer = word_4_2_6, my_question = word_4_2_6)};
    if (e1 == "4_2_6" && myData.value.database_exam_list[e1] == "4" ){helper_creat_data_caseA(my_answer = word_4_2_6, my_question = word_4_2_6)};
    //5年级 上册
    if (e1 == "5_1_1" && myData.value.database_exam_list[e1] == "1" ){helper_creat_data_case1(my_answer = word_5_1_1, my_question = word_5_1_1)};
    if (e1 == "5_1_1" && myData.value.database_exam_list[e1] == "2" ){helper_creat_data_case2(my_answer = word_5_1_1, my_question = word_5_1_1)};
    if (e1 == "5_1_1" && myData.value.database_exam_list[e1] == "3" ){helper_creat_data_case3(my_answer = word_5_1_1, my_question = word_5_1_1)};
    if (e1 == "5_1_1" && myData.value.database_exam_list[e1] == "4" ){helper_creat_data_caseA(my_answer = word_5_1_1, my_question = word_5_1_1)};
    if (e1 == "5_1_2" && myData.value.database_exam_list[e1] == "1" ){helper_creat_data_case1(my_answer = word_5_1_2, my_question = word_5_1_2)};
    if (e1 == "5_1_2" && myData.value.database_exam_list[e1] == "2" ){helper_creat_data_case2(my_answer = word_5_1_2, my_question = word_5_1_2)};
    if (e1 == "5_1_2" && myData.value.database_exam_list[e1] == "3" ){helper_creat_data_case3(my_answer = word_5_1_2, my_question = word_5_1_2)};
    if (e1 == "5_1_2" && myData.value.database_exam_list[e1] == "4" ){helper_creat_data_caseA(my_answer = word_5_1_2, my_question = word_5_1_2)};
    if (e1 == "5_1_3" && myData.value.database_exam_list[e1] == "1" ){helper_creat_data_case1(my_answer = word_5_1_3, my_question = word_5_1_3)};
    if (e1 == "5_1_3" && myData.value.database_exam_list[e1] == "2" ){helper_creat_data_case2(my_answer = word_5_1_3, my_question = word_5_1_3)};
    if (e1 == "5_1_3" && myData.value.database_exam_list[e1] == "3" ){helper_creat_data_case3(my_answer = word_5_1_3, my_question = word_5_1_3)};
    if (e1 == "5_1_3" && myData.value.database_exam_list[e1] == "4" ){helper_creat_data_caseA(my_answer = word_5_1_3, my_question = word_5_1_3)};
    if (e1 == "5_1_4" && myData.value.database_exam_list[e1] == "1" ){helper_creat_data_case1(my_answer = word_5_1_4, my_question = word_5_1_4)};
    if (e1 == "5_1_4" && myData.value.database_exam_list[e1] == "2" ){helper_creat_data_case2(my_answer = word_5_1_4, my_question = word_5_1_4)};
    if (e1 == "5_1_4" && myData.value.database_exam_list[e1] == "3" ){helper_creat_data_case3(my_answer = word_5_1_4, my_question = word_5_1_4)};
    if (e1 == "5_1_4" && myData.value.database_exam_list[e1] == "4" ){helper_creat_data_caseA(my_answer = word_5_1_4, my_question = word_5_1_4)};
    if (e1 == "5_1_5" && myData.value.database_exam_list[e1] == "1" ){helper_creat_data_case1(my_answer = word_5_1_5, my_question = word_5_1_5)};
    if (e1 == "5_1_5" && myData.value.database_exam_list[e1] == "2" ){helper_creat_data_case2(my_answer = word_5_1_5, my_question = word_5_1_5)};
    if (e1 == "5_1_5" && myData.value.database_exam_list[e1] == "3" ){helper_creat_data_case3(my_answer = word_5_1_5, my_question = word_5_1_5)};
    if (e1 == "5_1_5" && myData.value.database_exam_list[e1] == "4" ){helper_creat_data_caseA(my_answer = word_5_1_5, my_question = word_5_1_5)};
    if (e1 == "5_1_6" && myData.value.database_exam_list[e1] == "1" ){helper_creat_data_case1(my_answer = word_5_1_6, my_question = word_5_1_6)};
    if (e1 == "5_1_6" && myData.value.database_exam_list[e1] == "2" ){helper_creat_data_case2(my_answer = word_5_1_6, my_question = word_5_1_6)};
    if (e1 == "5_1_6" && myData.value.database_exam_list[e1] == "3" ){helper_creat_data_case3(my_answer = word_5_1_6, my_question = word_5_1_6)};
    if (e1 == "5_1_6" && myData.value.database_exam_list[e1] == "4" ){helper_creat_data_caseA(my_answer = word_5_1_6, my_question = word_5_1_6)};
    //5年级 下册
    if (e1 == "5_2_1" && myData.value.database_exam_list[e1] == "1" ){helper_creat_data_case1(my_answer = word_5_2_1, my_question = word_5_2_1)};
    if (e1 == "5_2_1" && myData.value.database_exam_list[e1] == "2" ){helper_creat_data_case2(my_answer = word_5_2_1, my_question = word_5_2_1)};
    if (e1 == "5_2_1" && myData.value.database_exam_list[e1] == "3" ){helper_creat_data_case3(my_answer = word_5_2_1, my_question = word_5_2_1)};
    if (e1 == "5_2_1" && myData.value.database_exam_list[e1] == "4" ){helper_creat_data_caseA(my_answer = word_5_2_1, my_question = word_5_2_1)};
    if (e1 == "5_2_2" && myData.value.database_exam_list[e1] == "1" ){helper_creat_data_case1(my_answer = word_5_2_2, my_question = word_5_2_2)};
    if (e1 == "5_2_2" && myData.value.database_exam_list[e1] == "2" ){helper_creat_data_case2(my_answer = word_5_2_2, my_question = word_5_2_2)};
    if (e1 == "5_2_2" && myData.value.database_exam_list[e1] == "3" ){helper_creat_data_case3(my_answer = word_5_2_2, my_question = word_5_2_2)};
    if (e1 == "5_2_2" && myData.value.database_exam_list[e1] == "4" ){helper_creat_data_caseA(my_answer = word_5_2_2, my_question = word_5_2_2)};
    if (e1 == "5_2_3" && myData.value.database_exam_list[e1] == "1" ){helper_creat_data_case1(my_answer = word_5_2_3, my_question = word_5_2_3)};
    if (e1 == "5_2_3" && myData.value.database_exam_list[e1] == "2" ){helper_creat_data_case2(my_answer = word_5_2_3, my_question = word_5_2_3)};
    if (e1 == "5_2_3" && myData.value.database_exam_list[e1] == "3" ){helper_creat_data_case3(my_answer = word_5_2_3, my_question = word_5_2_3)};
    if (e1 == "5_2_3" && myData.value.database_exam_list[e1] == "4" ){helper_creat_data_caseA(my_answer = word_5_2_3, my_question = word_5_2_3)};
    if (e1 == "5_2_4" && myData.value.database_exam_list[e1] == "1" ){helper_creat_data_case1(my_answer = word_5_2_4, my_question = word_5_2_4)};
    if (e1 == "5_2_4" && myData.value.database_exam_list[e1] == "2" ){helper_creat_data_case2(my_answer = word_5_2_4, my_question = word_5_2_4)};
    if (e1 == "5_2_4" && myData.value.database_exam_list[e1] == "3" ){helper_creat_data_case3(my_answer = word_5_2_4, my_question = word_5_2_4)};
    if (e1 == "5_2_4" && myData.value.database_exam_list[e1] == "4" ){helper_creat_data_caseA(my_answer = word_5_2_4, my_question = word_5_2_4)};
    if (e1 == "5_2_5" && myData.value.database_exam_list[e1] == "1" ){helper_creat_data_case1(my_answer = word_5_2_5, my_question = word_5_2_5)};
    if (e1 == "5_2_5" && myData.value.database_exam_list[e1] == "2" ){helper_creat_data_case2(my_answer = word_5_2_5, my_question = word_5_2_5)};
    if (e1 == "5_2_5" && myData.value.database_exam_list[e1] == "3" ){helper_creat_data_case3(my_answer = word_5_2_5, my_question = word_5_2_5)};
    if (e1 == "5_2_5" && myData.value.database_exam_list[e1] == "4" ){helper_creat_data_caseA(my_answer = word_5_2_5, my_question = word_5_2_5)};
    if (e1 == "5_2_6" && myData.value.database_exam_list[e1] == "1" ){helper_creat_data_case1(my_answer = word_5_2_6, my_question = word_5_2_6)};
    if (e1 == "5_2_6" && myData.value.database_exam_list[e1] == "2" ){helper_creat_data_case2(my_answer = word_5_2_6, my_question = word_5_2_6)};
    if (e1 == "5_2_6" && myData.value.database_exam_list[e1] == "3" ){helper_creat_data_case3(my_answer = word_5_2_6, my_question = word_5_2_6)};
    if (e1 == "5_2_6" && myData.value.database_exam_list[e1] == "4" ){helper_creat_data_caseA(my_answer = word_5_2_6, my_question = word_5_2_6)};

    //6年级 上册
    if (e1 == "6_1_1" && myData.value.database_exam_list[e1] == "1" ){helper_creat_data_case1(my_answer = word_6_1_1, my_question = word_6_1_1)};
    if (e1 == "6_1_1" && myData.value.database_exam_list[e1] == "2" ){helper_creat_data_case2(my_answer = word_6_1_1, my_question = word_6_1_1)};
    if (e1 == "6_1_1" && myData.value.database_exam_list[e1] == "3" ){helper_creat_data_case3(my_answer = word_6_1_1, my_question = word_6_1_1)};
    if (e1 == "6_1_1" && myData.value.database_exam_list[e1] == "4" ){helper_creat_data_caseA(my_answer = word_6_1_1, my_question = word_6_1_1)};
    if (e1 == "6_1_2" && myData.value.database_exam_list[e1] == "1" ){helper_creat_data_case1(my_answer = word_6_1_2, my_question = word_6_1_2)};
    if (e1 == "6_1_2" && myData.value.database_exam_list[e1] == "2" ){helper_creat_data_case2(my_answer = word_6_1_2, my_question = word_6_1_2)};
    if (e1 == "6_1_2" && myData.value.database_exam_list[e1] == "3" ){helper_creat_data_case3(my_answer = word_6_1_2, my_question = word_6_1_2)};
    if (e1 == "6_1_2" && myData.value.database_exam_list[e1] == "4" ){helper_creat_data_caseA(my_answer = word_6_1_2, my_question = word_6_1_2)};
    if (e1 == "6_1_3" && myData.value.database_exam_list[e1] == "1" ){helper_creat_data_case1(my_answer = word_6_1_3, my_question = word_6_1_3)};
    if (e1 == "6_1_3" && myData.value.database_exam_list[e1] == "2" ){helper_creat_data_case2(my_answer = word_6_1_3, my_question = word_6_1_3)};
    if (e1 == "6_1_3" && myData.value.database_exam_list[e1] == "3" ){helper_creat_data_case3(my_answer = word_6_1_3, my_question = word_6_1_3)};
    if (e1 == "6_1_3" && myData.value.database_exam_list[e1] == "4" ){helper_creat_data_caseA(my_answer = word_6_1_3, my_question = word_6_1_3)};
    if (e1 == "6_1_4" && myData.value.database_exam_list[e1] == "1" ){helper_creat_data_case1(my_answer = word_6_1_4, my_question = word_6_1_4)};
    if (e1 == "6_1_4" && myData.value.database_exam_list[e1] == "2" ){helper_creat_data_case2(my_answer = word_6_1_4, my_question = word_6_1_4)};
    if (e1 == "6_1_4" && myData.value.database_exam_list[e1] == "3" ){helper_creat_data_case3(my_answer = word_6_1_4, my_question = word_6_1_4)};
    if (e1 == "6_1_4" && myData.value.database_exam_list[e1] == "4" ){helper_creat_data_caseA(my_answer = word_6_1_4, my_question = word_6_1_4)};
    if (e1 == "6_1_5" && myData.value.database_exam_list[e1] == "1" ){helper_creat_data_case1(my_answer = word_6_1_5, my_question = word_6_1_5)};
    if (e1 == "6_1_5" && myData.value.database_exam_list[e1] == "2" ){helper_creat_data_case2(my_answer = word_6_1_5, my_question = word_6_1_5)};
    if (e1 == "6_1_5" && myData.value.database_exam_list[e1] == "3" ){helper_creat_data_case3(my_answer = word_6_1_5, my_question = word_6_1_5)};
    if (e1 == "6_1_5" && myData.value.database_exam_list[e1] == "4" ){helper_creat_data_caseA(my_answer = word_6_1_5, my_question = word_6_1_5)};
    if (e1 == "6_1_6" && myData.value.database_exam_list[e1] == "1" ){helper_creat_data_case1(my_answer = word_6_1_6, my_question = word_6_1_6)};
    if (e1 == "6_1_6" && myData.value.database_exam_list[e1] == "2" ){helper_creat_data_case2(my_answer = word_6_1_6, my_question = word_6_1_6)};
    if (e1 == "6_1_6" && myData.value.database_exam_list[e1] == "3" ){helper_creat_data_case3(my_answer = word_6_1_6, my_question = word_6_1_6)};
    if (e1 == "6_1_6" && myData.value.database_exam_list[e1] == "4" ){helper_creat_data_caseA(my_answer = word_6_1_6, my_question = word_6_1_6)};
    //6年级 下册
    if (e1 == "6_2_1" && myData.value.database_exam_list[e1] == "1" ){helper_creat_data_case1(my_answer = word_6_2_1, my_question = word_6_2_1)};
    if (e1 == "6_2_1" && myData.value.database_exam_list[e1] == "2" ){helper_creat_data_case2(my_answer = word_6_2_1, my_question = word_6_2_1)};
    if (e1 == "6_2_1" && myData.value.database_exam_list[e1] == "3" ){helper_creat_data_case3(my_answer = word_6_2_1, my_question = word_6_2_1)};
    if (e1 == "6_2_1" && myData.value.database_exam_list[e1] == "4" ){helper_creat_data_caseA(my_answer = word_6_2_1, my_question = word_6_2_1)};
    if (e1 == "6_2_2" && myData.value.database_exam_list[e1] == "1" ){helper_creat_data_case1(my_answer = word_6_2_2, my_question = word_6_2_2)};
    if (e1 == "6_2_2" && myData.value.database_exam_list[e1] == "2" ){helper_creat_data_case2(my_answer = word_6_2_2, my_question = word_6_2_2)};
    if (e1 == "6_2_2" && myData.value.database_exam_list[e1] == "3" ){helper_creat_data_case3(my_answer = word_6_2_2, my_question = word_6_2_2)};
    if (e1 == "6_2_2" && myData.value.database_exam_list[e1] == "4" ){helper_creat_data_caseA(my_answer = word_6_2_2, my_question = word_6_2_2)};
    if (e1 == "6_2_3" && myData.value.database_exam_list[e1] == "1" ){helper_creat_data_case1(my_answer = word_6_2_3, my_question = word_6_2_3)};
    if (e1 == "6_2_3" && myData.value.database_exam_list[e1] == "2" ){helper_creat_data_case2(my_answer = word_6_2_3, my_question = word_6_2_3)};
    if (e1 == "6_2_3" && myData.value.database_exam_list[e1] == "3" ){helper_creat_data_case3(my_answer = word_6_2_3, my_question = word_6_2_3)};
    if (e1 == "6_2_3" && myData.value.database_exam_list[e1] == "4" ){helper_creat_data_caseA(my_answer = word_6_2_3, my_question = word_6_2_3)};
    if (e1 == "6_2_4" && myData.value.database_exam_list[e1] == "1" ){helper_creat_data_case1(my_answer = word_6_2_4, my_question = word_6_2_4)};
    if (e1 == "6_2_4" && myData.value.database_exam_list[e1] == "2" ){helper_creat_data_case2(my_answer = word_6_2_4, my_question = word_6_2_4)};
    if (e1 == "6_2_4" && myData.value.database_exam_list[e1] == "3" ){helper_creat_data_case3(my_answer = word_6_2_4, my_question = word_6_2_4)};
    if (e1 == "6_2_4" && myData.value.database_exam_list[e1] == "4" ){helper_creat_data_caseA(my_answer = word_6_2_4, my_question = word_6_2_4)};


}

fun helper_creat_data_case1(my_answer:List<List<String>>,my_question:List<List<String>>){
    var items :MutableList<MutableMap<String,MutableList<List<String>>>> = mutableListOf()
    for (answer in my_answer) {
        var question = my_question.filter { it != answer } //过滤question里面包含answer的答案
        var question_a1 = question.shuffled()//打乱列表顺序
        var question_a2 :MutableList<List<String>> = mutableListOf(question_a1[0],question_a1[1],answer)//把answer混进去
        var question_a3 = question_a2.shuffled() //把顺序打乱

        var new_question:MutableList<List<String>> = mutableListOf(question_a3[0],question_a3[1],question_a3[2])
        var new_answer:MutableList<List<String>> = mutableListOf(answer)
        var new_style:MutableList<List<String>> = mutableListOf(listOf("1"))
        var item = mutableMapOf("answer" to new_answer, "question" to new_question, "style" to new_style)//生成主数据的子集item
        items.add(item)//添加到主数据列表
    }
    println(items)
    myData.value.exam_case = items
    myData.value.exam_case_len = items.size
}
fun helper_creat_data_case2(my_answer:List<List<String>>,my_question:List<List<String>>){
    var items :MutableList<MutableMap<String,MutableList<List<String>>>> = mutableListOf()
    for (answer in my_answer) {
        var question = my_question.filter { it != answer } //过滤question里面包含answer的答案
        var question_a1 = question.shuffled()//打乱列表顺序
        var a1 = mutableListOf(question_a1[0],question_a1[1],answer).shuffled()//获取对对碰的左边3个数据 对顺序随机打乱
        var a2 = mutableListOf(question_a1[0],question_a1[1],answer).shuffled()//获取对对碰的右边3个数据 对顺序随机打乱

        var new_question:MutableList<List<String>> = mutableListOf(a1[0],a1[1],a1[2],a2[0],a2[1],a2[2])
        var new_answer:MutableList<List<String>> = mutableListOf(answer)
        var new_style:MutableList<List<String>> = mutableListOf(listOf("2"))
        var item = mutableMapOf("answer" to new_answer, "question" to new_question, "style" to new_style)//生成主数据的子集item
        items.add(item)//添加到主数据列表
    }
    println(items)
    myData.value.exam_case = items
    myData.value.exam_case_len = items.size
}
fun helper_creat_data_case3(my_answer:List<List<String>>,my_question:List<List<String>>){
    var items :MutableList<MutableMap<String,MutableList<List<String>>>> = mutableListOf()
    for (answer in my_answer) {
        var question = my_question.filter { it != answer } //过滤question里面包含answer的答案
        var question_a1 = question.shuffled()//打乱列表顺序
        var a1 = mutableListOf(question_a1[0],question_a1[1],question_a1[2],question_a1[3],answer).shuffled()//获取对对碰的左边3个数据 对顺序随机打乱
        var a2 = mutableListOf(question_a1[0],question_a1[1],question_a1[2],question_a1[3],answer).shuffled()//获取对对碰的右边3个数据 对顺序随机打乱

        var new_question:MutableList<List<String>> = mutableListOf(a1[0],a1[1],a1[2],a1[3],a1[4],a2[0],a2[1],a2[2],a2[3],a2[4])
        var new_answer:MutableList<List<String>> = mutableListOf(answer)
        var new_style:MutableList<List<String>> = mutableListOf(listOf("3"))
        var item = mutableMapOf("answer" to new_answer, "question" to new_question, "style" to new_style)//生成主数据的子集item
        items.add(item)//添加到主数据列表
    }
    println(items)
    myData.value.exam_case = items
    myData.value.exam_case_len = items.size
}
fun helper_creat_data_case4(my_answer:List<List<String>>,my_question:List<List<String>>){
    var items :MutableList<MutableMap<String,MutableList<List<String>>>> = mutableListOf()
    for (answer in my_answer) {
        var question = my_question.filter { it != answer } //过滤question里面包含answer的答案
        var question_a1 = question.shuffled()//打乱列表顺序
        var question_a2 :MutableList<List<String>> = mutableListOf(question_a1[0],question_a1[1],answer)//把answer混进去
        var question_a3 = question_a2.shuffled() //把顺序打乱

        var new_question:MutableList<List<String>> = mutableListOf(question_a3[0],question_a3[1],question_a3[2])
        var new_answer:MutableList<List<String>> = mutableListOf(answer)
        var new_style:MutableList<List<String>> = mutableListOf(listOf("4"))
        var item = mutableMapOf("answer" to new_answer, "question" to new_question, "style" to new_style)//生成主数据的子集item
        items.add(item)//添加到主数据列表
    }
    println(items)
    myData.value.exam_case = items
    myData.value.exam_case_len = items.size
}
fun helper_creat_data_caseA(my_answer:List<List<String>>,my_question:List<List<String>>){
    var items :MutableList<MutableMap<String,MutableList<List<String>>>> = mutableListOf()
    var result1 = helper_creat_data_case1(my_answer=my_answer,my_question=my_question)
    var result1A = myData.value.exam_case
    var result2 = helper_creat_data_case2(my_answer=my_answer,my_question=my_question)
    var result2A = myData.value.exam_case
    var result3 = helper_creat_data_case3(my_answer=my_answer,my_question=my_question)
    var result3A = myData.value.exam_case
    //indices是数列的循环，显示的坐标位置 0 1 2 3
    for (i in myData.value.exam_case.indices){
        var a1:MutableList<MutableMap<String,MutableList<List<String>>>> = mutableListOf(result1A[i],result2A[i],result3A[i])
        var item = a1.shuffled()
        items.add(item[0])//添加到主数据列表
    }
    println(items)
    myData.value.exam_case = items
    myData.value.exam_case_len = items.size
}
@Composable
fun my_main() {
    //3年级标题
    Text(
        text = "三年级",
        fontSize = 16.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .height(47.dp)
            .fillMaxWidth()
            .padding(top = 8.dp)
    )
    //3年级数据
    Column (
    ){
        Row(
            modifier = Modifier
                .height(108.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                helper_choose_exam_data(e1 = "3_1_1")
                            }
                        )
                    }
            ) {
                do_image(e1 = myData.value.database_exam_list["3_1_1"]?:"1")
                Text(text = "上.一单元",modifier = Modifier.align(Alignment.CenterHorizontally))

            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                helper_choose_exam_data(e1 = "3_1_2")
                            }
                        )
                    }
            ) {
                do_image(e1 = myData.value.database_exam_list["3_1_2"]?:"1")
                Text(text = "上.二单元",modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                helper_choose_exam_data(e1 = "3_1_3")
                            }
                        )
                    }
            ) {
                do_image(e1 = myData.value.database_exam_list["3_1_3"]?:"1")
                Text(text = "上.三单元",modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
        Row(
            modifier = Modifier
                .height(108.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                helper_choose_exam_data(e1 = "3_1_4")
                            }
                        )
                    }
            ) {
                do_image(e1 = myData.value.database_exam_list["3_1_4"]?:"1")
                Text(text = "上.四单元",modifier = Modifier.align(Alignment.CenterHorizontally))

            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                helper_choose_exam_data(e1 = "3_1_5")
                            }
                        )
                    }
            ) {
                do_image(e1 = myData.value.database_exam_list["3_1_5"]?:"1")
                Text(text = "上.五单元",modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                helper_choose_exam_data(e1 = "3_1_6")
                            }
                        )
                    }
            ) {
                do_image(e1 = myData.value.database_exam_list["3_1_6"]?:"1")
                Text(text = "上.六单元",modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
        Row(
            modifier = Modifier
                .height(108.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                helper_choose_exam_data(e1 = "3_2_1")
                            }
                        )
                    }
            ) {
                do_image(e1 = myData.value.database_exam_list["3_2_1"]?:"1")
                Text(text = "下.一单元",modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                helper_choose_exam_data(e1 = "3_2_2")
                            }
                        )
                    }
            ) {
                do_image(e1 = myData.value.database_exam_list["3_2_2"]?:"1")
                Text(text = "下.二单元",modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                helper_choose_exam_data(e1 = "3_2_3")
                            }
                        )
                    }
            ) {
                do_image(e1 = myData.value.database_exam_list["3_2_3"]?:"1")
                Text(text = "下.三单元",modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
        Row(
            modifier = Modifier
                .height(108.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                helper_choose_exam_data(e1 = "3_2_4")
                            }
                        )
                    }
            ) {
                do_image(e1 = myData.value.database_exam_list["3_2_4"]?:"1")
                Text(text = "下.四单元",modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                helper_choose_exam_data(e1 = "3_2_5")
                            }
                        )
                    }
            ) {
                do_image(e1 = myData.value.database_exam_list["3_2_5"]?:"1")
                Text(text = "下.五单元",modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                helper_choose_exam_data(e1 = "3_2_6")
                            }
                        )
                    }
            ) {
                do_image(e1 = myData.value.database_exam_list["3_2_6"]?:"1")
                Text(text = "下.六单元",modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
    }

    //4年级标题
    Text(
        text = "四年级",
        fontSize = 16.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .height(47.dp)
            .fillMaxWidth()
            .padding(top = 8.dp)
    )
    //4年级数据
    Column (
    ){
        Row(
            modifier = Modifier
                .height(108.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                helper_choose_exam_data(e1 = "4_1_1")
                            }
                        )
                    }
            ) {
                do_image(e1 = myData.value.database_exam_list["4_1_1"]?:"1")
                Text(text = "上.一单元",modifier = Modifier.align(Alignment.CenterHorizontally))

            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                helper_choose_exam_data(e1 = "4_1_2")
                            }
                        )
                    }
            ) {
                do_image(e1 = myData.value.database_exam_list["4_1_2"]?:"1")
                Text(text = "上.二单元",modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                helper_choose_exam_data(e1 = "4_1_3")
                            }
                        )
                    }
            ) {
                do_image(e1 = myData.value.database_exam_list["4_1_3"]?:"1")
                Text(text = "上.三单元",modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
        Row(
            modifier = Modifier
                .height(108.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                helper_choose_exam_data(e1 = "4_1_4")
                            }
                        )
                    }
            ) {
                do_image(e1 = myData.value.database_exam_list["4_1_4"]?:"1")
                Text(text = "上.四单元",modifier = Modifier.align(Alignment.CenterHorizontally))

            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                helper_choose_exam_data(e1 = "4_1_5")
                            }
                        )
                    }
            ) {
                do_image(e1 = myData.value.database_exam_list["4_1_5"]?:"1")
                Text(text = "上.五单元",modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                helper_choose_exam_data(e1 = "4_1_6")
                            }
                        )
                    }
            ) {
                do_image(e1 = myData.value.database_exam_list["4_1_6"]?:"1")
                Text(text = "上.六单元",modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
        Row(
            modifier = Modifier
                .height(108.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                helper_choose_exam_data(e1 = "4_2_1")
                            }
                        )
                    }
            ) {
                do_image(e1 = myData.value.database_exam_list["4_2_1"]?:"1")
                Text(text = "下.一单元",modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                helper_choose_exam_data(e1 = "4_2_2")
                            }
                        )
                    }
            ) {
                do_image(e1 = myData.value.database_exam_list["4_2_2"]?:"1")
                Text(text = "下.二单元",modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                helper_choose_exam_data(e1 = "4_2_3")
                            }
                        )
                    }
            ) {
                do_image(e1 = myData.value.database_exam_list["4_2_3"]?:"1")
                Text(text = "下.三单元",modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
        Row(
            modifier = Modifier
                .height(108.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                helper_choose_exam_data(e1 = "4_2_4")
                            }
                        )
                    }
            ) {
                do_image(e1 = myData.value.database_exam_list["4_2_4"]?:"1")
                Text(text = "下.四单元",modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                helper_choose_exam_data(e1 = "4_2_5")
                            }
                        )
                    }
            ) {
                do_image(e1 = myData.value.database_exam_list["4_2_5"]?:"1")
                Text(text = "下.五单元",modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                helper_choose_exam_data(e1 = "4_2_6")
                            }
                        )
                    }
            ) {
                do_image(e1 = myData.value.database_exam_list["4_2_6"]?:"1")
                Text(text = "下.六单元",modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
    }

    //5年级标题
    Text(
        text = "五年级",
        fontSize = 16.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .height(47.dp)
            .fillMaxWidth()
            .padding(top = 8.dp)
    )
    //5年级数据
    Column (
    ){
        Row(
            modifier = Modifier
                .height(108.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                helper_choose_exam_data(e1 = "5_1_1")
                            }
                        )
                    }
            ) {
                do_image(e1 = myData.value.database_exam_list["5_1_1"]?:"1")
                Text(text = "上.一单元",modifier = Modifier.align(Alignment.CenterHorizontally))

            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                helper_choose_exam_data(e1 = "5_1_2")
                            }
                        )
                    }
            ) {
                do_image(e1 = myData.value.database_exam_list["5_1_2"]?:"1")
                Text(text = "上.二单元",modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                helper_choose_exam_data(e1 = "5_1_3")
                            }
                        )
                    }
            ) {
                do_image(e1 = myData.value.database_exam_list["5_1_3"]?:"1")
                Text(text = "上.三单元",modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
        Row(
            modifier = Modifier
                .height(108.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                helper_choose_exam_data(e1 = "5_1_4")
                            }
                        )
                    }
            ) {
                do_image(e1 = myData.value.database_exam_list["5_1_4"]?:"1")
                Text(text = "上.四单元",modifier = Modifier.align(Alignment.CenterHorizontally))

            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                helper_choose_exam_data(e1 = "5_1_5")
                            }
                        )
                    }
            ) {
                do_image(e1 = myData.value.database_exam_list["5_1_5"]?:"1")
                Text(text = "上.五单元",modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                helper_choose_exam_data(e1 = "5_1_6")
                            }
                        )
                    }
            ) {
                do_image(e1 = myData.value.database_exam_list["5_1_6"]?:"1")
                Text(text = "上.六单元",modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
        Row(
            modifier = Modifier
                .height(108.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                helper_choose_exam_data(e1 = "5_2_1")
                            }
                        )
                    }
            ) {
                do_image(e1 = myData.value.database_exam_list["5_2_1"]?:"1")
                Text(text = "下.一单元",modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                helper_choose_exam_data(e1 = "5_2_2")
                            }
                        )
                    }
            ) {
                do_image(e1 = myData.value.database_exam_list["5_2_2"]?:"1")
                Text(text = "下.二单元",modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                helper_choose_exam_data(e1 = "5_2_3")
                            }
                        )
                    }
            ) {
                do_image(e1 = myData.value.database_exam_list["5_2_3"]?:"1")
                Text(text = "下.三单元",modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
        Row(
            modifier = Modifier
                .height(108.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                helper_choose_exam_data(e1 = "5_2_4")
                            }
                        )
                    }
            ) {
                do_image(e1 = myData.value.database_exam_list["5_2_4"]?:"1")
                Text(text = "下.四单元",modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                helper_choose_exam_data(e1 = "5_2_5")
                            }
                        )
                    }
            ) {
                do_image(e1 = myData.value.database_exam_list["5_2_5"]?:"1")
                Text(text = "下.五单元",modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                helper_choose_exam_data(e1 = "5_2_6")
                            }
                        )
                    }
            ) {
                do_image(e1 = myData.value.database_exam_list["5_2_6"]?:"1")
                Text(text = "下.六单元",modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
    }

    //6年级标题
    Text(
        text = "六年级",
        fontSize = 16.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .height(47.dp)
            .fillMaxWidth()
            .padding(top = 8.dp)
    )
    //6年级数据
    Column (
    ){
        Row(
            modifier = Modifier
                .height(108.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                helper_choose_exam_data(e1 = "6_1_1")
                            }
                        )
                    }
            ) {
                do_image(e1 = myData.value.database_exam_list["6_1_1"]?:"1")
                Text(text = "上.一单元",modifier = Modifier.align(Alignment.CenterHorizontally))

            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                helper_choose_exam_data(e1 = "6_1_2")
                            }
                        )
                    }
            ) {
                do_image(e1 = myData.value.database_exam_list["6_1_2"]?:"1")
                Text(text = "上.二单元",modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                helper_choose_exam_data(e1 = "6_1_3")
                            }
                        )
                    }
            ) {
                do_image(e1 = myData.value.database_exam_list["6_1_3"]?:"1")
                Text(text = "上.三单元",modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
        Row(
            modifier = Modifier
                .height(108.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                helper_choose_exam_data(e1 = "6_1_4")
                            }
                        )
                    }
            ) {
                do_image(e1 = myData.value.database_exam_list["6_1_4"]?:"1")
                Text(text = "上.四单元",modifier = Modifier.align(Alignment.CenterHorizontally))

            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                helper_choose_exam_data(e1 = "6_1_5")
                            }
                        )
                    }
            ) {
                do_image(e1 = myData.value.database_exam_list["6_1_5"]?:"1")
                Text(text = "上.五单元",modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                helper_choose_exam_data(e1 = "6_1_6")
                            }
                        )
                    }
            ) {
                do_image(e1 = myData.value.database_exam_list["6_1_6"]?:"1")
                Text(text = "上.六单元",modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
        Row(
            modifier = Modifier
                .height(108.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                helper_choose_exam_data(e1 = "6_2_1")
                            }
                        )
                    }
            ) {
                do_image(e1 = myData.value.database_exam_list["6_2_1"]?:"1")
                Text(text = "下.一单元",modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                helper_choose_exam_data(e1 = "6_2_2")
                            }
                        )
                    }
            ) {
                do_image(e1 = myData.value.database_exam_list["6_2_2"]?:"1")
                Text(text = "下.二单元",modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                helper_choose_exam_data(e1 = "6_2_3")
                            }
                        )
                    }
            ) {
                do_image(e1 = myData.value.database_exam_list["6_2_3"]?:"1")
                Text(text = "下.三单元",modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
        Row(
            modifier = Modifier
                .height(108.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {
                                helper_choose_exam_data(e1 = "6_2_4")
                            }
                        )
                    }
            ) {
                do_image(e1 = myData.value.database_exam_list["6_2_4"]?:"1")
                Text(text = "下.四单元",modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {

                            }
                        )
                    }
            ) {

            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {},
                            onTap = {

                            }
                        )
                    }
            ) {
    
            }
        }
    }




}

@Composable
fun do_image(e1:String){
    when (e1) {
        "1" ->
            Image(
                painter = painterResource(id = R.drawable.e1),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
            )
        "2" ->
            Image(
                painter = painterResource(id = R.drawable.e2),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
            )
        "3" ->
            Image(
                painter = painterResource(id = R.drawable.e3),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
            )
        "4" ->
            Image(
                painter = painterResource(id = R.drawable.e4),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
            )
        "5" ->
            Image(
                painter = painterResource(id = R.drawable.e5),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
            )
    }
}


@Composable
fun my_menu(){
    Row (){
        val imageModifier1 = Modifier
            .size(32.dp)
            .weight(1f)
            .padding(top = 8.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {},
                    onTap = {
                        main_io.value = "1"
                        println("555")
                    }
                )
            }
        val imageModifier2 = Modifier
            .size(32.dp)
            .weight(1f)
            .padding(top = 8.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {},
                    onTap = {
                        main_io.value = "2"
                        println("666")
                    }
                )
            }
        Image(
            painter = painterResource(id = R.drawable.exam1),
            contentDescription = null,
            modifier = imageModifier1
        )
        Image(
            painter = painterResource(id = R.drawable.me2),
            contentDescription = null,
            modifier = imageModifier2
        )
    }
}

val exam_user_info_list = mapOf(
    "user" to "guest",
    "user_name" to "guest",
    "keep_days" to "0",
    "keep_time" to "0",
    "keep_wealth" to "0",
    "keep_score" to "4000",
    "keep_words" to "0",
    "info_install" to "0"
)

val exam_list = mapOf(
    "3_1_1" to "1", "3_1_2" to "1", "3_1_3" to "1", "3_1_4" to "1", "3_1_5" to "1", "3_1_6" to "1",
    "3_2_1" to "1", "3_2_2" to "1", "3_2_3" to "1", "3_2_4" to "1", "3_2_5" to "1", "3_2_6" to "1",

    "4_1_1" to "1", "4_1_2" to "1", "4_1_3" to "1", "4_1_4" to "1", "4_1_5" to "1", "4_1_6" to "1",
    "4_2_1" to "1", "4_2_2" to "1", "4_2_3" to "1", "4_2_4" to "1", "4_2_5" to "1", "4_2_6" to "1",

    "5_1_1" to "1", "5_1_2" to "1", "5_1_3" to "1", "5_1_4" to "1", "5_1_5" to "1", "5_1_6" to "1",
    "5_2_1" to "1", "5_2_2" to "1", "5_2_3" to "1", "5_2_4" to "1", "5_2_5" to "1", "5_2_6" to "1",

    "6_1_1" to "1", "6_1_2" to "1", "6_1_3" to "1", "6_1_4" to "1", "6_1_5" to "1", "6_1_6" to "1",
    "6_2_1" to "1", "6_2_2" to "1", "6_2_3" to "1", "6_2_4" to "1",
)

val word_3_1_1 = listOf(
    listOf("ruler","尺子","a0003010101"),
    listOf("pencil","铅笔","a0003010102"),
    listOf("eraser","橡皮","a0003010103"),
    listOf("crayon","蜡笔","a0003010104"),
    listOf("bag","包","a0003010105"),
    listOf("pen","钢笔","a0003010106"),
    listOf("pencil box","铅笔盒","a0003010107"),
    listOf("book","书","a0003010108"),
    listOf("no","不","a0003010109"),
    listOf("your","你（们）的","a0003010110"),
)
val word_3_1_2 = listOf(
    listOf("red","红色；红色的","a0003010201"),
    listOf("green","绿色；绿色的","a0003010202"),
    listOf("yellow","黄色；黄色的","a0003010203"),
    listOf("blue","蓝色；蓝色的","a0003010204"),
    listOf("black","黑色；黑色的","a0003010205"),
    listOf("brown","棕色；棕色的","a0003010206"),
    listOf("white","白色；白色的","a0003010207"),
    listOf("orange","橙色；橙色的","a0003010208"),
    listOf("ok","好；行","a0003010209"),
    listOf("mum","妈妈","a0003010210"),
)
val word_3_1_3 = listOf(
    listOf("face","脸","a0003010301"),
    listOf("ear","耳朵","a0003010302"),
    listOf("eye","眼睛","a0003010303"),
    listOf("nose","鼻子","a0003010304"),
    listOf("mouth","嘴","a0003010305"),
    listOf("arm","胳膊","a0003010306"),
    listOf("hand","手","a0003010307"),
    listOf("head","头","a0003010308"),
    listOf("body","身体","a0003010309"),
    listOf("leg","腿","a0003010310"),
    listOf("foot","脚","a0003010311"),
    listOf("school","学校","a0003010312"),
)
val word_3_1_4 = listOf(
    listOf("duck","鸭子","a0003010401"),
    listOf("pig","猪","a0003010402"),
    listOf("cat","猫","a0003010403"),
    listOf("bear","熊","a0003010404"),
    listOf("dog","狗","a0003010405"),
    listOf("elephant","大象","a0003010406"),
    listOf("monkey","猴子","a0003010407"),
    listOf("bird","鸟","a0003010408"),
    listOf("tiger","老虎","a0003010409"),
    listOf("panda","熊猫","a0003010410"),
    listOf("zoo","动物园","a0003010411"),
    listOf("funny","滑稽的，好笑的","a0003010412"),
)
val word_3_1_5 = listOf(
    listOf("bread","面包","a0003010501"),
    listOf("juice","果汁","a0003010502"),
    listOf("egg","蛋","a0003010503"),
    listOf("milk","牛奶","a0003010504"),
    listOf("water","水","a0003010505"),
    listOf("cake","蛋糕","a0003010506"),
    listOf("fish","鱼肉；鱼","a0003010507"),
    listOf("rice","米饭","a0003010508"),
)
val word_3_1_6 = listOf(
    listOf("one","一","a0003010601"),
    listOf("two","二","a0003010602"),
    listOf("three","三","a0003010603"),
    listOf("four","四","a0003010604"),
    listOf("five","五","a0003010605"),
    listOf("six","六","a0003010606"),
    listOf("seven","七","a0003010607"),
    listOf("eight","八","a0003010608"),
    listOf("nine","九","a0003010609"),
    listOf("ten","十","a0003010610"),
    listOf("brother","兄；弟","a0003010611"),
    listOf("plate","盘子","a0003010612"),
)
val word_3_2_1 = listOf(
    listOf("uk","英国","a0003020101"),
    listOf("canada","加拿大","a0003020102"),
    listOf("usa","美国","a0003020103"),
    listOf("china","中国","a0003020104"),
    listOf("she","她","a0003020105"),
    listOf("student","学生","a0003020106"),
    listOf("pupil","小学生","a0003020107"),
    listOf("he","他","a0003020108"),
    listOf("teacher","老师","a0003020109"),
    listOf("boy","男孩","a0003020110"),
    listOf("and","和；与","a0003020111"),
    listOf("girl","女孩","a0003020112"),
    listOf("new","新的","a0003020113"),
    listOf("friend","朋友","a0003020114"),
    listOf("today","今天","a0003020115"),
)
val word_3_2_2 = listOf(
    listOf("father","爸爸，爹爹","a0003020201"),
    listOf("dad","(口语)爸爸,爹爹","a0003020202"),
    listOf("man","男人","a0003020203"),
    listOf("woman","女人","a0003020204"),
    listOf("mother","母亲；妈妈","a0003020205"),
    listOf("sister","姐；妹","a0003020206"),
    listOf("brother","兄；弟","a0003020207"),
    listOf("grandmother","(外)祖母","a0003020208"),
    listOf("grandma","(口语)(外)祖母","a0003020209"),
    listOf("grandfather","(外)祖父","a0003020210"),
    listOf("grandpa","(口语)(外)祖父","a0003020211"),
    listOf("family","家，家庭","a0003020212"),
)
val word_3_2_3 = listOf(
    listOf("thin","瘦的","a0003020301"),
    listOf("fat","胖的；肥的","a0003020302"),
    listOf("tall","高的","a0003020303"),
    listOf("short","矮的；短的","a0003020304"),
    listOf("long","长的","a0003020305"),
    listOf("small","小的","a0003020306"),
    listOf("big","大的","a0003020307"),
    listOf("giraffe","长颈鹿","a0003020308"),
    listOf("so","这么；那么","a0003020309"),
    listOf("children","儿童的复数","a0003020310"),
    listOf("tail","尾巴","a0003020311"),
)
val word_3_2_4 = listOf(
    listOf("on","在。。。上","a0003020401"),
    listOf("in","在。。。里","a0003020402"),
    listOf("under","在。。。下面","a0003020403"),
    listOf("chair","椅子","a0003020404"),
    listOf("desk","书桌","a0003020405"),
    listOf("cap","帽子","a0003020406"),
    listOf("ball","球","a0003020407"),
    listOf("car","小汽车","a0003020408"),
    listOf("boat","小船","a0003020409"),
    listOf("map","地图","a0003020410"),
    listOf("toy","玩具","a0003020411"),
    listOf("box","盒；箱","a0003020412"),
)
val word_3_2_5 = listOf(
    listOf("pear","梨","a0003020501"),
    listOf("apple","苹果","a0003020502"),
    listOf("orange","橙子","a0003020503"),
    listOf("banana","香蕉","a0003020504"),
    listOf("watermelon","西瓜","a0003020505"),
    listOf("strawberry","草莓","a0003020506"),
    listOf("grape","葡萄","a0003020507"),
    listOf("buy","买","a0003020508"),
    listOf("fruit","水果","a0003020509"),
)
val word_3_2_6 = listOf(
    listOf("eleven","十一","a0003020601"),
    listOf("twelve","十二","a0003020602"),
    listOf("thirteen","十三","a0003020603"),
    listOf("fourteen","十四","a0003020604"),
    listOf("fifteen","十五","a0003020605"),
    listOf("sixteen","十六","a0003020606"),
    listOf("seventeen","十七","a0003020607"),
    listOf("eighteen","十八","a0003020608"),
    listOf("nineteen","十九","a0003020609"),
    listOf("twenty","二十","a0003020610"),
    listOf("kite","风筝","a0003020611"),
    listOf("beautiful","美丽的","a0003020612"),
)
val word_4_1_1 = listOf(
    listOf("classroom","教室","a0004010101"),
    listOf("window","窗户","a0004010102"),
    listOf("blackboard","黑板","a0004010103"),
    listOf("light","电灯" ,"a0004010104"),
    listOf("picture","图画","a0004010105"),
    listOf("door","门","a0004010106"),
    listOf("teacher`s desk","教师的桌子","a0004010107"),
    listOf("computer","计算机","a0004010108"),
    listOf("fan","风扇","a0004010109"),
    listOf("wall","墙壁","a0004010110"),
    listOf("floor","地板","a0004010111"),
    listOf("really","(表示兴趣或惊讶)","a0004010112"),
    listOf("near","距离近","a0004010113"),
    listOf("TV","电视","a0004010114"),
    listOf("clean","打扫","a0004010115"),
    listOf("help","帮助","a0004010116"),
)
val word_4_1_2 = listOf(
    listOf("schoolbag","书包","a0004010201"),
    listOf("maths book"," 数学书 ","a0004010202"),
    listOf("English book","英语书","a0004010203"),
    listOf("Chinese book","语文书","a0004010204"),
    listOf("storybook"," 故事书 ","a0004010205"),
    listOf("candy"," 糖果","a0004010206"),
    listOf("notebook"," 笔记本","a0004010207"),
    listOf("toy"," 玩具","a0004010208"),
    listOf("key"," 钥匙","a0004010209"),
    listOf("wow"," 哇;呀","a0004010210"),
    listOf("lost"," 丢失","a0004010211"),
    listOf("so much"," 非常地","a0004010212"),
    listOf("cute"," 可爱的 ","a0004010213"),
)
val word_4_1_3 = listOf(
    listOf("strong","强壮的","a0004010301"),
    listOf("friendly","友好的","a0004010302"),
    listOf("quiet","安静的 ","a0004010303"),
    listOf("hair","头发 ","a0004010304"),
    listOf("shoe","鞋 ","a0004010305"),
    listOf("glasses"," 眼镜","a0004010306"),
    listOf("his"," 他的 ","a0004010307"),
    listOf("or"," 或者","a0004010308"),
    listOf("right","正确的;对的","a0004010309"),
    listOf("hat","(常指带檐的) 帽子","a0004010310"),
    listOf("her","她的","a0004010311"),
)
val word_4_1_4 = listOf(
    listOf("bedroom"," 卧室","a0004010401"),
    listOf("living room"," 客厅;起居室","a0004010402"),
    listOf("study"," 书房 ","a0004010403"),
    listOf("kitchen"," 厨房","a0004010404"),
    listOf("bathroom"," 浴室;洗手间 ","a0004010405"),
    listOf("bed"," 床","a0004010406"),
    listOf("phone"," 电话 ","a0004010407"),
    listOf("table"," 桌子 ","a0004010408"),
    listOf("sofa"," 长沙发 ","a0004010409"),
    listOf("fridge"," 冰箱 ","a0004010410"),
    listOf("find"," 找到","a0004010411"),
    listOf("them"," 他(她、它)们 ","a0004010412"),
)
val word_4_1_5 = listOf(
    listOf("beef"," 牛肉 ","a0004010501"),
    listOf("chicken"," 鸡肉 ","a0004010502"),
    listOf("noodles"," 面条 ","a0004010503"),
    listOf("soup"," 汤 ","a0004010504"),
    listOf("vegetable"," 蔬菜","a0004010505"),
    listOf("bowl"," 碗  ","a0004010506"),
    listOf("chopsticks"," 筷子 ","a0004010507"),
    listOf("fork"," 餐叉","a0004010508"),
    listOf("knife"," 刀","a0004010509"),
    listOf("spoon"," 勺","a0004010510"),
    listOf("dinner"," (中午或晚上吃的)正餐","a0004010511"),
    listOf("ready"," 准备好","a0004010512"),
    listOf("help yourself","为(自己)取用","a0004010513"),
    listOf("pass"," 给;递","a0004010514"),
    listOf("try"," 试;尝试","a0004010515"),
)
val word_4_1_6 = listOf(
    listOf("parents"," 父母","a0004010601"),
    listOf("cousin"," 同辈表亲","a0004010602"),
    listOf("uncle"," 舅父;叔父;伯父;","a0004010603"),
    listOf("aunt"," 姑姑;姨姑;姑妈;","a0004010604"),
    listOf("baby brother"," 婴儿小弟弟","a0004010605"),
    listOf("doctor"," 医生","a0004010606"),
    listOf("cook"," 厨师","a0004010607"),
    listOf("driver"," 司机","a0004010608"),
    listOf("farmer"," 农民","a0004010609"),
    listOf("nurse"," 护士","a0004010610"),
    listOf("people"," 人们","a0004010611"),
    listOf("but"," 但是 ","a0004010612"),
    listOf("little"," 小的","a0004010613"),
    listOf("puppy"," 小狗","a0004010614"),
    listOf("football player"," 足球运动员","a0004010615"),
    listOf("job"," 工作","a0004010616"),
    listOf("basketball"," 篮球","a0004010617"),
)
val word_4_2_1 = listOf(
    listOf("first floor","一楼","a0004020101"),
    listOf("second floor","二楼","a0004020102"),
    listOf("teachers`office","教师办公室","a0004020103"),
    listOf("library","图书馆","a0004020104"),
    listOf("playground","操场 ","a0004020105"),
    listOf("computer room","计算机房","a0004020106"),
    listOf("art room","美术教室","a0004020107"),
    listOf("music room","音乐教室 ","a0004020108"),
    listOf("next to","紧邻;在……近旁","a0004020109"),
    listOf("homework","作业 ","a0004020110"),
    listOf("class","班;班级 ","a0004020111"),
    listOf("forty","四十 ","a0004020112"),
    listOf("way","方向","a0004020113"),
)
val word_4_2_2 = listOf(
    listOf("breakfast","早餐;早饭 ","a0004020201"),
    listOf("English class","英语课 ","a0004020202"),
    listOf("lunch","午餐;午饭 ","a0004020203"),
    listOf("music class","音乐课","a0004020204"),
    listOf("PE class","体育课","a0004020205"),
    listOf("dinner ","中午或晚上吃的正餐","a0004020206"),
    listOf("get up","起床","a0004020207"),
    listOf("go to school","去上学 ","a0004020208"),
    listOf("go home","回家","a0004020209"),
    listOf("go to bed","上床睡觉 ","a0004020210"),
    listOf("over","结束 ","a0004020211"),
    listOf("now","现在;目前","a0004020212"),
    listOf("o`clock","(表示整点)……点钟","a0004020213"),
    listOf("kid","小孩","a0004020214"),
    listOf("thirty","三十 ","a0004020215"),
    listOf("hurry up","快点 ","a0004020216"),
    listOf("come on","快;加油","a0004020217"),
    listOf("just a minute","稍等一会儿","a0004020218"),
)
val word_4_2_3 = listOf(
    listOf("cold","寒冷的;冷的 ","a0004020301"),
    listOf("cool","凉的;凉爽的","a0004020302"),
    listOf("warm","温暖的;暖和的 ","a0004020303"),
    listOf("hot","热的;烫的","a0004020304"),
    listOf("sunny","阳光充足的","a0004020305"),
    listOf("windy","多风的;风大的 ","a0004020306"),
    listOf("cloudy","阴天的;多云的 ","a0004020307"),
    listOf("snowy","下雪(多)的 ","a0004020308"),
    listOf("rainy","阴雨的;多雨的 ","a0004020309"),
    listOf("outside","在户外 ","a0004020310"),
    listOf("be careful","小心 ","a0004020311"),
    listOf("weather","天气 ","a0004020312"),
    listOf("New York","纽约","a0004020313"),
    listOf("how about","……怎么样?……情况如何? ","a0004020314"),
    listOf("degree","度;度数","a0004020315"),
    listOf("world","世界 ","a0004020316"),
    listOf("London","伦敦","a0004020317"),
    listOf("Moscow","莫斯科","a0004020318"),
    listOf("Singapore","新加坡(市 ","a0004020319"),
    listOf("Sydney","悉尼 ","a0004020320"),
    listOf("fly","放(风筝等)","a0004020321"),
    listOf("love","(写信结尾的热情问候语)爱你的","a0004020322"),
)
val word_4_2_4 = listOf(
    listOf("tomato","西红柿","a0004020401"),
    listOf("potato","马铃薯;土豆","a0004020402"),
    listOf("green beans","豆角;四季豆","a0004020403"),
    listOf("carrot","胡萝卜" ,"a0004020404"),
    listOf("horse","马","a0004020405"),
    listOf("cow","母牛;奶牛","a0004020406"),
    listOf("sheep","羊;绵羊","a0004020407"),
    listOf("hen","母鸡","a0004020408"),
    listOf("these","(this的复数形式)这些","a0004020409"),
    listOf("yum","(表示味道或气味非常好)","a0004020410"),
    listOf("animal","兽;动物","a0004020411"),
    listOf("those","(that的复数形式)那些","a0004020412"),
    listOf("garden","花园;菜园","a0004020413"),
    listOf("farm","农场","a0004020414"),
    listOf("goat","山羊","a0004020415"),
    listOf("eat","吃","a0004020416"),
)
val word_4_2_5 = listOf(
    listOf("clothes","衣服;服装 ","a0004020501"),
    listOf("pants","裤子","a0004020502"),
    listOf("hat","(常指带檐的)帽子 ","a0004020503"),
    listOf("dress","连衣裙 ","a0004020504"),
    listOf("skirt","女裙","a0004020505"),
    listOf("coat","外衣;大衣 ","a0004020506"),
    listOf("sweater","毛衣 ","a0004020507"),
    listOf("sock","短袜 ","a0004020508"),
    listOf("shorts","短裤","a0004020509"),
    listOf("jacket","夹克衫","a0004020510"),
    listOf("shirt","尤指男士)衬衫 ","a0004020511"),
    listOf("yours","你的;你们的 ","a0004020512"),
    listOf("whose","谁的 ","a0004020513"),
    listOf("mine","我的","a0004020514"),
    listOf("pack","收拾(行李) ","a0004020515"),
    listOf("wait","等待","a0004020516"),
)
val word_4_2_6 = listOf(
    listOf("glove","(分手指的)手套 ","a0004020601"),
    listOf("scarf","围巾;披巾","a0004020602"),
    listOf("umbrella","伞;雨伞 ","a0004020603"),
    listOf("sunglasses","太阳镜 ","a0004020604"),
    listOf("pretty","美观的;精致的 ","a0004020605"),
    listOf("expensive","昂贵的;花钱多的","a0004020606"),
    listOf("cheap","花钱少的;便宜的 ","a0004020607"),
    listOf("nice","好的 ","a0004020608"),
    listOf("try on","试穿","a0004020609"),
    listOf("size","尺码;号","a0004020610"),
    listOf("of course","当然 ","a0004020611"),
    listOf("too","太;过于","a0004020612"),
    listOf("just","正好;恰好 ","a0004020613"),
    listOf("how much","多少钱 ","a0004020614"),
    listOf("eighty","八十 ","a0004020615"),
    listOf("dollar","元(美国、加拿大等国的货币单位) ","a0004020616"),
    listOf("sale","特价销售;大减价 ","a0004020617"),
    listOf("more","更多的 ","a0004020618"),
    listOf("us","我们","a0004020619"),
)
val word_5_1_1 = listOf(
    listOf("old","老的;年纪大的","a0005010101"),
    listOf("young","年轻的;岁数不大的 ","a0005010102"),
    listOf("funny","滑稽的;可笑的 ","a0005010103"),
    listOf("kind","体贴的;慈祥的;宽容的","a0005010104"),
    listOf("strict","要求严格的;严厉的 ","a0005010105"),
    listOf("polite","有礼貌的;客气的 " ,"a0005010106"),
    listOf("hardworking","工作努力的;辛勤的 ","a0005010107"),
    listOf("helpful","有用的;愿意帮忙的","a0005010108"),
    listOf("clever","聪明的;聪颖的 ","a0005010109"),
    listOf("shy","羞怯的;腼腆的;怕生的 ","a0005010110"),
    listOf("know","知道;了解 ","a0005010111"),
    listOf("our","我们的","a0005010112"),
    listOf("Ms","(用于女子的姓氏或姓名前，不指明婚否)女士 ","a0005010113"),
    listOf("will","(谈及将来)将要 ","a0005010114"),
    listOf("sometimes","有时;间或","a0005010115"),
    listOf("robot","机器人","a0005010116"),
    listOf("him","(用作宾语或表语)他 ","a0005010117"),
    listOf("speak","会说;会讲(某种语言);用(某种语言)说话 ","a0005010118"),
    listOf("finish","完成;做好","a0005010119"),
)
val word_5_1_2 = listOf(
    listOf("Monday","星期一 ","a0005010201"),
    listOf("Tuesday","星期二","a0005010202"),
    listOf("Wednesday","星期三 ","a0005010203"),
    listOf("Thursday","星期四 ","a0005010204"),
    listOf("Friday","星期五 ","a0005010205"),
    listOf("Saturday","星期六 ","a0005010206"),
    listOf("Sunday","星期日 ","a0005010207"),
    listOf("weekend","周末 ","a0005010208"),
    listOf("wash","洗","a0005010209"),
    listOf("wash my clothes","洗我的衣服 ","a0005010210"),
    listOf("watch","看 ","a0005010211"),
    listOf("watch TV","看电视 ","a0005010212"),
    listOf("do","做;干","a0005010213"),
    listOf("do homework","做作业 ","a0005010214"),
    listOf("read","看;读 ","a0005010215"),
    listOf("read books","看书","a0005010216"),
    listOf("play","踢;玩;参加(体育运动)","a0005010217"),
    listOf("play football","踢足球","a0005010218"),
    listOf("cooking","烹饪;烹调 ","a0005010219"),
    listOf("often","时常;常常 ","a0005010220"),
    listOf("park","公园","a0005010221"),
    listOf("tired","疲倦的 ","a0005010222"),
    listOf("sport","体育运动 ","a0005010223"),
    listOf("play sports","做体育运动","a0005010224"),
    listOf("should","(常用于纠正别人)应该，应当","a0005010225"),
    listOf("every","每一个，每个 ","a0005010226"),
    listOf("day","一天;一日","a0005010227"),
    listOf("schedule","工作计划;日程安排","a0005010228"),
)
val word_5_1_3 = listOf(
    listOf("sandwich","三明治","a0005010301"),
    listOf("salad","蔬菜沙拉;混合沙拉","a0005010303"),
    listOf("hamburger","汉堡包 ","a0005010303"),
    listOf("ice cream","冰激凌 ","a0005010304"),
    listOf("tea","茶;茶水","a0005010305"),
    listOf("fresh","新鲜的;刚摘的 ","a0005010306"),
    listOf("healthy","健康的","a0005010307"),
    listOf("delicious","美味的;可口的 ","a0005010308"),
    listOf("hot","辣的;辛辣的","a0005010309"),
    listOf("sweet","含糖的;甜的 ","a0005010310"),
    listOf("drink","喝;饮","a0005010311"),
    listOf("thirsty","渴的;口渴的 ","a0005010312"),
    listOf("favourite","/特别喜爱的 ","a0005010313"),
    listOf("food","食物","a0005010314"),
    listOf("Dear","(用于信函抬头的名字或头衔前)亲爱的","a0005010315"),
    listOf("onion","洋葱;葱头","a0005010316"),
)
val word_5_1_4 = listOf(
    listOf("sing","唱;唱歌 ","a0005010401"),
    listOf("song","歌曲","a0005010402"),
    listOf("sing English songs","唱英文歌曲","a0005010403"),
    listOf("play the pipa","弹琵琶","a0005010404"),
    listOf("kung fu","/功夫;武术 ","a0005010405"),
    listOf("dance","跳舞 ","a0005010407"),
    listOf("draw","画","a0005010408"),
    listOf("cartoon","漫画 ","a0005010409"),
    listOf("draw cartoons","画漫画 ","a0005010410"),
    listOf("cook","烹调;烹饪 ","a0005010411"),
    listOf("swim","游泳","a0005010412"),
    listOf("play basketball"," 打篮球 ","a0005010413"),
    listOf("pingpong","乒乓球 ","a0005010414"),
    listOf("play pingpong"," 打乒乓球 ","a0005010415"),
    listOf("speak English"," 说英语 ","a0005010416"),
    listOf("party","聚会;派对","a0005010417"),
    listOf("next","下一个的;紧接着的接下来的","a0005010418"),
    listOf("wonderful","极好的;了不起的","a0005010419"),
    listOf("learn","学;学习;学会 ","a0005010420"),
    listOf("any","任何的;任一的 ","a0005010421"),
    listOf("problem","问题 ","a0005010422"),
    listOf("no problem"," 没问题 ","a0005010423"),
    listOf("want","要;想要 ","a0005010424"),
    listOf("send","邮寄;发送 ","a0005010425"),
    listOf("email","电子邮件","a0005010426"),
    listOf("at","(后面接邮件地址)","a0005010427"),
)
val word_5_1_5 = listOf(
    listOf("clock","时钟;钟","a0005010501"),
    listOf("plant","植物 ","a0005010502"),
    listOf("bottle","瓶子 ","a0005010503"),
    listOf("water bottle"," 水瓶","a0005010504"),
    listOf("bike","自行车;脚踏车 ","a0005010505"),
    listOf("photo","照片;相片 ","a0005010506"),
    listOf("front","正面","a0005010507"),
    listOf("in front of"," 在……前面","a0005010508"),
    listOf("between","/在……中间 ","a0005010509"),
    listOf("above","在(或向)……上面 ","a0005010510"),
    listOf("beside","在旁边(附近) ","a0005010511"),
    listOf("behind","在(或向)……后面","a0005010512"),
    listOf("there","(表示存在或发生) ","a0005010513"),
    listOf("grandparent"," 祖父祖母;外祖父;外祖母","a0005010514"),
    listOf("their","他们的;她们的;它们的","a0005010515"),
    listOf("house","/房屋;房子;住宅 ","a0005010516"),
    listOf("lot","大量;许多 ","a0005010517"),
    listOf("lots of"," 大量;许多","a0005010518"),
    listOf("flower","花;花朵 ","a0005010519"),
    listOf("move","搬家 ","a0005010520"),
    listOf("dirty","肮脏的","a0005010521"),
    listOf("everywhere","处处;到处","a0005010522"),
    listOf("mouse","老鼠 ","a0005010523"),
    listOf("live","住;居住","a0005010524"),
    listOf("nature","自然界;大自然","a0005010525"),
)
val word_5_1_6 = listOf(
    listOf("forest","森林;林区 ","a0005010601"),
    listOf("river","河;江 ","a0005010602"),
    listOf("lake","湖;湖泊","a0005010603"),
    listOf("mountain","高山;山岳 ","a0005010604"),
    listOf("hill","山丘;小山","a0005010605"),
    listOf("tree","树;树木;乔木 ","a0005010606"),
    listOf("bridge","桥","a0005010607"),
    listOf("building","建筑物;房子;楼房","a0005010608"),
    listOf("village","村庄;村镇","a0005010609"),
    listOf("house","房屋;房子;住宅 ","a0005010610"),
    listOf("boating","划船 ","a0005010611"),
    listOf("go boating"," 去划船","a0005010612"),
    listOf("rabbit","兔;野兔 ","a0005010613"),
    listOf("high","高的","a0005010614"),
)
val word_5_2_1 = listOf(
    listOf("eat breakfast","吃早饭 ","a0005020101"),
    listOf("have class","上……课 ","a0005020102"),
    listOf("play sports","进行体育运动","a0005020103"),
    listOf("exercise","活动;运动 ","a0005020104"),
    listOf("do morning exercises","做早操 ","a0005020105"),
    listOf("eat dinner","吃晚饭","a0005020106"),
    listOf("clean my room","打扫我的房间 ","a0005020107"),
    listOf("go for a walk","散步","a0005020108"),
    listOf("go shopping","去买东西;购物 ","a0005020109"),
    listOf("take","学习;上(课) ","a0005020110"),
    listOf("dancing","跳舞;舞蹈","a0005020111"),
    listOf("take a dancing class","上舞蹈课 ","a0005020112"),
    listOf("when","什么时候;何时 ","a0005020113"),
    listOf("after","在(时间)后 ","a0005020114"),
    listOf("start","开始","a0005020115"),
    listOf("usually","通常地;惯常地 ","a0005020116"),
    listOf("Spain","西班牙 ","a0005020117"),
    listOf("late","晚;迟","a0005020118"),
    listOf("a.m.","午前;上午 ","a0005020119"),
    listOf("p.m.","午后;下午 ","a0005020120"),
    listOf("why","为什么","a0005020121"),
    listOf("shop","去买东西;购物 ","a0005020122"),
    listOf("work","工作","a0005020123"),
    listOf("last","上一个的;刚过去的 ","a0005020124"),
    listOf("sound","听起来好像 ","a0005020125"),
    listOf("also","还;也 ","a0005020126"),
    listOf("busy","忙的","a0005020127"),
    listOf("need","需要","a0005020128"),
    listOf("play","戏剧;剧本 ","a0005020129"),
    listOf("letter","/信 ","a0005020130"),
    listOf("live","居住","a0005020131"),
    listOf("island","岛","a0005020132"),
    listOf("always","总是;一直 ","a0005020133"),
    listOf("cave","山洞;洞穴 ","a0005020134"),
    listOf("go swimming","去游泳 ","a0005020135"),
    listOf("win","获胜","a0005020136"),
)
val word_5_2_2 = listOf(
    listOf("spring","春天","a0005020201"),
    listOf("summer","夏天 ","a0005020202"),
    listOf("autumn","秋天 ","a0005020203"),
    listOf("winter","冬天 ","a0005020204"),
    listOf("season","季节 ","a0005020205"),
    listOf("picnic","野餐 ","a0005020206"),
    listOf("go on a picnic","去野餐 ","a0005020207"),
    listOf("pick","摘;采集 ","a0005020208"),
    listOf("pick apples","摘苹果","a0005020209"),
    listOf("snowman","雪人 ","a0005020210"),
    listOf("make a snowman","堆雪人 ","a0005020211"),
    listOf("go swimming","去游泳 ","a0005020212"),
    listOf("which","哪一个","a0005020213"),
    listOf("best","最;最高程度地 ","a0005020214"),
    listOf("snow","雪 ","a0005020215"),
    listOf("good job","做得好","a0005020216"),
    listOf("because","因为","a0005020217"),
    listOf("vacation","假期 ","a0005020218"),
    listOf("all","全;完全","a0005020219"),
    listOf("pink","粉色;粉色的","a0005020220"),
    listOf("lovely","可爱的;美丽的 ","a0005020221"),
    listOf("leaf","叶子(复数 leaves)","a0005020222"),
    listOf("fall","落下;【美】秋天 ","a0005020223"),
    listOf("paint","用颜料绘画","a0005020224"),
)
val word_5_2_3 = listOf(
    listOf("January","一月 ","a0005020301"),
    listOf("February","二月 ","a0005020302"),
    listOf("March","三月 ","a0005020303"),
    listOf("April","四月 ","a0005020304"),
    listOf("May","五月 ","a0005020305"),
    listOf("June","六月 ","a0005020306"),
    listOf("July","七月 ","a0005020307"),
    listOf("August","八月","a0005020308"),
    listOf("September","九月 ","a0005020309"),
    listOf("October","十月","a0005020310"),
    listOf("November","十一月 ","a0005020311"),
    listOf("December","十二月 ","a0005020312"),
    listOf("few","不多;很少 ","a0005020313"),
    listOf("a few","一些 ","a0005020314"),
    listOf("thing","事情","a0005020315"),
    listOf("meet","集会;开会 ","a0005020316"),
    listOf("sports meet","运动会 ","a0005020317"),
    listOf("trip ","旅行 ","a0005020318"),
    listOf("year","年 ","a0005020319"),
    listOf("plant","种植","a0005020320"),
    listOf("contest","比赛;竞赛 ","a0005020321"),
    listOf("the Great Wall","长城","a0005020322"),
    listOf("national","国家的","a0005020323"),
    listOf("National Day","国庆日","a0005020324"),
    listOf("American","/美国的 ","a0005020325"),
    listOf("Thanksgiving","感恩节","a0005020326"),
    listOf("Christmas","圣诞节 ","a0005020327"),
    listOf("game","游戏","a0005020328"),
    listOf("riddle","谜;谜语 ","a0005020329"),
    listOf("act","扮演","a0005020330"),
    listOf("act out","把……表演出来 ","a0005020331"),
    listOf("RSVP","(尤用于请柬)请赐复 ","a0005020332"),
    listOf("by","在……之前","a0005020333"),
)
val word_5_2_4 = listOf(
    listOf("first(1st)","第一(的) ","a0005020401"),
    listOf("second(2nd)","第二(的 ","a0005020402"),
    listOf("third(3rd)","第三(的) ","a0005020403"),
    listOf("fourth(4th)","第四(的) ","a0005020404"),
    listOf("fifth(5th)","第五(的) ","a0005020405"),
    listOf("twelfth(12th)","第十二","a0005020406"),
    listOf("twentieth (20th)","第二十(的)","a0005020407"),
    listOf("twentyfirst(21st)","第二十一(的)","a0005020408"),
    listOf("twentythird(23rd)","第二十三(的)","a0005020409"),
    listOf("thirtieth(30th)","第三十(的)","a0005020410"),
    listOf("special","特殊的;特别的 ","a0005020411"),
    listOf("show","展览","a0005020412"),
    listOf("festival","节日 ","a0005020413"),
    listOf("kitten","小猫 ","a0005020414"),
    listOf("diary","日记","a0005020415"),
    listOf("still","仍然;依旧;还是 ","a0005020416"),
    listOf("noise","声音;响声;噪音 ","a0005020417"),
    listOf("fur","(某些动物的)浓密的软毛","a0005020418"),
    listOf("open","开着的 ","a0005020419"),
    listOf("walk","行走","a0005020420"),
)
val word_5_2_5 = listOf(
    listOf("mine","我的","a0005020501"),
    listOf("yours","你(们)的 ","a0005020502"),
    listOf("his","他的 ","a0005020503"),
    listOf("hers","她的","a0005020504"),
    listOf("theirs","他们的;她们的;它们的","a0005020505"),
    listOf("ours","我们的","a0005020506"),
    listOf("climbing","(正在)攀登;攀爬 ","a0005020507"),
    listOf("eating","(正在)吃","a0005020508"),
    listOf("playing","(正在)玩耍","a0005020509"),
    listOf("jumping","(正在)跳","a0005020510"),
    listOf("drinking","(正在)喝(水)","a0005020511"),
    listOf("sleeping","(正在)睡觉 ","a0005020512"),
    listOf("each","每一;各个 ","a0005020513"),
    listOf("other","其他 ","a0005020514"),
    listOf("each other","相互","a0005020515"),
    listOf("excited","兴奋的;激动的","a0005020516"),
    listOf("like","像……那样","a0005020517"),
)
val word_5_2_6 = listOf(
    listOf("doing morning exercises","(正在)做早操","a0005020601"),
    listOf("having class","(正在)上……课 ","a0005020602"),
    listOf("eating lunch","(正在)吃午饭 ","a0005020603"),
    listOf("reading a book","(正在)看书 ","a0005020604"),
    listOf("listening to music","(正在)听音乐","a0005020605"),
    listOf("keep","保持某种状态 ","a0005020606"),
    listOf("keep to the right","靠右","a0005020607"),
    listOf("keep your desk clean","保持你的课桌干净","a0005020608"),
    listOf("talk quietly","小声讲话 ","a0005020609"),
    listOf("turn","顺序","a0005020610"),
    listOf("take turns"," 按顺序来 ","a0005020611"),
    listOf("bamboo","竹子","a0005020612"),
    listOf("its","(指事物、动物或幼儿)它的;他的;她的 ","a0005020613"),
    listOf("show","给人看","a0005020614"),
    listOf("anything","任何事物 ","a0005020615"),
    listOf("else","另外;其他","a0005020616"),
    listOf("exhibition","展览 ","a0005020617"),
    listOf("say","说;讲 ","a0005020618"),
    listOf("have a look"," 看一看 ","a0005020619"),
    listOf("sushi","寿司 ","a0005020620"),
    listOf("teach","教","a0005020621"),
    listOf("sure","(表示同意)当然 ","a0005020622"),
    listOf("Canadian","加拿大的 ","a0005020623"),
    listOf("Spanish","西班牙的","a0005020624"),
)
val word_6_1_1 = listOf(
    listOf("science","科学","a0006010101"),
    listOf("museum","博物馆","a0006010102"),
    listOf("post office","邮局 ","a0006010103"),
    listOf("bookstore","书店 ","a0006010104"),
    listOf("cinema","电影院 ","a0006010105"),
    listOf("hospital","医院 ","a0006010106"),
    listOf("crossing","十字路口 ","a0006010107"),
    listOf("turn","转弯 ","a0006010108"),
    listOf("left","左","a0006010109"),
    listOf("straight","笔直地 ","a0006010110"),
    listOf("right","右 ","a0006010111"),
    listOf("ask","问","a0006010112"),
    listOf("sir","(对男子的礼貌称呼)先生","a0006010113"),
    listOf("interesting","有趣的 ","a0006010114"),
    listOf("Italian","意大利的 ","a0006010115"),
    listOf("restaurant","餐馆 ","a0006010116"),
    listOf("pizza","比萨饼","a0006010117"),
    listOf("street","大街;街道 ","a0006010118"),
    listOf("get","到达","a0006010119"),
    listOf("GPS","全球(卫星)定位系统","a0006010120"),
    listOf("gave","提供;交给","a0006010121"),
    listOf("feature","特点","a0006010122"),
    listOf("follow","跟着 ","a0006010123"),
    listOf("far","较远的 ","a0006010124"),
    listOf("tell","告诉","a0006010125"),
)
val word_6_1_2 = listOf(
    listOf("on foot","步行","a0006010201"),
    listOf("by","(表示方式)乘 ","a0006010202"),
    listOf("bus","公共汽车 ","a0006010203"),
    listOf("plane","飞机 ","a0006010204"),
    listOf("taxi","出租汽车 ","a0006010205"),
    listOf("ship","(大)船 ","a0006010206"),
    listOf("subway","地铁 ","a0006010207"),
    listOf("train","火车","a0006010208"),
    listOf("slow","(使)放慢速度;慢的 ","a0006010209"),
    listOf("down","减少;降低 ","a0006010210"),
    listOf("slow down","慢下来","a0006010211"),
    listOf("stop","停下 ","a0006010212"),
    listOf("Mrs","夫人 ","a0006010213"),
    listOf("early","早到的 ","a0006010214"),
    listOf("helmet","头盔 ","a0006010215"),
    listOf("must","必须 ","a0006010216"),
    listOf("wear","戴","a0006010217"),
    listOf("attention","注意 ","a0006010218"),
    listOf("pay attention to","注意 ","a0006010219"),
    listOf("traffic","交通","a0006010220"),
    listOf("traffic lights","交通信号灯","a0006010221"),
    listOf("Munich","慕尼黑(德国城市)","a0006010222"),
    listOf("Germany","德国","a0006010223"),
    listOf("Alaska","阿拉斯加州(美国州名)","a0006010224"),
    listOf("sled","雪橇 ","a0006010225"),
    listOf("fast","快的 ","a0006010226"),
    listOf("ferry","轮渡","a0006010227"),
    listOf("Papa Westray","帕帕韦斯特雷岛","a0006010228"),
    listOf("Scotland","苏格兰","a0006010229"),
)
val word_6_1_3 = listOf(
    listOf("visit","拜访 ","a0006010301"),
    listOf("film","电影","a0006010302"),
    listOf("see a film","看电影 ","a0006010303"),
    listOf("trip","旅行","a0006010304"),
    listOf("take a trip","去旅行","a0006010305"),
    listOf("supermarket","超市 ","a0006010306"),
    listOf("evening","晚上;傍晚 ","a0006010307"),
    listOf("tonight","在今晚 ","a0006010308"),
    listOf("tomorrow","明天 ","a0006010309"),
    listOf("next week","下周","a0006010310"),
    listOf("dictionary","词典 ","a0006010311"),
    listOf("comic","滑稽的","a0006010312"),
    listOf("comic book","(儿童的)连环画册 ","a0006010313"),
    listOf("word","单词 ","a0006010314"),
    listOf("word book","单词书","a0006010315"),
    listOf("postcard","明信片 ","a0006010316"),
    listOf("lesson","课 ","a0006010317"),
    listOf("space","太空","a0006010318"),
    listOf("ravel","(尤指长途)旅行 ","a0006010319"),
    listOf("half/","一半 ","a0006010320"),
    listOf("price","价格 ","a0006010321"),
    listOf("MidAutumn Festival","中秋节 ","a0006010322"),
    listOf("together","一起 ","a0006010323"),
    listOf("get together"," 聚会 ","a0006010324"),
    listOf("mooncake"," 月饼 ","a0006010325"),
    listOf("poem","诗 ","a0006010326"),
    listOf("moon","月亮","a0006010327"),
)
val word_6_1_4 = listOf(
    listOf("studies","学习 ","a0006010401"),
    listOf("puzzle","谜 ","a0006010402"),
    listOf("hiking","远足 ","a0006010403"),
    listOf("pen pal","笔友 ","a0006010404"),
    listOf("hobby","业余爱好 ","a0006010405"),
    listOf("jasmine","茉莉 ","a0006010406"),
    listOf("idea","想法;主意","a0006010407"),
    listOf("Canberra","堪培拉(澳大利亚首都)","a0006010408"),
    listOf("amazing","令人惊奇的 ","a0006010409"),
    listOf("shall","表示征求意见 ","a0006010410"),
    listOf("goal","射门 ","a0006010411"),
    listOf("join","加入 ","a0006010412"),
    listOf("club","俱乐部 ","a0006010413"),
    listOf("share","分享","a0006010414"),
)
val word_6_1_5 = listOf(
    listOf("factory","工厂","a0006010501"),
    listOf("worker","工人","a0006010502"),
    listOf("postman","邮递员 ","a0006010503"),
    listOf("businessman","商人企业家","a0006010504"),
    listOf("police officer","警察","a0006010505"),
    listOf("fisherman","渔民 ","a0006010506"),
    listOf("scientist","科学家 ","a0006010507"),
    listOf("pilot","飞行员 ","a0006010508"),
    listOf("coach","教练 ","a0006010509"),
    listOf("country","国家 ","a0006010510"),
    listOf("head teacher","校长 ","a0006010511"),
    listOf("sea","大海 ","a0006010512"),
    listOf("stay","保持","a0006010513"),
    listOf("university","大学 ","a0006010514"),
    listOf("gym","体育馆 ","a0006010515"),
    listOf("if","如果","a0006010516"),
    listOf("reporter","记者 ","a0006010517"),
    listOf("use","使用 ","a0006010518"),
    listOf("type","打字","a0006010519"),
    listOf("quickly","迅速地 ","a0006010520"),
    listOf("secretary","秘书","a0006010521"),
)
val word_6_1_6 = listOf(
    listOf("angry","生气的 ","a0006010601"),
    listOf("afraid","害怕 ","a0006010602"),
    listOf("sad","难过的","a0006010603"),
    listOf("worried","担心的;发愁的 ","a0006010604"),
    listOf("happy","高兴的 ","a0006010605"),
    listOf("see a doctor"," 看病 ","a0006010606"),
    listOf("wear","穿","a0006010607"),
    listOf("more","更多的 ","a0006010608"),
    listOf("deep","深的 ","a0006010609"),
    listOf("breath","呼吸","a0006010610"),
    listOf("take a deep breath","深深吸一口气 ","a0006010611"),
    listOf("count","数数","a0006010612"),
    listOf("count to ten"," 数到十 ","a0006010613"),
    listOf("chase","追赶","a0006010614"),
    listOf("mice","老鼠","a0006010615"),
    listOf("bad","邪恶的;坏的 ","a0006010616"),
    listOf("hurt","(使)受伤 ","a0006010617"),
    listOf("ill","有病;不舒服 ","a0006010618"),
    listOf("wrong","有毛病 ","a0006010619"),
    listOf("should","应该 ","a0006010620"),
    listOf("feel","觉得;感到 ","a0006010621"),
    listOf("well","健康;身体好 ","a0006010622"),
    listOf("sit","坐","a0006010623"),
    listOf("grass","草坪 ","a0006010624"),
    listOf("hear","听见 ","a0006010625"),
    listOf("ant","蚂蚁","a0006010626"),
    listOf("worry","担心;担忧 ","a0006010627"),
    listOf("stuck","陷住;无法移动 ","a0006010628"),
    listOf("mud","泥 ","a0006010629"),
    listOf("pull","拉;拽","a0006010630"),
    listOf("everyone","每人","a0006010631"),
)
val word_6_2_1 = listOf(
    listOf("younger","更年轻的","a0006020101"),
    listOf("older","更年长的","a0006020102"),
    listOf("taller","更高的","a0006020103"),
    listOf("shorter","更矮的;更短的","a0006020104"),
    listOf("longer","级更长的","a0006020105"),
    listOf("thinner","更瘦的","a0006020106"),
    listOf("heavier","更重的","a0006020107"),
    listOf("bigger","更大的","a0006020108"),
    listOf("smaller","更小的","a0006020109"),
    listOf("stronger","更强壮的","a0006020110"),
    listOf("dinosaur","大厅","a0006020111"),
    listOf("metre","米","a0006020112"),
    listOf("than","比 ","a0006020113"),
    listOf("both","两个都","a0006020114"),
    listOf("kilogram","千克;公斤 ","a0006020115"),
    listOf("countryside","乡村 ","a0006020116"),
    listOf("lower","的比较更低地","a0006020117"),
    listOf("shadow","阴影;影子","a0006020118"),
    listOf("smarter","更聪明的","a0006020119"),
    listOf("become","开始变得;变成","a0006020120"),
)
val word_6_2_2 = listOf(
    listOf("cleaned","过去式打扫","a0006020201"),
    listOf("stayed","过去式 停留;待","a0006020202"),
    listOf("washed","过去式 洗","a0006020203"),
    listOf("watched","过去式 看","a0006020204"),
    listOf("had","过去式 患病;得病","a0006020205"),
    listOf("had a cold"," 感冒","a0006020206"),
    listOf("slept","过去式 觉","a0006020207"),
    listOf("read"," 过去式 读","a0006020208"),
    listOf("saw","过去式 看见 ","a0006020209"),
    listOf("last","最近的;上一个的 ","a0006020210"),
    listOf("yesterday","昨天 ","a0006020211"),
    listOf("before","在……之前 ","a0006020212"),
    listOf("drank","过去式 喝","a0006020213"),
    listOf("show","演出","a0006020214"),
    listOf("magazine","杂志","a0006020215"),
    listOf("better","比较级 更好的","a0006020216"),
    listOf("faster","比较级 更快的","a0006020217"),
    listOf("hotel","旅馆","a0006020218"),
    listOf("fixed","过去式 修理","a0006020219"),
    listOf("broken","破损的 ","a0006020220"),
    listOf("lamp","台灯","a0006020221"),
    listOf("loud","喧闹的;大声的 ","a0006020222"),
    listOf("enjoy","享受……乐趣;喜爱","a0006020223"),
    listOf("stay","暂住;逗留","a0006020224"),
)
val word_6_2_3 = listOf(
    listOf("went","过去式 去 ","a0006020301"),
    listOf("camp","野营","a0006020302"),
    listOf("went camping","(尤指在假日)野营","a0006020303"),
    listOf("fish","钓鱼;捕鱼","a0006020304"),
    listOf("went fishing","去钓鱼","a0006020305"),
    listOf("rode","过去式 骑(马;自行车)","a0006020306"),
    listOf("hurt","过去式 (使)受伤","a0006020307"),
    listOf("ate","过去式 吃","a0006020308"),
    listOf("took","过去式 拍照 ","a0006020309"),
    listOf("took pictures"," 照相","a0006020310"),
    listOf("bought","过去式 买 ","a0006020311"),
    listOf("gift","礼物","a0006020312"),
    listOf("fell","过去式 摔倒 ","a0006020313"),
    listOf("off","从(某处)落下","a0006020314"),
    listOf("Labour Day","劳动节 ","a0006020315"),
    listOf("mule","骡子","a0006020316"),
    listOf("Turpan","吐鲁番","a0006020317"),
    listOf("could","过去式 能","a0006020318"),
    listOf("till","直到","a0006020319"),
    listOf("beach","海滩;沙滩 ","a0006020320"),
    listOf("basket","篮;筐 ","a0006020321"),
    listOf("part","角色","a0006020322"),
    listOf("licked","过去式 舔","a0006020323"),
    listOf("laughed","过去式 笑","a0006020324"),
)
val word_6_2_4 = listOf(
    listOf("dining hall","饭厅 ","a0006020401"),
    listOf("grass","草坪 ","a0006020402"),
    listOf("gym","体育馆","a0006020403"),
    listOf("ago","以前","a0006020404"),
    listOf("cycling","骑自行车运动(或活动)","a0006020405"),
    listOf("go cycling"," 去骑自行车 ","a0006020406"),
    listOf("iceskate","滑冰 ","a0006020407"),
    listOf("badminton","羽毛球运动","a0006020408"),
    listOf("star","星 ","a0006020409"),
    listOf("easy","容易的","a0006020410"),
    listOf("look up","(在词典中或通过电脑)查阅","a0006020411"),
    listOf("Internet","互联网 ","a0006020412"),
    listOf("different","不同的","a0006020413"),
    listOf("active","积极的;活跃的 ","a0006020414"),
    listOf("race","赛跑","a0006020415"),
    listOf("nothing","没有什么","a0006020416"),
    listOf("thought","过去式 想","a0006020417"),
    listOf("felt","过去式 感觉 ","a0006020418"),
    listOf("cheetah","猎豹 ","a0006020419"),
    listOf("trip","绊倒","a0006020420"),
    listOf("woke","过去式 醒","a0006020421"),
    listOf("dream","梦","a0006020422"),
)

