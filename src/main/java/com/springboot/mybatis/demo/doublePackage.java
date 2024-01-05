package com.springboot.mybatis.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class doublePackage {
    public ArrayList<ArrayList<Double>> res = new ArrayList<>();

    /**
     *
     * @param input 需要产生排列组合的数组
     * @param weishu 需要产生排列组合的位数
     * @param nowweishu 当前已经完成的位数
     * @param nowres 当前已经装入数组的数字
     * @param num_weishu 需要使用该位数之后的数字
     */
    public void dfs(List<Double> input, int weishu, int nowweishu, ArrayList<Double> nowres, int num_weishu)//用来产生子集合
    {
        if(nowweishu == weishu)
        {
            res.add((ArrayList<Double>) nowres.clone());
            return;
        }
        for(int i = num_weishu + 1;i<input.size();i++)
        {
            nowres.add(input.get(i));
            dfs(input,weishu,nowweishu+1,nowres,i);
            nowres.remove(nowres.size()-1);
        }
    }

    public ArrayList<ArrayList<Double>> print(List<Double> input, int weishu) {
        res = new ArrayList<>();
        ArrayList<Double> nowres= new ArrayList<>();
        dfs(input,weishu,0,nowres,-1);
        return res;
    }



    public ArrayList<Double> sch_emuration(ArrayList<Double> nums,double target)
    {
        double lapse = 10000;
        ArrayList<Double> res = new ArrayList<>();
        for (int i = 1;i<=nums.size();i++)
        {
            ArrayList<ArrayList<Double>> ares = print(nums,i);

            for (int j = 0;j<ares.size();j++)
            {
                double thissum = 0;
                for (int k = 0;k<ares.get(j).size();k++)
                {
                    thissum+=ares.get(j).get(k);
                }
                if (Math.abs(thissum - target)<lapse)
                {
                    res = (ArrayList<Double>) ares.get(j).clone();
                    lapse = Math.abs(thissum - target);
                }
            }
        }

        return res;
    }


    public static void main(String args[]) {
        doublePackage t = new doublePackage();
        Random r = new Random();
        ArrayList<Double> nums =  new ArrayList<>();
       /* nums.add(8.05);nums.add(6.98);nums.add(6.19);nums.add(5.0);nums.add(22.96);nums.add(4.71);nums.add(4.74);nums.add(4.25);
        nums.add(6.34);nums.add(2.77);nums.add(7.31);nums.add(3.59);nums.add(19.55);*/

        double[] doubleArr = new double[]{55.14, 72.87, 86.31, 74.52, 53.67, 44.18, 18.98};
        for(int i = 0; i < doubleArr.length; i++) {
            nums.add(doubleArr[i]);
        }


        ArrayList<Double> res = t.sch_emuration(nums,298.00);
        double sum = 0;
        for (int i=0;i<res.size();i++) {
            System.out.println(res.get(i));
            sum = sum + res.get(i);
        }
        System.out.println(sum);
    }
}
