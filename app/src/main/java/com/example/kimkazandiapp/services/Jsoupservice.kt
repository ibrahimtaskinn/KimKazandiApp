package com.example.kimkazandiapp.services

import android.util.Log
import com.example.kimkazandiapp.data.entity.Data
import com.example.kimkazandiapp.data.entity.DetailData
import com.example.kimkazandiapp.data.repository.DatabaseDaoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class Jsoupservice @Inject constructor(private val databaseDaoRepository: DatabaseDaoRepository) {
    private val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}

        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}

        override fun getAcceptedIssuers(): Array<X509Certificate> {
            return arrayOf()
        }
    })

    private fun createTrustAllSSLContext(): SSLContext? {
        return try {
            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, trustAllCerts, SecureRandom())
            sslContext
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            null
        } catch (e: KeyManagementException) {
            e.printStackTrace()
            null
        }
    }

    private fun parseDetails(doc2: Document): DetailData? {
        return try {
            val detail =
                doc2.getElementsByClass("secondGallery").select(".scrollbar-dynamic").getOrNull(0)?.text()
            val basTarih =
                doc2.getElementsByClass("info mainSocial").select(".kalanSure").getOrNull(1)?.text()
            val sonTarih =
                doc2.getElementsByClass("info mainSocial").select(".kalanSure").getOrNull(2)?.text()
            val cekTarih =
                doc2.getElementsByClass("info mainSocial").select(".kalanSure").getOrNull(3)?.text()
            val ilnTarih =
                doc2.getElementsByClass("info mainSocial").select(".kalanSure").getOrNull(4)?.text()
            val minharcama =
                doc2.getElementsByClass("info mainSocial").select(".kalanSure").getOrNull(5)?.text()
            val hediyeDeger =
                doc2.getElementsByClass("info mainSocial").select(".kalanSure").getOrNull(6)?.text()
            val hediyeSayi =
                doc2.getElementsByClass("info mainSocial").select(".kalanSure").getOrNull(7)?.text()

            DetailData(
                detail = detail,
                basTarih = basTarih,
                sonTarih = sonTarih,
                cekTarih = cekTarih,
                ilnTarih = ilnTarih,
                minharcama = minharcama,
                hediyeDeger = hediyeDeger,
                hediyeSayi = hediyeSayi
            )
        } catch (e: Exception) {
            Log.e("Jsoup Error", "Element seçiminde hata oluştu", e)
            null
        }
    }

    // https://www.kimkazandi.com/cekilisler sayfasında ilk 8 çekiliş
    suspend fun cekilisler() {
        withContext(Dispatchers.IO) {
            try {
                val sslContext = createTrustAllSSLContext()
                if (sslContext != null) {
                    HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.socketFactory)
                }
                val url = "https://www.kimkazandi.com/cekilisler"
                val doc: Document = Jsoup.connect(url).timeout(15000).ignoreContentType(true).get()
                val cekilisElements =
                    doc.getElementsByClass("bg-transparent pb20").select(".col-sm-3.col-lg-3.item")
                        .subList(0, 8)
                for (element in cekilisElements) {

                    val imgUrl = element.getElementsByClass("img").select("img").attr("abs:src")
                    val detailUrl = element.select("a").attr("abs:href")
                    val title = element.select("h4").text()
                    val time = element.select(".date.d-flex").get(0).text()
                    val hediye = element.select(".date.d-flex").get(1).text()
                    val kosul = element.select(".date.d-flex").get(2).text()

                    val doc2: Document = Jsoup.connect(detailUrl).ignoreContentType(true).get()
                    val detailData = parseDetails(doc2)

                    val data = detailData?.let {
                        Data(
                            detailUrl = detailUrl,
                            imgUrl = imgUrl,
                            title = title,
                            time = time,
                            hediye = hediye,
                            kosul = kosul,
                            tur = "cekilisler/ilk8",
                            detailData = it
                        )
                    }

                    if (data != null) {
                        databaseDaoRepository.insertData(data)
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    // Seçili sayfalarda tüm çekilişleri getirir
    suspend fun cekilisturler() {
        withContext(Dispatchers.IO) {
            try {
                val sslContext = createTrustAllSSLContext()
                if (sslContext != null) {
                    HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.socketFactory)
                }

                val Turs: MutableList<String> = mutableListOf(
                    "yeni-baslayanlar",
                    "bedava-katilim",
                    "araba-kazan",
                    "telefon-tablet-kazan",
                    "tatil-kazan"
                )

                for (Tur in Turs) {
                    val url = "https://www.kimkazandi.com/cekilisler/$Tur"
                    val doc: Document = Jsoup.connect(url).timeout(15000).ignoreContentType(true).get()
                    val cekilisElements =
                        doc.getElementsByClass("bg-transparent pb20").select(".col-sm-3.col-lg-3.item")
                    for (element in cekilisElements) {

                        val imgUrl = element.getElementsByClass("img").select("img").attr("abs:src")
                        val detailUrl = element.select("a").attr("abs:href")
                        val title = element.select("h4").text()
                        val time = element.select(".date.d-flex").get(0).text()
                        val hediye = element.select(".date.d-flex").get(1).text()
                        val kosul = element.select(".date.d-flex").get(2).text()

                        val doc2: Document = Jsoup.connect(detailUrl).ignoreContentType(true).get()
                        val detailData = parseDetails(doc2)

                        val data = detailData?.let {
                            Data(
                                detailUrl = detailUrl,
                                imgUrl = imgUrl,
                                title = title,
                                time = time,
                                hediye = hediye,
                                kosul = kosul,
                                tur = Tur,
                                detailData = it
                            )
                        }

                        if (data != null) {
                            databaseDaoRepository.insertData(data)
                        }
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    suspend fun updateDataIfNecessary() = coroutineScope {
        val latestTimestamp = databaseDaoRepository.getLatestTimestamp()
        val currentTime = System.currentTimeMillis()
        if (latestTimestamp == null || currentTime - latestTimestamp > 3 * 60 * 60 * 1000) {
            async { cekilisler() }
            async { cekilisturler() }
        }
    }
}