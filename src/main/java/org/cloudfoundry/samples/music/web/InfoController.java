package org.cloudfoundry.samples.music.web;

import org.cloudfoundry.samples.music.domain.ApplicationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudException;
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
        ApplicationInfo applicationInfo = new ApplicationInfo(springEnvironment.getActiveProfiles(), getServiceNames());

        String dbConnection = "";
        if (dataSource != null) {
            try {
                dbConnection = dataSource.getConnection().toString();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        applicationInfo.setDbInfo(dbConnection);
        final Cloud cloud = getCloud();
        if (cloud != null) {
            applicationInfo.setAppId(cloud.getApplicationInstanceInfo().getAppId());
            applicationInfo.setInstanceId(cloud.getApplicationInstanceInfo().getInstanceId());
        }
        return applicationInfo;
    }


    @RequestMapping(value = "/service")
    public List<ServiceInfo> showServiceInfo() {
        if (getCloud() != null) {
            return getCloud().getServiceInfos();
        } else {
            return new ArrayList<>();
        }
    }


    private String[] getServiceNames() {
        if (getCloud() != null) {
            final List<ServiceInfo> serviceInfos = getCloud().getServiceInfos();

            List<String> names = new ArrayList<>();
            for (ServiceInfo serviceInfo : serviceInfos) {
                names.add(serviceInfo.getId());
            }
            return names.toArray(new String[names.size()]);
        } else {
            return new String[]{};
        }
    }


    private Cloud getCloud() {
        try {
            CloudFactory cloudFactory = new CloudFactory();
            return cloudFactory.getCloud();
        } catch (CloudException ce) {
            return null;
        }
    }

}