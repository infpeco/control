package infrarojo.detectar;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import infrarojo.detectar.marcas.ActualDetector;
import infrarojo.detectar.marcas.HtcDetector;
import infrarojo.detectar.marcas.LeDetector;
import infrarojo.detectar.marcas.LgDetector;
import infrarojo.detectar.marcas.ObsoleteSamsungDetector;
import infrarojo.log.Logs;
import infrarojo.transmitir.TransmisorTipo;

import java.util.ArrayList;
import java.util.List;

public class DetectorDeInfraRojo {
    public static class DetectorParams {
        public final Context context;
        public final Logs logger;

        public DetectorParams(Context context, Logs logger) {
            this.context = context;
            this.logger = logger;
        }


        public boolean hasAnyPackage(String... packageNames) {
            PackageManager manager = context.getPackageManager();
            List<ApplicationInfo> packages = manager.getInstalledApplications(0);
            for (ApplicationInfo info : packages) {
                for (String packageName : packageNames) {
                    if (info.packageName.startsWith("com.uei.")) {
                        logger.log("Package: " + info.packageName);
                    }
                    if (info.packageName.contains(packageName)) {
                        return true;
                    }
                }
            }
            return false;
        }

    }

    protected final DetectorParams detectorParams;
    protected final List<IDetector> detectors = new ArrayList<>();

    public DetectorDeInfraRojo(Context context, Logs logger) {
        this.detectorParams = new DetectorParams(context, logger);

        logger.log("Build.MANUFACTURER: " + Build.MANUFACTURER);

        this.detectors.add(new LgDetector());
        this.detectors.add(new HtcDetector());
        this.detectors.add(new LeDetector());
        this.detectors.add(new ObsoleteSamsungDetector());
        this.detectors.add(new ActualDetector());
    }

    private TransmisorTipo detect(DetectorParams detectorParams) {
        for (IDetector detector : detectors) {
            if (detector.hasTransmitter(detectorParams)) {
                return detector.getTransmitterType();
            }
        }
        return TransmisorTipo.Undefined;
    }

    public TransmisorTipo detect() {
        detectorParams.logger.log("Detection started");
        TransmisorTipo transmitterType = detect(detectorParams);
        detectorParams.logger.log("Detection result: " + transmitterType);
        return transmitterType;
    }
}
