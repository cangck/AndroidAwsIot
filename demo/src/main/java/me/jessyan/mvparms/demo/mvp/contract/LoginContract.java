package me.jessyan.mvparms.demo.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import io.reactivex.Observable;
import me.jessyan.mvparms.demo.mvp.model.entity.BaseResponse;
import me.jessyan.mvparms.demo.mvp.model.entity.Token;
import me.jessyan.mvparms.demo.mvp.model.entity.TokenInfo;


public interface LoginContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        /**
         * 显示登录提示框
         */
        void showloading();

        /**
         * 隐藏对话框
         */
        void dimissloading();

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        /**
         * 登录接口
         *
         * @param username
         * @param password
         * @return
         */
        Observable<BaseResponse<Token>> login(String username, String password);

        /**
         * 注册接口
         *
         * @param username
         * @param password
         * @param code
         * @return
         */
        Observable<BaseResponse<String>> register(String username, String password, String phone, String code, String zone);

        Observable<TokenInfo> obtainToken(String username, String password);
    }
}
