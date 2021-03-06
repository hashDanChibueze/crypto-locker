package com.psiphiglobal.proto._core;


import com.psiphiglobal.proto.endpoints.DocumentEndpoint;
import com.psiphiglobal.proto.endpoints.HealthEndpoint;
import com.psiphiglobal.proto.endpoints.ShareEndpoint;
import com.psiphiglobal.proto.endpoints.UserEndpoint;
import com.psiphiglobal.proto.logging.RequestLogger;
import com.psiphiglobal.proto.logging.ResponseLogger;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath(Constants.BASE_URI)
public class ProtoApplication extends Application
{
    public ProtoApplication()
    {
    }

    @Override
    public Set<Class<?>> getClasses()
    {
        Set<Class<?>> resources = new HashSet<>();

        /* Logger */
        resources.add(RequestLogger.class);
        resources.add(ResponseLogger.class);

        /* Endpoints */
        resources.add(HealthEndpoint.class);
        resources.add(UserEndpoint.class);
        resources.add(DocumentEndpoint.class);
        resources.add(ShareEndpoint.class);

        /* Error Handler */
        resources.add(DefaultExceptionHandler.class);

        return resources;
    }
}