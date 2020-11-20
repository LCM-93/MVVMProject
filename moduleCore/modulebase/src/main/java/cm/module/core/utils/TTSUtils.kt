package cm.module.core.utils

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import java.util.*

/**
 * 文字转语音工具类
 */
class TTSUtils private constructor() : UtteranceProgressListener(), TextToSpeech.OnInitListener {


    private var mTextToSpeech: TextToSpeech? = null
    private var isInit: Boolean = false
    private var waitSpeak: String? = null

    companion object {
        private const val TAG = "TTSUtils"
        val instance: TTSUtils by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { TTSUtils() }
    }

    private fun init(context: Context) {
        mTextToSpeech = TextToSpeech(context.applicationContext, this)
        mTextToSpeech?.setOnUtteranceProgressListener(this)
    }

    private fun release() {
        mTextToSpeech?.stop()
        mTextToSpeech?.shutdown()
        mTextToSpeech = null
        isInit = false
        waitSpeak = null
    }

    fun speak(context: Context, text: String) {
        if (!isInit) {
            waitSpeak = text
            init(context)
            return
        } else {
            speak(text)
        }
    }

    private fun speak(text: String) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            mTextToSpeech?.speak(
                text,
                TextToSpeech.QUEUE_FLUSH,
                null,
                TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID
            )
        } else {
            mTextToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, hashMapOf())
        }
        waitSpeak = null
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            mTextToSpeech?.let {
                when (it.setLanguage(Locale.CHINESE)) {
                    TextToSpeech.LANG_MISSING_DATA, TextToSpeech.LANG_NOT_SUPPORTED -> {
                        Log.e(TAG, "onInit 数据丢失或语言不支持")
                        release()
                    }
                    else -> {
                        isInit = true
                        waitSpeak?.let { speak(waitSpeak!!) }
                        Log.d(TAG, "onInit 支持该语言")
                    }
                }
            }
        } else if (status == TextToSpeech.ERROR) {
            Log.e(TAG, "initError")
            release()
        }

    }

    override fun onStart(utteranceId: String?) {
        Log.d(TAG, "speak start")
    }

    override fun onDone(utteranceId: String?) {
        Log.d(TAG, "speak done")
        release()
    }

    override fun onError(utteranceId: String?) {
        Log.d(TAG, "speak error")
        release()
    }


}