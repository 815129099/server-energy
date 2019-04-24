package com.example.demo.util.jna.po;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class TIME_FRAME extends Structure {
    byte tm_year;
    byte tm_mon;
    byte tm_mday;
    byte tm_hour_start;
    byte tm_min_start;
    byte tm_sec_start;
    byte tm_hour_end;
    byte tm_min_end;
    byte tm_sec_end;

    public static class ByReference extends TIME_FRAME implements Structure.ByReference { }
    public static class ByValue  extends TIME_FRAME  implements Structure.ByValue { }

    @Override
    protected List getFieldOrder() {
        return Arrays.asList(new String[]{"tm_year", "tm_mon", "tm_mday", "tm_hour_start","tm_min_start",
                "tm_sec_start","tm_hour_end","tm_min_end","tm_sec_end"});
    }
}
