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
    RoomServiceDao roomServiceDao;

    @Autowired
    BlockRepository blockRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @GetMapping("/rooms")
    public ModelAndView getRooms() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/rooms");
        modelAndView.addObject("rooms", roomRepository.findAll());
        return modelAndView;
    }

    @GetMapping("/logged/rooms")
    public ModelAndView getRoomsAsLogged() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/logged/rooms");
        modelAndView.addObject("rooms", roomRepository.findAll());
        return modelAndView;
    }

    @GetMapping("/rooms/{id}")
    public ModelAndView getRoom(@PathVariable(value = "id") String roomId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/room");
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException("číslem", roomId));
        modelAndView.addObject("room", room);
        return modelAndView;
    }

    @GetMapping("/logged/rooms/{id}")
    public ModelAndView getRoomAsLogged(@PathVariable(value = "id") String roomId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/logged/room");
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException("číslem", roomId));
        modelAndView.addObject("room", room);
        return modelAndView;
    }

    @GetMapping("/logged/rooms/new_room")
    public ModelAndView getNewRoomPage() {
        ModelAndView modelAndView = new ModelAndView();
        Room room = new Room();
        modelAndView.addObject("room", room);
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
            BlocksCreationDto blocks = new BlocksCreationDto();
            blocks.setRoomReference(room);
            blocks.setIsSeats(new ArrayList<>());
            for (int i = 0; i < room.getNumberOfRows(); i++) {
                List<Boolean> blockRow = new ArrayList<>();
                for (int j = 0; j < room.getNumberOfColumns(); j++) {
                    blockRow.add(true);
                }
                blocks.addBlockRow(blockRow);
            }

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
            room.setRoomCreator(teacherRepository.findByEmail(userEmail));
            roomRepository.save(room);

            Room roomReference = roomRepository.findByRoomNumber(room.getRoomNumber());
            for (int i = 0; i < room.getNumberOfRows(); i++) {
                for (int j = 0; j < room.getNumberOfColumns(); j++) {
                    Boolean isSeat = true;
                    try {
                        isSeat = blocks.getBlockRow(i).get(j);
                    } catch (IndexOutOfBoundsException e) {
                        isSeat = false;
                        blocks.getBlockRow(i).add(false);
                    } finally {
                        if (isSeat == null) {
                            isSeat = false;
                            blocks.getBlockRow(i).set(j, false);
                        }
                    }
                    Block block = new Block(isSeat, j + 1, room.getNumberOfRows() - i, roomReference);
                    blockRepository.save(block);
                }
            }

            modelAndView.addObject("successMessage", "Místnost \"" + room.getRoomNumber() + "\" byla úspěšně vytvořena.");
            modelAndView.addObject("rooms", roomRepository.findAll());
            modelAndView.setViewName("pages/logged/rooms");
        }
        return modelAndView;
    }
}