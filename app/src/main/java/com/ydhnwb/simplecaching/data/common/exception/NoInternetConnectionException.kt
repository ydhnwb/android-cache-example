package com.ydhnwb.simplecaching.data.common.exception

import okio.IOException

class NoInternetConnectionException : IOException() {
    override val message: String
        get() = "You are offline"
}