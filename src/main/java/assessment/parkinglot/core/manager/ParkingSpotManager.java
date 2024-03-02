package assessment.parkinglot.core.manager;

import assessment.parkinglot.common.enums.VehicleType;
import assessment.parkinglot.common.response.SpotAvailableResponse;

public interface ParkingSpotManager {

    /**
     * Retrieves the availability of a parking spot.
     *
     * @return         	an object representing the parking spot availability
     */
    SpotAvailableResponse getParkingSpotAvailable();

    /**
     * Retrieves the availability of a parking spot for the specified vehicle type.
     *
     * @param  vehicleType    the type of vehicle for which the parking spot availability is being checked
     */
    SpotAvailableResponse getParkingSpotAvailable(VehicleType vehicleType);

}
