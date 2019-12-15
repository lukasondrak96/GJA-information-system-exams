package cz.vutbr.fit.gja.models.room;

import cz.vutbr.fit.gja.common.ErrorMessageCreator;
import cz.vutbr.fit.gja.dto.BlocksCreationDto;
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
        modelAndView.addObject("rooms", blockServiceDao.getRoomAndNumberOfSeatsOfAllRooms());
        return modelAndView;
    }

    @GetMapping("/logged/rooms")
    public ModelAndView getRoomsAsLogged() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/logged/rooms");
        modelAndView.addObject("rooms", blockServiceDao.getRoomAndNumberOfSeatsOfAllRooms());
        return modelAndView;
    }

    @GetMapping("/rooms/{id}")
    public ModelAndView getRoom(@PathVariable(value = "id") String roomId) {
        ModelAndView modelAndView = new ModelAndView();
        Room room = roomServiceDao.getRoom(roomId);
        if (room == null) {
            ErrorMessageCreator.errorPageWithMessage(modelAndView, "Místnost s číslem " + roomId + " neexistuje.");
        } else {
            modelAndView.setViewName("pages/room");
            modelAndView.addObject("room", room);
        }
        return modelAndView;
    }

    @GetMapping("/logged/rooms/{id}")
    public ModelAndView getRoomAsLogged(@PathVariable(value = "id") String roomId) {
        ModelAndView modelAndView = new ModelAndView();
        Room room = roomServiceDao.getRoom(roomId);
        if (room == null) {
            ErrorMessageCreator.errorPageWithMessageLogged(modelAndView, "Místnost s číslem " + roomId + " neexistuje.");
        } else {
            modelAndView.setViewName("pages/logged/room");
            modelAndView.addObject("room", room);
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
    public ModelAndView generateRoom(@Valid Room room, BindingResult bindingResult, ModelMap modelMap) {
        ModelAndView modelAndView = new ModelAndView();
        // Check for the validations
        if(bindingResult.hasErrors()) {
            modelMap.addAttribute("bindingResult", bindingResult);
            modelAndView.setViewName("pages/logged/new_room");
            modelAndView.addObject("room", room);
        } else if(roomServiceDao.isRoomAlreadyCreated(room)) {
            modelAndView.addObject("message", "Místnost s číslem " + room.getRoomNumber() + " již existuje.");
            modelAndView.setViewName("pages/logged/new_room");
            modelAndView.addObject("room", new Room());
        } else {
            BlocksCreationDto blocks = new BlocksCreationDto().prepareBlockListsAndFillWithFalse(room);
            modelAndView.addObject("room", room);
            modelAndView.addObject("all_blocks", blocks);
            modelAndView.setViewName("pages/logged/new_room");
        }
        return modelAndView;
    }

    @PostMapping("/logged/rooms/new_room/create")
    public ModelAndView createNewRoom(@Valid BlocksCreationDto blocks, BindingResult bindingResult, ModelMap modelMap) {
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
            modelAndView.addObject("message", "Místnost s číslem " + room.getRoomNumber() + " již existuje.");
            modelAndView.addObject("room", new Room());
            modelAndView.setViewName("pages/logged/new_room");
            modelAndView.addObject(room);
        } else {
            String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
            Teacher roomCreator = teacherServiceDao.getTeacher(userEmail);
            if (roomCreator == null) {
                ErrorMessageCreator.errorPageWithMessageLogged(modelAndView, "Tento uživatel neexistuje");
            }
            room.setRoomCreator(roomCreator);
            roomServiceDao.saveRoomToDatabase(room);

            Room roomReference = roomServiceDao.getRoom(room.getRoomNumber());
            blockServiceDao.createAndSaveBlocksForRoom(roomReference, blocks);

            modelAndView.addObject("successMessage", "Místnost \"" + room.getRoomNumber() + "\" byla úspěšně vytvořena.");
            modelAndView.addObject("rooms", blockServiceDao.getRoomAndNumberOfSeatsOfAllRooms());
            modelAndView.setViewName("pages/logged/rooms");
        }
        return modelAndView;
    }
}