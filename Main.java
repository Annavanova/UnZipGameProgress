package WorkInFileLesson.UnZipGameProgress;

import WorkInFileLesson.GameProgress.GameProgress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    private static String mainPaph = "C:\\Users\\juliya\\Desktop\\Games\\savegames\\zip.zip";
    private static String paphDesirializ = "C:\\Users\\juliya\\Desktop\\Games\\savegames\\saveGame2.dat";

    public static void main(String[] args) {
        //Рапспаковка файлов
        openZip(mainPaph);
        //Десериализация
        openProgress(paphDesirializ);
    }

    static void openZip(String whichZipPaph) {
        try (ZipInputStream zipInput = new ZipInputStream(new FileInputStream(whichZipPaph))) {
            ZipEntry entry;
            byte[] buffer = new byte[1024];
            while ((entry = zipInput.getNextEntry()) != null) {
                File unpackFile = new File(entry.getName());
                if (entry.isDirectory()) {
                    unpackFile.mkdir();
                } else {
                    new File(unpackFile.getParent()).mkdir();
                    FileOutputStream fos = new FileOutputStream(unpackFile);
                    int lenght;
                    while ((lenght = zipInput.read(buffer)) > 0) {
                        fos.write(buffer, 0, lenght);
                        System.out.printf(" \nФайл %s распакован ", entry.getName());
                    }
                    fos.close();
                }
                zipInput.closeEntry();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static GameProgress openProgress(String mainPaph) {
        GameProgress gameProgress = null;
        try (FileInputStream fileInputStream = new FileInputStream(mainPaph)) {
            ObjectInputStream outFile = new ObjectInputStream(fileInputStream);
            gameProgress = (GameProgress) outFile.readObject();
            System.out.printf("Файл %s десириализрван", mainPaph);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return gameProgress;
    }
}
