package nl.zvnv.task1;

import nl.zvnv.task1.Objects.CargoPier;
import nl.zvnv.task1.Objects.Tunnel;
import nl.zvnv.task1.Objects.util.CargoType;
import nl.zvnv.task1.Objects.util.ShipVolume;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class AppTest {
    @Test
    void testPierGoodsType() {
        CargoType cargoType = CargoType.BREAD;
        CargoPier cargoPier = new CargoPier(cargoType);

        ShipPlaceholder ship = new ShipPlaceholder(cargoType, ShipVolume.SMALL);
        ShipPlaceholder badTypeShip = new ShipPlaceholder(CargoType.CLOTHES, ShipVolume.SMALL);

        cargoPier.checkAndLoad(ship);
        cargoPier.checkAndLoad(badTypeShip);

        Assertions.assertThat(ship.value).isEqualTo(10);
        Assertions.assertThat(badTypeShip.value).isEqualTo(0);
    }

    @Test
    void testLoadVolume() {
        CargoType cargoType = CargoType.BREAD;
        CargoPier cargoPier = new CargoPier(cargoType);

        ShipPlaceholder smallShip = new ShipPlaceholder(cargoType, ShipVolume.SMALL);
        ShipPlaceholder mediumShip = new ShipPlaceholder(cargoType, ShipVolume.MEDIUM);
        ShipPlaceholder largeShip = new ShipPlaceholder(cargoType, ShipVolume.LARGE);

        cargoPier.checkAndLoad(smallShip);
        cargoPier.checkAndLoad(mediumShip);
        cargoPier.checkAndLoad(largeShip);

        Assertions.assertThat(smallShip.value).isEqualTo(10);
        Assertions.assertThat(mediumShip.value).isEqualTo(50);
        Assertions.assertThat(largeShip.value).isEqualTo(100);
    }

    @Test
    void testTunnel() {
        int duration = 1000;
        Tunnel tunnel = new Tunnel(1, duration);
        ShipPlaceholder ship = new ShipPlaceholder(CargoType.CLOTHES, ShipVolume.SMALL);

        Assertions.assertThatNoException().isThrownBy(() -> tunnel.goThrough(ship));
        Assertions.assertThat(ship.duration).isEqualTo(duration);
    }
}
