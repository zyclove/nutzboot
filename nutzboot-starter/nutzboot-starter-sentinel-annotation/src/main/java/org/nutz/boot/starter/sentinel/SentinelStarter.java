package org.nutz.boot.starter.sentinel;

import org.nutz.boot.annotation.PropDoc;
import org.nutz.boot.starter.ServerFace;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

@IocBean
public class SentinelStarter implements ServerFace {
    
    private static final String PRE = "sentinel.";
    
    @Inject("refer:$ioc")
    protected Ioc ioc;
    @Inject
    protected PropertiesProxy conf;

    private static final String CONSOLE_SERVER = "csp.sentinel.dashboard.server";
    private static final String SERVER_PORT = "csp.sentinel.api.port";
    private static final String HEARTBEAT_INTERVAL_MS = "csp.sentinel.heartbeat.interval.ms";
    private static final String HEARTBEAT_CLIENT_IP = "csp.sentinel.heartbeat.client.ip";

    @PropDoc(value = "是否启动Sentinel客户端", defaultValue = "false", type = "boolean")
    public static final String PROP_ENABLED = PRE + "enabled";

    @PropDoc(value = "Sentinel客户端名称,不设置则自动获取nutz.application.name", type = "string")
    public static final String PROP_PROJECR_NAME = PRE + "project.name";

    @PropDoc(value = "Sentinel客户端端口", defaultValue = "8721", type = "int")
    public static final String PROP_SERVER_PORT = PRE + SERVER_PORT;

    @PropDoc(value = "Sentinel控制台地址", defaultValue = "localhost:9090", type = "string")
    public static final String PROP_CONSOLE_SERVER = PRE + CONSOLE_SERVER;

    @PropDoc(value = "Sentinel客户端通信间隔毫秒数", defaultValue = "3000", type = "int")
    public static final String PROP_HEARTBEAT_INTERVAL_MS = PRE + HEARTBEAT_INTERVAL_MS;

    @PropDoc(value = "Sentinel客户端IP,不配置则自动获取本地IP", defaultValue = "", type = "string")
    public static final String PROP_HEARTBEAT_CLIENT_IP = PRE + HEARTBEAT_CLIENT_IP;

    @Override
    public void start() throws Exception {
        if (conf.getBoolean(PROP_SERVER_PORT, false)) {
            System.setProperty("java.net.preferIPv4Stack", "true");
            System.setProperty("project.name", conf.get(PROP_PROJECR_NAME, conf.get("nutz.application.name", conf.get("dubbo.application.name", ""))));
            System.setProperty(SERVER_PORT, conf.get(PROP_SERVER_PORT, "8721"));
            System.setProperty(CONSOLE_SERVER, conf.get(PROP_CONSOLE_SERVER, "localhost:9090"));
            System.setProperty(HEARTBEAT_INTERVAL_MS, conf.get(PROP_HEARTBEAT_INTERVAL_MS, "3000"));
            System.setProperty(HEARTBEAT_CLIENT_IP, conf.get(PROP_HEARTBEAT_CLIENT_IP, ""));
        }
    }
}
