package com.example.demo.util.jna.test;

import com.example.demo.util.jna.po.S_CLOU102_PACK;
import com.example.demo.util.jna.po.S_CLOU102_UNPACK;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;

public interface Testclou102 extends Library {
    String filePath = Testclou102.class.getResource("").getPath().replaceFirst("/"," ")+"clou102.dll";

    Testclou102 INSTANCE = (Testclou102) Native.loadLibrary(filePath, Testclou102.class);
    public int pack_clou102(S_CLOU102_PACK s_clou102_pack, String send, IntByReference len);
    public int unpack_clou102(String recvbuf, int len, S_CLOU102_UNPACK s_clou102_unpack);
}
