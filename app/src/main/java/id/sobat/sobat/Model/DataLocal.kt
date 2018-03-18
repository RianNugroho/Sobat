package id.sobat.sobat.Model

import java.util.HashMap

class DataLocal {
    companion object {
        const val RC_SIGN_IN = 9698
        const val TAG_SIGN_IN = "SIGN_IN"
        const val TAG_NICKNAME = "NICKNAME_CHECK"
        const val TAG_QUERY = "QUERY_DATA"
        const val DATA_KEY_SHARE = "SHARE_DJAFYEJSNXDDFS"
        lateinit var user: HashMap<String, Any?>
        var width = 0
        var out = false
    }
}