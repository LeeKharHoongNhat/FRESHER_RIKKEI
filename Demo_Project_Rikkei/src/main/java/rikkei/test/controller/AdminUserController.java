package rikkei.test.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import rikkei.test.config.Utils;
import rikkei.test.entity.UserCase;
import rikkei.test.service.user.UserService;

/**
 * Admin User Controller
 *
 * @author NhatLKH
 *
 */
@Controller
@RequestMapping(path = "/admin/user")
public class AdminUserController {

    /** userService */
    @Autowired
    private UserService userService;

    /** passwordEncoder */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * getAllUser
     *
     * @param status (trạng thái User)
     * @param model  (dùng để truyền dữ liệu sang view)
     * @return view: user/list
     */
    // Get All User
    @GetMapping("/listuser/{status}")
    public String getAllUser(@PathVariable("status") Integer status, Model model) {
        List<UserCase> listUser = userService.listUserByStatus(status);
        model.addAttribute("list", listUser);
        return "user/list";
    }

    /**
     * getUserByIdUser
     *
     * @param id         (id tự tăng)
     * @param model(dùng để truyền dữ liệu sang view)
     * @return view: user/getone
     */
    // Get One User
    @GetMapping("/getuser/{id}")
    public String getUserByIdUser(@PathVariable("id") Integer id, Model model) {
        UserCase user = userService.getUserbyId(id);
        model.addAttribute("user", user);
        return "user/getone";
    }

    /**
     * showCreateForm
     *
     * @return view: user/create
     */
    // Get form create
    @GetMapping("/create")
    public ModelAndView showCreateForm() {
        return new ModelAndView("user/create", "userCase", new UserCase());
    }

