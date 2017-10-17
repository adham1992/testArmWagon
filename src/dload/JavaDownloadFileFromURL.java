package dload;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavaDownloadFileFromURL {
    private static String URL = "http://172.16.127.9/fki/";

    private static String DIR = "c:\\testFolder\\";

    private static String OLD_FILENAME = "index_old.xml";
    private static String NEW_FILENAME = "index.xml";

    public static void main(String[] args) throws IOException {
        String url = "http://172.16.127.9/fki/index.xml";
//        String url = "http://172.16.127.9/fki/ADM.xml.xz";
        Long start;

        start = System.currentTimeMillis();
        System.out.println(start);
//        downloadUsingNIO(url, "c:\\testFolder\\ADM.xml.xz");
        downloadUsingNIO(url, DIR + NEW_FILENAME);
        System.out.println("File is downloading...");
        System.out.println("time1 : " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
//        downloadUsingStream(url, "c:\\testFolder\\index_stream.xml");
        System.out.println("time2 : " + (System.currentTimeMillis() - start));



//        String NEW_FILENAME = "c:\\testFolder\\index.xml";
        List<InfoXML> infListNew = ParseXML.parseXML(DIR + NEW_FILENAME);
        System.out.println(infListNew.size());

        System.out.println("-----------------------------------------------");
//        String OLD_FILENAME = "c:\\testFolder\\index_old.xml";
        List<InfoXML> infListOld = ParseXML.parseXML(DIR + OLD_FILENAME);
        System.out.println(infListOld.size());

        if (infListNew.size() == infListOld.size()) {
            System.out.println("====");
            for (int i = 0; i < infListNew.size(); i++) {
                if (!infListNew.get(i).equals(infListOld.get(i))) {
                    System.out.println(infListNew.get(i).toString());
                }
            }
        } else if (infListNew.size() > infListOld.size()) {
            System.out.println(">>>");
            for (int i = 0; i < infListNew.size(); i++) {
                if (!infListNew.get(i).equals(infListOld.get(i))) {
                    System.out.println(infListNew.get(i).toString());
                }
            }
        } else if (infListNew.size() < infListOld.size()) {
            System.out.println("<<<");
            for (int i = 0; i < infListOld.size(); i++) {
                if (!infListOld.get(i).equals(infListNew.get(i))) {
                    System.out.println(infListNew.get(i).toString());
                }
            }
        }


        delOldFile(DIR, OLD_FILENAME);
        renameFile();


    }

    private static void downloadUsingNIO(String urlStr, String file) throws IOException {
        URL url = new URL(urlStr);
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }

    private static void downloadUsingStream(String urlStr, String file) throws IOException {
        URL url = new URL(urlStr);
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        FileOutputStream fis = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int count = 0;
        while ((count = bis.read(buffer, 0, 1024)) != -1) {
            fis.write(buffer, 0, count);
        }
        fis.close();
        bis.close();
    }

    private static void delOldFile(String dir, String fName) {
        String path = dir + fName;
        File file = new File(path);
        if (file.delete()) {
            System.out.println("file is deleted!");
        } else {
            System.out.println("file isn't deleted!");
        }
    }

    private static void renameFile() {
        final File oldFile = new File(DIR, NEW_FILENAME);
        final File newFile = new File(DIR, OLD_FILENAME);
        if (oldFile.exists() && !newFile.exists()) {
            if (oldFile.renameTo(newFile)) {
                System.out.println("file is renamed");
            } else {
                System.out.println("file isn't renamed");
            }
        }
    }
}
