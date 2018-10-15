package org.openbox.dashboard.websocket;

import org.moonlightcontroller.managers.ConnectionManager;
import org.moonlightcontroller.managers.XidGenerator;
import org.moonlightcontroller.topology.InstanceLocationSpecifier;
import org.openbox.dashboard.DashboardMessageRequest;
import org.openbox.dashboard.DashboardRequestSender;
import org.openbox.dashboard.NetworkInformationService;
import org.openbox.dashboard.SouthboundProfiler;
import org.openboxprotocol.exceptions.InstanceNotAvailableException;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

@Controller
public class DashboardController {

    @SubscribeMapping("/topic/topology")
    public Map<String, List> topologyInit() {
        return NetworkInformationService.getInstance().getTopologyGraph();
    }

    public static void start() {

    }


    @Scheduled(fixedRate = 20000)
    private void scheduledStatRequests() throws InstanceNotAvailableException {

        for (InstanceLocationSpecifier locationSpecifier : ConnectionManager.getInstance().getAliveInstances()) {

            DashboardMessageRequest message = new DashboardMessageRequest();

            message.setLocationSpecifier(locationSpecifier);

            int xid = XidGenerator.generateXid();
            try {
                ConnectionManager.getInstance().sendMessage(
                        xid,
                        locationSpecifier,
                        message.getRequestMessage(),
                        new DashboardRequestSender());
            } catch (InstanceNotAvailableException ignore) {} // no instance is running on the location

        }
    }

    @Scheduled(fixedRate = 30000)
    private void scheduledKeepAliveCheck() throws InstanceNotAvailableException {
        for (InstanceLocationSpecifier locationSpecifiers : ConnectionManager.getInstance().getDeadInstances()) {
            NetworkInformationService.getInstance().removeOBI(locationSpecifiers.getId());
        }
    }
}
