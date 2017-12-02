package BigdataComponents;

import java.util.*;
import java.io.*;

class Main {
    public static String FirstReverse(String str) {

        // code goes here
    /* Note: In Java the return type of a function and the
       parameter types being passed are defined, so this return
       call must match the return type of the function.
       You are free to modify the return type. */
        char[] chs = str.toCharArray();
        StringBuilder sb = new StringBuilder("");

        for(int i=chs.length -1; i >= 0; i--){
            sb.append(chs[i]);
        }

        return sb.toString();

    }

    public static void main (String[] args) {
        // keep this function call here
        Scanner s = new Scanner(System.in);
        System.out.print(FirstReverse(s.nextLine()));
    }

}
