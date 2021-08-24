package org.isdp.vertx.gateway.enmu;

import io.vertx.servicediscovery.Record;

import java.util.ArrayList;

/**
 * 服务注册表，实现服务的线上注册
 */
public class ServiceRegister {

    /**
     * 服务元数据的注册
     */
    ArrayList<Record> serviceRegister = new ArrayList<>();

    /**
     * 添加服务
     * @param record
     */
    public void addServiceRegister(Record record){
        serviceRegister.add(record);
    };

    /**
     * 取消服务
     * @param key
     */
    public void removeServiceRegister(String key){
        serviceRegister.remove(key);
    };
}
