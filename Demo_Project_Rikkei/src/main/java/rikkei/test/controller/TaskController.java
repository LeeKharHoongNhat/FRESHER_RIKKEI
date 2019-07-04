package rikkei.test.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import rikkei.test.config.Utils;
import rikkei.test.entity.Task;
import rikkei.test.entity.UserCase;
import rikkei.test.service.task.TaskService;
import rikkei.test.service.user.UserService;

/**
 * TaskController
 *
 * @author NhatLKH
 *
 */
@Controller
@RequestMapping(path = "/task")
public class TaskController {

    /** taskService **/
    @Autowired
    private TaskService taskService;

    /** userService **/
    @Autowired
    private UserService userService;

    /**
     * test
     *
     * @return view: test
     */
    @GetMapping("/test")
    public String test() {
        return "test";
    }

    /**
     * getCalendar
     *
     * @param week  (khoảng cách của tuần đang hiển thị với tuần hiện tại theo đơn
     *              vị tuần )
     * @param model (dùng để truyền dữ liệu sang view)
     * @return view: calendar/calendar
     */
    @GetMapping("/gettask/{week}")
    public String getCalendar(@PathVariable("week") Integer week, Model model) {
        Date date = new Date();
        model.addAttribute("daynow", Utils.formatDate(date));
        return "calendar/calendar";
    }

    /**
     * allTask
     *
     * @param delFlag (trạng thái vô hiệu hóa User)
     * @param week    (khoảng cách của tuần đang hiển thị với tuần hiện tại theo đơn
     *                vị tuần )
     * @param model   (dùng để truyền dữ liệu sang view)
     * @return view: calendar/task
     * @throws ParseException
     */
    @GetMapping("/alltask/{delflag}/{week}")
    public String allTask(@PathVariable("delflag") Integer delFlag, @PathVariable("week") Integer week, Model model)
            throws ParseException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String idUser = auth.getName();
        ArrayList<String> arrTime = new ArrayList<>();
        List<Task> listAll = taskService.getTaskByIdUser(idUser, delFlag);

        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        SimpleDateFormat fmt = new SimpleDateFormat("E");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        cal.add(Calendar.DAY_OF_MONTH, (7 * week));

        if (fmt.format(cal.getTime()).equals("Mon")) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        } else {
            if (fmt.format(cal.getTime()).equals("Tue")) {
                cal.add(Calendar.DAY_OF_MONTH, -2);
            } else {
                if (fmt.format(cal.getTime()).equals("Wed")) {
                    cal.add(Calendar.DAY_OF_MONTH, -3);
                } else {
                    if (fmt.format(cal.getTime()).equals("Thu")) {
                        cal.add(Calendar.DAY_OF_MONTH, -4);
                    } else {
                        if (fmt.format(cal.getTime()).equals("Fri")) {
                            cal.add(Calendar.DAY_OF_MONTH, -5);
                        } else {
                            if (fmt.format(cal.getTime()).equals("Sat")) {
                                cal.add(Calendar.DAY_OF_MONTH, -6);
                            } else {
                                cal.add(Calendar.DAY_OF_MONTH, 0);
                            }

                        }

                    }

                }

            }

        }

        arrTime.add(0, Utils.formatDateSort(cal.getTime()));
        for (int i = 1; i < 7; i++) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
            arrTime.add(i, Utils.formatDateSort(cal.getTime()));
        }

        for (int i = 0; i < listAll.size(); i++) {

            listAll.get(i).setCheckStartTime(Utils.formatDateSort(df.parse(listAll.get(i).getStartTime())));
            listAll.get(i).setCheckEndTime(Utils.formatDateSort(df.parse(listAll.get(i).getEndTime())));

            listAll.get(i).setStartTime(Utils.formatTime(df.parse(listAll.get(i).getStartTime())));
            listAll.get(i).setEndTime(Utils.formatTime(df.parse(listAll.get(i).getEndTime())));

            listAll.get(i).setBooleanTime(checkTime(listAll.get(i).getStartTime(), listAll.get(i).getEndTime(),
                    listAll.get(i).getCheckStartTime()));

        }

        List<UserCase> listUser = userService.listUserByStatus(1);

