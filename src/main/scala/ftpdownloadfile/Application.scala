package ftpdownloadfile

object Application extends App {

  FTPDownloadFile.FTPDownloadFileSimple("195.144.107.198",
    21,
    "demo",
    "password",
    "/pub/example/readme.txt",
    "/home/mako/workspace/test/readme.txt")

}
