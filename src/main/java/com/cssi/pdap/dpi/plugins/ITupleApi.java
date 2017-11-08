/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cssi.pdap.dpi.plugins;

import java.util.List;

/**
 *
 * @author sdorgan
 */
public interface ITupleApi {
     void addField(String fieldName, Object fieldValue);
    boolean contains(String field);
    int fieldIndex(String field);
    Object find(String pattern);
    Field getField(String name);
    Fields getFields(String name);
    Object getValue();
    Object getValueByField(String fieldName);
    List<Object> getValues();
    List<Object> select(Fields fields);
    int size();
    
}
