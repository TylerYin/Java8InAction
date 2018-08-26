package com.fwzs.data.qrcode;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class OrganizeQrcodeAndBoxcode {
    public static void main(String... args) throws IOException {
        FileWriter fileWriter = new FileWriter("src/com/fwzs/data/new.txt", false);

        int count = 0;
        int loopCount = 0;
        int packRate = 10;

        List<String> boxs = Files.lines(Paths.get("src/com/fwzs/data/CF180813036.txt"), Charset.defaultCharset()).collect(Collectors.toList());
        List<String> qrcodes = Files.lines(Paths.get("src/com/fwzs/data/CF180813041.txt"), Charset.defaultCharset()).collect(Collectors.toList());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            for (String code : qrcodes) {
                bufferedWriter.newLine();
                bufferedWriter.write(code + "," + sdf.format(new Date()) + "," + boxs.get(count) + "," + sdf.format(new Date()));

                loopCount++;
                if (loopCount % packRate == 0) {
                    count++;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("finish!" + loopCount);
    }
}
