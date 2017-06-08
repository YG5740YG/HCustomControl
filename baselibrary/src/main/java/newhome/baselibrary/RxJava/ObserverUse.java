package newhome.baselibrary.RxJava;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import newhome.baselibrary.R;
import newhome.baselibrary.Routers.Routers;
import newhome.baselibrary.Tools.Logs;
import newhome.baselibrary.Bus.RoutersAction;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func1;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by 20160330 on 2017/3/24 0024.
 */
//http://gank.io/post/560e15be2dca930e00da1083              没看完，看不动了
public class ObserverUse{
    //创建 Observer
//    Observer 即观察者，它决定事件触发的时候将有怎样的行为。 RxJava 中的 Observer 接口的实现方式：
    /**
     * RxJava 有四个基本概念：Observable (可观察者，即被观察者)、 Observer (观察者)、 subscribe (订阅)、事件。Observable 和
     * Observer 通过 subscribe() 方法实现订阅关系，从而 Observable 可以在需要的时候发出事件来通知 Observer。
     与传统观察者模式不同， RxJava 的事件回调方法除了普通事件 onNext() （相当于 onClick() / onEvent()）之外，还定义了两个特殊的事件：
     onCompleted() 和 onError()。
     onCompleted(): 事件队列完结。RxJava 不仅把每个事件单独处理，还会把它们看做一个队列。RxJava 规定，当不会再有新的 onNext() 发出时，
     需要触发 onCompleted() 方法作为标志。
     onError(): 事件队列异常。在事件处理过程中出异常时，onError() 会被触发，同时队列自动终止，不允许再有事件发出。
     在一个正确运行的事件序列中, onCompleted() 和 onError() 有且只有一个，并且是事件序列中的最后一个。需要注意的是，onCompleted() 和
     onError() 二者也是互斥的，即在队列中调用了其中一个，就不应该再调用另一个。
     */
    private Observer<String> observer = new Observer<String>() {
        @Override
        public void onNext(String s) {
            Logs.Debug("onNext=="+s);
        }
        @Override
        public void onCompleted() {
            Logs.Debug("Completed");
        }
        @Override
        public void onError(Throwable e) {
            Logs.Debug("Error");
        }
    };
    //    除了 Observer 接口之外，RxJava 还内置了一个实现了 Observer 的抽象类：Subscriber。 Subscriber 对 Observer 接口进行了一些扩展，
// 但他们的基本使用方式是完全一样的：
    private  Subscriber<String> subscriber = new Subscriber<String>() {
        @Override
        public void onNext(String s) {
            Logs.Debug("onNext=="+s);
        }

        @Override
        public void onCompleted() {
            Logs.Debug("onCompleted==");
        }

        @Override
        public void onError(Throwable e) {
            Logs.Debug("onError==");
        }
    };
    /*
    在 RxJava 的 subscribe 过程中，Observer 也总是会先被转换成一个 Subscriber 再使用。所以如果你只想使用基本功能，
    选择 Observer 和 Subscriber 是完全一样的。它们的区别对于使用者来说主要有两点：
    onStart(): 这是 Subscriber 增加的方法。它会在 subscribe 刚开始，而事件还未发送之前被调用，可以用于做一些准备工作，例如数据的清零或重置。
    这是一个可选方法，默认情况下它的实现为空。需要注意的是，如果对准备工作的线程有要求（例如弹出一个显示进度的对话框，这必须在主线程执行），
    onStart() 就不适用了，因为它总是在 subscribe 所发生的线程被调用，而不能指定线程。要在指定的线程来做准备工作，可以使用 doOnSubscribe() 方法，
    具体可以在后面的文中看到。
    unsubscribe(): 这是 Subscriber 所实现的另一个接口 Subscription 的方法，用于取消订阅。在这个方法被调用后，Subscriber 将不再接收事件。
    一般在这个方法调用前，可以使用 isUnsubscribed() 先判断一下状态。 unsubscribe() 这个方法很重要，因为在 subscribe() 之后， Observable
    会持有 Subscriber 的引用，这个引用如果不能及时被释放，将有内存泄露的风险。所以最好保持一个原则：要在不再使用的时候尽快在合适的地方
    （例如 onPause() onStop() 等方法中）调用 unsubscribe() 来解除引用关系，以避免内存泄露的发生。
    2) 创建 Observable
    Observable 即被观察者，它决定什么时候触发事件以及触发怎样的事件。 RxJava 使用 create() 方法来创建一个 Observable ，并为它定义事件触发规则：
     */
/*
可以看到，这里传入了一个 OnSubscribe 对象作为参数。OnSubscribe 会被存储在返回的 Observable 对象中，它的作用相当于一个计划表，
当 Observable 被订阅的时候，OnSubscribe 的 call() 方法会自动被调用，事件序列就会依照设定依次触发（对于上面的代码，
就是观察者Subscriber 将会被调用三次 onNext() 和一次 onCompleted()）。这样，由被观察者调用了观察者的回调方法，
就实现了由被观察者向观察者的事件传递，即观察者模式。
 */
    private Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
        @Override
        public void call(Subscriber<? super String> subscriber) {
            subscriber.onNext("Hello");
            subscriber.onNext("Hi");
            subscriber.onNext("Aloha");
            subscriber.onCompleted();
        }
    });
    //    create() 方法是 RxJava 最基本的创造事件序列的方法。基于这个方法， RxJava 还提供了一些方法用来快捷创建事件队列
