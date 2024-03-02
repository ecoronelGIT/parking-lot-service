package assessment.parkinglot.common.enums;

public enum SpotType {
    MOTORCYCLE,
    COMPACT_CAR,
    REGULAR;

    public static SpotType getSpotTypeByVehicleType(VehicleType vehicleType) {
        return switch (vehicleType) {
            case MOTORCYCLE -> SpotType.MOTORCYCLE;
            case CAR -> SpotType.COMPACT_CAR;
            case VAN -> SpotType.REGULAR;
        };
    }
}
