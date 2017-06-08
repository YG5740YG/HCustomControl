package newhome.baselibrary.Save.SaveFile;

/**
 * Created by 20160330 on 2017/3/21.
 */

/**
 *  SQLiteOpenHelper 是一个抽象类，这意味着如果我们想要使用它的话，
 就需要创建一个自己的帮助类去继承它。SQLiteOpenHelper 中有两个抽象方法，分别是
 onCreate()和 onUpgrade()，我们必须在自己的帮助类里面重写这两个方法，然后分别在这两
 个方法中去实现创建、升级数据库的逻辑。
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
SQLiteOpenHelper 中 还 有 两 个 非 常 重 要 的 实 例 方 法 ， getReadableDatabase() 和
getWritableDatabase()。这两个方法都可以创建或打开一个现有的数据库（如果数据库已存在
则直接打开，否则创建一个新的数据库），并返回一个可对数据库进行读写操作的对象。不
同的是，当数据库不可写入的时候（如磁盘空间已满）getReadableDatabase()方法返回的对
象将以只读的方式去打开数据库，而 getWritableDatabase()方法则将出现异常。
 */
/*
SQLiteOpenHelper 中有两个构造方法可供重写，一般使用参数少一点的那个构造方法即
可。这个构造方法中接收四个参数，
第一个参数是 Context，这个没什么好说的，必须要有它才能对数据库进行操作。
第二个参数是数据库名，创建数据库时使用的就是这里指定的名称。
第三个参数允许我们在查询数据的时候返回一个自定义的 Cursor，一般都是传入 null。
第四个参数表示当前数据库的版本号，可用于对数据库进行升级操作。构建出
SQLiteOpenHelper 的实例之后，再调用它的 getReadableDatabase()或 getWritableDatabase()方
法就能够创建数据库了，数据库文件会存放在/data/data/<package name>/databases/目录下。
此时，重写的 onCreate()方法也会得到执行，所以通常会在这里去处理一些创建表的逻辑。
 */
public class MySQLite extends SQLiteOpenHelper {
    /**
     * integer 表示整型，real 表示浮点型，text 表示文本类型，blob 表示二进制类型。另外，上述建表语句中我们还
     使用了 primary key 将 id 列设为主键，并用 autoincrement关键字表示 id 列是自增长的。
     */
//    public static final String CREATE_BOOK = "create table book ("
//            + "id integer primary key autoincrement, "
//            + "author text, "
//            + "price real, "
//            + "pages integer, "
//            + "name text)";//数据结构，一张book数据表

