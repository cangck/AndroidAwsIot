package me.jessyan.mvparms.demo.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.di.component.DaggerLoginComponent;
import me.jessyan.mvparms.demo.di.module.LoginModule;
import me.jessyan.mvparms.demo.mvp.contract.LoginContract;
import me.jessyan.mvparms.demo.mvp.presenter.LoginPresenter;
import me.jessyan.mvparms.demo.mvp.ui.fragment.LoginFragment;
import me.jessyan.mvparms.demo.mvp.ui.globalui.AlertDialogUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.login_username)
    EditText loginUsername;
    @BindView(R.id.login_passwd)
    EditText loginPasswd;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.weibao)
    ImageView weibao;
    @BindView(R.id.weixin)
    ImageView weixin;
    @BindView(R.id.register)
    Button register;
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    @BindView(R.id.login_container)
    LinearLayout loginContainer;

    LoginFragment loginFragment;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLoginComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_login; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

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
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.login, R.id.register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                mPresenter.register(loginUsername.getText().toString(), loginPasswd.getText().toString());
                break;
            case R.id.register:
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                loginFragment = LoginFragment.newInstance();
                fragmentTransaction.add(R.id.fragment_container, loginFragment).commit();
                loginContainer.setVisibility(View.GONE);
                fragmentContainer.setVisibility(View.VISIBLE);
                break;
        }
    }

    AlertDialog show;

    @Override
    public void showloading() {
        show = AlertDialogUtils.get().show(LoginActivity.this);
        show.show();
    }

    @Override
    public void dimissloading() {
        show.dismiss();
    }

    @Override
    public void onBackPressed() {

        if (loginContainer.getVisibility() == View.GONE) {
            loginContainer.setVisibility(View.VISIBLE);
            fragmentContainer.setVisibility(View.GONE);
            return;
        }
        super.onBackPressed();
    }

    /**
     * 处理返回键
     */
    public interface FragmentBackListener {

        void onbackForward();
    }
}
