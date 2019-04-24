package com.example.demo.util.jna.test;

public class testJNA {
    public static void main(String[] args){
        String filePath = Testclou102.class.getResource("").getPath().replaceFirst("/"," ")+"clou102.dll";
        System.out.println(filePath);
        /*
        S_CLOU102_PACK sClou102Pack = new S_CLOU102_PACK();
        sClou102Pack.typeID = 0x01;
        sClou102Pack.address = 1;
        String send= "";
        IntByReference len = new IntByReference();
        int num = Testclou102.INSTANCE.pack_clou102(sClou102Pack,send,len);
        System.out.println(num);*/
    }
}
