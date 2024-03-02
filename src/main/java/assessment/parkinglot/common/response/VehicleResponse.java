package assessment.parkinglot.common.response;

import assessment.parkinglot.common.enums.VehicleType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class VehicleResponse {
    private Long id;
    private String vehicleNumber;
    private VehicleType vehicleType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyy-MM-dd HH:mm:ss")
    private LocalDateTime vehicleParkDate;
    private List<ParkingSpotResponse> parkingSpots;
}
