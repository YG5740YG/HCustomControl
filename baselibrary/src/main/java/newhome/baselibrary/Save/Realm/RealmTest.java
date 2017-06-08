//package newhome.myviwe.MyTool.Realm;
//
///**
// * Created by 20160330 on 2017/3/23 0023.
// */
////Realm简介
///**
// * 支持的属性
// boolean, byte, short,int,long,float, double,String, Date 和，byte[], RealmObject, RealmList<? extends RealmObject>
// 还支持Boolean, Byte, Short, Integer, Long, Float 和 Double
// Tip：整数类型 short、int 和 long 都被映射到 Realm 内的相同类型（实际上为 long ）
// @PrimaryKey——表示该字段是主键
// 使用过数据库的同学应该看出来了，PrimaryKey就是主键。使用@PrimaryKey来标注，字段类型必须是字符串（String）或整数（byte，short，int或long）以及它们的包装类型（Byte,Short, Integer, 或 Long）。不可以存在多个主键，使用字符串字段作为主键意味着字段被索引（注释@PrimaryKey隐式地设置注释@Index）。
// @PrimaryKey
// private String id;
// @Required——表示该字段非空
// 在某些情况下，有一些属性是不能为null的。使用@Required可用于用于强行要求其属性不能为空，只能用于Boolean, Byte, Short, Integer, Long, Float, Double, String, byte[] 和 Date。在其它类型属性上使用 @Required修饰会导致编译失败。
// Tip：基本数据类型不需要使用注解 @Required，因为他们本身就不可为空。
// @Required
// private String name;
// @Ignore——表示忽略该字段
// 被添加@Ignore标签后，存储数据时会忽略该字段。
// @Ignore
// private String name;
// @Index——添加搜索索引
// 为字段添加搜索索引，这样会使得插入的速度变慢，数据量也变得更大。不过在查询速度将变得更快，建议只在优化读取性能的特定情况时添加索引。支持索引：String，byte，short，int，long，boolean和Date字段。
// ```
// 注意：如果你创建Model并运行过，然后修改了Model。那么就需要升级数据库，否则会抛异常。升级方式后面会提到
// */
//
//import android.app.Activity;
//import android.app.Application;
//import android.content.Context;
//import android.graphics.Movie;
//import android.util.Log;
//
//import com.squareup.okhttp.internal.Internal;
//
//import java.util.List;
//import java.util.UUID;
//
//import io.realm.DynamicRealm;
//import io.realm.Realm;
//import io.realm.RealmAsyncTask;
//import io.realm.RealmConfiguration;
//import io.realm.RealmMigration;
//import io.realm.RealmResults;
//import io.realm.RealmSchema;
//import io.realm.Sort;
//import newhome.baselibrary.Tools.Logs;
//import rx.Observable;
//import rx.functions.Action1;
//import rx.functions.Func1;
//
///**
// *  * 介绍
//
// Realm 是一个 MVCC （多版本并发控制）数据库，由Y Combinator公司在2014年7月发布一款支持运行在手机、平板和可穿戴设备上的嵌入式数据库，目标是取代SQLite。
// Realm 本质上是一个嵌入式数据库，他并不是基于SQLite所构建的。它拥有自己的数据库存储引擎，可以高效且快速地完成数据库的构建操作。和SQLite不同，它允许你在持久层直接和数据对象工作。在它之上是一个函数式风格的查询api，众多的努力让它比传统的SQLite 操作更快 。
// 详细介绍（如果进不去，看这个也行）
//
// 优势
//
// 如果在在使用它时候，连它的优势在哪都不知道的话就有点说不过去了。
//
// 易用
// Ream 不是在SQLite基础上的ORM，它有自己的数据查询引擎。并且十分容易使用。
// 快速
// 由于它是完全重新开始开发的数据库实现，所以它比任何的ORM速度都快很多，甚至比SLite速度都要快。
// 跨平台
// Realm 支持 iOS & OS X (Objective‑C & Swift) & Android。我们可以在这些平台上共享Realm数据库文件，并且上层逻辑可以不用任何改动的情况下实现移植。
// 高级
// Ream支持加密，格式化查询，易于移植，支持JSON，流式api，数据变更通知等高级特性
// 可视化
// Realm 还提供了一个轻量级的数据库查看工具，在Mac Appstore 可以下载“Realm Browser”这个工具，开发者可以查看数据库当中的内容，执行简单的插入和删除数据的操作。（windows上还不清楚）
// 条件
//
// 目前不支持Android以外的Java
// Android Studio >= 1.5.1
// 较新的Android SDK版本
// JDK version >=7.
// 支持API 9(Android 2.3)以及之后的版本
// */
////Realm从v1.0.0后，不支持Ecilpse，我们推荐使用Android Studio
////使用前需要在全局build.gradle中配置 classpath  "io.realm:realm-gradle-plugin:2.3.0"
////然后在项目模块build.gradle中加入apply plugin: 'realm-android'
//public class RealmTest{
//    Realm realm;
//    RealmAsyncTask transaction;
//    RealmConfiguration config;
//
//    /**
//     * 初始化Realm, 建议在{@link Application#onCreate()}方法中调用
//     * @param appContext
//     * @param dbVersion
//     */
//    public void init(Context appContext, long dbVersion) {
//        config = new RealmConfiguration.Builder()
//                .name("app.realm")
//                .encryptionKey(new byte[64])
//                .schemaVersion(dbVersion)
//                .deleteRealmIfMigrationNeeded()
//                .build();
//        realm = Realm.getInstance(config);
//    }
//    //获取最大的PrimaryKey并加一
//    private String generateNewPrimaryKey() {
//        String primaryKey = "0";
//        //必须排序, 否则last可能不是PrimaryKey最大的数据. findAll()查询出来的数据是乱序的
//        RealmResults<User> results = realm.where(User.class).findAllSorted("id", Sort.ASCENDING);
//        //mRealm.where(Movie.class).findAllSorted("id", Sort.DESCENDING);
//        if(results != null && results.size() > 0) {
//            User last = results.last(); //根据id顺序排序后, last()取得的对象就是PrimaryKey的值最大的数据
//            primaryKey = last.getId() + 1;
//        }
//        return primaryKey;
//    }
//    /**
//     * 退出应用的时候调用
//     */
//    public void destroy() {
//        if (realm != null) {
//            if(!realm.isClosed()) {
//                realm.close();
//            }
//            realm = null;
//        }
//    }
//    //默认配置Realm
//    public void CreateRealm(){
//        Realm.init(this);
//        realm = Realm.getDefaultInstance();//使用默认配置
//        //这时候会创建一个叫做 default.realm的Realm文件，一般来说，这个文件位于/data/data/包名/files/。通过realm.getPath()来获得该Realm的绝对路径。
//        //注意：模拟器上运行时，Realm.getDefaultInstance()抛出异常，真机上没问题（不止何故）
//    }
//    //自定义配置Realm,使用RealmConfiguration来配置Realm
//    public void CreateRealmCustom(){
//        /*
//Builder.name : 指定数据库的名称。如不指定默认名为default。
//Builder.schemaVersion : 指定数据库的版本号。
//Builder.encryptionKey : 指定数据库的密钥。
//Builder.migration : 指定迁移操作的迁移类。
//Builder.deleteRealmIfMigrationNeeded : 声明版本冲突时自动删除原数据库。
//Builder.inMemory : 声明数据库只在内存中持久化。
//build : 完成配置构建。
//         */
//        RealmConfiguration config = new RealmConfiguration.Builder()
//                .name("myrealm.realm") //文件名
//                .schemaVersion(0) //版本号
//                .build();
//        realm = Realm.getInstance(config);
//    }
//    //创建非持久化的Realm，也就是保持在内存中，应用关闭后就清除了。
//    public void CreateRealmNonPersistent(Context context){
//        RealmConfiguration myConfig = new RealmConfiguration.Builder()
//                .name("myrealm.realm")//保存在内存中
//                .inMemory() .build();
//    }
//    //关闭Realm
//    public void CloseRealm(){
//        realm.close();
//    }
//    //给Realm中增加数据,写入操作需要在事务中进行，可以使用executeTransaction方法来开启事务。使用executeTransaction方法插入数据
//    //在execute方法中执行插入操作 注意：如果在UI线程中插入过多的数据，可能会导致主线程拥塞。
//    public void addData(){
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                User user = realm.createObject(User.class);
//                user.setId("0");
//                user.setName("Gavin");
//                user.setAge(23);
//            }
//        });
//    }
//    //更新数据库
//    /*
//    使用copyToRealmOrUpdate或copyToRealm方法插入数据
//    当Model中存在主键的时候，推荐使用copyToRealmOrUpdate方法插入数据。如果对象存在，就更新该对象；反之，它会创建一个新的对象。
//    若该Model没有主键，使用copyToRealm方法，否则将抛出异常。
//     */
//    ////如果RealmObject对象没有primaryKey, 会报错: java.lang.IllegalArgumentException: A RealmObject with no @PrimaryKey cannot be updated: class com.stone.hostproject.db.model.Movie
//    //包含主键 更新
//    public void addDataOne(){
//        final User user = new User();
//        user.setName("Jack");
//        user.setAge(20);
//        user.setId("1");
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                realm.copyToRealmOrUpdate(user);
//            }
//        });
//    }
//    //不包含主键 更新
//    public void addDatatwo(){
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                User1 user = realm.createObject(User1.class);
//                user.name = "Micheal";
//                user.age = 30;
//            }
//        });
//    }
//    //插入数据,executeTransaction方法插入数据
//    public void insertData() {
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                User2 user = realm.createObject(User2.class);
//                user.setName("Gain");
//                user.setAge(23);
//
//                Dog dog1 = realm.createObject(Dog.class);
//                dog1.setId("0");
//                dog1.setAge(1);
//                dog1.setName("二哈");
//                user.getDogs().add(dog1);
//
//                Dog dog2 = realm.createObject(Dog.class);
//                dog1.setId("1");
//                dog2.setAge(2);
//                dog2.setName("阿拉撕家");
//                user.getDogs().add(dog2);
//            }
//        });
//    }
//    //另一种方法可以用于插入数据,在插入前，先调用beginTransaction()，完成后调用commitTransaction()即可。
//    public void insertDataTransaction() {
//        realm.beginTransaction();//开启事务
//        User2 user = realm.createObject(User2.class);
//        user.setName("Gain");
//        user.setAge(23);
//        Dog dog1 = realm.createObject(Dog.class);
//        dog1.setId("0");
//        dog1.setAge(1);
//        dog1.setName("二哈");
//        user.getDogs().add(dog1);
//        Dog dog2 = realm.createObject(Dog.class);
//        dog1.setId("1");
//        dog2.setAge(2);
//        dog2.setName("阿拉斯加");
//        user.getDogs().add(dog2);
//        realm.commitTransaction();//提交事务
//    }
//    //注意：在UI和后台线程同时开启创建write的事务，可能会导致ANR错误。为了避免该问题，可以使用executeTransactionAsync来实现。
//    //使用executeTransactionAsync该方法会开启一个子线程来执行事务，并且在执行完成后进行结果通知。
////    还可以加入监听
//    public void insertDataRealmAsyncTask () {
//        transaction =  realm.executeTransactionAsync(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                User user = realm.createObject(User.class);
//                user.setName("Eric");
//                user.setId( UUID.randomUUID().toString());// UUID.randomUUID().toString();唯一标识符
//            }
//        }, new Realm.Transaction.OnSuccess() {
//            @Override
//            public void onSuccess() {
//                //成功回调
//            }
//        }, new Realm.Transaction.OnError() {
//            @Override
//            public void onError(Throwable error) {
//                //失败回调
//            }
//        });
//    }
//    //注意：如果当Acitivity或Fragment被销毁时，在OnSuccess或OnError中执行UI操作，将导致程序奔溃 。用RealmAsyncTask .cancel();可以取消事务
////    在onStop中调用，避免crash
//    public void stopTransaction(){
//        if (transaction != null && !transaction.isCancelled()) {
//            transaction.cancel();
//        }
//    }
//    //数据查询
//    /**
//     * 条件查询，Realm 支持以下查询条件（来源于官网）：
//     between()、greaterThan()、lessThan()、greaterThanOrEqualTo() 和 lessThanOrEqualTo()
//     equalTo() 和 notEqualTo()
//     contains()、beginsWith() 和 endsWith()
//     isNull() 和 isNotNull()
//     isEmpty() 和 isNotEmpty()
//     */
//    public void selectData(){
//        List<User> users= realm.where(User.class).findAll();
//        //User user=realm.where(User.class).equalTo("id", id).findFirst();
//        for (User user: users) {
//            Logs.Debug("id:" + user.getId() + " name:" + user.getName() + " age:" + user.getAge());
//        }
//    }
//    //eg:查询年龄小于15的User
//    public void selectDatalssthan(){
//        List<User> users= realm.where(User.class).lessThan("age", 15).findAll();
//        for (User user: users) {
//            Logs.Debug("id:" + user.getId() + " name:" + user.getName() + " age:" + user.getAge());
//        }
//    }
//    //聚合查询，支持的聚合操作有sum，min，max，average以下代码片段得到所有人的平均年龄
//    public void selectDataAverageAge(){
//        double age = realm.where(User.class).findAll().average("age");
//        Logs.Debug("average age:" + age);
//    }
//    //更新数据,此列以用户名作为标识
//    public void upData(){
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                User user = realm.where(User.class).equalTo("name", "user9").findFirst();
//                if (user != null) {
//                    user.setAge(99);
//                    user.setName("二逼青年");
//                }
//                Logs.Debug("更新成功");
//            }
//        });
//    }
//    //删除数据
//    public void deleteData(){
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                User user = realm.where(User.class).equalTo("name", "user0").findFirst();
//                if (user != null) {
//                    user.deleteFromRealm();
//                }
//                Logs.Debug("删除成功");
//            }
//        });
//    }
//    //在实际开发中我们和json打交道的机会比较多，所以直接从json去创建对象是十分有用的
//    //直接把json数据存入realm
//    private void addFromJson() {
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                String json = "{\n" +
//                        "    \"id\": \"uuid1\",\n" +
//                        "    \"name\": \"solid\",\n" +
//                        "    \"age\": 20\n" +
//                        "}";
//                String jsons = "[\n" +
//                        "    {\n" +
//                        "        \"id\": \"uuid1\",\n" +
//                        "        \"name\": \"solid\",\n" +
//                        "        \"age\": 20\n" +
//                        "    },\n" +
//                        "    {\n" +
//                        "        \"id\": \"uuid2\",\n" +
//                        "        \"name\": \"jhack\",\n" +
//                        "        \"age\": 21\n" +
//                        "    },\n" +
//                        "    {\n" +
//                        "        \"id\": \"uuid3\",\n" +
//                        "        \"name\": \"tom\",\n" +
//                        "        \"age\": 22\n" +
//                        "    }\n" +
//                        "]";
//                //realm.createObjectFromJson(User.class, json);
//                realm.createAllFromJson(User.class, jsons);
//            }
//        });
//    }
// /*
// 数据模型改变的处理
//
//开发中数据模型不可能从一开始创建了，就保证后面的开发过程中不会更改，对于Realm如果其中的某个实体类改变了，而我们没有做任何的处理，
//就会报错，如果还处于应用的开发的初期，这无所谓，直接清空数据即可，但是如果应用已经发布了，我们就需要去寻找一种解决方案了。
//  */
// public class MyMigration implements RealmMigration {
//     @Override
//     public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
//         Logs.Debug("oldVersion:" + oldVersion + " newVersion:" + newVersion);
//         RealmSchema schema = realm.getSchema();
//         //在版本2中加逻辑代码
//             if (newVersion == 2) {
//                 schema.get("User")
//                         .addField("desc", String.class);
//             }
//         //把版本改为2，新版本
////         RealmConfiguration realmConfig = new RealmConfiguration.Builder()
////                 .schemaVersion(2)
////                 .migration(new MyMigration())
////                 .build();
//
//     }
//
//     @Override
//     public boolean equals(Object obj) {
//         return obj instanceof MyMigration;
//     }
//
//     @Override
//     public int hashCode() {
//         return super.hashCode();
//     }
// }
//    /**
//     * 与RxJava的结合
//     Realm原生是支持Rxjava的，由于RxJava 是可选依赖，所以在使用的时候需要在app的build文件中添加RxJava库的依赖，下面是使用的代码片段。
//     */
//    public void RxJava() {
//        realm.where(User.class).findAll()
//                .asObservable()
//                .flatMap(new Func1<RealmResults<User>, Observable<User>>() {
//                    @Override
//                    public Observable<User> call(RealmResults<User> users) {
//                        return Observable.from(users);
//                    }
//                })
//                .subscribe(new Action1<User>() {
//                    @Override
//                    public void call(User user) {
//                        Logs.Debug("id:" + user.getId() + " name:" + user.getName() + " age:" + user.getAge() + " desc:");
//
//                    }
//                });
//        //遗憾的是Realm在Windows中没有相关的查看器，只有Mac版的，所以不能直接在Windows中查看Realm数据库中的数据，希望官方后面会有支持吧。
//    }
//
//}
//
