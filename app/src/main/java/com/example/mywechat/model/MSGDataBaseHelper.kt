package com.example.secondexperiment.model

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MSGDataBaseHelper(val context: Context, name: String, version: Int) :
        SQLiteOpenHelper(context, name, null, version){
    private val createTableMsg = "create table message(" +
            "id integer primary key autoincrement," +
            "sender varchar(20)," +
            "recipient varchar(20)," +
            "content text," +
            "send_time datetime)"

    private val createTableUsers = "create table users(" +
            "id integer primary key autoincrement," +
            "name varchar(20) unique," +
            "password varchar(20))"

    private val createTableFriend = "create table friends(" +
            "id integer primary key autoincrement," +
            "mine_id varchar(20)," +
            "friend_id varchar(20))"
//    "insert into account (account, password) values(?, ?)",
    private val initData = "insert into message (sender, recipient, content, send_time) values('aaa', 'bbb', 'hello', '2020-12-8')"
//        "insert into users values('aaa', '123'), values('bbb', '123');" +
//        "insert into friends values('aaa', 'bbb'), values('aaa', 'ccc'), values('aaa', 'ddd'), values('aaa', 'eee')," +
//        "values('aaa', 'fff'), values('aaa', 'www'), values('aaa', 'qqq'), values('aaa', 'zzz');"
//    private val initData = "insert into users (name, password) values('aaa', '123')"
    private val insertMsgs = "insert into message values(null,'bbb', 'bbb', 'ccccccccccccccc', '2020-12-8')," +
        "(null,'bbb', 'eeeee', 'dddddddddddddddddddddd', '2020-12-8')," +
        "(null,'bbb', 'eeeee', 'eeeeeeeeeeeeee', '2020-12-8')," +
        "(null,'哈士奇', '米老鼠', '米老鼠', '2020-12-8 00:22:22')," +
        "(null,'哈士奇', '林志颖', 'hello,林志颖', '2020-12-8 00:22:22')," +
        "(null,'哈士奇', '小白', 'hello,小白', '2020-12-8 00:22:22')," +
        "(null,'哈士奇', '萨摩耶', 'hello,萨摩耶', '2020-12-8 00:22:22')," +
        "(null,'哈士奇', '周杰伦', 'hello,周杰伦', '2020-12-8 00:22:22')," +
        "(null,'哈士奇', '管家', 'hello,管家', '2020-12-8 00:22:22')," +
        "(null,'哈士奇', '苹果', 'hello,苹果', '2020-12-8 00:22:22')," +
        "(null,'哈士奇', '胡歌', 'hello,胡歌', '2020-12-8 00:22:22')," +
        "(null,'哈士奇', '派大星', 'hello,派大星', '2020-12-8 00:22:22')," +
        "(null,'哈士奇', '章鱼哥', 'hello,章鱼哥', '2020-12-8 00:22:22')," +
        "(null,'蓝猫', '哈士奇', '嗯，你好', '2020-12-8 00:11:11');"

    private val insertUsers = "insert into users values(null, '哈士奇', '123')," +
            "(null, '蓝猫', '123');"
//
    private val insertFriends = "insert into friends values(null, '哈士奇', '蓝猫'),(null, '哈士奇', '苹果')," +
        "(null, '哈士奇', '米老鼠'),(null, '哈士奇', '派大星'),(null, '哈士奇', '章鱼哥'),(null, '哈士奇', '蟹老板')," +
        "(null, '哈士奇', '杨幂'),(null, '哈士奇', '小柯基'),(null, '哈士奇', '小白'),(null, '哈士奇', '泡芙')," +
        "(null, '哈士奇', '林志颖'),(null, '哈士奇', '彭于晏'),(null, '哈士奇', '胡歌'),(null, '哈士奇', '周杰伦')," +
        "(null, '哈士奇', '笑笑'),(null, '哈士奇', '利刃'),(null, '哈士奇', '小心背后'),(null, '哈士奇', '笑着哦')," +
        "(null, '哈士奇', '萨摩耶'),(null, '哈士奇', '艾伦'),(null, '哈士奇', '兵长'),(null, '哈士奇', '管家');"


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createTableUsers)
        db.execSQL(createTableFriend)
        db.execSQL(createTableMsg)
        db.execSQL(initData)
        db.execSQL(insertMsgs)
        db.execSQL(insertFriends)
        db.execSQL(insertUsers)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }
}