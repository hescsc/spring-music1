package org.cloudfoundry.samples.music.domain;

public class ApplicationInfo {
    private String[] profiles;
    private String[] services;

    private String instanceId;
    private String appId;

    private String dbInfo;
    private String cloudInfo;
    private String divInfo;

    public ApplicationInfo(String[] profiles, String[] services) {
        this.profiles = profiles;
        this.services = services;
    }

    public String[] getProfiles() {
        return profiles;
    }

    public void setProfiles(String[] profiles) {
        this.profiles = profiles;
    }

    public String[] getServices() {
        return services;
    }

    public void setServices(String[] services) {
        this.services = services;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getDbInfo() {
        return dbInfo;
    }

    public void setDbInfo(String dbInfo) {
        this.dbInfo = dbInfo;
    }

    public String getCloudInfo() {
        return cloudInfo;
    }

    public void setCloudInfo(String cloudInfo) {
        this.cloudInfo = cloudInfo;
    }

    public String getDivInfo() {
        return divInfo;
    }

    public void setDivInfo(String divInfo) {
        this.divInfo = divInfo;
    }
}
