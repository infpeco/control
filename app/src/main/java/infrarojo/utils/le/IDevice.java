package infrarojo.utils.le;

import infrarojo.utils.le.Device.DeviceTypes;

public interface IDevice {
    String getBrand();

    String getDeviceTypeName();

    int getId();

    String getModel();

    String getName();

    DeviceTypes getType();

    String getVersion();
}
