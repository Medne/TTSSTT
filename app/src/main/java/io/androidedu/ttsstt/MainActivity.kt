package io.androidedu.ttsstt

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener, TextToSpeech.OnInitListener {

    private val txtRecordListener by lazy { findViewById<TextView>(R.id.activity_main_txtRecord) }
    private val btnReadRecord by lazy { findViewById<Button>(R.id.activity_main_btnReadRecord) }
    private val btnListenRecord by lazy { findViewById<Button>(R.id.activity_main_btnListenRecord) }

    private val textToSpeech by lazy { TextToSpeech(this, this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        txtRecordListener.text =
                "Ben sana kürk alamam doğrusu,\n" +
                "Güzel bileklerine bilezik alamam;\n" +
                "Bir kap yemek, bir elbise...\n" +
                "Öyle bir tad var ki fakirliğimizde,\n" +
                "Başka hiçbir şeyde bulamam...\n" +
                "\n" +
                "Sokağımız arnavut kaldırımı,\n" +
                "Evimiz ahşap iki oda.\n" +
                "Daha iyisi de olabilirdi ya...\n" +
                "Şükür buna da."

//        txtRecordListener.text = "Koyduğum nokta belki son ben bunu bilemem aynı bomb gibi gelir sana belki de aynı ton.\n" +
//                "Dibi delik gelebilir ama aynı fon\n" +
//                "Kendini bilemez montofon ve monoton düşer hep alt aynı don\n" +
//                "Anlatırım ben derdimi yalnız eyy biminii microphone\n" +
//                "Şimdi bana bir bakınız haydi muamelesi kesebilir haas.. ve de Muhammed Ali gibi gelir asi\n" +
//                "Bana bak beni gör ve de öl vasi\n" +
//                "Sesim hep duyulur tepeden bariton\n" +
//                "Mekânım olabilir her an ozon\n" +
//                "Yanıma gelenin canına girecektir eyy bimini microphone...\n" +
//                "\n" +
//                "O oo bunu ben bunu sen şunu ben onu sen\n" +
//                "Gülü ben günü sen fenomen olacak dolacak her yer\n" +
//                "Menemen kıvamı balçık gelemem\n" +
//                "Elemem dostumu göremezsem düşmanın postunu yere seremezsem\n" +
//                "Rüzgâr gibi esemezsem veremezsem kalbimi geri gelemezsem\n" +
//                "Sen beni bilemedin yüreğimi göremedin\n" +
//                "Kendini bilemedin yamacıma gelemedin\n" +
//                "Amacına varamadın her yeri karaladın\n" +
//                "Barışı da yaraladın acımadan aldın\n" +
//                "Rüyalara daldın bal arısı gök karası\n" +
//                "İstanbul kalem harp okulundan çıktı en baba rap işte başına darısı\n" +
//                "Ma ma mını ma mını ma mını Ma ma mını ma mını ma mını Ma ma mını ma mını ma mını Ma ma mını ma mını ma mını... " +
//                "microphone'unu getirsene bana baksana laklak etme sakın ha sen beni dinle\n" +
//                "Kelimelerimi kilometrelerce milimetrik hesaplarla yazdım ha\n" +
//                "İhtiyacınız var mı yardıma hatalı rhyme'lar Fatalrhyme'la çatal olur ha\n" +
//                "Yatacaktır yere bala banacaktır kanacaktır akacaktır\n" +
//                "Rüzgâr gibi esecektir geri gelecektir Ceza etnik bir sentezdir\n" +
//                "Bela teknik bir arızadır üç fazdır kanadı kırıktır\n" +
//                "Taktik kuşkunun oluşumudur şu yaptığım rapteki uçurumdur..."

        btnReadRecord.setOnClickListener(this)
        btnListenRecord.setOnClickListener(this)
    }

    override fun onClick(view: View) {

        when (view.id) {

            R.id.activity_main_btnReadRecord -> {

                startActivity(Intent(this, SpeechToTextActivity::class.java))
            }

            R.id.activity_main_btnListenRecord -> {

                textToSpeech.setPitch(1.0F)
                textToSpeech.setSpeechRate(2.5F)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ttsGreater21(txtRecordListener.text.toString())
                } else {
                    ttsUnder20(txtRecordListener.text.toString())
                }
            }
        }
    }

    override fun onInit(status: Int) {

        when (status) {

            TextToSpeech.SUCCESS -> {

                val result = textToSpeech.setLanguage(Locale("tr", "TR"))
//                val result = textToSpeech.setLanguage(Locale.FRANCE)

                when (result) {

                    TextToSpeech.LANG_MISSING_DATA,
                    TextToSpeech.LANG_NOT_SUPPORTED -> {

                        Toast.makeText(this, "This Language is not supported", Toast.LENGTH_SHORT).show()
                    }

                    else -> {

                        btnListenRecord.isEnabled = true

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            ttsGreater21(txtRecordListener.text.toString())
                        } else {
                            ttsUnder20(txtRecordListener.text.toString())
                        }
                    }
                }
            }

            else -> {

                Log.e("TTS", "Initilization Failed!")
            }
        }
    }

    private fun ttsUnder20(text: String) {

        val hashMap = HashMap<String, String>()
        hashMap[TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID] = "MessageId"
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, hashMap)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun ttsGreater21(text: String) {

        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID)
    }

    override fun onDestroy() {
        textToSpeech.stop()
        textToSpeech.shutdown()
        super.onDestroy()
    }
}
