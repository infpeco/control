package infrarojo.transmitir;

public enum TransmisorTipo {
    Undefined,
    Obsolete, // Samsung
    Actual,   // ConsumerIRManager
    HTC,
    Le(true),
    LG(true),
    LG_WithOutDevice(true),
    LG_Actual; // ConsumerIRManager

    private final boolean hasIrDevices;

    TransmisorTipo() {
        this(false);
    }

    TransmisorTipo(boolean hasIrDevices) {
        this.hasIrDevices = hasIrDevices;
    }

    public boolean hasIrDevices() {
        return hasIrDevices;
    }
}