    /**
     * creatUser
     *
     * @param userCase (đối tượng User)
     * @param result   (dùng để check argument)
     * @param model    (dùng để truyền dữ liệu sang view)
     * @param redirect (dùng để truyền flash mess)
     * @return view: user/list
     */
    // Create a new User
    @PostMapping("/savecreate")
    public String creatUser(@ModelAttribute("userCase") @Valid UserCase userCase, BindingResult result, Model model,
            RedirectAttributes redirect) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String idUser = auth.getName();
        String msg = null;
        if (result.hasErrors()) {
            return "user/creat";
        } else {
            String idUserInForm = userCase.getIdUser();
            int searchUser = userService.getRecordsTotalbyIdUser(idUserInForm);
            if (searchUser == 0) {
                String timeNow = formatString(Utils.formatDateTime(new java.util.Date()));
                if (userCase.getPassword().equals("") || userCase.getPassword() == null) {
                    userCase.setPassword("123456");
                }

                List<UserCase> listUserByStatusOn = userService.listUser();
                if (listUserByStatusOn == null) {
                    msg = "null list User";
                } else {

                    // 1 là trạng thái status Đang Hoạt Động
                    int recordsUserByStatusOn = userService.getRecordsTotal(1);
                    if (recordsUserByStatusOn == 0) {
                        msg = "records is zero";
                    } else {

                        // lấy user cuối cùng
                        UserCase userCheck = listUserByStatusOn.get(recordsUserByStatusOn - 1);
                        if (userCheck == null) {
                            msg = "null user check";
                        } else {

                            // tăng lên 1 đơn vị id cho user mới
                            userCase.setId(userCheck.getId() + 1);

                            userCase.setPassword(passwordEncoder.encode(userCase.getPassword()));
                            userCase.setCreatBy(idUser);
                            userCase.setCreatAt(timeNow);
                            userCase.setUpdateBy(idUser);
                            userCase.setUpdateAt(timeNow);
                            userCase.setStatus(1);
                            userCase.setDelFlag(false);
                            boolean check = userService.addNew(userCase);

                            if (check) {
                                msg = "Create [" + userCase.getIdUser() + "] success!";
                            } else {
                                msg = "Create [" + userCase.getIdUser() + "] failure!";
                            }

                        }
                    }
                }
            } else {
                String mess = "Mã User đã tồn tại";
                model.addAttribute("mess", mess);
                return "user/create";
            }
            redirect.addFlashAttribute("msg", msg);
            return "redirect:/admin/user/listuser/1";
        }
    }

    /**
     * getUserByIdUser
     *
     * @param idUser (id của User)
     * @param model  (dùng để truyền dữ liệu sang view)
     * @return view: user/edit
     */
    // Get User
    @GetMapping("/edit/{idUser}")
    public String getUserByIdUser(@PathVariable("idUser") String idUser, Model model, RedirectAttributes redirect) {
        String msg = null;
        if (idUser == null) {
            msg = "null param ";
        } else {
            UserCase user = userService.findByIdUser(idUser);
            if (user == null) {
                msg = "null user";
            } else {
                String pass = user.getPassword();
                if (pass == null) {
                    msg = "null pass";
                } else {
                    user.setOldPass(pass);
                    model.addAttribute("user", user);
                }
            }
        }
        redirect.addFlashAttribute("msg", msg);
        return "user/edit";
    }

    /**
     * saveUpdateUser
     *
     * @param user     (đối tượng User)
     * @param result   (dùng để check argument)
     * @param redirect (dùng để truyền flash mess)
     * @return view: user/list
     */
    @PostMapping("/saveupdate")
    public String saveUpdateUser(@ModelAttribute("user") @Valid UserCase user, BindingResult result,
            RedirectAttributes redirect) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String idAdmin = auth.getName();
        String msg = null;
        if (user == null) {
            msg = "null user information!";
        } else {
            if (user.getPassword() == null) {
                msg = "null password!";
            } else {
                if (user.getPassword().equals("")) {
                    user.setPassword(user.getOldPass());
                } else {
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                }
                if (result.hasErrors()) {
                    return "user/editthisuser";
                } else {
                    if (user.getCreatAt() == null) {
                        msg = "null time create!";
                    } else {
                        String word = String.valueOf(user.getCreatAt().charAt(10));
                        if (word.equals(" ")) {
                            String timeCreate = user.getCreatAt().replace(" ", "T");
                            user.setCreatAt(timeCreate);
                            String timeNow = formatString(Utils.formatDateTime(new java.util.Date()));
                            user.setUpdateBy(idAdmin);
                            user.setUpdateAt(timeNow);
                            boolean check = userService.update(user);

                            if (check) {
                                msg = "Update your account success!";
                            } else {
                                msg = "Update your account failure!";
                            }
                        } else {
                            msg = "wrong time format!";
                        }
                    }
                }
            }
        }
        redirect.addFlashAttribute("msg", msg);
        return "redirect:/admin/user/listuser/1";
    }

    /**
     * deleteUser
     *
     * @param id       (id tự tăng)
     * @param redirect (dùng để truyền flash mess)
     * @return view: user/list
     */
    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") Integer id, RedirectAttributes redirect) {
        String msg = null;
        if (id == null) {
            msg = "null id";
        } else {
            UserCase user = userService.getUserbyId(id);
            if (user == null) {
                msg = "null user";
            } else {
                user.setDelFlag(true);
                boolean check = userService.update(user);

                if (check) {
                    msg = "Delete [" + user.getIdUser() + "] success!";
                } else {
                    msg = "Delete [" + user.getIdUser() + "] failure!";
                }
                redirect.addFlashAttribute("msg", msg);
            }
        }
        return "redirect:/admin/user/listuser/1";
    }

    /**
     * formatString
     *
     * @param str (chuỗi ban đầu trước khi format)
     * @return strFormat (chuỗi sau khi format)
     */
    public String formatString(String str) {
        String strFormat = "";
        if (String.valueOf(str.charAt(10)).equals("T")) {
            strFormat = String.valueOf(str.charAt(8)) + String.valueOf(str.charAt(9)) + String.valueOf(str.charAt(7))
                    + String.valueOf(str.charAt(5)) + String.valueOf(str.charAt(6)) + String.valueOf(str.charAt(7))
                    + String.valueOf(str.charAt(0)) + String.valueOf(str.charAt(1)) + String.valueOf(str.charAt(2))
                    + String.valueOf(str.charAt(3)) + " " + String.valueOf(str.charAt(11))
                    + String.valueOf(str.charAt(12)) + String.valueOf(str.charAt(13)) + String.valueOf(str.charAt(14))
                    + String.valueOf(str.charAt(15));
        } else {
            strFormat = String.valueOf(str.charAt(6)) + String.valueOf(str.charAt(7)) + String.valueOf(str.charAt(8))
                    + String.valueOf(str.charAt(9)) + String.valueOf(str.charAt(2)) + String.valueOf(str.charAt(3))
                    + String.valueOf(str.charAt(4)) + String.valueOf(str.charAt(2)) + String.valueOf(str.charAt(0))
                    + String.valueOf(str.charAt(1)) + "T" + String.valueOf(str.charAt(11))
                    + String.valueOf(str.charAt(12)) + String.valueOf(str.charAt(13)) + String.valueOf(str.charAt(14))
                    + String.valueOf(str.charAt(15));
        }

        return strFormat;
    }

}
