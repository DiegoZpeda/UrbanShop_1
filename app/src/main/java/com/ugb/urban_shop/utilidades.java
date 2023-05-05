package com.ugb.urban_shop;

public class utilidades {
    static String url_consulta= "http://192.168.224.1:5984/db_amigos/_design/amigos/_view/amigos";
    static  String url_mto = "http://192.168.224.1.6:5984/db_amigos/";

    public String generarIdUnico(){
        return java.util.UUID.randomUUID().toString();
    }

}
