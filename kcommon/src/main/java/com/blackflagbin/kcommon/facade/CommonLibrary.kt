package com.blackflagbin.kcommon.facade

import android.app.Application
import android.content.Context
import com.blackflagbin.kcommon.listener.ErrorHandleCallBack
import com.blackflagbin.kcommon.listener.OnPageCreateListener
import com.blackflagbin.kcommon.listener.OnPageDestroyListener
import com.blankj.utilcode.util.Utils

class CommonLibrary private constructor() {

    lateinit var context: Context
    lateinit var baseUrl: String
    lateinit var apiClass: Class<*>
    lateinit var cacheClass: Class<*>
    var isDebug: Boolean = true
    var headerMap: Map<String, String>? = null
    var errorHandleMap: Map<Int, ErrorHandleCallBack>? = null
    var onPageCreateListener: OnPageCreateListener? = null
    var onPageDestroyListener: OnPageDestroyListener? = null

    fun init(
            context: Application,
            baseUrl: String,
            apiClass: Class<*>,
            cacheClass: Class<*>,
            isDebug: Boolean = true,
            headerMap: Map<String, String>? = null,
            errorHandleMap: Map<Int, ErrorHandleCallBack>? = null,
            onPageCreateListener: OnPageCreateListener? = null,
            onPageDestroyListener: OnPageDestroyListener? = null) {
        this.context = context
        Utils.init(context)
        this.baseUrl = baseUrl
        this.apiClass = apiClass
        this.cacheClass = cacheClass
        this.isDebug = isDebug
        this.headerMap = headerMap
        this.errorHandleMap = errorHandleMap
        this.onPageCreateListener = onPageCreateListener
        this.onPageDestroyListener = onPageDestroyListener
    }


    private object InnerClass {
        internal var instance = CommonLibrary()
    }

    companion object {
        val instance: CommonLibrary
            get() = InnerClass.instance
    }


}
