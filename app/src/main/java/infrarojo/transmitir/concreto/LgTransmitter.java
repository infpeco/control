package infrarojo.transmitir.concreto;

import android.content.Context;

//import com.lge.hardware.IRBlaster.Device;
//import com.lge.hardware.IRBlaster.IRAction;
//import com.lge.hardware.IRBlaster.IRBlaster;
//import com.lge.hardware.IRBlaster.IRBlasterCallback;
//import com.lge.hardware.IRBlaster.IRFunction;
//import com.lge.hardware.IRBlaster.ResultCode;
//import com.obd.infrared.devices.IrDevice;
//import com.obd.infrared.devices.IrFunction;
//import com.obd.infrared.log.Logger;
//import com.obd.infrared.transmit.TransmitInfo;
//import com.obd.infrared.transmit.Transmitter;

import java.util.ArrayList;
import java.util.List;

import infrarojo.dispositivos.DispositivosIR;
import infrarojo.dispositivos.FuncionIR;
import infrarojo.log.Logs;
import infrarojo.transmitir.Transmisor;
import infrarojo.transmitir.TransmitirInfo;


public abstract class LgTransmitter extends Transmisor implements IRBlasterCallback {

    protected final IRBlaster irBlaster;

    public LgTransmitter(Context context, Logs logger) {
        super(context, logger);
        logger.log("Try to create LG IRBlaster");
        irBlaster = IRBlaster.getIRBlaster(context, this);
        logger.log("IRBlaster created");
    }

    @Override
    public void start() {
        logger.log("Start not supported in LG IRBlaster");
    }

    @Override
    public void transmit(TransmitirInfo transmitInfo) {
        try {
            if (isReady) {
                beforeSendIr();
                logger.log("Try to transmit LG IRBlaster");

                int resultCode = irBlaster.sendIRPattern(transmitInfo.frequency, transmitInfo.pattern);
                logger.log("Result: " + ResultCode.getString(resultCode));
            } else {
                logger.log("LG IRBlaster not ready");
            }
        } catch (Exception e) {
            logger.error("On try to transmit LG IRBlaster", e);
        }
    }

    @Override
    public void transmit(int deviceId, int functionId, int duration) {
        try {
            if (isReady) {
                logger.log("Transmitting with device id " + deviceId + " and function id " + functionId + " and duration " + duration);
                irBlaster.sendIR(new IRAction(deviceId, functionId, duration));
            } else {
                logger.log("LG IRBlaster not ready");
            }
        } catch (Exception e) {
            logger.error("On try to transmit LG IRBlaster", e);
        }
    }

    protected abstract void beforeSendIr();

    @Override
    public void stop() {
        try {
            logger.log("Try to close LG IRBlaster");
            irBlaster.close();
        } catch (Exception e) {
            logger.error("On try to close LG IRBlaster", e);
        }
    }

    private boolean isReady = false;

    @Override
    public boolean isReady() {
        return isReady;
    }

    @Override
    public void IRBlasterReady() {
        isReady = true;
        logger.log("LG IRBlaster ready");
    }

    @Override
    public void learnIRCompleted(int i) {
        logger.log("LG IRBlaster.learnIRCompleted : " + i);
    }

    @Override
    public void newDeviceId(int i) {
        logger.log("LG IRBlaster.newDeviceId : " + i);
    }

    @Override
    public void failure(int i) {
        logger.log("LG IRBlaster.failure : " + i);
    }

    @Override
    public List<DispositivosIR> getIrDevices(Logs logger) {
        try {
            Device[] devices = irBlaster.getDevices();
            List<DispositivosIR> result = new ArrayList<>();
            if (devices != null && devices.length > 0) {
                logger.log("Devices size: " + devices.length);

                for (Device device : devices) {
                    DispositivosIR irDevice = new DispositivosIR(device.Id, device.Name);

                    if (device.KeyFunctions != null && device.KeyFunctions.size() > 0) {
                        for (FuncionIR function : device.KeyFunctions) {
                            FuncionIR irFunction = new FuncionIR(function.Id, function.Name, function.IsLearned);
                            irDevice.addFunction(irFunction);
                        }
                        result.add(irDevice);
                        continue;
                    }

                    if (device.Functions != null && device.Functions.length > 0) {
                        String[] labelsByDevice = irBlaster.getAllFunctionLabels(device.Id, device.Functions);
                        for (int i = 0; i < device.Functions.length; i++) {
                            FuncionIR irFunction = new FuncionIR(device.Functions[i], labelsByDevice[i], null);
                            irDevice.addFunction(irFunction);
                        }
                        result.add(irDevice);
                    }
                }
            }
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