//    just(T...): 将传入的参数依次发送出来。
    Observable observable1 = Observable.just("Hello", "Hi", "Aloha");
    // 将会依次调用：
// onNext("Hello");
// onNext("Hi");
// onNext("Aloha");
// onCompleted();
//    from(T[]) / from(Iterable<? extends T>) : 将传入的数组或 Iterable 拆分成具体对象后，依次发送出来\
    String[] words = {"Hello", "Hi", "Aloha"};
    Observable observable2 = Observable.from(words);
// 将会依次调用：
// onNext("Hello");
// onNext("Hi");
// onNext("Aloha");
// onCompleted();
    /**********************上面 just(T...) 的例子和 from(T[]) 的例子，都和之前的 create(OnSubscribe) 的例子是等价的。**************/
    //3) Subscribe (订阅)
//    创建了 Observable 和 Observer 之后，再用 subscribe() 方法将它们联结起来，整条链子就可以工作了。代码形式很简单
//    observable.subscribe(observer);
// 或者：
//observable.subscribe(subscriber);Observable.subscribe(Subscriber) 的内部实现是这样的（仅核心代码）：
    // 注意：这不是 subscribe() 的源码，而是将源码中与性能、兼容性、扩展性有关的代码剔除后的核心代码。
// 如果需要看源码，可以去 RxJava 的 GitHub 仓库下载。
//    public Subscription subscribe(Subscriber subscriber) {
//        subscriber.onStart();
//        onSubscribe.call(subscriber);
//        return subscriber;
//    }
/*
可以看到，subscriber() 做了3件事：
调用 Subscriber.onStart() 。这个方法在前面已经介绍过，是一个可选的准备方法。
调用 Observable 中的 OnSubscribe.call(Subscriber) 。在这里，事件发送的逻辑开始运行。从这也可以看出，在 RxJava 中， Observable 并不是在创建的时候就立即开始发送事件，而是在它被订阅的时候，即当 subscribe() 方法执行的时候。
将传入的 Subscriber 作为 Subscription 返回。这是为了方便 unsubscribe().
 */
    /**
     * 除了 subscribe(Observer) 和 subscribe(Subscriber) ，subscribe() 还支持不完整定义的回调，RxJava 会自动根据定义创建出 Subscriber 。形式如下：

     Action1<String> onNextAction = new Action1<String>() {
     // onNext()
     @Override
     public void call(String s) {
     Log.d(tag, s);
     }
     };
     Action1<Throwable> onErrorAction = new Action1<Throwable>() {
     // onError()
     @Override
     public void call(Throwable throwable) {
     // Error handling
     }
     };
     Action0 onCompletedAction = new Action0() {
     // onCompleted()
     @Override
     public void call() {
     Log.d(tag, "completed");
     }
     };

     // 自动创建 Subscriber ，并使用 onNextAction 来定义 onNext()
     observable.subscribe(onNextAction);
     // 自动创建 Subscriber ，并使用 onNextAction 和 onErrorAction 来定义 onNext() 和 onError()
     observable.subscribe(onNextAction, onErrorAction);
     // 自动创建 Subscriber ，并使用 onNextAction、 onErrorAction 和 onCompletedAction 来定义 onNext()、 onError() 和 onCompleted()
     observable.subscribe(onNextAction, onErrorAction, onCompletedAction);
     简单解释一下这段代码中出现的 Action1 和 Action0。 Action0 是 RxJava 的一个接口，它只有一个方法 call()，这个方法是无参无返回值的；由于 onCompleted() 方法也是无参无返回值的，因此 Action0 可以被当成一个包装对象，将 onCompleted() 的内容打包起来将自己作为一个参数传入 subscribe() 以实现不完整定义的回调。这样其实也可以看做将 onCompleted() 方法作为参数传进了 subscribe()，相当于其他某些语言中的『闭包』。 Action1 也是一个接口，它同样只有一个方法 call(T param)，这个方法也无返回值，但有一个参数；与 Action0 同理，由于 onNext(T obj) 和 onError(Throwable error) 也是单参数无返回值的，因此 Action1 可以将 onNext(obj) 和 onError(error) 打包起来传入 subscribe() 以实现不完整定义的回调。事实上，虽然 Action0 和 Action1 在 API 中使用最广泛，但 RxJava 是提供了多个 ActionX 形式的接口 (例如 Action2, Action3) 的，它们可以被用以包装不同的无返回值的方法。

     注：正如前面所提到的，Observer 和 Subscriber 具有相同的角色，而且 Observer 在 subscribe() 过程中最终会被转换成 Subscriber 对象，因此，从这里开始，后面的描述我将用 Subscriber 来代替 Observer ，这样更加严谨。
     */
