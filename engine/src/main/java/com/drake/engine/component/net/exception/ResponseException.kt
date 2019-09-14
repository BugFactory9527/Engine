/*
 * Copyright (C) 2018, Umbrella CompanyLimited All rights reserved.
 * Project：Engine
 * Author：Drake
 * Date：9/11/19 7:25 PM
 */

package com.drake.engine.component.net.exception

/**
 *  对应网络请求后台定义的错误信息
 * @property errorMessage String 网络请求错误信息
 * @property errorCode Int 网络请求错误码
 * @constructor
 */
class ResponseException(val errorMessage: String, val errorCode: Int) : Throwable()
