package cz.vutbr.fit.gja.controllers.rooms;

import cz.vutbr.fit.gja.dto.BlocksCreationDto;
import cz.vutbr.fit.gja.models.Block;
import cz.vutbr.fit.gja.models.Room;
import cz.vutbr.fit.gja.repositories.BlockRepository;
import cz.vutbr.fit.gja.repositories.RoomRepository;
import cz.vutbr.fit.gja.repositories.TeacherRepository;
import cz.vutbr.fit.gja.services.RoomServiceDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class BlockController {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    BlockRepository blockRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    RoomServiceDaoImpl roomServiceDao;

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

            modelAndView.addObject("successMessage", "Místnost " + room.getRoomNumber() + " byla úspěšně vytvořena.");
            modelAndView.addObject("room", roomReference);
            modelAndView.addObject("all_blocks", blocks);
            modelAndView.setViewName("pages/logged/new_room");
        }
        return modelAndView;
    }
}
