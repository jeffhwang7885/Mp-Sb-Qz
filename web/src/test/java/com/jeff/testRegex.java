package com.jeff;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

public class testRegex {
    @Test
    public void testRegex() {
        String str = ",.!，，D_NAME。！；‘’”“**dfs  #$%^&()-+1431221\"\"中           国123漢字かどうかのjavaを決定";
        str = str.replaceAll("[\\pP\\pS\\pZ]", "");
        System.out.println(str);
    }

    @Test
    public void testString() {
        String str = "18100906";
        String matchId = str.substring(6);
        System.out.println(matchId);
        matchId = StringUtils.leftPad(matchId, 3, "0");
        System.out.println(matchId);
        System.out.println(str.substring(0, 6));
    }
}
