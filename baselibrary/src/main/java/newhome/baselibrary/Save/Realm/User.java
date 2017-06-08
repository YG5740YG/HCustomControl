//package newhome.myviwe.MyTool.Realm;
//
//import io.realm.RealmObject;
//import io.realm.annotations.Ignore;
//import io.realm.annotations.PrimaryKey;
//import io.realm.annotations.Required;
//
///**
// * Created by 20160330 on 2017/3/23 0023.
// */
////创建一个User类，需要继承RealmObject。支持public, protected和 private的类以及方法
//    /*
//    除了直接继承于RealmObject来声明 Realm 数据模型之外，还可以通过实现 RealmModel接口并添加 @RealmClass修饰符来声明。
//@RealmClass
//public class User implements RealmModel {
//    ...
//}
//     */
//public class User extends RealmObject {
//    @PrimaryKey
//    private String id;//增加注解，设置为主键
//    @Required
//    private String name;//非空
//    @Required
//    private int age;//非空，基本数据类型不需要使用注解 @Required，因为他们本身就不可为空。
//    @Ignore
//    private int meiyiyi;//不进行存储，被添加@Ignore标签后，存储数据时会忽略该字段。
//
//    public String getId(){
//        return id;
//    }
//    public void setId(String id){
//        this.id=id;
//    }
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public int getAge() {
//        return age;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }
//}
