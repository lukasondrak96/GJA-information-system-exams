package cz.vutbr.fit.gja.models.room;

import cz.vutbr.fit.gja.common.ModelAndViewSetter;
import cz.vutbr.fit.gja.dto.BlocksDto;
import cz.vutbr.fit.gja.models.block.BlockServiceDao;
import cz.vutbr.fit.gja.models.exam.ExamServiceDao;
import cz.vutbr.fit.gja.models.teacher.Teacher;
import cz.vutbr.fit.gja.models.teacher.TeacherServiceDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * This class encapsulates all methods that handle incoming room requests and send responses
 */
@Controller
public class RoomController {
    @Autowired
    RoomServiceDao roomServiceDao;

    @Autowired
    BlockServiceDao blockServiceDao;

    @Autowired
    ExamServiceDao examServiceDao;

    @Autowired
    TeacherServiceDaoImpl teacherServiceDao;

    /**
     * Shows all rooms for normal user
     * @return /rooms page with all rooms
     */
    @GetMapping("/rooms")
    public ModelAndView getRooms() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/rooms");
        modelAndView.addObject("roomHolders", blockServiceDao.getRoomAndNumberOfSeatsOfAllRooms());
        return modelAndView;
    }

    /**
     * Shows all rooms for logged user
     * @return /logged/rooms page with all rooms
     */
    @GetMapping("/logged/rooms")
    public ModelAndView getRoomsAsLogged() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/logged/rooms");
        modelAndView.addObject("roomHolders", blockServiceDao.getRoomAndNumberOfSeatsOfAllRooms());
        return modelAndView;
    }

    /**
     * Shows one specific room for normal user
     * @return /room page with information about one specific room
     */
    @GetMapping("/rooms/{id}")
    public ModelAndView getRoom(@PathVariable(value = "id") String roomId) {
        ModelAndView modelAndView = new ModelAndView();
        Room room = roomServiceDao.getRoomByRoomNumber(roomId);
        if (room == null) {
            ModelAndViewSetter.errorPageWithMessage(modelAndView, "Místnost s číslem \"" + roomId + "\" neexistuje.");
        } else {
            BlocksDto blocks = blockServiceDao.getAllBlocksOfRoomAsDto(room);
            modelAndView.addObject("all_blocks", blocks);
            modelAndView.setViewName("pages/room");
        }
        return modelAndView;
    }

    /**
     * Shows one specific room for logged user
     * @return /logged/room page with information about one specific room
     */
    @GetMapping("/logged/rooms/{id}")
    public ModelAndView getRoomAsLogged(@PathVariable(value = "id") String roomNumber) {
        ModelAndView modelAndView = new ModelAndView();
        Room room = roomServiceDao.getRoomByRoomNumber(roomNumber);
        if (room == null) {
            ModelAndViewSetter.errorPageWithMessageLogged(modelAndView, "Místnost s číslem \"" + roomNumber + "\" neexistuje.");
        } else {
            BlocksDto blocks = blockServiceDao.getAllBlocksOfRoomAsDto(room);
            modelAndView.addObject("all_blocks", blocks);
            modelAndView.setViewName("pages/logged/room");
        }
        return modelAndView;
    }

    /**
     * Deletes one specific room
     * @return /logged/rooms page with message about deletion result
     */
    @GetMapping("/logged/rooms/{id}/delete")
    public ModelAndView deleteRoomAsLogged(@PathVariable(value = "id") String roomNumber) {
        ModelAndView modelAndView = new ModelAndView();

        if (examServiceDao.checkIfExamRunIsInRoom(roomServiceDao.getRoomByRoomNumber(roomNumber))) {
           return ModelAndViewSetter.errorPageWithMessageLogged(modelAndView, "Místnost " + roomNumber + " nelze smazat z důvodu konání zkoušky.");
        }

        long numberOfDeletedRooms = 0;
        try {
            numberOfDeletedRooms = roomServiceDao.deleteRoomByRoomNumber(roomNumber);
        } catch (IllegalAccessError e) {
            return ModelAndViewSetter.errorPageWithMessageLogged(modelAndView, e.getMessage());
        }

        if (numberOfDeletedRooms == 0) {
            ModelAndViewSetter.errorPageWithMessageLogged(modelAndView, "Místnost s číslem \"" + roomNumber + "\" neexistuje.");
        } else {
            modelAndView.setViewName("pages/logged/rooms");
            modelAndView.addObject("successMessage", "Místnost s číslem\"" + roomNumber + "\" byla úspěšně odstraněna.");
            modelAndView.addObject("roomHolders", blockServiceDao.getRoomAndNumberOfSeatsOfAllRooms());
        }
        return modelAndView;
    }

    /**
     * Shows form for new room creation
     * @return /logged/new_room page with form to set number of rows and columns of room
     */
    @GetMapping("/logged/rooms/new_room")
    public ModelAndView getNewRoomPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("room", new Room());
        modelAndView.setViewName("pages/logged/new_room");
        return modelAndView;
    }

    /**
     * Gets and processes the request for generating room structure
     * @return /logged/new_room page with second form to define room structure
     */
    @PostMapping("/logged/rooms/new_room")
    public ModelAndView generateRoom(Room room, BindingResult bindingResult, ModelMap modelMap) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("room", room);
        modelAndView.setViewName("pages/logged/new_room");
        // Check for the validations
        if(bindingResult.hasErrors()) {
            modelMap.addAttribute("bindingResult", bindingResult);
        } else if(room.getNumberOfRows() < 1) {
            modelMap.addAttribute("message", "Počet řad musí být větší než 0");
        } else if(room.getNumberOfColumns() < 1) {
            modelMap.addAttribute("message", "Počet míst v řadě musí být větší než 0");
        } else {
            BlocksDto blocks = new BlocksDto();
            blocks.createIsSeatListOfSeats(room);
            modelAndView.addObject("room", room);
            modelAndView.addObject("all_blocks", blocks);
        }
        return modelAndView;
    }

    /**
     * Gets and processes the request for new room creation
     * @return /logged/rooms page with list of all rooms in database
     */
    @PostMapping("/logged/rooms/new_room/create")
    public ModelAndView createNewRoom(@Valid BlocksDto blocks, BindingResult bindingResult, ModelMap modelMap) {
        ModelAndView modelAndView = new ModelAndView();
        Room room = blocks.getRoomReference();
        if (room == null) {
            modelAndView.addObject("message", "Vyskytla se chyba. Prosím, zkuste to znovu;");
            modelAndView.addObject("room", new Room());
            modelAndView.setViewName("pages/logged/new_room");
        }
        // Check for the validations
        if (bindingResult.hasErrors()) {
            modelMap.addAttribute("bindingResult", bindingResult);
            setModelAndView(modelAndView, blocks, room);
        } else if(room.getRoomNumber().isEmpty()) {
            modelAndView.addObject("message", "Číslo místnosti je povinné");
            setModelAndView(modelAndView, blocks, room);
        } else if (roomServiceDao.isRoomAlreadyCreated(room)) {
            modelAndView.addObject("message", "Místnost s číslem \"" + room.getRoomNumber() + "\" již existuje.");
            setModelAndView(modelAndView, blocks, room);
        } else {
            createAndSaveRoom(blocks, modelAndView, room);

            modelAndView.addObject("successMessage", "Místnost \"" + room.getRoomNumber() + "\" byla úspěšně vytvořena.");
            modelAndView.addObject("roomHolders", blockServiceDao.getRoomAndNumberOfSeatsOfAllRooms());
            modelAndView.setViewName("pages/logged/rooms");
        }
        return modelAndView;
    }

    /**
     * Shows form for room editing
     * @return /logged/edit_room page with form to edit number of rows and columns of room and redefine room structure
     */
    @GetMapping("/logged/rooms/{id}/edit")
    public ModelAndView getEditPage(@PathVariable(value = "id") String name) {
        ModelAndView modelAndView = new ModelAndView();
        Room room = roomServiceDao.getRoomByRoomNumber(name);

        if (examServiceDao.checkIfExamRunIsInRoom(room)) {
            return ModelAndViewSetter.errorPageWithMessageLogged(modelAndView, "Místnost " + room.getRoomNumber() + " nelze smazat z důvodu konání zkoušky.");
        }

        if (room == null) {
            return ModelAndViewSetter.errorPageWithMessageLogged(modelAndView, "Místnost s číslem \"" + name + "\" neexistuje.");
        }
        BlocksDto blocks = blockServiceDao.getAllBlocksOfRoomAsDto(room);

        modelAndView.addObject("room", room);
        modelAndView.addObject("all_blocks", blocks);
        modelAndView.setViewName("pages/logged/edit_room");
        return modelAndView;
    }

    /**
     * Gets and processes the request for changing room size
     * @return /logged/edit_room page with form to edit number of rows and columns of room and redefine room structure
     */
    @PostMapping("/logged/rooms/{id}/edit")
    public ModelAndView editRoom(@PathVariable(value = "id") String name, Room room, BindingResult bindingResult, ModelMap modelMap) {
        ModelAndView modelAndView = new ModelAndView();
        room.setRoomNumber(name);
        // Check for the validations
        if(bindingResult.hasErrors()) {
            modelMap.addAttribute("bindingResult", bindingResult);
            modelAndView.addObject("room", room);
        } else {
            try {
                roomServiceDao.deleteRoomByRoomNumber(name);
            } catch (IllegalAccessError e) {
                return ModelAndViewSetter.errorPageWithMessageLogged(modelAndView, e.getMessage());
            }

            BlocksDto blocks = new BlocksDto();
            blocks.createIsSeatListOfSeats(room);
            modelAndView.addObject("isNew", false);
            modelAndView.addObject("room", room);
            modelAndView.addObject("all_blocks", blocks);

        }
        modelAndView.setViewName("pages/logged/edit_room");
        return modelAndView;
    }

    /**
     * Gets and processes the request for room editing
     * @return /logged/rooms page with list of all rooms in database
     */
    @PostMapping("/logged/rooms/{id}/edit/update")
    public ModelAndView updateRoom(@PathVariable(value = "id") String name, @Valid BlocksDto blocks, BindingResult bindingResult, ModelMap modelMap) {
        ModelAndView modelAndView = new ModelAndView();
        Room room = blocks.getRoomReference();
        if (room == null) {
            modelAndView.addObject("message", "Vyskytla se chyba. Prosím, zkuste to znovu;");
            modelAndView.addObject("room", new Room());
            modelAndView.setViewName("pages/logged/new_room");
        }
        // Check for the validations
        if (bindingResult.hasErrors()) {
            modelMap.addAttribute("bindingResult", bindingResult);
            modelAndView.addObject("room", room);
            modelAndView.addObject("blocks", blocks);
            modelAndView.setViewName("pages/logged/new_room");
        } else {
            roomServiceDao.deleteRoomById(room.getIdRoom());
            createAndSaveRoom(blocks, modelAndView, room);

            modelAndView.addObject("successMessage", "Editace byla úspěšná");
            modelAndView.addObject("roomHolders", blockServiceDao.getRoomAndNumberOfSeatsOfAllRooms());
            modelAndView.setViewName("pages/logged/rooms");
        }
        return modelAndView;
    }

    private void createAndSaveRoom(@Valid BlocksDto blocks, ModelAndView modelAndView, Room room) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Teacher roomCreator = teacherServiceDao.getTeacher(userEmail);
        if (roomCreator == null) {
            ModelAndViewSetter.errorPageWithMessageLogged(modelAndView, "Tento uživatel neexistuje");
        }
        room.setTeacherReference(roomCreator);
        Room roomReference = roomServiceDao.saveRoomToDatabase(room);

        blockServiceDao.createAndSaveBlocksForRoom(roomReference, blocks);
    }

    private void setModelAndView(ModelAndView modelAndView, @Valid BlocksDto blocks, Room room) {
        blocks.replaceNullValuesWithFalse();
        modelAndView.addObject("room", room);
        modelAndView.addObject("all_blocks", blocks);
        modelAndView.setViewName("pages/logged/new_room");
    }
}