//        Integer countTaskTag = taskService.getRecordsTaskParticipants(idUser, delFlag);
//        if (countTaskTag != 0) {
//            List<Task> listTaskTag = taskService.getTaskByIdParticipants(idUser, delFlag);
//            model.addAttribute("listtasktag", listTaskTag);
//        }

        model.addAttribute("listtask", listAll);
        model.addAttribute("listday", arrTime);
        model.addAttribute("week", week);
        model.addAttribute("checkdaynow", Utils.formatDateSort(date));
        model.addAttribute("daynow", Utils.formatDate(date));
        model.addAttribute("taskwork", new Task());
        model.addAttribute("listuser", listUser);
        return "calendar/task";

    }

    /**
     * createTask
     *
     * @param taskWork ( thông tin của đối tượng Task)
     * @param result   (dùng để check argument)
     * @param model    (dùng để truyền dữ liệu sang view)
     * @param file     (đường dẫn file trong máy tính)
     * @return view: calendar/calendar
     * @throws ParseException
     */
    @PostMapping("/savecreate")
    public String createTask(@ModelAttribute("taskwork") @Valid Task taskWork, BindingResult result, Model model,
            @RequestParam("file") MultipartFile file) throws ParseException {

        if (taskWork.getAllDay() == true) {
            taskWork.setEndTime(taskWork.getStartTime() + "T23:00");
            taskWork.setStartTime(taskWork.getStartTime() + "T00:00");
        }

        if (taskWork.getParticipants1() != null && taskWork.getParticipants1().equals("none")) {
            taskWork.setParticipants2("none");
            taskWork.setParticipants3("none");
        }

        if (taskWork.getParticipants1() != null && taskWork.getParticipants2() != null
                && taskWork.getParticipants2().equals("none")) {
            taskWork.setParticipants3("none");
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String idUser = auth.getName();
        Date date = new Date();
        Timestamp timeStampFormat = new Timestamp(date.getTime());
        if (result.hasErrors()) {
            return "test/calendar";
        } else {
            if (taskWork.getLoopTask().equals("day")) {
                String getStartTime = taskWork.getStartTime();
                String getEndTime = taskWork.getEndTime();
                String getEndTimeLoop = taskWork.getEndLoopTask();

                Integer idTask = getId() + 1;
                taskWork.setId(idTask);
                taskWork.setBigTask(0);
                taskWork.setStartTime(getStartTime);
                taskWork.setEndTime(getEndTimeLoop + "T00:00");
                taskWork.setCreateBy(idUser);
                taskWork.setCreateAt(timeStampFormat);
                taskWork.setUpdateBy(idUser);
                taskWork.setUpdateAt(timeStampFormat);
                taskWork.setStatus(1);
                taskWork.setDelFlag(false);

                String nameImage = uploadFile(file);
                taskWork.setImage(nameImage);
                taskService.addNew(taskWork);

                taskWork.setEndTime(getEndTime);
                int numberDayLoop = countDayLoop(getStartTime, getEndTimeLoop);
                createTaskLoopDay(taskWork, numberDayLoop, idTask, idUser, file);

            } else {
                if (taskWork.getLoopTask().equals("week")) {
                    String getStartTime = taskWork.getStartTime();
                    String getEndTime = taskWork.getEndTime();
                    String getEndTimeLoop = taskWork.getEndLoopTask();

                    Integer idTask = getId() + 1;
                    taskWork.setId(idTask);
                    taskWork.setBigTask(0);
                    taskWork.setStartTime(getStartTime);
                    taskWork.setEndTime(getEndTimeLoop + "T00:00");
                    taskWork.setCreateBy(idUser);
                    taskWork.setCreateAt(timeStampFormat);
                    taskWork.setUpdateBy(idUser);
                    taskWork.setUpdateAt(timeStampFormat);
                    taskWork.setStatus(1);
                    taskWork.setDelFlag(false);

                    String nameImage = uploadFile(file);
                    taskWork.setImage(nameImage);
                    taskService.addNew(taskWork);

                    taskWork.setEndTime(getEndTime);
                    int numberDayLoop = countDayLoop(getStartTime, getEndTimeLoop);
                    createTaskLoopWeek(taskWork, numberDayLoop, idTask, idUser, file);
                } else {
                    if (taskWork.getLoopTask().equals("month")) {
                        String getStartTime = taskWork.getStartTime();
                        String getEndTime = taskWork.getEndTime();
                        String getEndTimeLoop = taskWork.getEndLoopTask();

                        Integer idTask = getId() + 1;
                        taskWork.setId(idTask);
                        taskWork.setBigTask(0);
                        taskWork.setStartTime(getStartTime);
                        taskWork.setEndTime(getEndTimeLoop + "T00:00");
                        taskWork.setCreateBy(idUser);
                        taskWork.setCreateAt(timeStampFormat);
                        taskWork.setUpdateBy(idUser);
                        taskWork.setUpdateAt(timeStampFormat);
                        taskWork.setStatus(1);
                        taskWork.setDelFlag(false);

                        String nameImage = uploadFile(file);
                        taskWork.setImage(nameImage);
                        taskService.addNew(taskWork);

                        taskWork.setEndTime(getEndTime);
                        int numberDayLoop = countDayLoop(getStartTime, getEndTimeLoop);
                        createTaskLoopMonth(taskWork, numberDayLoop, idTask, idUser, file);
                    } else {
                        if (taskWork.getLoopTask().equals("year")) {
                            String getStartTime = taskWork.getStartTime();
                            String getEndTime = taskWork.getEndTime();
                            String getEndTimeLoop = taskWork.getEndLoopTask();

                            Integer idTask = getId() + 1;
                            taskWork.setId(idTask);
                            taskWork.setBigTask(0);
                            taskWork.setStartTime(getStartTime);
                            taskWork.setEndTime(getEndTimeLoop + "T00:00");
                            taskWork.setCreateBy(idUser);
                            taskWork.setCreateAt(timeStampFormat);
                            taskWork.setUpdateBy(idUser);
                            taskWork.setUpdateAt(timeStampFormat);
                            taskWork.setStatus(1);
                            taskWork.setDelFlag(false);

                            String nameImage = uploadFile(file);
                            taskWork.setImage(nameImage);
                            taskService.addNew(taskWork);

                            taskWork.setEndTime(getEndTime);
                            int numberDayLoop = countDayLoop(getStartTime, getEndTimeLoop);
                            createTaskLoopYear(taskWork, numberDayLoop, idTask, idUser, file);
                        } else {

                            Integer idTask = getId() + 1;
                            taskWork.setId(idTask);
                            taskWork.setBigTask(-1);

                            taskWork.setCreateBy(idUser);
                            taskWork.setCreateAt(timeStampFormat);
                            taskWork.setUpdateBy(idUser);
                            taskWork.setUpdateAt(timeStampFormat);
                            taskWork.setStatus(1);
                            taskWork.setDelFlag(false);

                            String nameImage = uploadFile(file);
                            taskWork.setImage(nameImage);
                            taskService.addNew(taskWork);
                        }
                    }
                }
            }

        }
        return "redirect:/task/gettask/0";
    }

    /**
     * uploadFile
     *
     * @param file (đường dẫn file trong máy tính)
     * @return filename ( tên file kèm đuôi file)
     */
    public String uploadFile(MultipartFile file) {
        File fileImage = new File("D:\\Calendar_Rikkei");

        if (!fileImage.exists()) {
            if (fileImage.mkdir()) {
                System.out.println("Image is created!");
            } else {
                System.out.println("Failed to create image!");
            }
        }
        String filename = file.getOriginalFilename();
        try {
            byte barr[] = file.getBytes();

            BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(fileImage + "/" + filename));
            bout.write(barr);
            bout.flush();
            bout.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        return filename;
    }

    /**
     * createTaskLoopYear
     *
     * @param taskWork   ( thông tin của đối tượng Task)
     * @param numberLoop (số ngày lặp)
     * @param idBigTask  ( id của task cha)
     * @param idUser     ( id của User thao tác hiện tại)
     * @param file       ( đường dẫn file trong máy tính)
     * @throws ParseException
     */
    public void createTaskLoopYear(Task taskWork, Integer numberLoop, Integer idBigTask, String idUser,
            MultipartFile file) throws ParseException {
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        SimpleDateFormat dateFmt = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Timestamp timeStampFormat = new Timestamp(date.getTime());
        String startDate = null;
        String endDate = null;
        String startDateFormat = formatString(taskWork.getStartTime());
        String endDateFormat = formatString(taskWork.getEndTime());

        for (int i = 0; i <= numberLoop; i++) {
            if (i != 0) {
                taskWork.setId(getId() + 1);

                taskWork.setBigTask(idBigTask);

                String dayOfYear = String.valueOf(startDateFormat.charAt(0))
                        + String.valueOf(startDateFormat.charAt(1));
                String monthOfYear = String.valueOf(startDateFormat.charAt(3))
                        + String.valueOf(startDateFormat.charAt(4));

                cal.setTime(dateFmt.parse(startDateFormat));
                cal.add(Calendar.DAY_OF_MONTH, i);
                String checkDay = String.valueOf(cal.get(Calendar.DATE));
                String checkMonth = String.valueOf(cal.get(Calendar.MONTH) + 1);
                if (checkDay.equals(dayOfYear) && checkMonth.equals(monthOfYear)) {
                    startDate = dateFmt.format(cal.getTime());
                    taskWork.setStartTime(formatString(startDate));

                    cal.setTime(dateFmt.parse(endDateFormat));
                    cal.add(Calendar.DAY_OF_MONTH, i);
                    endDate = dateFmt.format(cal.getTime());
                    taskWork.setEndTime(formatString(endDate));

                    taskWork.setCreateBy(idUser);
                    taskWork.setCreateAt(timeStampFormat);
                    taskWork.setUpdateBy(idUser);
                    taskWork.setUpdateAt(timeStampFormat);
                    taskWork.setStatus(1);
                    taskWork.setDelFlag(false);

                    String nameImage = uploadFile(file);
                    taskWork.setImage(nameImage);
                    taskService.addNew(taskWork);
                }

            } else {
                taskWork.setId(getId() + 1);

                taskWork.setBigTask(idBigTask);
                taskWork.setCreateBy(idUser);
                taskWork.setCreateAt(timeStampFormat);
                taskWork.setUpdateBy(idUser);
                taskWork.setUpdateAt(timeStampFormat);
                taskWork.setStatus(1);
                taskWork.setDelFlag(false);

                String nameImage = uploadFile(file);
                taskWork.setImage(nameImage);
                taskService.addNew(taskWork);
            }
        }
    }

    /**
     * createTaskLoopMonth
     *
     * @param taskWork   ( thông tin của đối tượng Task)
     * @param numberLoop (số ngày lặp)
     * @param idBigTask  ( id của task cha)
     * @param idUser     ( id của User thao tác hiện tại)
     * @param file       ( đường dẫn file trong máy tính)
     * @throws ParseException
     */
    public void createTaskLoopMonth(Task taskWork, Integer numberLoop, Integer idBigTask, String idUser,
            MultipartFile file) throws ParseException {
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        SimpleDateFormat dateFmt = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Timestamp timeStampFormat = new Timestamp(date.getTime());
        String startDate = null;
        String endDate = null;
        String startDateFormat = formatString(taskWork.getStartTime());
        String endDateFormat = formatString(taskWork.getEndTime());

        for (int i = 0; i <= numberLoop; i++) {
            if (i != 0) {

                taskWork.setId(getId() + 1);

                taskWork.setBigTask(idBigTask);

                String dayOfMonth = String.valueOf(startDateFormat.charAt(0))
                        + String.valueOf(startDateFormat.charAt(1));

                cal.setTime(dateFmt.parse(startDateFormat));
                cal.add(Calendar.DAY_OF_MONTH, i);
                String checkDay = String.valueOf(cal.get(Calendar.DATE));
                if (checkDay.equals(dayOfMonth)) {
                    startDate = dateFmt.format(cal.getTime());
                    taskWork.setStartTime(formatString(startDate));

                    cal.setTime(dateFmt.parse(endDateFormat));
                    cal.add(Calendar.DAY_OF_MONTH, i);
                    endDate = dateFmt.format(cal.getTime());
                    taskWork.setEndTime(formatString(endDate));

                    taskWork.setCreateBy(idUser);
                    taskWork.setCreateAt(timeStampFormat);
                    taskWork.setUpdateBy(idUser);
                    taskWork.setUpdateAt(timeStampFormat);
                    taskWork.setStatus(1);
                    taskWork.setDelFlag(false);

                    String nameImage = uploadFile(file);
                    taskWork.setImage(nameImage);

                    taskService.addNew(taskWork);
                }

            } else {
                taskWork.setId(getId() + 1);

                taskWork.setBigTask(idBigTask);
                taskWork.setCreateBy(idUser);
                taskWork.setCreateAt(timeStampFormat);
                taskWork.setUpdateBy(idUser);
                taskWork.setUpdateAt(timeStampFormat);
                taskWork.setStatus(1);
                taskWork.setDelFlag(false);

                String nameImage = uploadFile(file);
                taskWork.setImage(nameImage);
                taskService.addNew(taskWork);
            }

        }
    }

    /**
     * createTaskLoopWeek
     *
     * @param taskWork   ( thông tin của đối tượng Task)
     * @param numberLoop (số ngày lặp)
     * @param idBigTask  ( id của task cha)
     * @param idUser     ( id của User thao tác hiện tại)
     * @param file       ( đường dẫn file trong máy tính)
     * @throws ParseException
     */
    public void createTaskLoopWeek(Task taskWork, Integer numberLoop, Integer idBigTask, String idUser,
            MultipartFile file) throws ParseException {
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        SimpleDateFormat dateFmt = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Timestamp timeStampFormat = new Timestamp(date.getTime());
        String startDate = null;
        String endDate = null;
        String startDateFormat = formatString(taskWork.getStartTime());
        String endDateFormat = formatString(taskWork.getEndTime());

        for (int i = 0; i <= numberLoop; i++) {

            if (i != 0) {
                taskWork.setId(getId() + 1);

                taskWork.setBigTask(idBigTask);
                String dayOfWeek = taskWork.getLoopWeek();
                int dayOfWeekLength = taskWork.getLoopWeek().length();

                for (int j = 1; j < dayOfWeekLength; j++) {
                    cal.setTime(dateFmt.parse(startDateFormat));
                    cal.add(Calendar.DAY_OF_MONTH, i);
                    String checkDay = String.valueOf(cal.get(Calendar.DAY_OF_WEEK));
                    String indexDay = String.valueOf(dayOfWeek.charAt(j));
                    if (indexDay.equals(checkDay)) {
                        startDate = dateFmt.format(cal.getTime());
                        taskWork.setStartTime(formatString(startDate));

                        cal.setTime(dateFmt.parse(endDateFormat));
                        cal.add(Calendar.DAY_OF_MONTH, i);
                        endDate = dateFmt.format(cal.getTime());
                        taskWork.setEndTime(formatString(endDate));

                        taskWork.setCreateBy(idUser);
                        taskWork.setCreateAt(timeStampFormat);
                        taskWork.setUpdateBy(idUser);
                        taskWork.setUpdateAt(timeStampFormat);
                        taskWork.setStatus(1);
                        taskWork.setDelFlag(false);

                        String nameImage = uploadFile(file);
                        taskWork.setImage(nameImage);
                        taskService.addNew(taskWork);

                    }
                }
            } else {
                taskWork.setId(getId() + 1);

                taskWork.setBigTask(idBigTask);
                taskWork.setCreateBy(idUser);
                taskWork.setCreateAt(timeStampFormat);
                taskWork.setUpdateBy(idUser);
                taskWork.setUpdateAt(timeStampFormat);
                taskWork.setStatus(1);
                taskWork.setDelFlag(false);

                String nameImage = uploadFile(file);
                taskWork.setImage(nameImage);
                taskService.addNew(taskWork);
            }
        }
    }

    /**
     * createTaskLoopDay
     *
     * @param taskWork   ( thông tin của đối tượng Task)
     * @param numberLoop (số ngày lặp)
     * @param idBigTask  ( id của task cha)
     * @param idUser     ( id của User thao tác hiện tại)
     * @param file       ( đường dẫn file trong máy tính)
     * @throws ParseException
     */
    public void createTaskLoopDay(Task taskWork, Integer numberLoop, Integer idBigTask, String idUser,
            MultipartFile file) throws ParseException {
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        SimpleDateFormat dateFmt = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Timestamp timeStampFormat = new Timestamp(date.getTime());
        String startDate = null;
        String endDate = null;
        String startDateFormat = formatString(taskWork.getStartTime());
        String endDateFormat = formatString(taskWork.getEndTime());

        for (int i = 0; i <= numberLoop; i++) {
            taskWork.setId(getId() + 1);
            taskWork.setBigTask(idBigTask);
            if (i != 0) {
                cal.setTime(dateFmt.parse(startDateFormat));
                cal.add(Calendar.DAY_OF_MONTH, i);
                startDate = dateFmt.format(cal.getTime());
                taskWork.setStartTime(formatString(startDate));

                cal.setTime(dateFmt.parse(endDateFormat));
                cal.add(Calendar.DAY_OF_MONTH, i);
                endDate = dateFmt.format(cal.getTime());
                taskWork.setEndTime(formatString(endDate));

            }

            taskWork.setCreateBy(idUser);
            taskWork.setCreateAt(timeStampFormat);
            taskWork.setUpdateBy(idUser);
            taskWork.setUpdateAt(timeStampFormat);
            taskWork.setStatus(1);
            taskWork.setDelFlag(false);

            String nameImage = uploadFile(file);
            taskWork.setImage(nameImage);
            taskService.addNew(taskWork);

        }
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

    /**
     * getId
     *
     * @return id
     */
    public int getId() {
        List<Task> getAllTask = taskService.getAllTask();
        int recordsTask = taskService.getRecordsTotal();
        Task taskCheck = getAllTask.get(recordsTask - 1);
        int id = taskCheck.getId();
        return id;
    }

    /**
     * countDayLoop
     *
     * @param startTime   (thời gian bắt đầu task)
     * @param endTimeLoop ( thời gian kết thúc task)
     * @return countDayLoop (số ngày giữa 2 khoảng thời gian bắt đầu và kết thúc
     *         task)
     * @throws ParseException
     */
    // tính số ngày lặp khi chọn lặp theo ngày
    public int countDayLoop(String startTime, String endTimeLoop) throws ParseException {
        DateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formatStartTime = String.valueOf(startTime.charAt(8)) + String.valueOf(startTime.charAt(9))
                + String.valueOf(startTime.charAt(7)) + String.valueOf(startTime.charAt(5))
                + String.valueOf(startTime.charAt(6)) + String.valueOf(startTime.charAt(7))
                + String.valueOf(startTime.charAt(0)) + String.valueOf(startTime.charAt(1))
                + String.valueOf(startTime.charAt(2)) + String.valueOf(startTime.charAt(3));
        String formatEndLoopTime = String.valueOf(endTimeLoop.charAt(8)) + String.valueOf(endTimeLoop.charAt(9))
                + String.valueOf(endTimeLoop.charAt(7)) + String.valueOf(endTimeLoop.charAt(5))
                + String.valueOf(endTimeLoop.charAt(6)) + String.valueOf(endTimeLoop.charAt(7))
                + String.valueOf(endTimeLoop.charAt(0)) + String.valueOf(endTimeLoop.charAt(1))
                + String.valueOf(endTimeLoop.charAt(2)) + String.valueOf(endTimeLoop.charAt(3));
        Date date1 = null;
        Date date2 = null;

        date1 = simpleDateFormat.parse(formatStartTime);
        date2 = simpleDateFormat.parse(formatEndLoopTime);

        long getDiff = date2.getTime() - date1.getTime();

        long getDaysDiff = getDiff / (24 * 60 * 60 * 1000);

        int countDayLoop = (int) getDaysDiff;

        return countDayLoop;

    }

    /**
     * checkTime
     *
     * @param startHour ( giờ bắt đầu task)
     * @param endHour   ( giờ kết thúc task)
     * @param dateTask  (ngày của task)
     * @return result ( kiểm tra task có trong thời gian hoạt động hay không)
     * @throws ParseException
     */
    // kiểm tra task đang trong thời gian hoạt động hay k
    public boolean checkTime(String startHour, String endHour, String dateTask) throws ParseException {
        boolean bltime = false;
        boolean bldate = false;
        boolean result = false;
        Date date = new Date();
        String dateFormat = Utils.formatDateSort(date);

        if (dateTask.equals(dateFormat)) {
            bldate = true;
        } else {
            bldate = false;
        }
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm:ss a");

        Date start = hourFormat.parse(startHour);
        Date end = hourFormat.parse(endHour);
        Date now = new Date(System.currentTimeMillis());
        String nowHourStr = hourFormat.format(now.getTime());

        try {
            Date nowHour = hourFormat.parse(nowHourStr);
            if (nowHour.after(start) && nowHour.before(end) || (nowHour.equals(start) || (nowHour.equals(end)))) {
                bltime = true;
            }

        } catch (ParseException e) {
            bltime = false;
        }

        if (bldate && bltime) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    /**
     * getMaDuAn
     *
     * @param model (dùng để truyền dữ liệu sang view)
     * @param id    ( id tự tăng)
     * @return view: calendar/viewtas
     */
    @RequestMapping("/getdataform/{id}")
    public String getMaDuAn(Model model, @PathVariable("id") Integer id) {
        List<UserCase> listUser = userService.listUserByStatus(1);
        model.addAttribute("listuser", listUser);
        Task task = taskService.getTaskById(id);
        String startTime = task.getStartTime().replace(" ", "T");
        String endTime = task.getEndTime().replace(" ", "T");
        task.setStartTime(startTime);
        task.setEndTime(endTime);
        model.addAttribute("task", task);
        return "calendar/viewtask";
    }
}
