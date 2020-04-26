package ftpdownloadfile

import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

import org.apache.commons.net.ftp.FTP
import org.apache.commons.net.ftp.FTPClient

import scala.util.{Failure, Success, Try}

// Adapted from java code https://www.codejava.net/java-se/ftp/java-ftp-file-download-tutorial-and-example

object FTPDownloadFile {
  def FTPDownloadFileSimple(server: String, port: Int = 21, user: String, password: String, remoteFile: String, downloadFileStr: String): Unit = {

    val ftpClient: FTPClient = new FTPClient()

    Try {
      ftpClient.connect(server, port)
      ftpClient.login(user, password)
      ftpClient.enterLocalPassiveMode()
      ftpClient.setFileType(FTP.BINARY_FILE_TYPE)

      // APPROACH #1: using retrieveFile(String, OutputStream)
      val downloadFile = new File(downloadFileStr)
      val outputStream: OutputStream = new BufferedOutputStream(new FileOutputStream(downloadFile))
      val success = ftpClient.retrieveFile(remoteFile, outputStream)
      outputStream.close()

      if (success) println("File downloaded successfully")
      else println("Unsuccessfully download")

    } match {
      case Success(value) => Try {
        if (ftpClient.isConnected) {
          ftpClient.logout()
          ftpClient.disconnect()
        }
      } match {
        case Success(value) => println(s"Logout from FTP sever successful")
        case Failure(exception) => println(s"Logout unsuccessful $exception")
      }

      case Failure(exception) => println(exception)
    }
  }
}
