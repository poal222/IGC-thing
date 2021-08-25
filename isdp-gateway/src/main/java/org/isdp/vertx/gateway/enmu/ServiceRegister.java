package org.isdp.vertx.gateway.enmu;

import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.ServiceDiscovery;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 服务注册表，实现服务的线上注册
 */
public class ServiceRegister {

    /**
     * 通过微服务发现，获取服务
     */
   public static ServiceDiscovery discovery = null;


    public static void setDiscovery(ServiceDiscovery discovery) {
        ServiceRegister.discovery = discovery;
    }

    /**
     * 服务元数据的注册
     */
    static ArrayList<Record> serviceRegister = new ArrayList<>();

    /**
     * 添加服务
     * @param record
     */
    public static void addServiceRegister(Record record){
        serviceRegister.add(record);
    };

    /**
     * 取消服务
     * @param key
     */
    public static void removeServiceRegister(String key){
        serviceRegister.remove(key);
    };

    public static Record findRecord(String gatewayName) {
      List<Record> list = serviceRegister.stream().filter(record -> {
         return   gatewayName.equalsIgnoreCase(record.getName());
       }).collect(Collectors.toList());

        if(list.isEmpty())  return  new Record();
        return  list.get(0);
    }

}
