package io.androidedu.ttsstt

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import java.util.*

//internet gerektirir.
/**
 * 1. On your device go to Settings -> Language and Input. Click on icon on Google voice input.
 * 2. Under ALL tab select the language you want to download.
 * 3. Once the language package downloaded, you can see it under INSTALLED tab.
 */
class SpeechToTextActivity : AppCompatActivity() {

    private val txtOrder1 by lazy { findViewById<TextView>(R.id.activity_speech_to_text_txtOrder1) }
    private val txtOrder2 by lazy { findViewById<TextView>(R.id.activity_speech_to_text_txtOrder2) }
    private val txtOrder3 by lazy { findViewById<TextView>(R.id.activity_speech_to_text_txtOrder3) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speech_to_text)

        promtSpeechInput()
    }

    private fun promtSpeechInput() {

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Konuş Artık !")

        try {
            startActivityForResult(intent, 100)
        } catch (exception: ActivityNotFoundException) {

            Toast.makeText(this, "Speech To Text Not Supported", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {

            100 -> {

                when (resultCode) {

                    Activity.RESULT_OK -> {

                        val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)

                        val teaIndex = result?.get(0)?.indexOf("çay")!!
                        val toastIndex = result[0]?.indexOf("tost")!!
                        val chocalateIndex = result[0]?.indexOf("çikolata")!!

                        if (teaIndex != -1) {

                            txtOrder1.text = result[0]?.substring((teaIndex - 2), (teaIndex + 3))
                            txtOrder1.visibility = View.VISIBLE
                        }else{
                            txtOrder1.visibility = View.GONE
                        }

                        if (toastIndex != -1) {

                            txtOrder2.text = result[0]?.substring((toastIndex - 2), (toastIndex + 4))
                            txtOrder2.visibility = View.VISIBLE
                        }else{
                            txtOrder2.visibility = View.GONE
                        }

                        if (chocalateIndex != -1) {

                            txtOrder3.text = result[0]?.substring((chocalateIndex - 2), (chocalateIndex + 8))
                            txtOrder3.visibility = View.VISIBLE
                        }else{
                            txtOrder3.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }
}
