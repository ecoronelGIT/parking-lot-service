--vehicle
CREATE TABLE IF NOT EXISTS public.vehicle
(
    id SERIAL PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    number VARCHAR(10) NOT NULL UNIQUE,
    park_date TIMESTAMP NOT NULL
);

--parking_spot
CREATE TABLE IF NOT EXISTS public.parking_spot
(
    id SERIAL PRIMARY KEY,
    number INT NOT NULL UNIQUE,
    type VARCHAR(255) NOT NULL,
    vehicle_id BIGINT,
    FOREIGN KEY (vehicle_id) REFERENCES vehicle(id)
);