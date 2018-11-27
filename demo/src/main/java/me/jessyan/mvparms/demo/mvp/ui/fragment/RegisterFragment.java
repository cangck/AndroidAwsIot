package me.jessyan.mvparms.demo.mvp.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.mvp.contract.LoginContract;
import me.jessyan.mvparms.demo.mvp.presenter.LoginPresenter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class RegisterFragment extends BaseFragment<LoginPresenter> implements LoginContract.View {


    Unbinder unbinder;

    @BindView(R.id.login_username)
    EditText loginUsername;
    @BindView(R.id.login_passwd)
    EditText loginPasswd;
    @BindView(R.id.login_code)
    EditText loginCode;
    @BindView(R.id.register)
    Button register;
    @BindView(R.id.register_checkbox)
    CheckBox registerCheckbox;
    @BindView(R.id.rl_register)
    RelativeLayout rlRegister;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.register_count)
    TextView registerCount;
    @BindView(R.id.forget_account)
    TextView forgetAccount;
    @BindView(R.id.rl_login)
    RelativeLayout rlLogin;
    @BindView(R.id.weibao)
    ImageView weibao;
    @BindView(R.id.weixin)
    ImageView weixin;
    @BindView(R.id.third_login)
    RelativeLayout thirdLogin;
    @BindView(R.id.login_container)
    LinearLayout loginContainer;
    @BindView(R.id.tv_getcode)
    TextView tvGetcode;
    @BindView(R.id.ll_code)
    LinearLayout llCode;


    public RegisterFragment() {
    }


    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        rlRegister.setVisibility(View.VISIBLE);
        // 注册一个事件回调，用于处理SMSSDK接口请求的结果
        SMSSDK.registerEventHandler(eventHandler);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.i("TAG", "setUserVisibleHint: " + isVisibleToUser);
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }

    @Override
    public void showloading() {

    }

    @Override
    public void dimissloading() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        rlRegister.setVisibility(View.VISIBLE);
        registerCheckbox.setOnCheckedChangeListener((compoundButton, status) -> {
            //处理checkbox状态
            if (status) {
                register.setEnabled(true);
            } else {
                register.setEnabled(false);
            }
        });
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SMSSDK.registerEventHandler(eventHandler);
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @OnClick({
            R.id.tv_getcode,
            R.id.register
    })
    public void onClick(View view) {
        // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
        String phone = getUserName(loginUsername);
        String password = getUserPassword(loginPasswd);
        switch (view.getId()) {
            case R.id.tv_getcode:
                // TODO: 2018/11/19 验证电话号码
                SMSSDK.getVerificationCode("86", phone);
                countDownTimer.start();
                break;
            case R.id.register_checkbox:
                break;
            case R.id.register:
                String code = loginCode.getText().toString();
                SMSSDK.submitVerificationCode("86", phone, code);
                break;
        }
    }

    /**
     * 获取验证码倒计时
     */
    CountDownTimer countDownTimer = new CountDownTimer(60 * 1000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            tvGetcode.setEnabled(false);
            tvGetcode.setText("(" + millisUntilFinished / 1000 + ")");
        }

        @Override
        public void onFinish() {
            tvGetcode.setEnabled(true);
            tvGetcode.setText("获取验证码");
        }
    };

    /**
     * 短信验证码的处理逻辑
     */
    EventHandler eventHandler = new EventHandler() {
        public void afterEvent(int event, int result, Object data) {
            // afterEvent会在子线程被调用，因此如果后续有UI相关操作，需要将数据发送到UI线程
            Message msg = new Message();
            msg.arg1 = event;
            msg.arg2 = result;
            msg.obj = data;
            new Handler(Looper.getMainLooper(), new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    int event = msg.arg1;
                    int result = msg.arg2;
                    Object data = msg.obj;
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            // TODO 处理成功得到验证码的结果
                            // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                        } else {
                            // TODO 处理错误的结果
                            ((Throwable) data).printStackTrace();
                        }
                    } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            // TODO 处理验证码验证通过的结果
//                            { id=613 when=-196ms what=0 arg1=3 arg2=-1 obj={phone=15112286305, country=86} target=android.os.Handler }
                            try {
                                JSONObject jsonObject = new JSONObject((String) msg.obj);
                                String phone = (String) jsonObject.get("phone");
                                String code = (String) jsonObject.get("country");
                                mPresenter.register(phone, getCode(loginCode), code);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
//
                        } else {
                            loginCode.setHint("验证码错误!!");
                            // TODO 处理错误的结果
                            ((Throwable) data).printStackTrace();
                        }
                    }
                    // TODO 其他接口的返回结果也类似，根据event判断当前数据属于哪个接口
                    return false;
                }
            }).sendMessage(msg);
        }
    };


    /**
     * 获取用户名
     *
     * @param editText
     * @return
     */
    public String getUserName(EditText editText) {
        String s = editText.getText().toString();
        return s;
    }

    /**
     * @param editText
     * @return
     */
    public String getUserPassword(EditText editText) {
        return editText.getText().toString();
    }

    /**
     * 获取验证码
     *
     * @param editText
     * @return
     */
    public String getCode(EditText editText) {
        return editText.getText().toString();
    }
}
