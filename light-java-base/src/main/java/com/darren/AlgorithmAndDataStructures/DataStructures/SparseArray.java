package com.darren.AlgorithmAndDataStructures.DataStructures;

import com.darren.utils.DateTools;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Project: light
 * Author : Eric
 * Time   : 2020-09-26 23:06
 * Desc   : 稀疏数组
 * 多用于相同数据较多的二维数组，用以降低数据量
 * 典型应用是五子棋
 * 棋盘 -> 二维数组 -> (稀疏数组) -> 写入文件  【存档功能】
 * 读取文件 -> 稀疏数组 -> 二维数组 ->》 棋盘 【接上局】
 */
public class SparseArray {

    public static void main(String[] args) {
        int[][] chessArray = new int[11][11];
        chessArray[1][2] = 1;
        chessArray[2][3] = 2;
        for (int[] row : chessArray) {
            for (int col : row) {
                System.out.printf("%d\t", col);
            }
            System.out.println();
        }

        // 二维数据转稀疏数组
        int[][] sparseArray = convertToSparseArray(chessArray);

        // 稀疏数组存档
        saveToDisk(sparseArray);

        // 稀疏数组转回二维数组
        int[][] chessRecover = recoverFromDisk();

        // 遍历恢复后的数组
        System.out.println("\n恢复后数组：");
        for (int[] row : chessRecover) {
            for (int col : row) {
                System.out.printf("%d\t", col);
            }
            System.out.println();
        }

    }

    /**
     * 将一个二维数组转为稀疏数组
     *
     * @param chessArray
     * @return
     */
    private static int[][] convertToSparseArray(int[][] chessArray) {
        // 统计有效数据的个数
        int num = 0;
        for (int[] row : chessArray) {
            for (int col : row) {
                if (col > 0) {
                    num++;
                }
            }
        }

        int rows = chessArray.length;
        int cols = 0;
        if (rows > 0) {
            cols = chessArray[0].length;
        }
        System.out.printf("二维数组行：%d，列：%d \n", rows, cols);

        // 转为稀疏数组-第一行用于存储原始数组多少行多少列有多少个有效数据，所以稀疏数组要比原始有效数据个数多一行
        int[][] sparseArray = new int[num + 1][3];
        sparseArray[0][0] = rows;
        sparseArray[0][1] = cols;
        sparseArray[0][2] = num;

        // 有效数据所在的行、列及有效值
        int sparseLine = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (chessArray[i][j] > 0) {
                    sparseLine++;
                    sparseArray[sparseLine][0] = i;
                    sparseArray[sparseLine][1] = j;
                    sparseArray[sparseLine][2] = chessArray[i][j];
                }
            }
        }
        return sparseArray;
    }

    /**
     * 保存稀疏数组到硬盘
     *
     * @param sparseArray
     */
    private static void saveToDisk(int[][] sparseArray) {
        BufferedWriter bufferedWriter = null;

        try {
            File to = new File("/data/appdatas/chessHistory.data");
            System.out.println("文件最后修改时间：" + DateTools.dateToString(new Date(to.lastModified())));//最后修改时间

            FileOutputStream fileOutputStream = new FileOutputStream(to);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
            bufferedWriter = new BufferedWriter(outputStreamWriter);

            // 遍历稀疏数组
            System.out.println("\n遍历稀疏数组：");
            for (int[] row : sparseArray) {
                String newLine = String.format("%d\t%d\t%d\n", row[0], row[1], row[2]);
                // 写入文件
                bufferedWriter.write(newLine);
                System.out.print(newLine);
            }
            bufferedWriter.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (bufferedWriter != null)
                    bufferedWriter.close();
            } catch (IOException e) {
                System.out.println("输出流关闭异常！");
            }
        }
    }

    /**
     * 从磁盘恢复二维数组
     *
     * @return
     */
    private static int[][] recoverFromDisk() {
        int[][] chessRecover = null;

        File from = new File("/data/appdatas/chessHistory.data");
        BufferedReader bufferedReader = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(from);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            bufferedReader = new BufferedReader(inputStreamReader);


            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] split = StringUtils.split(line, "\t");

                if (chessRecover == null) {
                    chessRecover = new int[Integer.valueOf(split[0])][Integer.valueOf(split[1])];
                } else {
                    chessRecover[Integer.valueOf(split[0])][Integer.valueOf(split[1])] = Integer.valueOf(split[2]);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (bufferedReader != null)
                    bufferedReader.close();
            } catch (IOException e) {
                System.out.println("输入流关闭异常");
            }
        }
        return chessRecover;
    }
}
