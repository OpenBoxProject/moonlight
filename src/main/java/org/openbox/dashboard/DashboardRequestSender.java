package org.openbox.dashboard;

import org.moonlightcontroller.managers.models.IRequestSender;
import org.moonlightcontroller.managers.models.messages.Error;
import org.moonlightcontroller.managers.models.messages.IMessage;
import org.moonlightcontroller.managers.models.messages.ReadResponse;

import java.util.logging.Logger;

public class DashboardRequestSender implements IRequestSender {
    private final static Logger LOG = Logger.getLogger(DashboardRequestSender.class.getName());

    @Override
    public void onSuccess(IMessage message) {
        if (message instanceof ReadResponse){
            ReadResponse rr = (ReadResponse)message;

            LOG.info("got a read response:" + rr.getBlockId() + "::" + rr.getReadHandle() + "::" + rr.getResult());
        }
    }

    @Override
    public void onFailure(Error err) {
        LOG.info("got an error:" + err.getError_type() + "::" + err.getExtended_message());
    }
}