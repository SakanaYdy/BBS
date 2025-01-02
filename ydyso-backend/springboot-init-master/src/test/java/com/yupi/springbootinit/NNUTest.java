package com.yupi.springbootinit;

import cn.hutool.http.HttpRequest;

public class NNUTest {

    public static void main(String[] args) {
        String json = "{\"name\":\"XNXQDM\",\"value\":\"2023-2024-2,2024-2025-1\",\"linkOpt\":\"and\",\"builder\":\"m_value_equal\"},{\"name\":\"SFYX\",\"caption\":\"是否有效\",\"linkOpt\":\"AND\",\"builderList\":\"cbl_m_List\",\"builder\":\"m_value_equal\",\"value\":\"1\",\"value_display\":\"是\"}";
        String url = "https://ehallapp.nnu.edu.cn/jwapp/sys/cjcx/modules/cjcx/xscjcx.do";

        String result = HttpRequest
                .post(url)
                .body(json)
                .execute()
                .body();
        System.out.println(result);

    }

}
