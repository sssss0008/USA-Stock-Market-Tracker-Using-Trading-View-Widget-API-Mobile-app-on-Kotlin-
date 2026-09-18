package com.example

import android.app.Application
import android.system.Os

class MarketApplication : Application() {
    companion object {
        init {
            configureMesaEnvironment()
        }

        private fun configureMesaEnvironment() {
            try {
                // Pre-configure environment to tell Mesa to use software rasterizer
                // and silence missing /dev/dri rendernode warning logs in emulator environments
                Os.setenv("LIBGL_ALWAYS_SOFTWARE", "1", true)
                Os.setenv("MESA_LOADER_DRIVER_OVERRIDE", "swrast", true)
                Os.setenv("MESA_DEBUG", "silent", true)
            } catch (_: Throwable) {
            }
        }
    }

    override fun onCreate() {
        configureMesaEnvironment()
        super.onCreate()
    }
}
