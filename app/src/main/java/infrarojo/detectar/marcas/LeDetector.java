package infrarojo.detectar.marcas;


import infrarojo.detectar.DetectorDeInfraRojo;
import infrarojo.detectar.IDetector;
import infrarojo.transmitir.TransmisorTipo;
import infrarojo.utils.Constantes;

/**
 * Created by Andrew on 20.10.2017
 */

public class LeDetector implements IDetector {
    @Override
    public boolean hasTransmitter(DetectorDeInfraRojo.DetectorParams detectorParams) {
        try {
            boolean hasPackage = detectorParams.hasAnyPackage(Constantes.LE_COOLPAD_IR_SERVICE_PACKAGE, Constantes.LE_DEFAULT_IR_SERVICE_PACKAGE_2);
            detectorParams.logger.log("Check Le IR interface: " + hasPackage);
            return hasPackage;
        } catch (Exception e) {
            detectorParams.logger.error("On Le ir error", e);
            return false;
        }
    }

    @Override
    public TransmisorTipo getTransmitterType() {
        return TransmisorTipo.Le;
    }
}
