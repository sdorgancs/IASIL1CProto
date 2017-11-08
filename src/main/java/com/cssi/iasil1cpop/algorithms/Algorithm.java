/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cssi.iasil1cpop.algorithms;

/**
 * Base interface defining algorithms
 * @author sdorgan
 * @param <Input> 
 * @param <Output>
 */
public interface Algorithm <Input, Output>{
    Output call(Input input);
}
