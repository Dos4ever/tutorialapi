package com.tutorialapi.server;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.eclipse.jetty.http.HttpScheme.HTTPS;
import static org.eclipse.jetty.http.HttpVersion.HTTP_1_1;

public class TutorialApiServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(TutorialApiServer.class);
    public static void main(String[] args) throws Exception {
        LOGGER.info("Hello Word!");

        HttpConfiguration httpConfiguration = new HttpConfiguration();
        httpConfiguration.setSecureScheme(HTTPS.asString());
        httpConfiguration.setSecurePort(8443);
        httpConfiguration.addCustomizer(new SecureRequestCustomizer());
        httpConfiguration.setSendServerVersion(false);

        HttpConnectionFactory httpConnectionFactory = new HttpConnectionFactory(httpConfiguration);

        SslContextFactory.Server sslContextFactory = new SslContextFactory.Server();
        sslContextFactory.setKeyStorePath("C:\\Users\\Dos4ever\\IdeaProjects\\tutorialapi\\tutorialapi-server\\src\\main\\resources\\certs\\tutorialapi.p12");
        sslContextFactory.setKeyStorePassword("lifechange");
        sslContextFactory.setKeyManagerPassword("lifechange");
        sslContextFactory.setTrustAll(true);

        SslConnectionFactory sslConnectionFactory = new SslConnectionFactory(sslContextFactory, HTTP_1_1.asString());

        Server server = new Server();

        ServerConnector serverConnector = new ServerConnector(server, sslConnectionFactory, httpConnectionFactory);
        serverConnector.setName("secure");
        serverConnector.setPort(httpConfiguration.getSecurePort());

        server.addConnector(serverConnector);

        server.start();
        server.join();
    }
}