    public static final String CREATE_BOOK = "create table Book ("
            + "id integer primary key autoincrement, "
            + "author text, "
            + "price real, "
            + "pages integer, "
            + "name text, "
            + "category_id integer)";//升级增加一行数据
    public static final String CREATE_CATEGORY = "create table Category ("
            + "id integer primary key autoincrement, "
            + "category_name text, "
            + "category_code integer)";//数据库中的另外一张表
    private Context mContext;
    //构造函数参数：调用的activity,数据库名字，游标可以传null，版本号
    public MySQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_BOOK);//新建数据表，参数为建表语句的一个字符串常量
        sqLiteDatabase.execSQL(CREATE_CATEGORY);//对于新用户是一次性增加两个表，对于老客户则不执行此方法，而是更新数据
        Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_SHORT).show();
        /*
       当数据表创建一次以后，BookStore.db 数据库已经存在了，之后不管我们操作，MyDatabaseHelper 中的
       onCreate()方法都不会再次执行，因此新添加的表也就不能再这个地方创建。
       若要再加新表，方法一：
       先将程序卸载掉，这时BookStore.db 数据库已经不存在，然后重新运行
       方法二：
       在onUpgrade中控制增加
         */
    }
    /**
     我们在 onUpgrade()方法中执行了两条 DROP 语句，如果发现数据库中已经
     存在 Book 表或 Category 表了，就将这两张表删除掉，然后再调用 onCreate()方法去重新创
     建。这里先将已经存在的表删除掉，是因为如果在创建表时发现这张表已经存在了，就会直
     接报错。
     */
    /*
    如何让 onUpgrade()方法能够执行了，还记得 SQLiteOpenHelper 的构
造方法里接收的第四个参数吗？它表示当前数据库的版本号，之前我们传入的是 1，现在只
要传入一个比 1 大的数，就可以让 onUpgrade()方法得到执行了
主要用于数据库更新
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
//        sqLiteDatabase.execSQL("drop table if exists Book");
//        sqLiteDatabase.execSQL("drop table if exists Category");
//        onCreate(sqLiteDatabase);
        //每一个数据库版本都会对应
//        一个版本号，当指定的数据库版本号大于当前数据库版本号的时候，就会进入到 onUpgrade()
//        方法中去执行更新操作。这里需要为每一个版本号赋予它各自改变的内容，然后在
//        onUpgrade()方法中对当前数据库的版本号进行判断，再执行相应的改变就可以了。
        switch (oldVersion) {
            case 1:
                sqLiteDatabase.execSQL(CREATE_CATEGORY);//升级为版本1，新增一个数据表
            case 2:
                sqLiteDatabase.execSQL("alter table Book add column category_id integer");//升级为版本2，改变数据表中的一个数据，增加一个字段
            default:
        }
    }

    //自定义方法
    // ------新建数据库
    public static void CreatSQLite(MySQLite mySQLite){
        mySQLite.getWritableDatabase();//创建数据库，并创建数据表，若改数据表存在，则不进行创建，不存在改表，则进行创建
    }
    //------添加数据--曾、删、查、改（CRUD）
//    调用 SQLiteOpenHelper的 getReadableDatabase()或 getWritableDatabase()
//    方法是可以用于创建和升级数据库的，不仅如此，这两个方法还都会返回一个SQLiteDatabase对象
    /*
     C 代表添加（Create），R 代表查询（Retrieve），U 代表更新（Update），D代表删除（Delete）。
     */
    //-----曾
    /**
     * SQLiteDatabase中提供了一个 insert()方法，这个方法就是专门用于添加数据的。它接收三个
     参数，第一个参数是表名，我们希望向哪张表里添加数据，这里就传入该表的名字。第二个
     参数用于在未指定添加数据的情况下给某些可为空的列自动赋值 NULL，一般我们用不到这
     个功能，直接传入 null 即可。第三个参数是一个 ContentValues 对象，它提供了一系列的 put()
     方法重载，用于向 ContentValues 中添加数据，只需要将表中的每个列名以及相应的待添加
     数据传入即可
     */
    public static void insertData(MySQLite mySQLite){
        SQLiteDatabase sqLiteDatabase=mySQLite.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name","The da vinci code");
        values.put("author","Dan Brown");
        values.put("pages",454);
        values.put("price",16.96);
        sqLiteDatabase.insert("Book",null,values);//插入第一条数据
        values.clear();
        values.put("name", "The Lost Symbol");
        values.put("author", "Dan Brown");
        values.put("pages", 510);
        values.put("price", 19.95);
        sqLiteDatabase.insert("Book", null, values); //
    }
    //-----更新
    /**
     *  update()方法用于对数据进行更新，这个方法
     接收四个参数，第一个参数和 insert()方法一样，也是表名，在这里指定去更新哪张表里的数
     据。第二个参数是 ContentValues 对象，要把更新数据在这里组装进去。第三、第四个参数
     用于去约束更新某一行或某几行中的数据，不指定的话默认就是更新所有行。
     第三个参数对应的是 SQL 语句的 where 部分，表示去更新所有 name 等于?的行，而?是一个占位符
     第四个参数提供的一个字符串数组为第三个参数中的每个占位符指定相应的内容
     */
    public static void upData(MySQLite mySQLite) {
        SQLiteDatabase sqLiteDatabase = mySQLite.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("price",200.4);
        sqLiteDatabase.update("Book",values,"name=?",new String[]{"The da vinci code"});
    }
    //-----删除

    /**
     *  delete()方法专门用于删除数据，这个方法接收三个参数，第一个参数仍然是表名，第二、第三个参数又是用于去约束删除某一
     行或某几行的数据，不指定的话默认就是删除所有行。
     第二、第三个参数来指定仅删除那些页数超过 500 页的书籍
     * @param mySQLite
     */
    public static void deleteData(MySQLite mySQLite) {
        SQLiteDatabase sqLiteDatabase = mySQLite.getWritableDatabase();
        sqLiteDatabase.delete("Book","pages>?",new String[]{"500"});
    }
    //----查
    /**
     * SQLiteDatabase中还提供了一个 query()方法用于对数据进行查询。
     这个方法的参数非常复杂，最短的一个方法重载也需要传入七个参数。那我们就先来看一下
     这七个参数各自的含义吧，第一个参数不用说，当然还是表名，表示我们希望从哪张表中查
     询数据。第二个参数用于指定去查询哪几列，如果不指定则默认查询所有列。第三、第四个
     参数用于去约束查询某一行或某几行的数据，不指定则默认是查询所有行的数据。第五个参
     数用于指定需要去 group by 的列，不指定则表示不对查询结果进行 group by 操作。第六个参
     数用于对 group by 之后的数据进行进一步的过滤，不指定则表示不进行过滤。第七个参数用
     于指定查询结果的排序方式，不指定则表示使用默认的排序方式。
     table from table_name 指定查询的表名
     columns select column1, column2 指定查询的列名
     selection where column = value 指定 where的约束条件
     selectionArgs - 为 where中的占位符提供具体的值
     groupBy group by column 指定需要 group by 的列
     having having column = value 对 group by 后的结果进一步约束
     orderBy order by column1, column2 指定查询结果的排序方式
     调用
     query()方法后会返回一个 Cursor 对象，查询到的所有数据都将从这个对象中取出。
     */
    public static void selectData(MySQLite mySQLite) {
        SQLiteDatabase sqLiteDatabase = mySQLite.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.query("Book",null,null,null,null,null,null);//对表中所有数据进行查询,把结果放入游标对象中
        if(cursor.moveToFirst())
        //调用它的 moveToFirst()方法将数据的指针移动到第一行的位置
        {
            do{//进入了一个循环当中，去遍历查询到的每一行数据
                //  遍历Cursor 对象，取出数据并打印
                String name = cursor.getString(cursor.
                        getColumnIndex("name"));//通过 Cursor 的 getColumnIndex()方法获取到某一列在表中对应的位置索引
                String author = cursor.getString(cursor.
                        getColumnIndex("author"));
                int pages = cursor.getInt(cursor.getColumnIndex
                        ("pages"));
                double price = cursor.getDouble(cursor.
                        getColumnIndex("price"));
            }while (cursor.moveToNext());
        }
        cursor.close();//调用 close()方法来关闭 Cursor
    }

    //直接使用 SQL 来操作数据库
    /*
    添加数据的方法如下：
db.execSQL("insert into Book (name, author, pages, price) values(?, ?, ?, ?)",
new String[] { "The Da Vinci Code", "Dan Brown", "454", "16.96" });
db.execSQL("insert into Book (name, author, pages, price) values(?, ?, ?, ?)",
new String[] { "The Lost Symbol", "Dan Brown", "510", "19.95" });
     */
    /*
    更新数据的方法如下：
db.execSQL("update Book set price = ? where name = ?", new String[] { "10.99",
"The Da Vinci Code" });
     */
    /*
    删除数据的方法如下：
db.execSQL("delete from Book where pages > ?", new String[] { "500" });
查询数据的方法如下：
db.rawQuery("select * from Book", null);
     */
//SQLite 数据库是支持事务的，事务的特性可以保证让某一系列的操作要么全部完成，要么一个都不会完成。
    //----使用事务
    public static void sbeginTransactionData(MySQLite mySQLite) {
        SQLiteDatabase sqLiteDatabase = mySQLite.getWritableDatabase();
        sqLiteDatabase.beginTransaction(); //  开启事务
        try {
            sqLiteDatabase.delete("Book", null, null);
            if (true) {
//  在这里手动抛出一个异常，让事务失败
                throw new NullPointerException();
            }
            ContentValues values = new ContentValues();
            values.put("name", "Game of Thrones");
            values.put("author", "George Martin");
            values.put("pages", 720);
            values.put("price", 20.85);
            sqLiteDatabase.insert("Book", null, values);
            sqLiteDatabase.setTransactionSuccessful(); //  事务已经执行成功
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqLiteDatabase.endTransaction(); //  结束事务
        }
    }
}
