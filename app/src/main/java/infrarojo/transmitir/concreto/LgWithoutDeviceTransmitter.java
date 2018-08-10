package infrarojo.transmitir.concreto;

import android.content.Context;


import infrarojo.log.Logs;

public class LgWithoutDeviceTransmitter extends LgTransmitter {
    public LgWithoutDeviceTransmitter(Context context, Logs logger) {
        super(context, logger);
    }

    @Override
    protected void beforeSendIr() {
        // Do nothing before send ir command
    }
}
