package assessment.parkinglot.api;

import assessment.parkinglot.common.constants.Constants;
import assessment.parkinglot.common.enums.VehicleType;
import assessment.parkinglot.common.response.SpotAvailableResponse;
import assessment.parkinglot.common.route.Route;
import assessment.parkinglot.core.manager.ParkingSpotManagerImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "ParkingSpot API")
@RestController
@RequiredArgsConstructor
@RequestMapping(Route.BASE_PATH)
public class ParkingSpotController {

    private final ParkingSpotManagerImpl parkingSpotManager;

    /**
     * Get available parking spot
     *
     * @return the response with the available parking spot
     */
    @Operation(description = "Get available parking spot", responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Do calculation and return de result",
                    content = { @Content(
                            schema = @Schema(implementation = SpotAvailableResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )}
            )
    })
    @GetMapping(Route.V1_SPOT_AVAILABLE_PATH)
    public SpotAvailableResponse getParkingSpotAvailable() {
        return parkingSpotManager.getParkingSpotAvailable();
    }

    /**
     * Get available parking spot by type
     *
     * @param vehicleType The type of vehicle
     * @return The available parking spot response
     */
    @Operation(description = "Get available parking spot by type", responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Do calculation and return de result",
                    content = { @Content(
                            schema = @Schema(implementation = SpotAvailableResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                    )}
            )
    })
    @GetMapping(Route.V1_SPOT_PATH)
    public SpotAvailableResponse getParkingSpots(
            @Parameter(description = "The type of vehicle" )
            @RequestParam(Constants.VEHICLE_TYPE) VehicleType vehicleType) {
        return parkingSpotManager.getParkingSpotAvailable(vehicleType);
    }

}
