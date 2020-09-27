package com.darren.AlgorithmAndDataStructures.DataStructures;

/**
 * Project: light
 * Author : Eric
 * Time   : 2020-09-26 23:06
 * Desc   : 稀疏数组
 * 多用于相同数据较多的二维数组，用以降低数据量
 */
public class SparseArray {

    public static void main(String[] args) {
        int[][] chessArray = new int[11][11];
        chessArray[1][2] = 1;
        chessArray[2][3] = 2;
        int rows = chessArray.length;
        int cols = 0;
        if (rows > 0) {
            cols = chessArray[0].length;
        }
        System.out.printf("二维数组行：%d，列：%d \n", rows, cols);
        for (int[] row : chessArray) {
            for (int col : row) {
                System.out.printf("%d\t", col);
            }
            System.out.println();
        }

        // 统计有效数据的个数
        int num = 0;
        for (int[] row : chessArray) {
            for (int col : row) {
                if (col > 0) {
                    num++;
                }
            }
        }

        // 转为稀疏数组
        int[][] sparseArray = new int[num + 1][3];
        sparseArray[0][0] = rows;
        sparseArray[0][1] = cols;
        sparseArray[0][2] = num;

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
        // 遍历稀疏数组
        System.out.println("\n遍历稀疏数组：");
        for (int[] row : sparseArray) {
            System.out.printf("%d\t%d\t%d\n", row[0], row[1], row[2]);

        }

        // 稀疏数组转回二维数组
        int[][] chessRecover = new int[sparseArray[0][0]][sparseArray[0][1]];
        for (int i = 1; i < sparseArray.length; i++) {
            int li = sparseArray[i][0];
            int ld = sparseArray[i][1];
            chessRecover[li][ld] = sparseArray[i][2];
        }

        // 遍历稀疏数组
        System.out.println("\n恢复后数组：");
        for (int[] row : chessRecover) {
            for (int col : row) {
                System.out.printf("%d\t", col);
            }
            System.out.println();
        }


    }
}
