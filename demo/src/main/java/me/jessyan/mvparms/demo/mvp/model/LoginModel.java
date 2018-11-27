package me.jessyan.mvparms.demo.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.jessyan.mvparms.demo.mvp.contract.LoginContract;
import me.jessyan.mvparms.demo.mvp.model.api.service.UserService;
import me.jessyan.mvparms.demo.mvp.model.entity.BaseResponse;
import me.jessyan.mvparms.demo.mvp.model.entity.Token;
import me.jessyan.mvparms.demo.mvp.model.entity.TokenInfo;


@ActivityScope
public class LoginModel extends BaseModel implements LoginContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public LoginModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }


    @Override
    public Observable<BaseResponse<Token>> login(String username, String password) {
        return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .login(username, password);
    }

    @Override
    public Observable<BaseResponse<String>> register(String username, String password, String phone, String code, String zone) {
        return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .registerUser(username, password, phone, code, zone);
    }


    @Override
    public Observable<TokenInfo> obtainToken(String username, String password) {
        return null;
    }


}