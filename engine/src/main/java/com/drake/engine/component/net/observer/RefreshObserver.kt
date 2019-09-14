/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：Engine
 * Author：Drake
 * Date：9/11/19 7:25 PM
 */

package com.drake.engine.component.net.observer

import android.view.View
import android.view.View.OnAttachStateChangeListener
import com.drake.brv.PageRefreshLayout
import io.reactivex.observers.DefaultObserver

/**
 * 自动结束下拉刷新和上拉加载状态
 */
abstract class RefreshObserver<M>(val pageRefreshLayout: PageRefreshLayout) :
    DefaultObserver<M>() {


    init {
        pageRefreshLayout.addOnAttachStateChangeListener(object : OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {

            }

            override fun onViewDetachedFromWindow(v: View) {
                cancel()
            }
        })
    }


    /**
     * 关闭进度对话框并提醒错误信息
     *
     * @param e 包括错误信息
     */
    override fun onError(e: Throwable) {
        pageRefreshLayout.finish(false)
        NetObserver.showErrorMsg(e)
    }


    override fun onComplete() {
        pageRefreshLayout.finish(true)
    }
}
