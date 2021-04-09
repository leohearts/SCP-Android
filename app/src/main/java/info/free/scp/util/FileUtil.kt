package info.free.scp.util

import android.content.Context
import android.content.res.AssetManager
import android.os.Environment
import android.util.Log
import info
import info.free.scp.SCPConstants.DETAIL_DB_NAME
import info.free.scp.SCPConstants.SCP_DB_NAME
import info.free.scp.ScpApplication
import info.free.scp.ScpApplication.Companion.context
import info.free.scp.db.AppInfoDatabase
import info.free.scp.db.ScpDatabase
import org.jetbrains.anko.*
import java.io.*


/**
 * Created by zhufree on 2018/11/22.
 * 备份工具类
 * 下载的数据库scp_data.db,用户数据scp_info.db和level.xml放在documents文件夹
 * 数据库直接下到documents然后复制到私有文件夹
 * scp_info.db和level.xml在每次备份时复制到documents
 */

class FileUtil(val mContext: Context) {

    companion object {
        private var fileUtil: FileUtil? = null
        var prefFilename = "level.xml"
        var infoDBFilename = "scp_info.db"
        val appFolderName = "scp_download"
        val absPath = Environment.getDataDirectory().absolutePath
        val pkgName = ScpApplication.context.packageName
        val sp = File.separator
        val privatePrefDirPath = "$absPath${sp}data$sp$pkgName${sp}shared_prefs$sp" // 'data/data/info.free.scp/shared_prefs/'
        val privateDbDirPath = "$absPath${sp}data$sp$pkgName${sp}databases$sp"
        val documentDirPath = "${
            Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS)
        }$sp$appFolderName$sp"

        fun getInstance(context: Context): FileUtil {
            return fileUtil ?: FileUtil(context)
        }
    }

    /**
     * Copies your database from your local assets-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled. This is done by transfering bytestream.
     */
    @Throws(IOException::class)
    fun copyCategoryDb() {
        val dbReady = checkDataReady(SCP_DB_NAME)
        if (dbReady) return
        info("copy db from asset to $privateDbDirPath")
        val am: AssetManager = context.assets
        // Open your local db as the input stream
        val myInput: InputStream = am.open(SCP_DB_NAME)

        // Path to the just created empty db
        //不存在先创建文件夹
        //不存在先创建文件夹
        val dirPath = File(privateDbDirPath)
        if (dirPath.mkdir()) {
            info("创建成功")
        } else {
            info("创建失败")
        }
        val outFileName: String = privateDbDirPath + SCP_DB_NAME
        val path = File(outFileName)

        // Open the empty db as the output stream
        val myOutput: OutputStream = FileOutputStream(outFileName)

        // transfer bytes from the input file to the output file
        val buffer = ByteArray(1024)
        var length: Int
        while (myInput.read(buffer).also { length = it } > 0) {
            myOutput.write(buffer, 0, length)
        }

        // Close the streams
        myOutput.flush()
        myOutput.close()
        myInput.close()
    }

    /**
     * 根据文件夹路径+文件名凑出备份文件全路径 Documents/scp_download
     * @param fileName String
     * @return String
     */
    private fun getBackUpFilePath(fileName: String): String {
        checkBackupDir()
        return "$documentDirPath$fileName"
    }

    /**
     * 检查备份文件是否存在，如果没有就创建
     */
    private fun checkBackupDir() {
        val documentDir = File(documentDirPath)
        if (!documentDir.exists()) {
            documentDir.mkdirs()
        }
    }

    /**
     * 检查私有数据库中scp_data.db是否存在
     */
    fun checkDataReady(dbName: String): Boolean {
        val backUpFile = File("$privateDbDirPath$dbName")
        return backUpFile.exists()
    }

    fun checkBackupDataExist(): Boolean {
        val backUpFile = File(getBackUpFilePath(DETAIL_DB_NAME))
        return backUpFile.exists() &&
                backUpFile.lastModified() > PreferenceUtil.getServerLastUpdateTime()
                && backUpFile.length() > 100 * 1000 * 1000
    }

    /**
     * 检查scp_info.db或pref文件有无备份在Documents/scp_download文件夹
     */
    fun checkBackUpFileExist(fileName: String): Boolean {
        val backUpFile = File(getBackUpFilePath(fileName))
        return backUpFile.exists()
    }

    /**
     * 复制pref/db文件到备份文件夹
     * [filePath] String
     * [backup] 备份还是恢复操作
     */
    private fun copyFile(fromFilePath: String, toFilePath: String) {
        Log.i("freescp", fromFilePath)
        if (fromFilePath.isEmpty() || toFilePath.isEmpty()) return
        val fromFile = File(fromFilePath)
        val toFile = File(toFilePath)
        if (fromFile.exists()) {
            fromFile.copyTo(toFile, true)
        }
    }


    /**
     * 恢复数据的Dialog
     */
    fun showRestore() {
        val backUpFile = File(getBackUpFilePath(infoDBFilename))
        if (backUpFile.exists()) {
            mContext.alert("检测到本设备有之前保存的用户数据，是否恢复", "恢复") {
                yesButton {
                    mContext.toast("开始恢复")
                    doAsync {
                        if (restore()) {
                            ScpDatabase.getNewInstance()
                            AppInfoDatabase.getNewInstance()
                            uiThread {
                                mContext.toast("恢复完成")
                            }
                        }
                    }
                }
                noButton { }
            }.show()
        }
    }

    /**
     * 恢复数据库
     */
    private fun restore(): Boolean {
        try {
            copyFile(getBackUpFilePath(DETAIL_DB_NAME), "$privateDbDirPath$DETAIL_DB_NAME")
            copyFile(getBackUpFilePath(infoDBFilename), "$privateDbDirPath$infoDBFilename")
            copyFile(getBackUpFilePath(prefFilename), "$privatePrefDirPath$prefFilename")
            return true
        } catch (e: Exception) {
            mContext.toast("文件复制出错：" + e.message)
        }
        return false
    }

    fun restoreData(): Boolean {
        try {
            copyFile(getBackUpFilePath(DETAIL_DB_NAME), "$privateDbDirPath$DETAIL_DB_NAME")
            return true
        } catch (e: Exception) {
            mContext.toast("文件复制出错：" + e.message)
        }
        return false
    }

    fun backup(): Boolean {
        try {
            copyFile("$privateDbDirPath$DETAIL_DB_NAME", getBackUpFilePath(DETAIL_DB_NAME))
            copyFile("$privateDbDirPath$infoDBFilename", getBackUpFilePath(infoDBFilename))
            copyFile("$privatePrefDirPath$prefFilename", getBackUpFilePath(prefFilename))
            return true
        } catch (e: Exception) {
            mContext.toast("文件复制出错：" + e.message)
        }
        return false
    }
}
