package infrarojo.detectar;

import infrarojo.transmitir.TransmisorTipo;

public interface IDetector {
    boolean hasTransmitter(DetectorDeInfraRojo.DetectorParams detectorParams);
    TransmisorTipo getTransmitterType();
}
