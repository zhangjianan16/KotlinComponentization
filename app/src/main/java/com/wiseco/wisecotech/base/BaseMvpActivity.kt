package com.leifu.mvpkotlin.base

import android.os.Bundle
import android.widget.Toast
import com.wiseco.wisecotech.base.BaseActivity
import com.wiseco.wisecotech.views.LoadingUtil
import com.wiseco.wisecotech.views.MultipleStatusView

/**
 *描述:
 */
abstract class BaseMvpActivity<in V : IBaseView, P : IBasePresenter<V>> : BaseActivity(), IBaseView {

    var mPresenter: P? = null
    /**
     * 多种状态的 View 的切换
     */
    var mLayoutStatusView: MultipleStatusView? = null

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mPresenter = createPresenter()
        mPresenter?.attachView(this as V)
    }

    override fun initListener() {
        super.initListener()
        mLayoutStatusView?.setOnClickListener { startNetwork() }
    }

    /**
     * 释放一些资源
     */
    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()
    }

    /**
     * 加载中
     */
    override fun showLoading() {
        mLayoutStatusView?.showLoading() ?: LoadingUtil.showLoading(mActivity, "加载中...")
    }

    /**
     * 取消加载
     */
    override fun dismissLoading() {
        LoadingUtil.dismissLoading()
    }

    /**
     * 无网络
     */
    override fun showNoNetwork() {
        mLayoutStatusView?.showNoNetwork()
    }

    /**
     * 显示内容视图
     */
    override fun showContent() {
        mLayoutStatusView?.showContent()
    }

    /**
     * 显示错误视图
     */
    override fun showError() {
        mLayoutStatusView?.showError()
    }

    /**
     * 显示错误提示
     */
    override fun showErrorMsg(msg: String) {
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show()
    }

    /**
     * 请求网络数据
     */
    abstract fun startNetwork()

    /**
     * 创建Presenter
     */
    abstract fun createPresenter(): P
}