//实例
//    a. 打印字符串数组,有问题
//    public void observableTest() {
//        String[] names = {"liming","wangxuan","chenjun"};
//        Observable observable3 = Observable.from(names)
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//
//                    }
//                })
//    }
//b. 由 id 取得图片并显示由指定的一个 drawable 文件 id drawableRes 取得图片，并显示在 ImageView 中，并在出现异常的时候打印 Toast 报错：
    int drawableRes = 0;
    String[] names = {"liming","wangxuan","chenjun"};
    public void observerTest1(final Context context){
        final ImageView imageView =new ImageView(context);
        //使用一
        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                subscriber.onNext("nihao");
                subscriber.onNext("wohao");
                subscriber.onNext("dajiahao");
                subscriber.onCompleted();
            }
        }).subscribe(new Observer<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(Object o) {
                Logs.Debug("ggg=============message=="+o.toString());
            }
        });
        //使用二  from  just  yongbuliao
        Observable observable2 = Observable.from(names);
        Subscriber<String> mySubscriber = new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {

                    }
                    @Override
                    public void onNext(String s) {

                    }
                };
                observable2.subscribe(mySubscriber);//ding yue
//        Observable.create(new Observable.OnSubscribe<Drawable>() {
//            @Override
//            public void call(Subscriber<? super Drawable> subscriber) {
//                Drawable drawable =context.getTheme().getDrawable(0);//需要api21以上
//                subscriber.onNext(drawable);
//                subscriber.onCompleted();
//            }
//        }).subscribe(new Observer<Drawable>() {
//            @Override
//            public void onNext(Drawable drawable) {
//                imageView.setImageDrawable(drawable);
//            }
//            @Override
//            public void onCompleted() {
//            }
//            @Override
//            public void onError(Throwable e) {
//                Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
//            }
//        });
    //在 RxJava 的默认规则中，事件的发出和消费都是在同一个线程的。也就是说，如果只用上面的方法，实现出来的只是一个同步的观察者模式。观察者模式本身的目的就是『后台处理，前台回调』的异步机制，因此异步对于 RxJava 是至关重要的。而要实现异步，则需要用到 RxJava 的另一个概念： Scheduler 。
