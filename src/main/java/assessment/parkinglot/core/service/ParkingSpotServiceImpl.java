package assessment.parkinglot.core.service;

import assessment.parkinglot.common.enums.VehicleType;
import assessment.parkinglot.domain.entity.ParkingSpotEntity;
import assessment.parkinglot.domain.repository.ParkingSpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingSpotServiceImpl implements ParkingSpotService {

    private final ParkingSpotRepository parkingSpotRepository;

    public ParkingSpotServiceImpl(ParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
    }

    public int getAmountOfParkingSpotAvailable() {
        return parkingSpotRepository.getAmountOfParkingSpotAvailable();
    }

    public int getAmountOfParkingSpotAvailable(VehicleType vehicleType) {
        int availableSpot = parkingSpotRepository.getAmountOfParkingSpotAvailable();
        if (VehicleType.VAN.equals(vehicleType)) {
            return availableSpot / 3;
        }
        return availableSpot;
    }

    public List<ParkingSpotEntity> getParkingSpotAvailable() {
        return parkingSpotRepository.getParkingSpotAvailable();
    }

    @Override
    public void removeVehicle(List<ParkingSpotEntity> parkingSpots) {
        parkingSpots.forEach(parkingSpot -> {
            parkingSpot.setVehicle(null);
            parkingSpotRepository.save(parkingSpot);
        });
    }

}
