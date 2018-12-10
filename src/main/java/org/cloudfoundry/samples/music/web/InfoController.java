package org.cloudfoundry.samples.music.web;

import org.cloudfoundry.samples.music.domain.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.service.ServiceInfo;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class InfoController {

    private Environment springEnvironment;

    @Autowired
    private DataSource dataSource;


    @Autowired
    public InfoController(Environment springEnvironment) {
        this.springEnvironment = springEnvironment;
    }

    @RequestMapping(value = "/appinfo")
    public ApplicationInfo info() {
        String cloudInfo;
        final Cloud cloud = getCloud();
        if (cloud != null) {
            cloudInfo = cloud.getApplicationInstanceInfo().toString();
        } else {
            cloudInfo = "cloud obj. was null";
        }


        String dbConnection = "";
        if (dataSource != null) {
            try {
                dbConnection = dataSource.getConnection().toString();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        ApplicationInfo applicationInfo = new ApplicationInfo(springEnvironment.getActiveProfiles(), getServiceNames());
        applicationInfo.setDbInfo(dbConnection);
        applicationInfo.setCloudInfo(cloudInfo);
        return applicationInfo;
    }

    private Cloud getCloud() {
        CloudFactory cloudFactory = new CloudFactory();
        return cloudFactory.getCloud();
    }

    @RequestMapping(value = "/service")
    public List<ServiceInfo> showServiceInfo() {
        final Cloud cloud = getCloud();
        if (cloud != null) {
            return cloud.getServiceInfos();
        } else {
            return new ArrayList<>();
        }
    }

    private String[] getServiceNames() {
        final Cloud cloud = getCloud();
        if (cloud != null) {
            final List<ServiceInfo> serviceInfos = cloud.getServiceInfos();

            List<String> names = new ArrayList<>();
            for (ServiceInfo serviceInfo : serviceInfos) {
                names.add(serviceInfo.getId());
            }
            return names.toArray(new String[names.size()]);
        } else {
            return new String[]{};
        }
    }
}