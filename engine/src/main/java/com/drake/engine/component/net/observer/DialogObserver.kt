/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：Engine
 * Author：Drake
 * Date：9/14/19 9:43 AM
 */

@file:Suppress("DEPRECATION")

package com.drake.engine.component.net.observer

import android.app.Dialog
import android.app.ProgressDialog
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle.Event
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.observers.DefaultObserver

/**
 * 自动加载对话框网络请求
 *
 *
 * 开始: 显示对话框
 * 错误: 提示错误信息, 关闭对话框
 * 完全: 关闭对话框
 */
abstract class DialogObserver<M>(
    var activity: FragmentActivity,
    var dialog: Dialog = ProgressDialog(activity),
    var cancelable: Boolean = true
) : DefaultObserver<M>(), LifecycleObserver {


    override fun onStart() {

        activity.lifecycle.addObserver(this)


        if (dialog is ProgressDialog) {
            (dialog as ProgressDialog).setMessage("加载中")
        }

        dialog.setOnDismissListener { cancel() }
        dialog.setCancelable(cancelable)
        dialog.show()
    }

    @OnLifecycleEvent(Event.ON_DESTROY)
    fun dismissDialog() {
        if (dialog.isShowing) {
            dialog.dismiss()
        }
    }

    /**
     * 关闭进度对话框并提醒错误信息
     *
     * @param e 包括错误信息
     */
    override fun onError(e: Throwable) {
        dismissDialog()
        NetObserver.showErrorMsg(e)
    }

    override fun onComplete() {
        dismissDialog()
    }

}
