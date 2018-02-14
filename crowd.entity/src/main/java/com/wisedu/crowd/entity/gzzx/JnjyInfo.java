package com.wisedu.crowd.entity.gzzx;

import java.io.Serializable;


public class JnjyInfo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * WID
     */
    private String wid;

    /**
     * 建议内容
     */
    private String content;

    /**
     * 发布时间
     */
    private String fbsj;

    /**
     * 发布人
     */
    private String fbr;

    /**
     * 响应时间
     */
    private String xysj;

    /**
     * 响应人
     */
    private String xyr;

    /**
     * 完成时间
     */
    private String wcsj;

    /**
     * 处理人
     */
    private String clr;

    /**
     * 状态
     */
    private String zt;

    /**
     * 处理意见
     */
    private String clyj;

    /**
     * 技能类型
     */
    private String jnlx;

    /**
     * WID
     * @return WID WID
     */
    public String getWid() {
        return wid;
    }

    /**
     * WID
     * @param wid WID
     */
    public void setWid(String wid) {
        this.wid = wid == null ? null : wid.trim();
    }

    /**
     * 建议内容
     * @return CONTENT 建议内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 建议内容
     * @param content 建议内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 发布时间
     * @return FBSJ 发布时间
     */
    public String getFbsj() {
        return fbsj;
    }

    /**
     * 发布时间
     * @param fbsj 发布时间
     */
    public void setFbsj(String fbsj) {
        this.fbsj = fbsj == null ? null : fbsj.trim();
    }

    /**
     * 发布人
     * @return FBR 发布人
     */
    public String getFbr() {
        return fbr;
    }

    /**
     * 发布人
     * @param fbr 发布人
     */
    public void setFbr(String fbr) {
        this.fbr = fbr == null ? null : fbr.trim();
    }

    /**
     * 响应时间
     * @return XYSJ 响应时间
     */
    public String getXysj() {
        return xysj;
    }

    /**
     * 响应时间
     * @param xysj 响应时间
     */
    public void setXysj(String xysj) {
        this.xysj = xysj == null ? null : xysj.trim();
    }

    /**
     * 响应人
     * @return XYR 响应人
     */
    public String getXyr() {
        return xyr;
    }

    /**
     * 响应人
     * @param xyr 响应人
     */
    public void setXyr(String xyr) {
        this.xyr = xyr == null ? null : xyr.trim();
    }

    /**
     * 完成时间
     * @return WCSJ 完成时间
     */
    public String getWcsj() {
        return wcsj;
    }

    /**
     * 完成时间
     * @param wcsj 完成时间
     */
    public void setWcsj(String wcsj) {
        this.wcsj = wcsj == null ? null : wcsj.trim();
    }

    /**
     * 处理人
     * @return CLR 处理人
     */
    public String getClr() {
        return clr;
    }

    /**
     * 处理人
     * @param clr 处理人
     */
    public void setClr(String clr) {
        this.clr = clr == null ? null : clr.trim();
    }

    /**
     * 状态
     * @return ZT 状态
     */
    public String getZt() {
        return zt;
    }

    /**
     * 状态
     * @param zt 状态
     */
    public void setZt(String zt) {
        this.zt = zt == null ? null : zt.trim();
    }

    /**
     * 处理意见
     * @return CLYJ 处理意见
     */
    public String getClyj() {
        return clyj;
    }

    /**
     * 处理意见
     * @param clyj 处理意见
     */
    public void setClyj(String clyj) {
        this.clyj = clyj == null ? null : clyj.trim();
    }

    /**
     * 技能类型
     * @return JNLX 技能类型
     */
    public String getJnlx() {
        return jnlx;
    }

    /**
     * 技能类型
     * @param jnlx 技能类型
     */
    public void setJnlx(String jnlx) {
        this.jnlx = jnlx == null ? null : jnlx.trim();
    }

    /**
     *
     * @mbggenerated 2017-12-26
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", wid=").append(wid);
        sb.append(", content=").append(content);
        sb.append(", fbsj=").append(fbsj);
        sb.append(", fbr=").append(fbr);
        sb.append(", xysj=").append(xysj);
        sb.append(", xyr=").append(xyr);
        sb.append(", wcsj=").append(wcsj);
        sb.append(", clr=").append(clr);
        sb.append(", zt=").append(zt);
        sb.append(", clyj=").append(clyj);
        sb.append(", jnlx=").append(jnlx);
        sb.append("]");
        return sb.toString();
    }
}