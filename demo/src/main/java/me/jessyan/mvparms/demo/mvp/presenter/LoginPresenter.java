package me.jessyan.mvparms.demo.mvp.presenter;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.mvparms.demo.mvp.contract.LoginContract;
import me.jessyan.mvparms.demo.mvp.model.entity.BaseResponse;
import me.jessyan.mvparms.demo.mvp.model.entity.Token;
import me.jessyan.mvparms.demo.mvp.ui.activity.UserActivity;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public LoginPresenter(LoginContract.Model model, LoginContract.View rootView) {
        super(model, rootView);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    /**
     * 注册用户
     *
     * @param username
     * @param passwd
     * @param code
     */
    public void register(String username, String passwd, String code) {

        ArmsUtils.putStringToSp(mApplication, "username", username);
        ArmsUtils.putStringToSp(mApplication, "passwd", passwd);


        mModel.register(username, passwd, "15112286305", code, "86")
                .subscribeOn(Schedulers.io())
                .debounce(2, TimeUnit.SECONDS)
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> {
                    mRootView.showloading();
                }).observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                }).compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<String> response) {
                        Log.i("TAG", "response" + response);
                        if (response.isSuccess()) {
                            // TODO: 2018/11/19 登录成功
                        } else {
                            ArmsUtils.makeText(mApplication, response.getMsg() + "");
                        }
                    }
                });
    }

    /**
     * 登录接口
     *
     * @param username
     * @param password
     */
    public void login(String username, String password) {
        mModel.login(username, password)
                .subscribeOn(Schedulers.io())
                .debounce(2, TimeUnit.SECONDS)
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> {
                    mRootView.showloading();
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<Token>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<Token> tokenBaseResponse) {
                        if (tokenBaseResponse.isSuccess()) {
                            ArmsUtils.makeText(mApplication, tokenBaseResponse.getData().getToken());
                            mRootView.launchActivity(new Intent(mApplication, UserActivity.class));
                            mRootView.killMyself();
                        } else {
                            ArmsUtils.makeText(mApplication, tokenBaseResponse.getMsg());
                        }
                    }
                });
    }
}
