package cn.avater.roomapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;


import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DbManager {

    @SuppressLint("CheckResult")
    public static void insert(final Context context, final Users users) {
        /*Observable<Boolean> observable = new Observable<Boolean>() {
            @Override
            protected void subscribeActual(Observer<? super Boolean> observer) {
                Log.e("TAG", "subscribeActual，thread =  " + Thread.currentThread().getName());
                UsersDataBase.getInstance(context).getUsersDao().insert(users);
            }
        };
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());*/
        Observable observable = Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter emitter) throws Exception {
                Log.e("TAG", "subscribe,thread = " + Thread.currentThread().getName());
                UsersDataBase.getInstance(context).getUsersDao().insert(users);
                emitter.onComplete();
            }
        });
        Observer observer = new Observer() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e("TAG", "onSubscribe,thread = " + Thread.currentThread().getName());
            }

            @Override
            public void onNext(Object o) {
                Log.e("TAG", "onNext,thread = " + Thread.currentThread().getName());
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG", "onError,thread = " + Thread.currentThread().getName());
            }

            @Override
            public void onComplete() {
                Log.e("TAG", "onComplete,thread = " + Thread.currentThread().getName());
            }
        };
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @SuppressLint("CheckResult")
    public static void getUserById(Context context, int id, final CallBack callBack) {
        UsersDataBase.getInstance(context).getUsersDao().getUserById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Users>>() {
                    @Override
                    public void accept(List<Users> users) throws Exception {
                        Log.e("TAG", "accept->users,thread = " + Thread.currentThread().getName());
                        if (users != null && users.size() > 0) {
                            Log.e("TAG", "users.size = " + users.size());
                            callBack.onSuccess();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("TAG", "accept->Throwable,thread = " + Thread.currentThread().getName());
                        callBack.onFailed();
                    }
                });
    }


    @SuppressLint("CheckResult")
    public static void getAllUsers(Context context) {
        Flowable<List<Users>> flowable = UsersDataBase.getInstance(context).getUsersDao().getUsers();
        flowable.observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Users>>() {
                    @Override
                    public void accept(List<Users> users) throws Exception {
                        Log.e("TAG", "查询成功,thread = " + Thread.currentThread().getName());
                        if (users != null && users.size() > 0) {
                            Log.e("TAG", "users.size = " + users.size());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("TAG", "查询失败,thread = " + Thread.currentThread().getName());
                    }
                });
    }

}