// 线程控制 —— Scheduler (一)
//    在不指定线程的情况下， RxJava 遵循的是线程不变的原则，即：在哪个线程调用 subscribe()，就在哪个线程生产事件；在哪个线程生产事件，就在哪个线程消费事件。如果需要切换线程，就需要用到 Scheduler （调度器）。
   Observable observable3= Observable.just(1, 2, 3, 4)
            .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
            .observeOn(AndroidSchedulers.mainThread()); // 指定 Subscriber 的回调发生在主线程
    Subscriber<String> mySubscriber1 = new Subscriber<String>() {
//            .subscribe(new Observer<Object>() {
                           @Override
                           public void onCompleted() {

                           }
                           @Override
                           public void onError(Throwable e) {
                               Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
                           }
                           @Override
                           public void onNext(String o) {
                              imageView.setImageResource(R.mipmap.add_gray_bg);
                           }
                       };
        observable3.subscribe(mySubscriber1);
                    /**
                     * 1) Scheduler 的 API (一)
                     在RxJava 中，Scheduler ——调度器，相当于线程控制器，RxJava 通过它来指定每一段代码应该运行在什么样的线程。RxJava
                     已经内置了几个 Scheduler ，它们已经适合大多数的使用场景：
                     Schedulers.immediate(): 直接在当前线程运行，相当于不指定线程。这是默认的 Scheduler。
                     Schedulers.newThread(): 总是启用新线程，并在新线程执行操作。
                     Schedulers.io(): I/O 操作（读写文件、读写数据库、网络信息交互等）所使用的 Scheduler。行为模式和 newThread() 差不多，
                     区别在于 io() 的内部实现是是用一个无数量上限的线程池，可以重用空闲的线程，因此多数情况下 io() 比 newThread() 更有效率。不要把计算工作放在 io() 中，可以避免创建不必要的线程。
                     Schedulers.computation(): 计算所使用的 Scheduler。这个计算指的是 CPU 密集型计算，即不会被 I/O 等操作限制性能的操作，
                     例如图形的计算。这个 Scheduler 使用的固定的线程池，大小为 CPU 核数。不要把 I/O 操作放在 computation() 中，否则 I/O
                     操作的等待时间会浪费 CPU。
                     另外， Android 还有一个专用的 AndroidSchedulers.mainThread()，它指定的操作将在 Android 主线程运行。
                     有了这几个 Scheduler ，就可以使用 subscribeOn() 和 observeOn() 两个方法来对线程进行控制了。 * subscribeOn():
                     指定 subscribe() 所发生的线程，即 Observable.OnSubscribe 被激活时所处的线程。或者叫做事件产生的线程。
                     * observeOn(): 指定 Subscriber 所运行在的线程。或者叫做事件消费的线程。
                     */
        Observable.just(1, 2, 3, 4)
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer number) {
                        Logs.Debug("number:" + number);
                    }
                });
        /*
        由于 subscribeOn(Schedulers.io()) 的指定，被创建的事件的内容 1、2、3、4 将会在 IO 线程发出；而由于 observeOn
        (AndroidScheculers.mainThread()) 的指定，因此 subscriber 数字的打印将发生在主线程 。事实上，这种在 subscribe() 之前写上两句
        subscribeOn(Scheduler.io()) 和 observeOn(AndroidSchedulers.mainThread()) 的使用方式非常常见，它适用于多数的
        『后台线程取数据，主线程显示』的程序策略
         */
        //由图片 id 取得图片并显示的例子
        int drawableRes = R.mipmap.back;
        ImageView imageView1 = new ImageView(context);
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
//                Drawable drawable = context.getTheme().getDrawable(drawableRes);//Api21以上可以用
//                subscriber.onNext(drawable);
//                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Observer<Drawable>() {
                    @Override
                    public void onNext(Drawable drawable) {
                        imageView.setImageDrawable(drawable);
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
                    }
                });
        //加载图片将会发生在 IO 线程，而设置图片则被设定在了主线程。这就意味着，即使加载图片耗费了几十甚至几百毫秒的时间
        // ，也不会造成丝毫界面的卡顿
    }
    /*
    4. 变换

终于要到牛逼的地方了，不管你激动不激动，反正我是激动了。

RxJava 提供了对事件序列进行变换的支持，这是它的核心功能之一，也是大多数人说『RxJava 真是太好用了』的最大原因。所谓变换，
就是将事件序列中的对象或整个序列进行加工处理，转换成不同的事件或事件序列。概念说着总是模糊难懂的，来看 API。
     */
    public void changeTest(Context context){
        //1) API        首先看一个 map() 的例子：
        /*
        这里出现了一个叫做 Func1 的类。它和 Action1 非常相似，也是 RxJava 的一个接口，用于包装含有一个参数的方法。 Func1 和 Action 的区别在于， Func1 包装的是有返回值的方法。另外，和 ActionX 一样，
        FuncX 也有多个，用于不同参数个数的方法。FuncX 和 ActionX 的区别在 FuncX 包装的是有返回值的方法。
可以看到，map() 方法将参数中的 String 对象转换成一个 Bitmap 对象后返回，而在经过 map() 方法后，事件的参数类型也由 String 转为了 Bitmap。
这种直接变换对象并返回的，是最常见的也最容易理解的变换。不过 RxJava 的变换远不止这样，它不仅可以针对事件对象，还可以针对整个事件队列，
这使得 RxJava 变得非常灵活。我列举几个常用的变换：
map(): 事件对象的直接变换，具体功能上面已经介绍过。它是 RxJava 最常用的变换
         */
        Observable.just("images/logo.png") // 输入类型 String
                .map(new Func1<String, Bitmap>() {
                    @Override
                    public Bitmap call(String filePath) { // 参数类型 String
//                        return getBitmapFromPath(filePath); // 返回类型 Bitmap,转换方法自己写
                        return null;
                    }
                })
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) { // 参数类型 Bitmap
//                        showBitmap(bitmap);
                    }
                });
        /*
        flatMap(): 这是一个很有用但非常难理解的变换，因此我决定花多些篇幅来介绍它。 首先假设这么一种需求：假设有一个数据结构『学生』，
        现在需要打印出一组学生的名字。实现方式很简单：
         */
        Student[] students = {new Student("小明",21),new Student("小红",22)};
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable e) {

            }
            @Override
            public void onNext(String name) {
               Logs.Debug("g=g=========");
            }
        };
        Observable.from(students)
                .map(new Func1<Student, String>() {
                    @Override
                    public String call(Student student) {
                        return student.getName();
                    }
                })
                .subscribe(subscriber);
        //如果要打印出每个学生所需要修的所有课程的名称呢？（需求的区别在于，每个学生只有一个名字，但却有多个课程。
        Course course1=new Course("语文");
        Course course2=new Course("英语");
        Course course3=new Course("数学");
        List<Course>courses=new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        Student[] students1 = {new Student("小明",21,courses),new Student("小红",22,courses)};
        Subscriber<Student> subscriber1 = new Subscriber<Student>() {
            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable e) {

            }
            @Override
            public void onNext(Student student) {
                List<Course> courses = student.getCourses();
                for (int i = 0; i < courses.size(); i++) {
                    Course course = courses.get(i);
                  Logs.Debug("gg============"+course.getCName());
                }
            }
        };
        Observable.from(students)
                .subscribe(subscriber1);
        //那么如果我不想在 Subscriber 中使用 for 循环，而是希望 Subscriber 中直接传入单个的 Course 对象呢（这对于代码复用很重要）？
        // 用 map() 显然是不行的，因为 map() 是一对一的转化，而我现在的要求是一对多的转化。那怎么才能把一个 Student 转化成多个 Course 呢？
