package infrarojo.transmitir.concreto;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.ConsumerIrManager;
import android.os.Build;

import infrarojo.log.Logs;
import infrarojo.transmitir.Transmisor;
import infrarojo.transmitir.TransmitirInfo;

import static android.content.Context.CONSUMER_IR_SERVICE;

public class ActualTransmitter extends Transmisor {

    private final ConsumerIrManager consumerIrManager;

    public ActualTransmitter(Context context, Logs logger) {
        super(context, logger);
        logger.log("Try to create ActualTransmitter");
        this.consumerIrManager = getConsumerIrManager();
        logger.log("ActualTransmitter created");
    }

    @SuppressWarnings("ResourceType")
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private ConsumerIrManager getConsumerIrManager() {
        return (ConsumerIrManager) context.getSystemService(CONSUMER_IR_SERVICE);
    }

    @Override
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void transmit(TransmitirInfo transmitInfo) {
        logger.log("Try to transmit");
        consumerIrManager.transmit(transmitInfo.frequency, transmitInfo.pattern);
    }

}
