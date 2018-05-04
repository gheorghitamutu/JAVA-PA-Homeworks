package lab08.server;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.*;

class MyFTPClient
        extends FTP {

    private String server = "localhost";
    private int port = 21;
    private String username = "ghita";
    private String password = "secret";

    private FTPClient ftpClient = new FTPClient();

    MyFTPClient() {
        try {
            ftpClient.connect(server, port);
            System.out.println("Connected to ftp server!");
            ftpClient.login(username, password);
            System.out.println("Logged to ftp server!");
        } catch (IOException e) {
            e.printStackTrace();
        }


        ftpClient.enterLocalPassiveMode();
        try {
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void downloadFile(String localFile, String remoteFile) {
        File downloadFile1 = new File(localFile);

        boolean success = false;
        OutputStream outputStream1;
        try {
            outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadFile1));
            success = ftpClient.retrieveFile(remoteFile, outputStream1);

            outputStream1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (success) {
            System.out.println("File " + remoteFile + " has been downloaded successfully.");
        }
        else {
            System.out.println("Failed to download " + remoteFile + " file!");
        }
    }

    void uploadFile(String localFile, String remoteFile) {
        File firstLocalFile = new File(localFile);

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(firstLocalFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Start uploading first file");
        boolean done = false;
        try {
            done = ftpClient.storeFile(remoteFile, inputStream);
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (done) {
            System.out.println("The first file is uploaded successfully.");
        }
    }
}