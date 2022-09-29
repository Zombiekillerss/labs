package lab1

data class Page(val title:String,val pageId: String){
    override fun toString(): String {
        return "title: ${this.title}, pageId: ${this.pageId}"
    }
}
