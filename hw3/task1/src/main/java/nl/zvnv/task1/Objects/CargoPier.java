package nl.zvnv.task1.Objects;

import nl.zvnv.task1.Objects.ship.IShip;
import nl.zvnv.task1.Objects.util.CargoType;

public class CargoPier {
    private final CargoType cargoType;
    public CargoPier(CargoType cargoType) {
        this.cargoType = cargoType;
    }
    public synchronized void checkAndLoad(IShip ship) {
        if (ship.getCargoType() == cargoType) {
            ship.loadCargo(ship.getVolume().getVolumeValue());
        }
    }
}
