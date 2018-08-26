package com.fwzs.data.bitmatrix;

import com.fwzs.data.utils.ZxingHandler;

import java.io.*;
import java.util.zip.GZIPInputStream;

/**
 * 扫描文件夹，批量生成二维码
 *
 * @author Tyler Yin
 */
public class GenerateBitMatrix {
    public static void main(String[] args) {
        String bitMatrixFilePath = "F:\\biMatrix_Source\\qrcode.txt.gz";

        String line;
        String qrCode;
        File file = new File(bitMatrixFilePath);
        String bitMatrixStorePath = "F:\\biMatrix_Destination\\" + file.getName().substring(0, file.getName().indexOf(".txt.gz"));

        int count = 0;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new GZIPInputStream(new FileInputStream(file)), "UTF-8"))) {
            File destinationDirectory = new File(bitMatrixStorePath);
            if (!destinationDirectory.exists()) {
                destinationDirectory.mkdirs();
            }

            while (null != (line = reader.readLine())) {
                if (line.contains("=")) {
                    qrCode = line.substring(line.indexOf("=") + 1);
                } else {
                    qrCode = line;
                }
                count++;
                ZxingHandler.encode2(line, 400, 400, bitMatrixStorePath + "\\" + qrCode + ".jpg");
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("总共生成二维码数量 ：" + count);
    }
}
