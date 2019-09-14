/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：Engine
 * Author：Drake
 * Date：9/11/19 7:25 PM
 */

package com.drake.engine.component.net.observer

import android.app.Dialog
import android.app.ProgressDialog
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import com.drake.brv.BindingAdapter
import com.drake.brv.PageRefreshLayout
import com.drake.statelayout.StateLayout
import io.reactivex.Observable


/**
 * 自动打印错误信息
 * @receiver Observable<M>
 * @param lifecycleOwner FragmentActivity? 如果不为null 则会根据当前activity销毁而取消订阅
 * @param block (M) -> UnitUtils
 */
fun <M> Observable<M>.net(
    lifecycleOwner: LifecycleOwner? = null,
    block: (NetObserver<M>.(M) -> Unit)? = null
) {

    subscribe(object : NetObserver<M>(lifecycleOwner) {
        override fun onNext(t: M) {
            block?.invoke(this, t)
        }
    })
}

/**
 * 自动处理多状态页面
 * @receiver Observable<M>
 * @param stateLayout StateLayout
 * @param block (M) -> UnitUtils
 */
fun <M> Observable<M>.state(stateLayout: StateLayout, block: StateObserver<M>.(M) -> Unit) {
    subscribe(object : StateObserver<M>(stateLayout) {
        override fun onNext(t: M) {
            block(t)
        }
    })
}

fun <M> Observable<M>.state(view: View, block: StateObserver<M>.(M) -> Unit) {
    subscribe(object : StateObserver<M>(view) {
        override fun onNext(t: M) {
            block(t)
        }
    })
}

fun <M> Observable<M>.state(activity: FragmentActivity, block: StateObserver<M>.(M) -> Unit) {
    subscribe(object : StateObserver<M>(activity) {
        override fun onNext(t: M) {
            block(t)
        }
    })
}

fun <M> Observable<M>.state(fragment: Fragment, block: StateObserver<M>.(M) -> Unit) {
    subscribe(object : StateObserver<M>(fragment) {
        override fun onNext(t: M) {
            block(t)
        }
    })
}

/**
 * 请求网络自动开启和关闭对话框
 * @receiver Observable<M>
 * @param activity FragmentActivity
 * @param cancelable Boolean 对话框是否可以被用户取消
 * @param block (M) -> Unit
 */
fun <M> Observable<M>.dialog(
    activity: FragmentActivity,
    cancelable: Boolean = true,
    block: (DialogObserver<M>.(M) -> Unit)? = null
) {
    subscribe(object : DialogObserver<M>(activity, cancelable = cancelable) {
        override fun onNext(t: M) {
            block?.invoke(this, t)
        }
    })
}

fun <M> Observable<M>.dialog(
    activity: FragmentActivity,
    dialog: Dialog = ProgressDialog(activity),
    cancelable: Boolean = true,
    block: (DialogObserver<M>.(M) -> Unit)? = null
) {
    subscribe(object : DialogObserver<M>(activity, dialog, cancelable) {
        override fun onNext(t: M) {
            block?.invoke(this, t)
        }
    })
}

/**
 * 自动结束下拉加载
 * @receiver Observable<M>
 * @param pageRefreshLayout SmartRefreshLayout
 * @param block (M) -> UnitUtils
 */
fun <M> Observable<M>.refresh(
    pageRefreshLayout: PageRefreshLayout,
    block: RefreshObserver<M>.(M) -> Unit
) {
    subscribe(object : RefreshObserver<M>(pageRefreshLayout) {
        override fun onNext(t: M) {
            block(t)
        }
    })
}

/**
 * 自动处理分页加载更多和下拉加载
 *
 * @receiver Observable<M>
 * @param pageRefreshLayout PageRefreshLayout
 * @param block (M) -> UnitUtils
 */
fun <M> Observable<M>.page(
    pageRefreshLayout: PageRefreshLayout,
    adapter: BindingAdapter? = null,
    block: PageObserver<M>.(M) -> Unit
) {
    subscribe(object : PageObserver<M>(pageRefreshLayout, adapter) {
        override fun onNext(t: M) {
            block(t)
        }
    })
}