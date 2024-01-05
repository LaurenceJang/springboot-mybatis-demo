package com.springboot.mybatis.demo;

public class test {

    private static int getNearestSum(int m, int[] value) {
        int[] result = new int[m+1];
        int[] result1 = new int[8];
        for (int i = 1; i <= value.length; i++) {
            for (int j = m; j >= 1; j--) {
                if (j >= value[i-1]) {

                    result[j] = Math.max(result[j], result[j-value[i-1]] + value[i-1]);
                    result1[i-1] = value[i-1];
                }
            }
        }
        for (int i = 0; i < result1.length; i++) {
            System.out.println(result1[i]);
        }

        return result[m];
    }


    /*private static double getNearestSumDouble(double m, double[] value) {
        double[] result = new double[m+1];
        for (int i = 1; i <= value.length; i++) {
            for (double j = m; j >= 1; j--) {
                if (j >= value[i-1]) {
                    result[j] = Math.max(result[j], result[j-value[i-1]] + value[i-1]);
                }
            }
        }
        return result[m];
    }*/

    public static void main(String[] args) {
        double[] value = new double[]{55.14, 72.87, 86.31, 74.52, 53.67, 44.18, 18.98};
        int[] valueint = new int[]{55, 73, 86, 75, 75, 54, 44, 19};
        /*int max = value.length;
        for(int i = 0; i < max; i++){
            System.out.println("当前选取个数为====" + i);
            System.out.println("resultArray====" + getNearestSumDouble(300, value));

        }*/
        System.out.println(getNearestSum(55, valueint));

    }

}
