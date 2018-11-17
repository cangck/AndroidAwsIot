package me.jessyan.mvparms.demo.mvp.presenter;

import android.app.Application;
import android.util.Log;

import com.google.gson.Gson;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.mvparms.demo.app.HttpStatus;
import me.jessyan.mvparms.demo.mvp.model.entity.Token;
import me.jessyan.mvparms.demo.mvp.model.entity.TokenInfo;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import me.jessyan.mvparms.demo.mvp.contract.LoginContract;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import okhttp3.MediaType;
import okhttp3.RequestBody;


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
     * @param username 用户名
     * @param passwd   密码
     */
    public void register(String username, String passwd) {
        ArmsUtils.putStringToSp(mApplication, "username", username);
        ArmsUtils.putStringToSp(mApplication, "passwd", passwd);

        mModel.registerUser(username, passwd)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(disposable -> {
                    //开始定于时执行
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    //执行完成之后调用
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<Token>(mErrorHandler) {
                    @Override
                    public void onNext(Token token) {
                        if (token != null && token.getCode() != HttpStatus.HTTP_400_BAD_REQUEST) {
                            mModel.obtainToken(
                                    ArmsUtils.getStringToSp(mApplication, "username"),
                                    ArmsUtils.getStringToSp(mApplication, "passwd")
                            ).subscribe(new ErrorHandleSubscriber<TokenInfo>(mErrorHandler) {
                                @Override
                                public void onNext(TokenInfo tokenInfo) {

                                }
                            });
                        }

                    }
                });
    }
}
