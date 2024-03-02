package assessment.parkinglot.common.request;

import assessment.parkinglot.common.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleRequest {

    private String vehicleNumber;
    private VehicleType vehicleType;
}
