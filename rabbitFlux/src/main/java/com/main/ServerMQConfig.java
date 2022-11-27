package com.main;

import com.filter.CustomMQFilterChain;
import com.server.MQFilter;

import java.util.ArrayList;
import java.util.List;

public class ServerMQConfig {
    private List<MQFilter> mqFilters = new ArrayList();


    public CustomMQFilterChain build() {
        //TODO  add  customer filters
        return  null;
    }
}
