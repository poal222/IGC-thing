package org.isdp.vertx.tenant;


import io.vertx.core.Future;

public class Tes {
    public Future<Integer> foo(int num){
        if(num == 1){
            return Future.succeededFuture(1);
        }else {
            return this.foo(num-1).compose(r -> Future.succeededFuture(num).map(r2 -> r*2));
        }
    }

    public static void main(String[] args) {
        Tes tes = new Tes();
        tes.foo(100).onSuccess(System.out::println);
    }
}
