package com.mall.hrmnew

import android.app.Activity
import com.mall.hrmnew.ui.appinterface.AppExit

class AndroidAppExit(private val activity: Activity) : AppExit {
    override fun exit() {
        activity.finishAffinity()
    }
}