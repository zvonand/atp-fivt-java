package nl.zvnv.task1;

import nl.zvnv.task1.Objects.ship.IShip;
import nl.zvnv.task1.Objects.util.CargoType;
import nl.zvnv.task1.Objects.util.ShipVolume;

public class ShipPlaceholder implements IShip {
    private final CargoType cargoType;
    private final ShipVolume volume;
    public int value;
    public int duration;

    public ShipPlaceholder(CargoType cargoType,
                           ShipVolume volume) {
        this.cargoType = cargoType;
        this.volume = volume;
    }

    public ShipVolume getVolume() {
        return volume;
    }

    public CargoType getCargoType() {
        return cargoType;
    }

    public void passTunnel(int duration) {
        this.duration = duration;
    }

    public void loadCargo(int value) {
        this.value = value;
    }

    @Override
    public void run() {
        return;
    }
}
