package cz.vutbr.fit.gja.models.room;

import java.util.List;

/**
 * ServiceDao class for Room
 */
public interface RoomServiceDao {

    /**
     * Checks if the room is already created
     * @param room room
     * @return true if room is created, false if room isn't created
     */
    boolean isRoomAlreadyCreated(Room room);

    /**
     * Gets all rooms from database
     * @return List of rooms from database
     */
    List<Room> getAllRoomsFromDatabase();

    /**
     * Gets specific room from database by id
     * @param id Room id
     * @return Room from database
     */
    Room getRoomById(int id);

    /**
     * Gets specific room from database by room number
     * @param roomNumber Room number
     * @return Room from database
     */
    Room getRoomByRoomNumber(String roomNumber);

    /**
     * Saves room to database
     * @param room Room to save
     * @return Saved room
     */
    Room saveRoomToDatabase(Room room);

    /**
     * Deletes specific room from database by id
     * @param id Room id
     * @return Number of deleted rooms
     * @throws IllegalAccessError Thrown if attempts to access or modify a field,
     *                            or to call a method that it does not have access to
     */
    long deleteRoomById(int id) throws IllegalAccessError;

    /**
     * Deletes specific room from database by room number
     * @param roomNumber Room number
     * @return Number of deleted rooms
     * @throws IllegalAccessError Thrown if attempts to access or modify a field,
     *                            or to call a method that it does not have access to
     */
    long deleteRoomByRoomNumber(String roomNumber) throws IllegalAccessError;

}
