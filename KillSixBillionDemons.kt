package eu.kanade.tachiyomi.extension.en.killsixbilliondemons

import eu.kanade.tachiyomi.source.model.*
import eu.kanade.tachiyomi.source.online.ParsedHttpSource
import okhttp3.Request
import okhttp3.Response
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import rx.Observable
import org.jsoup.Jsoup

class KillSixBillionDemons : ParsedHttpSource() {

    override val name = "Kill Six Billion Demons"

    override val baseUrl = "https://killsixbilliondemons.com"

    override val lang = "en"

    override val supportsLatest = false

    override fun fetchPopularManga(page: Int): Observable<MangasPage> {
        val manga = SManga.create().apply {
            title = "Kill Six Billion Demons"
            artist = "ABBADON"
            author = "ABBADON"
            status = SManga.ONGOING
            url = "" // doesn't work without :)
            description = "God Is Dead. Far beyond Earth, The Multiverse is in chaos as " +
                "criminals rule and callous kings maintain a fragile peace. One day, a mysterious " +
                "and bloody figure appears to a barista named Allison Wanda Ruth, and bestows her " +
                "with an Ancient Artifact known as the Key of Kings. In the process, Allison finds " +
                "herself suddenly transported to the final resting place of the gods in the exact center of the multiverse, " +
                "the red city of Throne. Allison must now travel through Throne and the 777,777 universes of creation to save her kidnapped boyfriend " +
                "(even if he is a creep) and stop the plans of the stone angels, masked demons and mad god-kings that want her Key for themselves."
            thumbnail_url = "https://vignette.wikia.nocookie.net/killsixbilliondemons/images/6/6b/Ksbd_1-0.png"
        }

        return Observable.just(MangasPage(arrayListOf(manga), false))
    }

    override fun fetchSearchManga(page: Int, query: String, filters: FilterList) = fetchPopularManga(page) // changed

    override fun fetchMangaDetails(manga: SManga): Observable<SManga> = Observable.just(manga)

//    override fun chapterListParse(response: Response): List<SChapter> {
//        val chapterList = super.chapterListParse(response).distinct()
//        return chapterList.mapIndexed {
//            i, ch -> ch.apply { chapter_number = chapterList.size.toFloat() - i }
//        }
//    }

    override fun chapterListSelector() = ".level-0"

    override fun chapterFromElement(element: Element): SChapter {
        val chapter = SChapter.create()
        chapter.name = element.text()
        chapter.url = "/chapter/" + element.attr("value").split("/")[4]
        return chapter
    }

    override fun pageListParse(document: Document): List<Page> {
//        val urlRegex = """/.*/\d*/""".toRegex()
//        val imageUrl = document.select("#comic a img").attr("src")
//        val pages = mutableListOf<Page>()
//        pages.add(Page(0, "", imageUrl))
//        val next = document.select("a[title=Next Page]").attr("href")
//        val nextPage = Jsoup.connect(next).get()
//        pages.addAll(pageListParse(nextPage))
//        if (urlRegex.matches(next)) {
//            val nextPage = Jsoup.connect(baseUrl + next).get()
//            pages.addAll(pageListParse(nextPage))
//        }
        // "wp-post-image"
        val img = document.select(".comic-thumbnail-in-archive a img").attr("srcset").split(" ")[0]
        val pages = mutableListOf<Page>()
        pages.add(Page(0, img, img))
        return pages
    }

    override fun imageUrlParse(document: Document) = throw Exception("Not used")

    override fun popularMangaSelector(): String = throw Exception("Not used")

    override fun searchMangaFromElement(element: Element): SManga = throw Exception("Not used")

    override fun searchMangaNextPageSelector(): String? = throw Exception("Not used")

    override fun searchMangaSelector(): String = throw Exception("Not used")

    override fun popularMangaRequest(page: Int): Request = throw Exception("Not used")

    override fun searchMangaRequest(page: Int, query: String, filters: FilterList): Request = throw Exception("Not used")

    override fun popularMangaNextPageSelector(): String? = throw Exception("Not used")

    override fun popularMangaFromElement(element: Element): SManga = throw Exception("Not used")

    override fun mangaDetailsParse(document: Document): SManga = throw Exception("Not used")

    override fun latestUpdatesNextPageSelector(): String? = throw Exception("Not used")

    override fun latestUpdatesFromElement(element: Element): SManga = throw Exception("Not used")

    override fun latestUpdatesRequest(page: Int): Request = throw Exception("Not used")

    override fun latestUpdatesSelector(): String = throw Exception("Not used")

}
