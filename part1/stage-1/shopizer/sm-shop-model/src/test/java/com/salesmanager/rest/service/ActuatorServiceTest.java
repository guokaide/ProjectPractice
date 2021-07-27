package com.salesmanager.rest.service;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class ActuatorServiceTest {

    /**
     * Test {@link ActuatorService#shutdown()}}
     */
    @Test
    public void testPostShutdown() throws URISyntaxException {
        URI uri = new URI("http://127.0.0.1:8080");
        ActuatorService actuatorService = RestClientBuilder.newBuilder()
                .baseUri(uri)
                .build(ActuatorService.class);

        Map<String, String> result = actuatorService.shutdown();

        System.out.println(result);
    }

}
