package infrarojo.transmitir;

import android.content.Context;

import java.util.List;

import infrarojo.dispositivos.DispositivosIR;
import infrarojo.log.Logs;
import infrarojo.transmitir.concreto.ActualTransmitter;
import infrarojo.transmitir.concreto.HtcTransmitter;
import infrarojo.transmitir.concreto.LeTransmitter;
import infrarojo.transmitir.concreto.LgWithDeviceTransmitter;
import infrarojo.transmitir.concreto.LgWithoutDeviceTransmitter;
import infrarojo.transmitir.concreto.ObsoleteTransmitter;
import infrarojo.transmitir.concreto.UndefinedTransmitter;

public abstract class Transmisor {
    public static Transmisor getTransmitterByType(TransmitirInfo transmitterType, Context context, Logs logger) {
        logger.log("Obtener transmisor por tipo: " + transmitterType);
        switch (transmitterType) {
            case Actual:
                return new ActualTransmitter(context, logger);
            case Obsolete:
                return new ObsoleteTransmitter(context, logger);
            case HTC:
                return new HtcTransmitter(context, logger);
            case LG:
                return new LgWithDeviceTransmitter(context, logger);
            case Le:
                return new LeTransmitter(context, logger);
            case LG_WithOutDevice:
                return new LgWithoutDeviceTransmitter(context, logger);
            case LG_Actual:
                return new ActualTransmitter(context, logger);
            default:
                return new UndefinedTransmitter(context, logger);
        }
    }


    protected final Context context;
    protected final Logs logger;

    public Transmisor(Context context, Logs logger) {
        this.context = context;
        this.logger = logger;
    }

    public void start() {
    }

    public abstract void transmit(TransmitirInfo transmitInfo);

    public boolean isReady() {
        return true;
    }

    public void stop() {
    }

    public void transmit(int deviceId, int functionId, int duration) {
        throw new RuntimeException("Transmitting by device id " + deviceId + " and function id " + functionId + " with duration " + duration + " not supported");
    }

    public List<DispositivosIR> getIrDevices(Logs log) {
        throw new RuntimeException("getIrDevices not supported");
    }
}
