package com.telefonica.kmpappscatalog

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AppInstallReceiver(
    private val androidPackage: String,
    private val onAppInstallStatusChanged: (Boolean) -> Unit) : BroadcastReceiver()
{
    override fun onReceive(context: Context, intent: Intent) {
        val packageName = intent.data?.schemeSpecificPart ?: return
        if (packageName == androidPackage) {
            when (intent.action) {
                Intent.ACTION_PACKAGE_ADDED -> onAppInstallStatusChanged(true)
                Intent.ACTION_PACKAGE_REMOVED -> onAppInstallStatusChanged(false)
            }
        }
    }
}