package rikkei.test.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import rikkei.test.config.Utils;
import rikkei.test.entity.UserCase;
import rikkei.test.service.user.UserService;

/**
 * MemberUserController
 *
 * @author NhatLKH
 *
 */
@Controller
@RequestMapping(path = "/user")
public class MemberUserController {
    /** userService **/
    @Autowired
    private UserService userService;

    /** passwordEncoder **/
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * getUserByIdUser
     *
     * @param model (dùng để truyền dữ liệu sang view)
     * @return view: user/editthisuser
     */
    // Get this User
    @GetMapping("/getthisuser")
    public String getUserByIdUser(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String idUser = auth.getName();
        UserCase user = userService.findByIdUser(idUser);
        String pass = user.getPassword();
        user.setOldPass(pass);
        model.addAttribute("user", user);
        return "user/editthisuser";
    }

    /**
     * editThisUser
     *
     * @param user     (thông tin của User)
     * @param result   (dùng để check argument)
     * @param redirect (dùng để truyền flash mess)
     * @return view: home
     */
    @PostMapping("/editthisuser")
    public String editThisUser(@ModelAttribute("user") @Valid UserCase user, BindingResult result,
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
        return "redirect:/";
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