//        这个时候，就需要用 flatMap()
        Subscriber<Course> subscriber3 = new Subscriber<Course>() {
            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable e) {

            }
            @Override
            public void onNext(Course course) {
               Logs.Debug("gg=========="+course.getCName());
            }
        };
        Observable.from(students)
                .flatMap(new Func1<Student, Observable<Course>>() {
                    @Override
                    public Observable<Course> call(Student student) {
                        return Observable.from(student.getCourses());
                    }
                })
                .subscribe(subscriber3);
        //从上面的代码可以看出， flatMap() 和 map() 有一个相同点：它也是把传入的参数转化之后返回另一个对象。但需要注意，和 map() 不同的是，
        // flatMap() 中返回的是个 Observable 对象，并且这个 Observable 对象并不是被直接发送到了 Subscriber 的回调方法中。 flatMap() 的原理
        // 是这样的：1. 使用传入的事件对象创建一个 Observable 对象；2. 并不发送这个 Observable, 而是将它激活，于是它开始发送事件；
        // 3. 每一个创建出来的 Observable 发送的事件，都被汇入同一个 Observable ，而这个 Observable 负责将这些事件统一交给 Subscriber 的回
        // 调方法。这三个步骤，把事件拆成了两级，通过一组新创建的 Observable 将初始的对象『铺平』之后通过统一路径分发了下去。而这个『铺平』
        // 就是 flatMap() 所谓的 flat。

    }
    //测试，学生类
    class Student{
        public String name;
        public int age;
       public List<Course> courses;
        public Student(String name,int age){
            this.name=name;
            this.age=age;
        }
        public Student(String name,int age,List<Course>courses){
            this.name=name;
            this.age=age;
            this.courses=courses;
        }
        public List<Course> getCourses(){
            return this.courses;
        }
        public String getName(){
            return this.name;
        }
    }
    class Course{
        public String CName;
        public Course(String CName){
            this.CName=CName;
        }
        public String getCName(){
            return this.CName;
        }
    }
    //2) 变换的原理：lift()
    //这些变换虽然功能各有不同，但实质上都是针对事件序列的处理和再发送。而在 RxJava 的内部，它们是基于同一个基础的变换方法： lift(Operator)。首先看一下 lift() 的内部实现（仅核心代码）：
    // 注意：这不是 lift() 的源码，而是将源码中与性能、兼容性、扩展性有关的代码剔除后的核心代码。
