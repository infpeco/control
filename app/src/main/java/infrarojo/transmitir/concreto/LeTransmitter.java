package infrarojo.transmitir.concreto;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;

//import com.obd.infrared.devices.IrDevice;
//import com.obd.infrared.devices.IrFunction;
//import com.obd.infrared.log.Logger;
//import com.obd.infrared.transmit.TransmitInfo;
//import com.obd.infrared.transmit.Transmitter;
//import com.obd.infrared.utils.Constants;
//import com.obd.infrared.utils.le.Device;
//import com.obd.infrared.utils.le.IControl;
//import com.obd.infrared.utils.le.IRAction;
//import com.obd.infrared.utils.le.IRFunction;

import java.util.ArrayList;
import java.util.List;

import infrarojo.dispositivos.DispositivosIR;
import infrarojo.dispositivos.FuncionIR;
import infrarojo.log.Logs;
import infrarojo.transmitir.Transmisor;
import infrarojo.transmitir.TransmitirInfo;
import infrarojo.utils.Constantes;
import infrarojo.utils.le.Device;
import infrarojo.utils.le.IControl;
import infrarojo.utils.le.IRAction;
import infrarojo.utils.le.IRFunction;

/**
 * Created by Andrew on 20.10.2017
 */

public class LeTransmitter extends Transmisor {

    private ServiceConnection mControlServiceConnection = new ConnectorListener();
    private IControl remoteControl;

    public LeTransmitter(Context context, Logs logger) {
        super(context, logger);
    }

    @Override
    public boolean isReady() {
        return remoteControl != null;
    }

    @Override
    public void transmit(TransmitirInfo transmitInfo) {
        if (isReady()) {
            try {
                remoteControl.transmit(transmitInfo.frequency, transmitInfo.pattern);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void transmit(int deviceId, int functionId, int duration) {
        if (isReady()) {
            try {
                logger.log("Transmitting with device id " + deviceId + " and function id " + functionId + " and duration " + duration);
                remoteControl.sendIR(new IRAction(deviceId, functionId, duration));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void start() {
        final String UEICONTROLPACKAGE = Build.BRAND.contains("Coolpad") ? Constantes.LE_COOLPAD_IR_SERVICE_PACKAGE : Constantes.LE_DEFAULT_IR_SERVICE_PACKAGE_2;

        try {
            Intent controlIntent = new Intent(IControl.DESCRIPTOR);
            controlIntent.setClassName(UEICONTROLPACKAGE, "com.uei.control.Service");
            context.bindService(controlIntent, this.mControlServiceConnection, Context.BIND_AUTO_CREATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        try {
            context.unbindService(this.mControlServiceConnection);
            remoteControl = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<DispositivosIR> getIrDevices(Logs logger) {
        try {
            Device[] devices = remoteControl.getDevices();
            logger.log("Devices from LE: " + (devices != null));
            List<DispositivosIR> result = new ArrayList<>();
            if (devices != null && devices.length > 0) {
                logger.log("Devices size: " + devices.length);
                for (Device device : devices) {
                    DispositivosIR irDevice = new DispositivosIR(device.Id, device.Name);

                    if (device.KeyFunctions != null && device.KeyFunctions.size() > 0) {
                        for (IRFunction function : device.KeyFunctions) {
                            FuncionIR irFunction = new FuncionIR(function.Id, function.Name, function.IsLearned);
                            irDevice.addFunction(irFunction);
                        }
                        result.add(irDevice);
                        continue;
                    }

                    if (device.Functions != null && device.Functions.length > 0) {
                        String[] labelsByDevice = remoteControl.getAllFunctionLabelsByDevice(device.Id, device.Functions);
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
            logger.error("LE", e);
            e.printStackTrace();
            return null;
        }
    }

    private class ConnectorListener implements ServiceConnection {
        ConnectorListener() {
        }

        public void onServiceDisconnected(ComponentName name) {
            remoteControl = null;
        }

        public void onServiceConnected(ComponentName name, IBinder service) {
            remoteControl = new IControl(service);
        }
    }

}
