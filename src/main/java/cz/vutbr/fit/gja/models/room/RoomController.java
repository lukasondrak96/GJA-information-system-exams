package cz.vutbr.fit.gja.models.room;

import cz.vutbr.fit.gja.common.ModelAndViewSetter;
import cz.vutbr.fit.gja.dto.BlocksDto;
import cz.vutbr.fit.gja.models.block.BlockServiceDao;
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

@Controller
public class RoomController {
    @Autowired
    RoomServiceDao roomServiceDao;

    @Autowired
    BlockServiceDao blockServiceDao;

    @Autowired
    TeacherServiceDaoImpl teacherServiceDao;

    @GetMapping("/rooms")
    public ModelAndView getRooms() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/rooms");
        modelAndView.addObject("roomHolders", blockServiceDao.getRoomAndNumberOfSeatsOfAllRooms());
        return modelAndView;
    }

    @GetMapping("/logged/rooms")
    public ModelAndView getRoomsAsLogged() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/logged/rooms");
        modelAndView.addObject("roomHolders", blockServiceDao.getRoomAndNumberOfSeatsOfAllRooms());
        return modelAndView;
    }

    @GetMapping("/rooms/{id}")
    public ModelAndView getRoom(@PathVariable(value = "id") String roomId) {
        ModelAndView modelAndView = new ModelAndView();
        Room room = roomServiceDao.getRoom(roomId);
        if (room == null) {
            ModelAndViewSetter.errorPageWithMessage(modelAndView, "Místnost s číslem \"" + roomId + "\" neexistuje.");
        } else {
            BlocksDto blocks = blockServiceDao.getAllBlocksOfRoom(room);
            modelAndView.addObject("all_blocks", blocks);
            modelAndView.setViewName("pages/room");
        }
        return modelAndView;
    }

    @GetMapping("/logged/rooms/{id}")
    public ModelAndView getRoomAsLogged(@PathVariable(value = "id") String roomId) {
        ModelAndView modelAndView = new ModelAndView();
        Room room = roomServiceDao.getRoom(roomId);
        if (room == null) {
            ModelAndViewSetter.errorPageWithMessageLogged(modelAndView, "Místnost s číslem \"" + roomId + "\" neexistuje.");
        } else {
            BlocksDto blocks = blockServiceDao.getAllBlocksOfRoom(room);
            modelAndView.addObject("all_blocks", blocks);
            modelAndView.setViewName("pages/logged/room");
        }
        return modelAndView;
    }

    @GetMapping("/logged/rooms/{id}/delete")
    public ModelAndView deleteRoomAsLogged(@PathVariable(value = "id") String roomId) {
        ModelAndView modelAndView = new ModelAndView();
        long numberOfDeletedRooms = 0;
        try {
            numberOfDeletedRooms = roomServiceDao.deleteRoom(roomId);
        } catch (IllegalAccessError e) {
            return ModelAndViewSetter.errorPageWithMessageLogged(modelAndView, e.getMessage());
        }

        if (numberOfDeletedRooms == 0) {
            ModelAndViewSetter.errorPageWithMessageLogged(modelAndView, "Místnost s číslem \"" + roomId + "\" neexistuje.");
        } else {
            modelAndView.setViewName("pages/logged/rooms");
            modelAndView.addObject("successMessage", "Místnost s číslem\"" + roomId + "\" byla úspěšně odstraněna.");
            modelAndView.addObject("roomHolders", blockServiceDao.getRoomAndNumberOfSeatsOfAllRooms());
        }
        return modelAndView;
    }

    @GetMapping("/logged/rooms/new_room")
    public ModelAndView getNewRoomPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("room", new Room());
        modelAndView.setViewName("pages/logged/new_room");
        return modelAndView;
    }

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
            String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
            Teacher roomCreator = teacherServiceDao.getTeacher(userEmail);
            if (roomCreator == null) {
                ModelAndViewSetter.errorPageWithMessageLogged(modelAndView, "Tento uživatel neexistuje");
            }
            room.setRoomCreator(roomCreator);
            roomServiceDao.saveRoomToDatabase(room);

            Room roomReference = roomServiceDao.getRoom(room.getRoomNumber());
            blockServiceDao.createAndSaveBlocksForRoom(roomReference, blocks);

            modelAndView.addObject("successMessage", "Místnost \"" + room.getRoomNumber() + "\" byla úspěšně vytvořena.");
            modelAndView.addObject("roomHolders", blockServiceDao.getRoomAndNumberOfSeatsOfAllRooms());
            modelAndView.setViewName("pages/logged/rooms");
        }
        return modelAndView;
    }

    @GetMapping("/logged/rooms/{id}/edit")
    public ModelAndView getEditPage(@PathVariable(value = "id") String name) {
        ModelAndView modelAndView = new ModelAndView();
        Room room = roomServiceDao.getRoom(name);
        if (room == null) {
            return ModelAndViewSetter.errorPageWithMessageLogged(modelAndView, "Místnost s číslem \"" + name + "\" neexistuje.");
        }
        BlocksDto blocks = blockServiceDao.getAllBlocksOfRoom(room);

        modelAndView.addObject("room", room);
        modelAndView.addObject("all_blocks", blocks);
        modelAndView.setViewName("pages/logged/edit_room");
        return modelAndView;
    }

    @PostMapping("/logged/rooms/{id}/edit")
    public ModelAndView editRoom(@PathVariable(value = "id") String name, @Valid Room room, BindingResult bindingResult, ModelMap modelMap) {
        ModelAndView modelAndView = new ModelAndView();
        // Check for the validations
        if(bindingResult.hasErrors()) {
            modelMap.addAttribute("bindingResult", bindingResult);
            modelAndView.setViewName("pages/logged/edit_room");
            modelAndView.addObject("room", room);
        } else {
            try {
                roomServiceDao.deleteRoom(name);
            } catch (IllegalAccessError e) {
                return ModelAndViewSetter.errorPageWithMessageLogged(modelAndView, e.getMessage());
            }

            BlocksDto blocks = new BlocksDto();
            blocks.createIsSeatListOfSeats(room);
            modelAndView.addObject("isNew", false);
            modelAndView.addObject("room", room);
            modelAndView.addObject("all_blocks", blocks);
            modelAndView.setViewName("pages/logged/edit_room");
        }
        return modelAndView;
    }

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
        } else if (roomServiceDao.isRoomAlreadyCreated(room)) {
            roomServiceDao.deleteRoom(room.getRoomNumber());
            String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
            Teacher roomCreator = teacherServiceDao.getTeacher(userEmail);
            if (roomCreator == null) {
                ModelAndViewSetter.errorPageWithMessageLogged(modelAndView, "Tento uživatel neexistuje");
            }
            room.setRoomCreator(roomCreator);
            roomServiceDao.saveRoomToDatabase(room);

            Room roomReference = roomServiceDao.getRoom(room.getRoomNumber());
            blockServiceDao.createAndSaveBlocksForRoom(roomReference, blocks);

            modelAndView.addObject("successMessage", "Místnost \"" + room.getRoomNumber() + "\" byla úspěšně vytvořena.");
            modelAndView.addObject("roomHolders", blockServiceDao.getRoomAndNumberOfSeatsOfAllRooms());
            modelAndView.setViewName("pages/logged/rooms");
        }
        return modelAndView;
    }

    private void setModelAndView(ModelAndView modelAndView, @Valid BlocksDto blocks, Room room) {
        blocks.replaceNullValuesWithFalse();
        modelAndView.addObject("room", room);
        modelAndView.addObject("all_blocks", blocks);
        modelAndView.setViewName("pages/logged/new_room");
    }
}