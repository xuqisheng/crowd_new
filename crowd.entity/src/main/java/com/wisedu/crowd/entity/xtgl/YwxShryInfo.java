package com.wisedu.crowd.entity.xtgl;

import java.io.Serializable;

public class YwxShryInfo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 
     */
    private String wid;

    /**
     * 
     */
    private String kfzid;

    /**
     * 
     */
    private String ywxid;

    /**
     * 
     */
    private Integer sftjgzl;

    /**
     * 
     * @return WID 
     */
    public String getWid() {
        return wid;
    }

    /**
     * 
     * @param wid 
     */
    public void setWid(String wid) {
        this.wid = wid == null ? null : wid.trim();
    }

    /**
     * 
     * @return KFZID 
     */
    public String getKfzid() {
        return kfzid;
    }

    /**
     * 
     * @param kfzid 
     */
    public void setKfzid(String kfzid) {
        this.kfzid = kfzid == null ? null : kfzid.trim();
    }

    /**
     * 
     * @return YWXID 
     */
    public String getYwxid() {
        return ywxid;
    }

    /**
     * 
     * @param ywxid 
     */
    public void setYwxid(String ywxid) {
        this.ywxid = ywxid == null ? null : ywxid.trim();
    }

    /**
     * 
     * @return SFTJGZL 
     */
    public Integer getSftjgzl() {
        return sftjgzl;
    }

    /**
     * 
     * @param sftjgzl 
     */
    public void setSftjgzl(Integer sftjgzl) {
        this.sftjgzl = sftjgzl;
    }

    /**
     *
     * @mbggenerated 2017-12-27
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", wid=").append(wid);
        sb.append(", kfzid=").append(kfzid);
        sb.append(", ywxid=").append(ywxid);
        sb.append(", sftjgzl=").append(sftjgzl);
        sb.append("]");
        return sb.toString();
    }
}