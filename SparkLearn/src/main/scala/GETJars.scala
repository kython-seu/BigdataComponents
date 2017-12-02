import java.io.File

object GETJars {
  def main(args: Array[String]): Unit = {
    getJars("/home/kason/workspace/BigdataComponents/spark-main/target/spark-main/WEB-INF/lib")
  }

  def getJars(path: String): List[String] ={
    var pathLists = List()
    val file = new File(path)
    val paths = file.getAbsolutePath
    System.out.println("paths " + paths)
    val direct = new File(paths)
    val files = direct.listFiles

    for (f <- files) {
      System.out.println("name " + f.getPath)
      pathLists.::(f.getAbsolutePath)
    }

    pathLists
  }

}
