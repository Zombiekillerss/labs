package lab1

data class Page(val title:String,val pageid: String){
    override fun toString(): String {
        return "title: ${this.title}, pageId: ${this.pageid}"
    }
}
