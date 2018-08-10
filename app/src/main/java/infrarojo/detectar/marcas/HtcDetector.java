package infrarojo.detectar.marcas;


import infrarojo.detectar.DetectorDeInfraRojo;
import infrarojo.detectar.IDetector;
import infrarojo.transmitir.TransmisorTipo;

public class HtcDetector implements IDetector {

    /**
     * Code from samples in HTC IR SDK
     */
    @Override
    public boolean hasTransmitter(DetectorDeInfraRojo.DetectorParams detectorParams) {
        try {
            boolean hasPackage = detectorParams.hasAnyPackage("com.htc.cirmodule");
            detectorParams.logger.log("Check HTC IR interface: " + hasPackage);
            return hasPackage;
        } catch (Exception e) {
            detectorParams.logger.error("On HTC ir error", e);
            return false;
        }
    }


    @Override
    public TransmisorTipo getTransmitterType() {
        return TransmisorTipo.HTC;
    }
}
