package cz.vutbr.fit.gja.controllers.rooms;

import cz.vutbr.fit.gja.Exceptions.RoomNotFoundException;
import cz.vutbr.fit.gja.dto.BlocksCreationDto;
import cz.vutbr.fit.gja.models.Block;
import cz.vutbr.fit.gja.models.Room;
import cz.vutbr.fit.gja.repositories.BlockRepository;
import cz.vutbr.fit.gja.repositories.RoomRepository;
import cz.vutbr.fit.gja.repositories.TeacherRepository;
import cz.vutbr.fit.gja.services.RoomServiceDao;
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
import java.util.ArrayList;
import java.util.List;

@Controller
public class RoomsController {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    RoomServiceDao roomServiceDao;

    @Autowired
    BlockRepository blockRepository;

    @GetMapping("/rooms")
    public ModelAndView rooms() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/rooms");
        modelAndView.addObject("rooms", roomRepository.findAll());
        return modelAndView;
    }

    @GetMapping("/logged/rooms")
    public ModelAndView loggedRooms() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/logged/rooms");
        modelAndView.addObject("rooms", roomRepository.findAll());
        return modelAndView;
    }

    @GetMapping("/logged/rooms/{id}")
    public ModelAndView loggedRoomsRoomId(@PathVariable(value = "id") String roomId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/logged/room");
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException("číslem", roomId));
        modelAndView.addObject("room", room);
        return modelAndView;
    }

    @GetMapping("/rooms/{id}")
    public ModelAndView roomsRoomId(@PathVariable(value = "id") String roomId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/room");
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException("číslem", roomId));
        modelAndView.addObject("room", room);
        return modelAndView;
    }

    @GetMapping("/logged/rooms/new_room")
    public ModelAndView newRoom() {
        ModelAndView modelAndView = new ModelAndView();
        Room room = new Room();
        modelAndView.addObject("room", room);
        modelAndView.setViewName("pages/logged/new_room");
        return modelAndView;
    }

    @PostMapping("/logged/rooms/new_room")
    public ModelAndView createNewRoom(@Valid Room room, BindingResult bindingResult, ModelMap modelMap) {
        ModelAndView modelAndView = new ModelAndView();
        // Check for the validations
        if(bindingResult.hasErrors()) {
            modelMap.addAttribute("bindingResult", bindingResult);
            modelAndView.addObject("room", new Room());
            modelAndView.setViewName("pages/logged/new_room");
            modelAndView.addObject("room", room);
        } else if(roomServiceDao.isRoomAlreadyCreated(room)) {
            modelAndView.addObject("message", "Místnost s číslem " + room.getRoomNumber() + " již existuje.");
            modelAndView.addObject("room", new Room());
            modelAndView.setViewName("pages/logged/new_room");
            modelAndView.addObject(room);
        } else {
            String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
            room.setRoomCreator(teacherRepository.findByEmail(userEmail));
//            roomRepository.save(room);

            BlocksCreationDto blocks = new BlocksCreationDto();
            blocks.setRoomReference(room);
            blocks.setBlocks(new ArrayList<>());
            for (int i = 0; i < room.getNumberOfRows(); i++) {
                List<Block> blockRow = new ArrayList<>();
                for (int j = 0; j < room.getNumberOfColumns(); j++) {
                    blockRow.add(new Block(true, j, i, room));
                }
                blocks.addBlockRow(blockRow);
            }

//            modelAndView.addObject("successMessage", "Místnost " + room.getRoomNumber() + " byla úspěšně vytvořena.");
            modelAndView.addObject("room", room);
            modelAndView.addObject("blocks", blocks);
            modelAndView.setViewName("pages/logged/new_room");
        }
        return modelAndView;
    }
}