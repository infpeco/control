//package infrarojo.detectar.marcas;
//
//import android.os.Build;
//
////import com.lge.hardware.IRBlaster.IRBlaster;
//import infrarojo.detectar.DetectorDeDispositivos;
//import infrarojo.detectar.DetectorDeInfraRojo;
//import infrarojo.detectar.IDetector;
//import infrarojo.transmitir.TransmisorTipo;
//
//public class LgDetector implements IDetector {
//
//    private TransmisorTipo transmitterType = TransmisorTipo.LG;
//
//    @Override
//    public boolean hasTransmitter(DetectorDeInfraRojo.DetectorParams detectorParams) {
//        try {
//            detectorParams.logger.log("Check LG: " + DetectorDeDispositivos.isLg());
//            if (DetectorDeDispositivos.isLg()) {
//                // in the Android version 5.1 on LG G4 work ConsumerIRManager
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
//                    ActualDetector actualDetector = new ActualDetector();
//                    if (actualDetector.hasTransmitter(detectorParams)) {
//                        transmitterType = TransmisorTipo.LG_Actual;
//                        return true;
//                    }
//                }
////
////                boolean isSdkSupported = IRBlaster.isSdkSupported(detectorParams.context);
////                detectorParams.logger.log("Check LG IRBlaster " + isSdkSupported);
////                return isSdkSupported;
//            } else {
//                return false;
//            }
//        } catch (Exception e) {
//            detectorParams.logger.error("On LG ir detection error", e);
//            return false;
//        }
//    }
//
//    @Override
//    public TransmisorTipo getTransmitterType() {
//        return transmitterType;
//    }
//}
//
