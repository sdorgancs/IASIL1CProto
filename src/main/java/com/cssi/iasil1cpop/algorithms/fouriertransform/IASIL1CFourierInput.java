/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cssi.iasil1cpop.algorithms.fouriertransform;

import java.io.Serializable;

import com.cssi.iasil1cpop.algorithms.utils.ComplexArray;

/**
 *
 * @author sdorgan
 */
public class IASIL1CFourierInput implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer firstScience;
    private Integer lastScience;
    private Double dopd;
    private Integer nfft;
    private ComplexArray spk;

    /**
     * @return the firstScience
     */
    public Integer getFirstScience() {
        return firstScience;
    }

    /**
     * @param firstScience the firstScience to set
     */
    public void setFirstScience(Integer firstScience) {
        this.firstScience = firstScience;
    }

    /**
     * @return the lastScience
     */
    public Integer getLastScience() {
        return lastScience;
    }

    /**
     * @param lastScience the lastScience to set
     */
    public void setLastScience(Integer lastScience) {
        this.lastScience = lastScience;
    }

    /**
     * @return the dopd
     */
    public Double getDopd() {
        return dopd;
    }

    /**
     * @param dopd the dopd to set
     */
    public void setDopd(Double dopd) {
        this.dopd = dopd;
    }

    /**
     * @return the nfft
     */
    public Integer getNfft() {
        return nfft;
    }

    /**
     * @param nfft the nfft to set
     */
    public void setNfft(Integer nfft) {
        this.nfft = nfft;
    }

    /**
     * A spectrum where the frequencies 
     * @return the spk
     */
    public ComplexArray getSpk() {
        return spk;
    }

    /**
     * @param spk the spk to set
     */
    public void setSpk(ComplexArray spk) {
        this.spk = spk;
    }
    

}
