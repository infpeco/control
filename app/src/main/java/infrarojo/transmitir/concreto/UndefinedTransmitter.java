package infrarojo.transmitir.concreto;

import android.content.Context;

import infrarojo.log.Logs;
import infrarojo.transmitir.Transmisor;
import infrarojo.transmitir.TransmitirInfo;

public class UndefinedTransmitter extends Transmisor {

    public UndefinedTransmitter(Context context, Logs logger) {
        super(context, logger);
        logger.warning("Created empty transmitter for undefined type of IR");
    }

    @Override
    public void transmit(TransmitirInfo transmitInfo) {

    }

}
