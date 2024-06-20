<<<<<<< HEAD
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gamebingo;

/**
 *
 * @author nguye
 */
public class RandomArray {
    private final int max = 75;
    private final int min = 1;
    private int[] array;
    private boolean[] flag ;

    public RandomArray() {
        flag = new boolean[75];
        array = new int[25];
        for (int i = 0; i<75; i++){
            flag[i] = false;
        }
    }
    public int[] getArray(){
        return array;
    }
    public void setArray(){
        int cnt = 0;
        while (cnt <25){
            int x = randomNumber();
            if (!flag[x-1]){
                array[cnt] = x;
                flag[x-1] = true;
                cnt++;
            }
        }
    }
    public int randomNumber(){
        return (int) (Math.random() * max + min);
    }
}
=======
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gamebingo;

/**
 *
 * @author nguye
 */
public class RandomArray {
    private final int max = 75;
    private final int min = 1;
    private int[] array;
    private boolean[] flag ;
    
    private int[] arrayServer;

    public RandomArray() {
        flag = new boolean[75];
        for (int i = 0; i<75; i++){
            flag[i] = false;
        }
    }
    public int[] getArray(){
        return array;
    }
    public void setArray(){
        array = new int[25];
        int cnt = 0;
        while (cnt <25){
            int x = randomNumber();
            if (!flag[x-1]){
                array[cnt] = x;
                flag[x-1] = true;
                cnt++;
            }
        }
    }
    public int[] getArrayServer(){
        return arrayServer;
    }
    public void setArrayServer(){
        arrayServer = new int[75];
        int cnt = 0;
        while (cnt <75){
            int x = randomNumber();
            if (!flag[x-1]){
                arrayServer[cnt] = x;
                flag[x-1] = true;
                cnt++;
            }
        }
    }
    public int randomNumber(){
        return (int) (Math.random() * max + min);
    }
}
>>>>>>> de89614f9158a98721e9df4c8afbde694356fc9b
