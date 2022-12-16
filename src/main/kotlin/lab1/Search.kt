package lab1

import com.google.gson.Gson
import com.google.gson.JsonObject
import java.awt.Desktop
import java.net.URI
import java.net.URL
import java.net.URLEncoder
class Search(private var request: String) {
    private val requestLink = "https://ru.wikipedia.org/w/api.php?action=query&list=search&utf8=&format=json&srsearch="
    private val resultLink = "https://ru.wikipedia.org/w/index.php?curid="

    private var pages: List<Page> = emptyList()

    init {
        request = URLEncoder.encode("\"$request\"", "UTF-8")
        val jsonString = URL("$requestLink$request").readText()

        pages = getResults(jsonString)
        if(pages.isEmpty()) {
            throw Exception("Null search result")
        }
    }
    private fun getResults(jsonString: String): List<Page> {
        val newResults: MutableList<Page> = mutableListOf()

        val jsonArray = Gson()
            .fromJson(jsonString, JsonObject::class.java)
            .getAsJsonObject("query")
            .getAsJsonArray("search")

        jsonArray.forEach {
            newResults.add(Page(it.asJsonObject.getAsJsonPrimitive("title").toString(),
                it.asJsonObject.getAsJsonPrimitive("pageid").toString() ))
        }

        return newResults
    }

    fun getPages():String{
        var page = ""
        for(i in pages.indices){
            page += "${i+1} " + pages[i].toString() + "\n"
        }
        return page
    }
    fun openLinkIndex(index: Int){
        Desktop.getDesktop().browse(URI(resultLink + pages[index].pageid))
    }
}