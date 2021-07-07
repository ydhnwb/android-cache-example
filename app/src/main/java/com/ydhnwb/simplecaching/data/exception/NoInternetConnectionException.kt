package com.ydhnwb.simplecaching.data.exception

import okio.IOException

class NoInternetConnectionException : IOException() {
    override val message: String
        get() = "You are offline"
}