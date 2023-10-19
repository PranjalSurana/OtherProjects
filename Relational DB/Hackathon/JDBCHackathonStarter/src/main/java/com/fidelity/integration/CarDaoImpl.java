package com.fidelity.integration;

import com.fidelity.model.Car;
import com.fidelity.model.CarBodyType;
import com.fidelity.model.CarEngine;
import com.fidelity.model.CarEngineType;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class CarDaoImpl implements CarDao {

    private DataSource dataSource;

//    private CarEngineType carEngineType;

    public CarDaoImpl(DataSource datasource) {
        this.dataSource = datasource;
    }

    DataSource getDataSource() {
        return dataSource;
    }

//    public CarEngineType getCarEngineType() {
//        return carEngineType;
//    }

    private final String queryAllCars = "Select Id, Make, Model, Body_type, Manufacture_date From E_CAR";

    private final static String INSERT_CAR = """
		INSERT INTO scott.e_car (
			id,
			make,
			model,
			body_type,
			manufacture_date
		) VALUES (
			?,
			?,
			?,
			?,
			?
		)
	""";

    private final static String INSERT_CAR_ENGINE = """
		INSERT INTO scott.e_car_engine (
			id,
			engine_type,
			engine_size_l,
			horsepower
		) VALUES (
			?,
			?,
			?,
			?
		)
	""";
    private final static String UPDATE_CAR = """
		UPDATE scott.e_car
		SET
			make = ?,
			model = ?,
			body_type = ?,
			manufacture_date = ?
		WHERE
			e_car.id = ?
	""";

    private final static String UPDATE_CAR_ENGINE = """
		UPDATE scott.e_car_engine 
		SET 
			engine_type = ?,
			engine_size_l = ?,
			horsepower = ?
		WHERE 
			id = ?
	""";

    private final static String DELETE_CAR = """
		DELETE FROM e_car
		WHERE
			id = ?
	""";

    private final static String DELETE_CAR_ENGINE = """
		DELETE FROM e_car_engine
		WHERE
			id = ?
	""";

    @Override
    public List<Car> getCars() {
        List<Car> carList = new ArrayList<>();
        List<CarEngine> carEngineList = new ArrayList<>();
        try (Connection connection = getDataSource().getConnection();
//             PreparedStatement stmt1 = connection.prepareStatement(queryAllCars);
             PreparedStatement stmt2 = connection.prepareStatement(queryAllCars)) {
//             carEngineList = getAndHandleResults(stmt1);
             carList = getAndHandleResults(stmt2);  //, carEngineList);
        }
        catch (SQLException e) {
            throw new DatabaseException("Cannot execute queryGetCars", e);
        }
        return carList;
    }

    @Override
    public Car insertCarAndEngine(Car car, CarEngine carEngine) throws SQLException {
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement carStatement = connection.prepareStatement(INSERT_CAR);
             PreparedStatement carEngineStatement = connection.prepareStatement(INSERT_CAR_ENGINE)) {

            // Insert into Car table
            carStatement.setInt(1, car.getId());
            carStatement.setString(2, car.getMake());
            carStatement.setString(3, car.getModel());
            CarBodyType bodyType = CarBodyType.of(car.getBodyTypeName());
            carStatement.setInt(4, bodyType.getCode());
            carStatement.setDate(5, Date.valueOf(car.getManufactureDate()));
            carStatement.executeUpdate();

            carEngineStatement.setInt(1, carEngine.getCarId());
            carEngineStatement.setInt(2, carEngine.getEngineType().getCode());
            carEngineStatement.setFloat(3, carEngine.getEngineSizeInLiters());
            carEngineStatement.setFloat(4, carEngine.getHorsepower());
            carEngineStatement.executeUpdate();
        }
        return car;
    }

    public Car updateCarAndCarEngine(Car car, CarEngine carEngine) throws SQLException {
        try (Connection connection = getDataSource().getConnection();
             PreparedStatement carStatement = connection.prepareStatement(UPDATE_CAR);
             PreparedStatement carEngineStatement = connection.prepareStatement(UPDATE_CAR_ENGINE)) {

            // Insert into Car table
            carStatement.setInt(5, car.getId());
            carStatement.setString(1, car.getMake());
            carStatement.setString(2, car.getModel());
            CarBodyType bodyType = CarBodyType.of(car.getBodyTypeName());
            carStatement.setInt(3, bodyType.getCode());
            carStatement.setDate(4, Date.valueOf(car.getManufactureDate()));
            int rowsUpdated = carStatement.executeUpdate();
            carEngineStatement.setInt(4, carEngine.getCarId());
            carEngineStatement.setInt(1, carEngine.getEngineType().getCode());
            carEngineStatement.setFloat(2, carEngine.getEngineSizeInLiters());
            carEngineStatement.setFloat(3, carEngine.getHorsepower());
            rowsUpdated += carEngineStatement.executeUpdate();

            if (rowsUpdated == 0) {
                throw new DatabaseException("No car with id " + car.getId());
            }

        }
        catch (SQLException ex) {
            throw new DatabaseException("Unable to update Employee with id=" + car.getId(), ex);
        }
        return car;
    }

    @Override
    public boolean deleteCar(int carId) {
        try(Connection connection = getDataSource().getConnection()) {
            try (PreparedStatement carStatement = connection.prepareStatement(DELETE_CAR);
                 PreparedStatement carEngineStatement = connection.prepareStatement(DELETE_CAR_ENGINE)) {
                carStatement.setInt(1, carId);
                carEngineStatement.setInt(1, carId);
                int rowsDeleted = carEngineStatement.executeUpdate();
                rowsDeleted += carStatement.executeUpdate();
                return rowsDeleted > 0;
            }
        }
        catch (SQLException ex) {
            throw new DatabaseException("Unable to delete Employee with id= " + carId, ex);
        }
    }

    private List<Car> getAndHandleResults(PreparedStatement stmt) throws SQLException {

        List<Car> cars = new ArrayList<>();

        try (Connection connection = getDataSource().getConnection()) {
            String sql = "SELECT " +
                    "c.id AS car_id, " +
                    "c.make AS car_make, " +
                    "c.model AS car_model, " +
                    "c.Body_type AS car_body_type, " +
                    "c.Manufacture_date AS car_manufacture_date, " +
                    "e.Id AS engine_car_id, " +
                    "e.ENGINE_TYPE AS engine_type, " +
                    "e.HORSEPOWER AS engine_horsepower, " +
                    "e.ENGINE_SIZE_L AS engine_size_liters " +
                    "FROM E_CAR c  " +
                    "INNER JOIN E_CAR_ENGINE e ON c.id = e.Id";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int carId = resultSet.getInt("car_id");
                String make = resultSet.getString("car_make");
                String model = resultSet.getString("car_model");
                String bodyTypeName = resultSet.getString("car_body_type");
                LocalDate manufactureDate = resultSet.getDate("car_manufacture_date").toLocalDate();

                int engineCarId = resultSet.getInt("engine_car_id");
                CarEngineType engineType = CarEngineType.of(resultSet.getInt("engine_type"));
                float horsepower = resultSet.getFloat("engine_horsepower");
                float engineSizeInLiters = resultSet.getFloat("engine_size_liters");

                CarEngine engine = new CarEngine(engineCarId, engineType, engineSizeInLiters, horsepower);
                Car car = new Car(carId, make, model, bodyTypeName, engine, manufactureDate);
                cars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cars;
    }

}