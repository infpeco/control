package infrarojo.transmitir.concreto;

import android.content.Context;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import infrarojo.log.Logs;
import infrarojo.transmitir.Transmisor;
import infrarojo.transmitir.TransmitirInfo;


public class ObsoleteTransmitter extends Transmisor {


    private final Object irdaService;
    private Method write_irsend;

    @SuppressWarnings("ResourceType")
    public ObsoleteTransmitter(Context context, Logs logger) {
        super(context, logger);
        logger.log("Try to create ObsoleteTransmitter");
        irdaService = context.getSystemService("irda");
        try {
            write_irsend = irdaService.getClass().getMethod("write_irsend", new Class[]{String.class});
        } catch (NoSuchMethodException e) {
            logger.error("ObsoleteTransmitter:NoSuchMethodException", e);
        }
        logger.log("ObsoleteTransmitter created");
    }

    @Override
    public void transmit(TransmitirInfo transmitInfo) {
        try {
            write_irsend.invoke(irdaService, transmitInfo.obsoletePattern);
        } catch (IllegalAccessException e) {
            logger.error("ObsoleteTransmitter:IllegalAccessException", e);
        } catch (InvocationTargetException e) {
            logger.error("ObsoleteTransmitter:InvocationTargetException", e);
        }
    }
}
