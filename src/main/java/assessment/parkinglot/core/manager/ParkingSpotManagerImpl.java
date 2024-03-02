package assessment.parkinglot.core.manager;

import assessment.parkinglot.common.enums.VehicleType;
import assessment.parkinglot.common.response.SpotAvailableResponse;
import assessment.parkinglot.core.service.ParkingSpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParkingSpotManagerImpl implements ParkingSpotManager {

    private final ParkingSpotService parkingSpotService;
    public SpotAvailableResponse getParkingSpotAvailable() {
        return new SpotAvailableResponse(parkingSpotService.getAmountOfParkingSpotAvailable());
    }

    public SpotAvailableResponse getParkingSpotAvailable(VehicleType vehicleType) {
        return new SpotAvailableResponse(parkingSpotService.getAmountOfParkingSpotAvailable(vehicleType));
    }

}
