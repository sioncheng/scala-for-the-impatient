package util

object PrintUtil {

    def printChapterTitle(chapter:Int, title: String):Unit = {
        print(f"===== chapter $chapter $title =====%n")
    }

    def printKeyPoints(points:String*): Unit = {
        for(point <- points)
            print(f"[] $point%n")
    }
}