// 如果需要看源码，可以去 RxJava 的 GitHub 仓库下载。
//    public <R> Observable<R> lift(Operator<? extends R, ? super T> operator) {
//        return Observable.create(new OnSubscribe<R>() {
//            @Override
//            public void call(Subscriber subscriber) {
//                Subscriber newSubscriber = operator.call(subscriber);
//                newSubscriber.onStart();
//                onSubscribe.call(newSubscriber);
//            }
//        });
//    }

    /**
     * 这段代码很有意思：它生成了一个新的 Observable 并返回，而且创建新 Observable 所用的参数 OnSubscribe 的回调方法 call() 中的实现竟然看起来和前面讲过的 Observable.subscribe() 一样！然而它们并不一样哟~不一样的地方关键就在于第二行 onSubscribe.call(subscriber) 中的 onSubscribe 所指代的对象不同（高能预警：接下来的几句话可能会导致身体的严重不适）——
     subscribe() 中这句话的 onSubscribe 指的是 Observable 中的 onSubscribe 对象，这个没有问题，但是 lift() 之后的情况就复杂了点。
     当含有 lift() 时：
     1.lift() 创建了一个 Observable 后，加上之前的原始 Observable，已经有两个 Observable 了；
     2.而同样地，新 Observable 里的新 OnSubscribe 加上之前的原始 Observable 中的原始 OnSubscribe，也就有了两个 OnSubscribe；
     3.当用户调用经过 lift() 后的 Observable 的 subscribe() 的时候，使用的是 lift() 所返回的新的 Observable ，于是它所触发的 onSubscribe.call(subscriber)，也是用的新 Observable 中的新 OnSubscribe，即在 lift() 中生成的那个 OnSubscribe；
     4.而这个新 OnSubscribe 的 call() 方法中的 onSubscribe ，就是指的原始 Observable 中的原始 OnSubscribe ，在这个 call() 方法里，新 OnSubscribe 利用 operator.call(subscriber) 生成了一个新的 Subscriber（Operator 就是在这里，通过自己的 call() 方法将新 Subscriber 和原始 Subscriber 进行关联，并插入自己的『变换』代码以实现变换），然后利用这个新 Subscriber 向原始 Observable 进行订阅。
     这样就实现了 lift() 过程，有点像一种代理机制，通过事件拦截和处理实现事件序列的变换。
     精简掉细节的话，也可以这么说：在 Observable 执行了 lift(Operator) 方法之后，会返回一个新的 Observable，这个新的 Observable 会像一个代理一样，负责接收原始的 Observable 发出的事件，并在处理后发送给 Subscriber。
     */
    //举一个具体的 Operator 的实现。下面这是一个将事件中的 Integer 对象转换成 String 的例子
    public void ob_lift() {
        observable.lift(new Observable.Operator<String, Integer>() {
            @Override
            public Subscriber<? super Integer> call(final Subscriber<? super String> subscriber) {
                // 将事件序列中的 Integer 对象转换为 String 对象
                return new Subscriber<Integer>() {
                    @Override
                    public void onNext(Integer integer) {
                        subscriber.onNext("" + integer);
                    }

                    @Override
                    public void onCompleted() {
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        subscriber.onError(e);
                    }
                };
            }
        });
        //讲述 lift() 的原理只是为了让你更好地了解 RxJava ，从而可以更好地使用它。然而不管你是否理解了 lift() 的原理，RxJava 都不建议开发者自定义 Operator 来直接使用 lift()，而是建议尽量使用已有的 lift() 包装方法（如 map() flatMap() 等）进行组合来实现需求，因为直接使用 lift() 非常容易发生一些难以发现的错误。
        /**
         * 1) Scheduler 的 API (二)
         前面讲到了，可以利用 subscribeOn() 结合 observeOn() 来实现线程控制，让事件的产生和消费发生在不同的线程。可是在了解了 map() flatMap() 等变换方法后，有些好事的（其实就是当初刚接触 RxJava 时的我）就问了：能不能多切换几次线程？
         答案是：能。因为 observeOn() 指定的是 Subscriber 的线程，而这个 Subscriber 并不是（严格说应该为『不一定是』，但这里不妨理解为『不是』）subscribe() 参数中的 Subscriber ，而是 observeOn() 执行时的当前 Observable 所对应的 Subscriber ，即它的直接下级 Subscriber 。换句话说，observeOn() 指定的是它之后的操作所在的线程。因此如果有多次切换线程的需求，只要在每个想要切换线程的位置调用一次 observeOn() 即可。上代码：
         */
//        Observable.just(1, 2, 3, 4) // IO 线程，由 subscribeOn() 指定
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.newThread())
//                .map(mapOperator) // 新线程，由 observeOn() 指定
//                .observeOn(Schedulers.io())
//                .map(mapOperator2) // IO 线程，由 observeOn() 指定
//                .observeOn(AndroidSchedulers.mainThread)
//                .subscribe(subscriber);  // Android 主线程，由 observeOn() 指定
        //如上，通过 observeOn() 的多次调用，程序实现了线程的多次切换。
