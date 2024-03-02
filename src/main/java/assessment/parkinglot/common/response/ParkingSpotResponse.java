package assessment.parkinglot.common.response;

import assessment.parkinglot.common.enums.SpotType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParkingSpotResponse {
    private Long id;
    private int spotNumber;
    private SpotType spotType;
}
