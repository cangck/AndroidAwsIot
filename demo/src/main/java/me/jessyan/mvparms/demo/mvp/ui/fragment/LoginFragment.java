package me.jessyan.mvparms.demo.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.di.component.DaggerLoginComponent;
import me.jessyan.mvparms.demo.di.module.LoginModule;
import me.jessyan.mvparms.demo.mvp.contract.LoginContract;
import me.jessyan.mvparms.demo.mvp.presenter.LoginPresenter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class LoginFragment extends BaseFragment<LoginPresenter> implements LoginContract.View {



    @BindView(R.id.login_username)
    EditText loginUsername;
    @BindView(R.id.login_passwd)
    EditText loginPasswd;
    @BindView(R.id.login_code)
    EditText loginCode;
    @BindView(R.id.tv_getcode)
    TextView tvGetcode;
    @BindView(R.id.ll_code)
    LinearLayout llCode;
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
    Unbinder unbinder;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerLoginComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(LoginFragment.this, view);
        return view;
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        thirdLogin.setVisibility(View.VISIBLE);
        rlLogin.setVisibility(View.VISIBLE);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @OnClick({
            R.id.register_count,
            R.id.forget_account
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_count:
                RegisterFragment registerFragment = RegisterFragment.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, registerFragment).commit();
                break;
            case R.id.forget_account:
                ForgetFragment forgetFragment = ForgetFragment.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, forgetFragment).commit();
                break;
        }
    }
}