//        不过，不同于 observeOn() ， subscribeOn() 的位置放在哪里都可以，但它是只能调用一次的
        //其实， subscribeOn() 和 observeOn() 的内部实现，也是用的 lift()
        //subscribeOn() 和 observeOn() 都做了线程切换的工作（图中的 "schedule..." 部位）。不同的是， subscribeOn() 的线程切换发生在 OnSubscribe 中，即在它通知上一级 OnSubscribe 时，这时事件还没有开始发送，因此 subscribeOn() 的线程控制可以从事件发出的开端就造成影响；而 observeOn() 的线程切换则发生在它内建的 Subscriber 中，即发生在它即将给下一级 Subscriber 发送事件时，因此 observeOn() 控制的是它后面的线程。
        /*
        3) 延伸：doOnSubscribe()
然而，虽然超过一个的 subscribeOn() 对事件处理的流程没有影响，但在流程之前却是可以利用的。
在前面讲 Subscriber 的时候，提到过 Subscriber 的 onStart() 可以用作流程开始前的初始化。然而 onStart() 由于在 subscribe() 发生时就被调用了，因此不能指定线程，而是只能执行在 subscribe() 被调用时的线程。这就导致如果 onStart() 中含有对线程有要求的代码（例如在界面上显示一个 ProgressBar，这必须在主线程执行），将会有线程非法的风险，因为有时你无法预测 subscribe() 将会在什么线程执行。
而与 Subscriber.onStart() 相对应的，有一个方法 Observable.doOnSubscribe() 。它和 Subscriber.onStart() 同样是在 subscribe() 调用后而且在事件发送前执行，但区别在于它可以指定线程。默认情况下， doOnSubscribe() 执行在 subscribe() 发生的线程；而如果在 doOnSubscribe() 之后有 subscribeOn() 的话，它将执行在离它最近的 subscribeOn() 所指定的线程。
         */
//        Observable.create(onSubscribe)
//                .subscribeOn(Schedulers.io())
//                .doOnSubscribe(new Action0() {
//                    @Override
//                    public void call() {
//                        progressBar.setVisibility(View.VISIBLE); // 需要在主线程执行
//                    }
//                })
//                .subscribeOn(AndroidSchedulers.mainThread()) // 指定主线程
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber);
//        在 doOnSubscribe()的后面跟一个 subscribeOn() ，就能指定准备工作的线程了。

    }

    //判断登录应用
    /**
     * 检测是否登录
     *
     * @return
     */
    public Observable<Boolean> checkLogin() {
        // 跳转至登录页面
//        final RealmResults<BaseData> results1 =
//                realm.where(BaseData.class)
//                        .findAll();
        final List<String> list=new ArrayList<>();
        return Observable.just(1,2,3).map(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                if (TextUtils.isEmpty(list.get(integer)))
                    return doLogin(0);
                return doLogin(integer);
            }
        });
    }
    Context context;
    private Boolean doLogin(Integer integer) {
        if (integer == 0) {
            Routers.open(context, RoutersAction.ACOUNT_LOGIN);
            return false;
        }
        return true;
    }
    //test
    private void test(){
        checkLogin()
                .subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                //回调方法
            }
        });
    }
}
